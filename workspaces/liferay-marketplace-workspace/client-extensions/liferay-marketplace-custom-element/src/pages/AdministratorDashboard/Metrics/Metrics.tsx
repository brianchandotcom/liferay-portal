/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayChart from '@clayui/charts';
import useSWR from 'swr';

import i18n from '../../../i18n';
import HeadlessCommerceAdminOrderImpl from '../../../services/rest/HeadlessCommerceAdminOrder';
import InfoCard from '../Components/InfoCard/InfoCard';
import useAccountsMetrics from '../hooks/useAccountsMetrics';
import useOrderAmountMetrics from '../hooks/useOrderDonutMetrics';
import useOrderMetrics from '../hooks/useOrderMetrics';
import {barChart, colors} from '../mock';
import OrdersTable from './OrdersTab';

const Metrics = () => {
	const {data: orderMetrics} = useOrderMetrics('week');
	const {data: accounts} = useAccountsMetrics('week');
	const {data: amount} = useOrderAmountMetrics('week');

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

	const infoCard = [
		{
			growth: amount?.growth,
			growthContext: `+${amount?.lastPeriod}, last week `,
			symbol: 'dollar-symbol',
			title: i18n.translate('amount'),
			value: amount?.totalCount,
		},
		{
			growth: orderMetrics?.growth,
			growthContext: `+${orderMetrics?.lastPeriod} last week `,
			symbol: 'shopping-cart',
			title: i18n.translate('orders'),
			value: orderMetrics?.totalCount,
		},
		{
			growth: accounts?.growth,
			growthContext: `+${accounts?.lastPeriod} last week `,
			symbol: 'users',
			title: i18n.translate('account'),
			value: accounts?.totalCount,
		},
		{
			growth: 68,
			growthContext: '+36k this week',
			symbol: 'thumbs-up-arrow',
			title: 'Conversion Rate',
			value: '249.194.46',
		},
	];

	return (
		<div className="d-flex flex-column">
			<div className="d-flex flex-wrap info-container mb-4">
				{infoCard.map((infoItem, index) => (
					<InfoCard
						growth={infoItem.growth}
						growthContext={infoItem.growthContext}
						key={index}
						symbol={infoItem.symbol}
						title={infoItem.title}
						value={infoItem.value}
					/>
				))}
			</div>

			<div className="d-flex flex-column metrics-container">
				<div className="d-flex flex-wrap metrics-container p-0">
					<div className="d-flex flex-column justify-content-center metrics-card p-5">
						<span className="font-weight-bold mb-3">Lines</span>
						<ClayChart
							axis={{
								x: {
									type: 'timeseries',
								},
							}}
							data={{
								colors: {
									product1: colors.color1,
									product2: colors.color2,
								},
								columns: [
									[
										'x',
										'2018-01-01',
										'2018-02-01',
										'2018-03-01',
										'2018-04-01',
										'2018-05-01',
										'2018-06-01',
										'2018-07-01',
										'2018-08-01',
										'2018-09-01',
										'2018-10-01',
										'2018-11-01',
									],
									[
										'product1',
										130,
										340,
										200,
										100,
										40,
										300,
										{high: 240, low: 140, mid: 180},
										{high: 380, low: 300, mid: 350},
										{high: 480, low: 320, mid: 400},
										{high: 260, low: 100, mid: 200},
										{high: 140, low: 100, mid: 120},
									],
									[
										'product2',
										210,
										180,
										30,
										90,
										40,
										120,
										{high: 260, low: 180, mid: 240},
										{high: 460, low: 360, mid: 420},
										{high: 180, low: 80, mid: 120},
										{high: 120, low: 60, mid: 80},
										{high: 80, low: 10, mid: 20},
									],
								],
								type: 'predictive',
								types: {
									product1: 'area-line-range',
									product2: 'area-spline-range',
								},
								x: 'x',
							}}
							predictionDate="2018-06-01"
						/>
					</div>
					<div className="d-flex flex-column justify-content-center metrics-card p-5">
						<span className="font-weight-bold mb-3">Donut</span>

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
				<div className="border d-flex flex-column justify-content-center p-6 rounded-lg">
					<OrdersTable items={orders?.items || []} />
				</div>
			</div>
		</div>
	);
};

export default Metrics;
