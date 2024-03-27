/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {addDays} from 'date-fns';
import useSWR from 'swr';

import SearchBuilder from '../../../core/SearchBuilder';
import HeadlessAdminUserImpl from '../../../services/rest/HeadlessAdminUser';

type UseOrderMetricsProps = 'month' | 'q1' | 'q2' | 'q3' | 'q4' | 'week';

export const METRIC_PARAMETER = {
	month: 30,
	q1: 1,
	q2: 2,
	q3: 3,
	q4: 4,
	week: 7,
};

const useAccountsMetrics = (param: UseOrderMetricsProps) => {
	const getAccountsMetrics = async () => {
		const currentTime = new Date();

		const beforeLastPeriod = addDays(
			currentTime,
			-METRIC_PARAMETER[param as keyof typeof METRIC_PARAMETER] * 2
		);

		const lastPeriod = addDays(
			currentTime,
			-METRIC_PARAMETER[param as keyof typeof METRIC_PARAMETER]
		);

		beforeLastPeriod.setHours(0, 0, 0);
		lastPeriod.setHours(23, 59, 59);

		const requestsParams = [
			new URLSearchParams({
				fields: 'id',
				pageSize: '1',
			}),
			new URLSearchParams({
				fields: 'id',
				filter: SearchBuilder.gt(
					'dateCreated',
					lastPeriod.toISOString()
				),
				pageSize: '1',
			}),
			new URLSearchParams({
				fields: 'id',
				filter: new SearchBuilder()
					.lt('dateCreated', lastPeriod.toISOString())
					.and()
					.gt('dateCreated', beforeLastPeriod.toISOString())
					.build(),
				pageSize: '1',
			}),
		];

		const response = await Promise.all(
			requestsParams.map((searchParam) =>
				HeadlessAdminUserImpl.getAccounts(searchParam)
			)
		);

		const newAccounts = response[1].totalCount - response[2].totalCount;

		return {
			beforeLastPeriod: response[2].totalCount,
			growth: Number(
				((newAccounts / response[1].totalCount) * 100).toFixed(2)
			),
			lastPeriod: response[1].totalCount,
			param,
			totalCount: response[0].totalCount,
		};
	};

	return useSWR('metrics/accounts', getAccountsMetrics);
};

export default useAccountsMetrics;
