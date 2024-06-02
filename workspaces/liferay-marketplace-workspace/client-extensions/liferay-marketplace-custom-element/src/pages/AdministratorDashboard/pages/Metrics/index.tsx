/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayChart from '@clayui/charts';
import {useMemo} from 'react';
import useSWR from 'swr';

import ErrorBoundary from '../../../../components/ErrorBoundary';
import SearchBuilder from '../../../../core/SearchBuilder';
import {ORDER_TYPES} from '../../../../enums/Order';
import i18n from '../../../../i18n';
import HeadlessCommerceAdminOrderImpl from '../../../../services/rest/HeadlessCommerceAdminOrder';
import InfoCard from '../../components/InfoCard';
import useAccountsMetrics from '../../hooks/useAccountsMetrics';
import useAnalyticsViewsMetrics from '../../hooks/useAnalyticsViewsMetrics';
import useOrderMetrics, {
	useOrderChartLineMetrics,
} from '../../hooks/useOrderMetrics';
import {colors} from '../../mock';
import OrdersTable from './OrdersTab';

const getTotalAmountCurrency = (amount = 0) =>
	new Intl.NumberFormat('en-US', {
		currency: 'USD',
		style: 'currency',
	}).format(amount);

const Metrics = () => {
	const {data: accounts} = useAccountsMetrics('week');
	const {
		data: analytics,
		isLoading: analyticsLoading,
		visitorsMetric,
	} = useAnalyticsViewsMetrics();
	const {data: orderChartLine} = useOrderChartLineMetrics();
	const {data: orderMetrics} = useOrderMetrics('week');

	const {metrics = []} = orderChartLine || {};

	const {data: orders} = useSWR<APIResponse<Order>>(
		'administrator-dashboard/orders',
		() =>
			HeadlessCommerceAdminOrderImpl.getOrders(
				new URLSearchParams({
					filter: SearchBuilder.in('orderTypeExternalReferenceCode', [
						ORDER_TYPES.CLOUDAPP,
						ORDER_TYPES.DXPAPP,
					]),
					nestedFields: 'account,orderItems',
					pageSize: '30',
					sort: 'createDate:desc',
				})
			)
	);

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

	return (
		<div className="d-flex flex-column">
			<div className="d-flex flex-wrap info-container mb-4">
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

			<div className="d-flex flex-column metrics-container">
				<div className="p-4 row">
					<div className="col-md-8 p-0">
						<span className="font-weight-bold">Orders p/week</span>

						{!!metrics.length && (
							<div className="mt-4">
								<ClayChart
									axis={{
										type: 'area-spline',
										x: {
											categories:
												metrics[0]?.weekDays ?? [],
											type: 'category',
										},
									}}
									data={{
										colors: {
											['Last 7 days']: colors.color1,
											['Previous Week']: colors.color2,
										},
										columns: [
											[
												'Last 7 days',
												...(metrics[0]?.dates ?? []),
											],
											[
												'Previous Week',
												...(metrics[1]?.dates ?? []),
											],
										],
										groups: [
											['Last 7 days', 'Previous Week'],
										],
										types: {
											['Last 7 days']: 'area-spline',
											['Previous Week']: 'area-spline',
										},
									}}
								/>
							</div>
						)}
					</div>

					<div className="col-md-4 p-0">
						<span className="font-weight-bold ml-5">
							Most visited product pages
						</span>

						<div className="mt-4">
							<ErrorBoundary className="ml-5">
								{!analyticsLoading &&
									analytics?.columns[0].length > 1 && (
										<ClayChart
											axis={{
												x: {
													type: 'category',
												},
											}}
											bar={{
												padding: 1,
												radius: {
													ratio: 0.2,
												},
												width: {
													max: 25,
												},
											}}
											data={{
												colors: analytics.colors,
												columns: analytics.columns,
												type: 'bar',
												x: 'x',
											}}
											grid={{
												lines: {
													front: false,
												},
												x: {
													show: false,
												},
												y: {
													show: false,
												},
											}}
											legend={{show: false}}
										/>
									)}
							</ErrorBoundary>
						</div>
					</div>
				</div>

				<div className="border d-flex flex-column justify-content-center p-6 rounded-lg">
					<OrdersTable items={orders?.items || []} />
				</div>
			</div>
		</div>
	);
};

export default Metrics;
