/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import AssetAppearsOnResolver from './AssetAppearsOnResolver';
import CommerceAverageOrderValueResolver from './CommerceAverageOrderValueResolver';
import CommerceAverageRevenuePerAccountResolver from './CommerceAverageRevenuePerAccountResolver';
import CommerceIncompleteOrdersResolver from './CommerceIncompleteOrdersResolver';
import CommerceTotalOrderValueResolver from './CommerceTotalOrderValueResolver';
import CustomAssetsListResolver from './CustomAssetsListResolver';
import DocumentsAndMediaListResolver from './DocumentsAndMediaListResolver';
import DocumentsAndMediaMetricsResolver from './DocumentsAndMediaMetricsResolver';
import EventAnalysisListResolver from './EventAnalysisListResolver';
import EventsByUserSessionsResolver from './EventsByUserSessionsResolver';
import ExperimentResolver from './ExperimentResolver';
import InterestsResolver from './InterestsResolver';
import PagePathResolver from './PagePathResolver';
import IndividualSiteMetricsResolver from './individualSiteMetricsResolver';

/**
 * How it works?
 *
 * Add a @client value on the query to mock data
 * on frontend side, example:
 *
 * query Foo ($foo: String!) {
 *     queryName (title: $title) @client {
 *         data
 *     }
 * }
 */

export const resolvers = {
	assetPages: AssetAppearsOnResolver,
	dashboards: CustomAssetsListResolver,
	document: DocumentsAndMediaMetricsResolver,
	documents: DocumentsAndMediaListResolver,
	eventAnalysisList: EventAnalysisListResolver,
	eventsByUserSessions: EventsByUserSessionsResolver,
	experiment: ExperimentResolver,
	individualInterests: InterestsResolver,
	orderAccountAverageCurrencyValues: CommerceAverageRevenuePerAccountResolver,
	orderAverageCurrencyValues: CommerceAverageOrderValueResolver,
	orderIncompleteCurrencyValues: CommerceIncompleteOrdersResolver,
	orderTotalCurrencyValues: CommerceTotalOrderValueResolver,
	pagePath: PagePathResolver,
	site: IndividualSiteMetricsResolver,
	siteInterests: InterestsResolver,
};
