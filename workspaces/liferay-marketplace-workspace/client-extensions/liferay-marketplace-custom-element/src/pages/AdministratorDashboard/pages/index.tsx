/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import { useMemo } from 'react';

import ErrorBoundary from '../../../components/ErrorBoundary';
import Page from '../../../components/Page';
import i18n from '../../../i18n';
import InfoCard from '../components/InfoCard';
import DonutKPIChart from '../components/charts/DonutKPIChart';
import useAccountsMetrics from '../hooks/useAccountsMetrics';
import useAnalyticsViewsMetrics from '../hooks/useAnalyticsViewsMetrics';
import useKPI from '../hooks/useKPI';
import useOrderMetrics from '../hooks/useOrderMetrics';
import { AdministratorOrdersListView } from './Orders';
import { AdministratorAppsListView } from './Apps/AdministratorAppsListView';

const getTotalAmountCurrency = (amount = 0) =>
	new Intl.NumberFormat('en-US', {
		currency: 'USD',
		style: 'currency',
	}).format(amount);

export default function AdministratorSummary() {
	const { data: accounts } = useAccountsMetrics('week');
	const { visitorsMetric } = useAnalyticsViewsMetrics();
	const { data: orderMetrics } = useOrderMetrics('week');

	const infoCard = useMemo(
		() => [
			{
				growth: accounts?.growth ?? 0,
				growthContext: `+${accounts?.lastPeriod ?? 0} this week `,
				symbol: 'users',
				title: i18n.translate('accounts'),
				value: accounts?.totalCount ?? 0,
			},
			{
				symbol: 'dollar-symbol',
				title: (
					<span>
						{i18n.translate('income')}{' '}
						&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;
					</span>
				),
				value: getTotalAmountCurrency(orderMetrics?.paidAmount),
			},
			{
				growth: orderMetrics?.growth ?? 0,
				growthContext: `+${orderMetrics?.lastPeriod ?? 0} this week `,
				symbol: 'shopping-cart',
				title: i18n.translate('orders'),
				value: orderMetrics?.totalCount ?? 0,
			},
			{
				symbol: 'analytics',
				title: 'Site Visitors',
				value: visitorsMetric ?? 0,
			},
		],
		[
			accounts?.growth,
			accounts?.lastPeriod,
			accounts?.totalCount,
			orderMetrics?.growth,
			orderMetrics?.lastPeriod,
			orderMetrics?.paidAmount,
			orderMetrics?.totalCount,
			visitorsMetric,
		]
	);

	const { data } = useKPI();

	return (
		<Page
			description={i18n.translate(
				'a-sleek-and-intuitive-admin-dashboard-for-monitoring-key-metrics'
			)}
			title={i18n.translate('admin-dashboard')}
		>
			<div className="d-flex flex-column">
				<div className="d-flex flex-wrap mb-4" style={{ gap: '20px' }}>
					<ErrorBoundary className="ml-5">
						{data?.map((chart, index) => (
							<DonutKPIChart
								chartData={chart}
								key={index}
							/>
						))}
					</ErrorBoundary>
				</div>

				<div className="d-flex flex-wrap info-container mb-8">
					{infoCard.map((infoItem, index) => (
						<InfoCard
							growth={infoItem.growth}
							growthContext={infoItem.growthContext}
							key={index}
							symbol={infoItem.symbol}
							title={infoItem.title as string}
							value={infoItem.value as string}
						/>
					))}
				</div>

				<Page
					pageRendererProps={{
						className: 'border py-2 rounded-lg mb-8',
					}}
					title={i18n.translate('recent-orders')}
				>
					<AdministratorOrdersListView
						listViewProps={{
							id: 'summary-orders',
							initialContext: { pageSize: 5 },
							paginationOptions: { displayType: 'never' },
						}}
					/>
				</Page>

				<Page
					pageRendererProps={{ className: 'border py-2 rounded-lg' }}
					title={i18n.translate('published-apps')}
				>
					<AdministratorAppsListView
						listViewProps={{
							id: 'summary-apps',
							initialContext: { pageSize: 5 },
							paginationOptions: { displayType: 'never' },
						}}
					/>
				</Page>
			</div>
		</Page>
	);
}
