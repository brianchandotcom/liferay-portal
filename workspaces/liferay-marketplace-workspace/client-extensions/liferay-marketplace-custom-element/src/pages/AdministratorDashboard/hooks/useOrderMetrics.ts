/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {addDays, eachDayOfInterval, format} from 'date-fns';
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

type FilterType = 'month' | 'q1' | 'q2' | 'q3' | 'q4' | 'week';

const useOrderMetrics = (param: FilterType) => {
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
				fields: 'id,orderStatus,totalAmount',
				pageSize: '-1',
				sort: 'createDate:desc',
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

		const paidAppsAmount = response[0].items
			.filter(({orderStatus}) => orderStatus === 0)
			.map(({totalAmount}) => totalAmount ?? 0)
			.reduce((prevTotal, currentTotal) => prevTotal + currentTotal, 0);

		return {
			beforeLastPeriod: response[2].totalCount,
			growth: (response[1].totalCount - response[2].totalCount) * 100,
			lastPeriod: response[1].totalCount,
			paidAmount: paidAppsAmount,
			param,
			totalCount: response[0].totalCount,
		};
	};

	return useSWR('metrics/order', getOrderMetrics);
};

const useOrderChartLineMetrics = () => {
	const getOrderMetrics = async () => {
		const currentTime = new Date();

		const beforeLastPeriod = addDays(
			currentTime,
			-METRIC_PARAMETER['week'] * 2
		);

		const lastPeriod = addDays(currentTime, -METRIC_PARAMETER['week']);

		const requestsParams = [
			new URLSearchParams({
				fields: 'id,createDate',
				filter: SearchBuilder.gt(
					'createDate',
					lastPeriod.toISOString()
				),
			}),
			new URLSearchParams({
				fields: 'id,createDate',
				filter: new SearchBuilder()
					.gt('createDate', lastPeriod.toISOString())
					.and()
					.lt('createDate', beforeLastPeriod.toISOString())
					.build(),
			}),
		];

		const lastPeriodDays = eachDayOfInterval({
			end: new Date(),
			start: lastPeriod,
		});

		const beforeLastPeriodDays = eachDayOfInterval({
			end: lastPeriod,
			start: beforeLastPeriod,
		});

		const daysInterval = [lastPeriodDays, beforeLastPeriodDays];

		const response = await Promise.all(
			requestsParams.map((searchParam) =>
				HeadlessCommerceAdminOrderImpl.getOrders(searchParam)
			)
		);

		const metrics = response.map(({items}, index) => {
			const dates = (daysInterval[index] as unknown) as Date[];

			return {
				dates: dates.map(
					(date) =>
						items.filter(
							(item) =>
								date.getDate() ===
								new Date(item.createDate).getDate()
						).length
				),
				weekDays: dates.map((date) => format(date, 'eeee')),
			};
		});

		return {metrics, response};
	};

	return useSWR('metrics/order/chartline', getOrderMetrics);
};

export {useOrderChartLineMetrics};

export default useOrderMetrics;
