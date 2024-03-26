/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayChart from '@clayui/charts';
import {useMemo} from 'react';
import useSWR from 'swr';

import i18n from '../../../../i18n';
import HeadlessCommerceAdminOrderImpl from '../../../../services/rest/HeadlessCommerceAdminOrder';
import InfoCard from '../../components/InfoCard';
import useAccountsMetrics from '../../hooks/useAccountsMetrics';
import useOrderMetrics, {
	useOrderChartLineMetrics,
} from '../../hooks/useOrderMetrics';
import {barChart, colors} from '../../mock';
import OrdersTable from './OrdersTab';

const getTotalAmountCurrency = (amount = 0) =>
	new Intl.NumberFormat('en-US', {
		currency: 'USD',
		style: 'currency',
	}).format(amount);

const MetricsWWW = () => {
	const {data: accounts} = useAccountsMetrics('week');

	const {data: orderChartLine} = useOrderChartLineMetrics();
	const {data: orderMetrics} = useOrderMetrics('week');

	const {metrics = []} = orderChartLine || {};

	const {data: orders} = useSWR<APIResponse<Order>>(
		'administrator-dashboard/orders',
		() =>
			HeadlessCommerceAdminOrderImpl.getOrders(
				new URLSearchParams({
					nestedFields: 'account,orderItems',
					pageSize: '10',
				})
			)
	);

	const infoCard = useMemo(
		() => [
			{
				growth: accounts?.growth,
				growthContext: `+${accounts?.lastPeriod} last week `,
				symbol: 'users',
				title: i18n.translate('accounts'),
				value: accounts?.totalCount,
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
				growth: orderMetrics?.growth,
				growthContext: `+${orderMetrics?.lastPeriod} last week `,
				symbol: 'shopping-cart',
				title: i18n.translate('orders'),
				value: orderMetrics?.totalCount,
			},
			{
				growth: 68,
				growthContext: '+36k this week',
				symbol: 'thumbs-up-arrow',
				title: 'Unique Visitors',
				value: '249.194.46',
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
											['Current Week']: colors.color1,
											['Last Week']: colors.color2,
										},
										columns: [
											[
												'Current Week',
												...(metrics[0]?.dates ?? []),
											],
											[
												'Last Week',
												...(metrics[1]?.dates ?? []),
											],
										],
										groups: [['Current Week', 'Last Week']],
										types: {
											['Current Week']: 'area-spline',
											['Last Week']: 'area-line',
										},
									}}
								/>
							</div>
						)}
					</div>

					<div className="col-md-4 p-0">
						<span className="font-weight-bold ml-5">
							Most visited pages
						</span>

						<div className="mt-4">
							<ClayChart
								data={{
									colors: {
										data1: colors.color1,
										data2: colors.color2,
									},
									columns: barChart.columns,
									type: 'donut',
								}}
							/>
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

export default MetricsWWW;
