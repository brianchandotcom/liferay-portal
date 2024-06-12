/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {addDays, eachDayOfInterval, format} from 'date-fns';
import useSWR from 'swr';

import SearchBuilder from '../../../core/SearchBuilder';
import {ORDER_TYPES} from '../../../enums/Order';
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

export const orderSearchBuilder = new SearchBuilder()
	.in('orderTypeExternalReferenceCode', [
		ORDER_TYPES.CLOUDAPP,
		ORDER_TYPES.DXPAPP,
	])
	.and();

const useOrderMetrics = (param: FilterType) => {
	return useSWR('metrics/order', async () => {
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
				fields: 'id,orderStatus,totalAmount',
				filter: orderSearchBuilder.clone().build(),
				pageSize: '-1',
				sort: 'createDate:desc',
			}),
			new URLSearchParams({
				fields: 'id',
				filter: orderSearchBuilder
					.clone()
					.gt('createDate', lastPeriod.toISOString())
					.build(),
				pageSize: '1',
			}),
			new URLSearchParams({
				fields: 'id',
				filter: orderSearchBuilder
					.clone()
					.lt('createDate', lastPeriod.toISOString())
					.and()
					.gt('createDate', beforeLastPeriod.toISOString())
					.build(),
				pageSize: '1',
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

		const newOrders = response[1].totalCount - response[2].totalCount;

		let growth = Number(
			((newOrders / response[1].totalCount) * 100).toFixed(2)
		);

		if (Number.isNaN(growth)) {
			growth = 0;
		}

		return {
			beforeLastPeriod: response[2].totalCount,
			growth,
			lastPeriod: response[1].totalCount,
			paidAmount: paidAppsAmount,
			param,
			totalCount: response[0].totalCount,
		};
	});
};

const useOrderChartLineMetrics = () => {
	return useSWR('metrics/order/chartline', async () => {
		const currentTime = new Date();

		const beforeLastPeriod = addDays(
			currentTime,
			-METRIC_PARAMETER['week'] * 2
		);

		const lastPeriod = addDays(currentTime, -METRIC_PARAMETER['week']);

		beforeLastPeriod.setHours(0, 0, 0);
		lastPeriod.setHours(23, 59, 59);

		const requestsParams = [
			new URLSearchParams({
				fields: 'id,createDate',
				filter: orderSearchBuilder
					.clone()
					.gt('createDate', lastPeriod.toISOString())
					.build(),
				pageSize: '-1',
			}),
			new URLSearchParams({
				fields: 'id,createDate',
				filter: orderSearchBuilder
					.clone()
					.gt('createDate', beforeLastPeriod.toISOString())
					.and()
					.lt('createDate', lastPeriod.toISOString())
					.build(),
				pageSize: '-1',
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
			const dates = daysInterval[index] as unknown as Date[];

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
	});
};

export {useOrderChartLineMetrics};

export default useOrderMetrics;
