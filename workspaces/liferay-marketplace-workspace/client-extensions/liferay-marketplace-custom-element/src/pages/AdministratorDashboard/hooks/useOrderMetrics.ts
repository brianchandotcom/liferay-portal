/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {addDays} from 'date-fns';
import useSWR from 'swr';

import SearchBuilder from '../../../core/SearchBuilder';
import HeadlessCommerceAdminOrderImpl from '../../../services/rest/HeadlessCommerceAdminOrder';

export const METRIC_PARAMETER = {
	month: 30,
	q1: 1,
	q2: 2,
	q3: 3,
	q4: 4,
	week: 7,
};

type useOrderMetricsProps = 'month' | 'q1' | 'q2' | 'q3' | 'q4' | 'week';

const useOrderMetrics = (param: useOrderMetricsProps) => {
	const getOrderMetrics = async () => {
		const currentTime = new Date();

		const beforeLastPeriod = addDays(
			currentTime,
			-METRIC_PARAMETER[param as keyof typeof METRIC_PARAMETER] * 2
		).toISOString();

		const lastPeriod = addDays(
			currentTime,
			-METRIC_PARAMETER[param as keyof typeof METRIC_PARAMETER]
		).toISOString();

		const requestsParams = [
			new URLSearchParams({
				fields: 'id,totalAmount',
				pageSize: '-1',
			}),
			new URLSearchParams({
				fields: 'id,totalAmount',
				filter: SearchBuilder.gt('createDate', lastPeriod),
			}),
			new URLSearchParams({
				fields: 'id,totalAmount',
				filter: new SearchBuilder()
					.gt('createDate', lastPeriod)
					.and()
					.lt('createDate', beforeLastPeriod)
					.build(),
			}),
		];

		const response = await Promise.all(
			requestsParams.map((searchParam) =>
				HeadlessCommerceAdminOrderImpl.getOrders(searchParam)
			)
		);

		return {
			beforeLastPeriod: response[2].totalCount,
			growth: (response[1].totalCount - response[2].totalCount) * 100,
			lastPeriod: response[1].totalCount,
			param,
			totalCount: response[0].totalCount,
		};
	};

	return useSWR('metrics/order', getOrderMetrics);
};

export default useOrderMetrics;
