/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {addDays} from 'date-fns';
import {useEffect, useMemo, useState} from 'react';
import useSWR from 'swr';

import SearchBuilder from '../../../core/SearchBuilder';
import {ORDER_TYPES, ORDER_WORKFLOW_STATUS_CODE} from '../../../enums/Order';
import trialOAuth2 from '../../../services/oauth/Trial';
import HeadlessCommerceAdminOrderImpl from '../../../services/rest/HeadlessCommerceAdminOrder';

type FilterType = 'month' | 'q1' | 'q2' | 'q3' | 'q4' | 'week';

export const METRIC_PARAMETER = {
	month: 30,
	q1: 1,
	q2: 2,
	q3: 3,
	q4: 4,
	week: 7,
};

const ACTIVE_REFRESH_INTERVAL = 60 * 1000;
const DEFAULT_REFRESH_INTERVAL = 240 * 1000;

const trialSearchBuilder = new SearchBuilder()
	.eq('orderTypeExternalReferenceCode', ORDER_TYPES.SOLUTIONS7)
	.and();

const useTrialMetrics = (param: FilterType) => {
	const [refreshInterval, setRefreshInterval] = useState(
		DEFAULT_REFRESH_INTERVAL
	);

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
			fields: 'account,accountId,createDate,customFields,id,name,orderItems,orderStatusInfo',
			filter: trialSearchBuilder.clone().build(),
			nestedFields: 'account,orderItems',
			pageSize: '60',
			sort: 'createDate:desc',
		}),
		new URLSearchParams({
			fields: 'orderStatus',
			filter: trialSearchBuilder
				.clone()
				.lambda('orderStatus', ORDER_WORKFLOW_STATUS_CODE.COMPLETED, {
					unquote: true,
				})
				.build(),
			pageSize: '1',
		}),
		new URLSearchParams({
			fields: 'orderStatus',
			filter: trialSearchBuilder
				.clone()
				.lambda('orderStatus', ORDER_WORKFLOW_STATUS_CODE.IN_PROGRESS, {
					unquote: true,
				})
				.build(),
			pageSize: '1',
		}),
		new URLSearchParams({
			fields: 'orderStatus',
			filter: trialSearchBuilder
				.clone()
				.lambda('orderStatus', ORDER_WORKFLOW_STATUS_CODE.ON_HOLD, {
					unquote: true,
				})
				.build(),
			pageSize: '1',
		}),
	];

	const {
		data: trialDataResponse = [],
		error,
		isLoading,
		mutate,
	} = useSWR<any>(
		'administrator-dashboard/metrics/trial',
		() =>
			Promise.all([
				trialOAuth2.getAvailability(),
				...requestsParams.map((searchParam) =>
					HeadlessCommerceAdminOrderImpl.getOrders(searchParam)
				),
			]),
		{refreshInterval}
	);

	const [
		availabilityResponse,
		orderTableData,
		expiredResponse,
		inProgressResponse,
		onHoldResponse,
	] = trialDataResponse;

	const orderItems = useMemo(
		() => orderTableData?.items ?? [],
		[orderTableData?.items]
	);

	useEffect(() => {
		const isProcessing = orderItems.some(({orderStatusInfo}: any) =>
			[
				ORDER_WORKFLOW_STATUS_CODE.PROCESSING,
				ORDER_WORKFLOW_STATUS_CODE.ON_HOLD,
			].includes(orderStatusInfo.code)
		);

		setRefreshInterval(
			isProcessing ? ACTIVE_REFRESH_INTERVAL : DEFAULT_REFRESH_INTERVAL
		);
	}, [orderItems]);

	return {
		availability: {
			...availabilityResponse,
			resourcesAvailable: `${
				availabilityResponse?.max - availabilityResponse?.available
			} / ${availabilityResponse?.max}`,
		},
		error,
		inProgressCount: inProgressResponse?.totalCount,
		isLoading,
		mutate,
		orderTableData,
		totalCount: {
			all: orderTableData?.totalCount ?? 0,
			expired: expiredResponse?.totalCount ?? 0,
			inProgress: inProgressResponse?.totalCount ?? 0,
			onHold: onHoldResponse?.totalCount ?? 0,
		},
	};
};

export default useTrialMetrics;
