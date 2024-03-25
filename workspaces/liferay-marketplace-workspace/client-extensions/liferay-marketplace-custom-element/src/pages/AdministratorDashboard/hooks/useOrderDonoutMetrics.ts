import useSWR from 'swr';
import {
	startOfQuarter,
	endOfQuarter,
	setQuarter,
	format,
	addDays,
} from 'date-fns';
import SearchBuilder from '../../../core/SearchBuilder';
import HeadlessCommerceAdminOrderImpl from '../../../services/rest/HeadlessCommerceAdminOrder';

export const METRIC_PARAMETER = {
	week: 7,
	month: 30,
	q1: 1,
	q2: 2,
	q3: 3,
	q4: 4,
};

type FilterType = 'week' | 'month' | 'q1' | 'q2' | 'q3' | 'q4';

const useOrderAmountMetrics = (param: FilterType) => {
	function getQuarterDates(year: number, quarter: number) {
		let baseDate = new Date(year, 0, 1);

		baseDate = setQuarter(baseDate, quarter);

		const startDate = startOfQuarter(baseDate);
		const endDate = endOfQuarter(baseDate);

		return {startDate, endDate};
	}

	const getOrderDonoutMetrics = async () => {
		const currentTime = new Date();
		const currentYear = format(currentTime, 'yyyy');
		const {startDate, endDate} = getQuarterDates(
			Number(currentYear),
			METRIC_PARAMETER[param as keyof typeof METRIC_PARAMETER]
		);

		let lastPeriod = startDate.toISOString();
		let beforeLastPeriod = endDate.toISOString();

		if (['week', 'month'].some((parameter) => parameter === param)) {
			lastPeriod = addDays(
				currentTime,
				-METRIC_PARAMETER[param as keyof typeof METRIC_PARAMETER]
			).toISOString();

			beforeLastPeriod = addDays(
				currentTime,
				-METRIC_PARAMETER[param as keyof typeof METRIC_PARAMETER] * 2
			).toISOString();
		}

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
			beforeLastPeriod: response[2].totalCount,
			growth: (response[1].totalCount - response[2].totalCount) * 100,
			lastPeriod: response[1].totalCount,
			totalCount: response[0].totalCount,
		};
	};

	return useSWR(`metrics/donutOrder/${param}`, getOrderDonoutMetrics);
};

export default useOrderAmountMetrics;
