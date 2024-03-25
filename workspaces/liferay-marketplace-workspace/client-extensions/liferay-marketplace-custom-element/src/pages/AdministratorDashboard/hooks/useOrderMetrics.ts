import useSWR from 'swr';
import SearchBuilder from '../../../core/SearchBuilder';
import HeadlessCommerceAdminOrderImpl from '../../../services/rest/HeadlessCommerceAdminOrder';
import {addDays} from 'date-fns';

export const METRIC_PARAMETER = {
	week: 7,
	month: 30,
	q1: 1,
	q2: 2,
	q3: 3,
	q4: 4,
};
type useOrderMetricsProps = 'week' | 'month' | 'q1' | 'q2' | 'q3' | 'q4';

const useOrderMetrics = (param: useOrderMetricsProps) => {
	const getOrderMetrics = async () => {
		const currentTime = new Date();

		const lastPeriod = addDays(
			currentTime,
			-METRIC_PARAMETER[param as keyof typeof METRIC_PARAMETER]
		).toISOString();

		const beforeLastPeriod = addDays(
			currentTime,
			-METRIC_PARAMETER[param as keyof typeof METRIC_PARAMETER] * 2
		).toISOString();

		const requestsParams = [
			{
				searchParams: new URLSearchParams({
					fields: 'id,totalAmount',
					pageSize: '-1',
				}),
			},
			{
				searchParams: new URLSearchParams({
					fields: 'id,totalAmount',
					filter: SearchBuilder.gt('createDate', lastPeriod),
				}),
			},
			{
				searchParams: new URLSearchParams({
					fields: 'id,totalAmount',
					filter: new SearchBuilder()
						.gt('createDate', lastPeriod)
						.and()
						.lt('createDate', beforeLastPeriod)
						.build(),
				}),
			},
		];

		const response = await Promise.all(
			requestsParams.map((request) =>
				HeadlessCommerceAdminOrderImpl.getOrders(request.searchParams)
			)
		);

		return {
			param,
			totalCount: response[0].totalCount,
			lastPeriod: response[1].totalCount,
			beforeLastPeriod: response[2].totalCount,
			growth: (response[1].totalCount - response[2].totalCount) * 100,
		};
	};

	return useSWR('metrics/order', getOrderMetrics);
};

export default useOrderMetrics;
