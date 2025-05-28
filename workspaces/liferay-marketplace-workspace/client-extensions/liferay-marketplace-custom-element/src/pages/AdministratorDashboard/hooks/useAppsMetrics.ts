/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {addDays} from 'date-fns';
import useSWR from 'swr';

import SearchBuilder from '../../../core/SearchBuilder';
import {
	ProductTypeVocabulary,
	ProductWorkflowStatusCode,
} from '../../../enums/Product';
import HeadlessCommerceAdminCatalog from '../../../services/rest/HeadlessCommerceAdminCatalog';

type FilterType = 'month' | 'q1' | 'q2' | 'q3' | 'q4' | 'week';

export const METRIC_PARAMETER = {
	month: 30,
	q1: 1,
	q2: 2,
	q3: 3,
	q4: 4,
	week: 7,
};

const searchBuilder = new SearchBuilder().lambda(
	'categoryNames',
	ProductTypeVocabulary.APP
);

const approved = searchBuilder
	.clone()
	.and()
	.in('statusCode', [ProductWorkflowStatusCode.APPROVED])
	.build();

const inReview = searchBuilder
	.clone()
	.and()
	.in('statusCode', [
		ProductWorkflowStatusCode.PENDING,
		ProductWorkflowStatusCode.DRAFT,
	])
	.build();

const currentTime = new Date();

const useAppsMetricks = (param: FilterType = 'week') => {
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

	const approvedBeforeLastWeek = searchBuilder
		.clone()
		.and()
		.in('statusCode', [ProductWorkflowStatusCode.APPROVED])
		.and()
		.lt('createDate', lastPeriod.toISOString())
		.and()
		.gt('createDate', beforeLastPeriod.toISOString())
		.build();

	const approvedLastWeek = searchBuilder
		.clone()
		.and()
		.in('statusCode', [ProductWorkflowStatusCode.APPROVED])
		.and()
		.gt('createDate', lastPeriod.toISOString())
		.build();

	const inReviewBeforeLastWeek = searchBuilder
		.clone()
		.and()
		.in('statusCode', [
			ProductWorkflowStatusCode.DRAFT,
			ProductWorkflowStatusCode.PENDING,
		])
		.and()
		.lt('createDate', lastPeriod.toISOString())
		.and()
		.gt('createDate', beforeLastPeriod.toISOString())
		.build();

	const inReviewLastWeek = searchBuilder
		.clone()
		.and()
		.in('statusCode', [
			ProductWorkflowStatusCode.DRAFT,
			ProductWorkflowStatusCode.PENDING,
		])
		.and()
		.gt('createDate', lastPeriod.toISOString())
		.build();

	const {data, ...swr} = useSWR('administrator/apps-kpis', () =>
		HeadlessCommerceAdminCatalog.getProductsDashboardKPI({
			approved,
			approvedBeforeLastWeek,
			approvedLastWeek,
			inReview,
			inReviewBeforeLastWeek,
			inReviewLastWeek,
			products: searchBuilder.clone().build(),
		}).then(({data: {metrics}}) => ({
			approved: metrics?.approved?.totalCount || 0,
			approvedBeforeLastWeek:
				metrics?.approvedBeforeLastWeek?.totalCount || 0,
			approvedLastWeek: metrics?.approvedLastWeek?.totalCount || 0,
			inReview: metrics?.inReview?.totalCount || 0,
			inReviewBeforeLastWeek:
				metrics?.inReviewBeforeLastWeek?.totalCount || 0,
			inReviewLastWeek: metrics?.inReviewLastWeek?.totalCount || 0,
			products: metrics?.products?.totalCount || 0,
		}))
	);

	return {
		...swr,
		...data,
	};
};

export default useAppsMetricks;
