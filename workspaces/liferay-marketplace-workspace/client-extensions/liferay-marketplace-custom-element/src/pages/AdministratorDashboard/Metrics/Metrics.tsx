import InfoCard from '../Components/InfoCard/InfoCard';
import {colors, barChart} from '../mock';

import ClayChart from '@clayui/charts';
import OrdersTable from './OrdersTab';
import useSWR from 'swr';

import HeadlessCommerceAdminOrderImpl from '../../../services/rest/HeadlessCommerceAdminOrder';
import useAccountsMetrics from '../hooks/useAccountsMetrics';
import useOrderMetrics from '../hooks/useOrderMetrics';
import useOrderDonoutMetrics from '../hooks/useOrderDonoutMetrics';
import {useState} from 'react';
import i18n from '../../../i18n';

type FilterType = 'week' | 'month' | 'q1' | 'q2' | 'q3' | 'q4';

const Metrics: React.FC<any> = () => {
	const [filterType, setFilterType] = useState<FilterType>('week');

	const {data: orderMetrics} = useOrderMetrics('week');
	const {data: accounts} = useAccountsMetrics('week');
	const {data: donout} = useOrderDonoutMetrics('week');

	const {data: orders} = useSWR<APIResponse<Order>>('orders', async () => {
		const data = await HeadlessCommerceAdminOrderImpl.getOrders(
			new URLSearchParams({
				pageSize: '10',
				nestedFields: 'orderItems,account',
			})
		);

		return data;
	});

	const infoCard = [
		{
			growth: donout?.growth,
			growthContext: `+${donout?.lastPeriod}, ${filterType} period `,
			symbol: 'dollar-symbol',
			title: i18n.translate('amount'),
			value: donout?.totalCount,
		},
		{
			growth: orderMetrics?.growth,
			growthContext: `+${orderMetrics?.lastPeriod} last `,
			symbol: 'shopping-cart',
			title: i18n.translate('orders'),
			value: orderMetrics?.totalCount,
		},
		{
			growth: accounts?.growth,
			growthContext: `+${accounts?.lastPeriod} last `,
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
			<div className="d-flex mb-4 flex-wrap info-container">
				{infoCard.map((infoItem, index) => {
					return (
						<InfoCard
							symbol={infoItem.symbol}
							key={index}
							title={infoItem.title}
							value={infoItem.value}
							growth={infoItem.growth}
							growthContext={infoItem.growthContext}
						/>
					);
				})}
			</div>

			<div className="d-flex flex-column metrics-container ">
				<div className="d-flex p-0 metrics-container flex-wrap ">
					<div className="d-flex metrics-card justify-content-center p-5 flex-column">
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
					<div className="d-flex metrics-card justify-content-center p-5 flex-column">
						<span className="font-weight-bold mb-3">Donut</span>

						<ClayChart
							data={{
								columns: barChart.columns,
								type: 'donut',
								colors: {
									data1: colors.color1,
									data2: colors.color2,
								},
							}}
						/>
					</div>
				</div>
				<div className="d-flex  justify-content-center p-6 flex-column border rounded-lg">
					<OrdersTable items={orders?.items || []} />
				</div>
			</div>
		</div>
	);
};

export default Metrics;
