/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {addDays} from 'date-fns';
import useSWR from 'swr';

import SearchBuilder from '../../../core/SearchBuilder';
import {ORDER_STATUS, ORDER_TYPES} from '../../../enums/Order';
import useMarketplaceSpringBootOAuth2 from '../../../hooks/useMarketplaceSpringBootOAuth2';
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

const useTrialMetrics = (param: FilterType) => {
	const marketplaceSpringBootOAuth2 = useMarketplaceSpringBootOAuth2();

	const beforeLastPeriod = addDays(
		new Date(),
		-METRIC_PARAMETER[param as keyof typeof METRIC_PARAMETER] * 2
	);

	const lastPeriod = addDays(
		new Date(),
		-METRIC_PARAMETER[param as keyof typeof METRIC_PARAMETER]
	);

	beforeLastPeriod.setHours(0, 0, 0);
	lastPeriod.setHours(23, 59, 59);

	const requestsParams = [
		new URLSearchParams({
			fields:
				'id,account,orderStatusInfo,createDate,customFields,name,accountId',
			filter: new SearchBuilder()
				.eq('orderTypeExternalReferenceCode', ORDER_TYPES.SOLUTIONS7)
				.build(),
			nestedFields: 'account,orderItems',
			pageSize: '15',
			sort: 'createDate:desc',
		}),
		new URLSearchParams({
			fields: 'id,orderStatus,customFields',
			filter: new SearchBuilder()
				.gt('createDate', lastPeriod.toISOString())
				.and()
				.eq('orderTypeExternalReferenceCode', ORDER_TYPES.SOLUTIONS7)
				.build(),
			pageSize: '-1',
			sort: 'createDate:desc',
		}),
		new URLSearchParams({
			fields: 'orderStatus,customFields',
			filter: new SearchBuilder()
				.eq('orderTypeExternalReferenceCode', ORDER_TYPES.SOLUTIONS7)
				.and()
				.lt('createDate', lastPeriod.toISOString())
				.and()
				.gt('createDate', beforeLastPeriod.toISOString())
				.build(),
			nestedFields: 'account,orderItems',
			pageSize: '-1',
			sort: 'createDate:desc',
		}),
		new URLSearchParams({
			fields: 'orderStatus',
			filter: new SearchBuilder()
				.eq('orderTypeExternalReferenceCode', ORDER_TYPES.SOLUTIONS7)
				.build(),
			pageSize: '-1',
		}),
	];

	const {data: trialDataResponse = [], error, isLoading} = useSWR<any>(
		'administrator-dashboard/metrics/trial',
		() =>
			Promise.all([
				marketplaceSpringBootOAuth2.getTrialAvailability(),
				...requestsParams.map((searchParam) =>
					HeadlessCommerceAdminOrderImpl.getOrders(searchParam)
				),
			])
	);

	const [
		availabilityResponse,
		orderTableData,
		orderLastPeriod,
		orderBeforeLastPeriod,
		ordersTrial,
	] = trialDataResponse;

	const getExiredQuantity = (orderLastPeriod: Order[]) =>
		orderLastPeriod?.filter(
			(order: Order) =>
				new Date() >
				new Date(order?.customFields?.['trial-end-date'] as string)
		).length;

	const resourcesAvailable = `${
		availabilityResponse?.max - availabilityResponse?.available
	} / ${availabilityResponse?.max}`;

	const onHold = ordersTrial?.items?.filter(
		(order: Order) => order.orderStatus === ORDER_STATUS.ON_HOLD
	).length;

	const expiredTrialsLastPeriod = getExiredQuantity(orderLastPeriod?.items);

	const expiredTrialsBeforeLastPeriod = getExiredQuantity(
		orderBeforeLastPeriod?.items
	);

	const getPeriodMetrics = (
		lastPeriodValue: number,
		beforeLastPeriodvalue: number
	) => {
		const newOrders = lastPeriodValue - beforeLastPeriodvalue;

		let growth = Number(((newOrders / lastPeriodValue) * 100).toFixed(3));

		if (Number.isNaN(growth)) {
			growth = 0;
		}

		return {
			beforeLastPeriod: beforeLastPeriodvalue,
			growth,
			lastPeriod: lastPeriodValue,
			totalCount: lastPeriodValue,
		};
	};

	return {
		availability: {
			...availabilityResponse,
			onHold,
			resourcesAvailable,
		},
		error,
		expired: getPeriodMetrics(
			expiredTrialsLastPeriod,
			expiredTrialsBeforeLastPeriod
		),
		isLoading,
		orderTableData,
		orders: getPeriodMetrics(
			orderLastPeriod?.totalCount,
			orderBeforeLastPeriod?.totalCount
		),
	};
};

export default useTrialMetrics;
