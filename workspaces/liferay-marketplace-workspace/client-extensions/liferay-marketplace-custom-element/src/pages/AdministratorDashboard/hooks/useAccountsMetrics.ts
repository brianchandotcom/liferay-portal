import useSWR from 'swr';

import HeadlessAdminUserImpl from '../../../services/rest/HeadlessAdminUser';
import {addDays} from 'date-fns';
import SearchBuilder from '../../../core/SearchBuilder';

export const METRIC_PARAMETER = {
	week: 7,
	month: 30,
	q1: 1,
	q2: 2,
	q3: 3,
	q4: 4,
};

type useOrderMetricsProps = 'week' | 'month' | 'q1' | 'q2' | 'q3' | 'q4';

const useAccountsMetrics = (param: useOrderMetricsProps) => {
	const getAccountsMetrics = async () => {
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
					fields: 'id,',
					pageSize: '-1',
				}),
			},
			{
				searchParams: new URLSearchParams({
					fields: 'id,',
					filter: SearchBuilder.gt('dateCreated', lastPeriod),
				}),
			},
			{
				searchParams: new URLSearchParams({
					fields: 'id,',
					filter: new SearchBuilder()
						.gt('dateCreated', lastPeriod)
						.and()
						.lt('dateCreated', beforeLastPeriod)
						.build(),
				}),
			},
		];

		const response = await Promise.all(
			requestsParams.map((request) =>
				HeadlessAdminUserImpl.getAccounts(request.searchParams)
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

	return useSWR('metrics/accounts', getAccountsMetrics);
};

export default useAccountsMetrics;
