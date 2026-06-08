/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {graphql} from '@apollo/client/react/hoc';
import React from 'react';
import Card from '~/shared/components/Card';
import {
	compose,
	withBaseResults,
	withQueryPagination,
	withQueryRangeSelectors,
} from '~/shared/hoc';
import getMetricsMapper from '~/shared/hoc/mappers/metrics';
import TouchpointsQuery, {
	TOUCHPOINTS_QUERY_TEST,
} from '~/shared/queries/TouchpointsQuery';
import {Sizes} from '~/shared/util/constants';
import {VISITORS_METRIC, createOrderIOMap} from '~/shared/util/pagination';
import {Routes} from '~/shared/util/router';
import {
	metricsListColumns,
	sitePagesListColumns,
} from '~/shared/util/table-columns';
import URLConstants from '~/shared/util/url-constants';
import {getRangeSelectorsFromQuery} from '~/shared/util/util';

// LRAC-6976 POC TEMP

const withData =
	(useDB = false) =>
	() =>
		graphql(
			useDB ? TOUCHPOINTS_QUERY_TEST : TouchpointsQuery,
			getMetricsMapper((result) => ({
				items: result.pages.assetMetrics,
				total: result.pages.total,
			}))
		);

// LRAC-6976 POC TEMP

const getTableWithData = (useDB) => {
	const TableWithData = withBaseResults(withData(useDB), {
		emptyDescription: (
			<>
				<span className="mr-1">
					{Liferay.Language.get(
						'check-back-later-to-verify-if-data-has-been-received-from-your-data-sources,-or-you-can-try-a-different-date-range'
					)}
				</span>

				<a
					href={URLConstants.SitesDashboardPages}
					key="DOCUMENTATION"
					target="_blank"
				>
					{Liferay.Language.get('learn-more-about-pages')}
				</a>
			</>
		),
		emptyIcon: {
			border: false,
			size: Sizes.XXXLarge,
			symbol: 'ac_satellite',
		},
		emptyTitle: Liferay.Language.get('there-are-no-pages-found'),
		getColumns: ({
			router: {
				params: {channelId, groupId},
				query,
			},
		}) => {
			const rangeSelectors = getRangeSelectorsFromQuery(query);

			return [
				sitePagesListColumns.getTitleUrl({
					channelId,
					groupId,
					rangeSelectors,
					route: Routes.SITES_TOUCHPOINTS_OVERVIEW,
				}),
				metricsListColumns.visitorsMetric,
				metricsListColumns.viewsMetric,
				metricsListColumns.avgTimeOnPageMetric,
				metricsListColumns.bounceRateMetric,
				metricsListColumns.entrancesMetric,
				metricsListColumns.exitRateMetric,
			];
		},
		legacyDropdownRangeKey: false,
		rowIdentifier: ['assetId', 'assetTitle'],
	});

	return TableWithData;
};

const TableWithDataNewDB = getTableWithData(true);
const TableWithDataOldDB = getTableWithData();

// TODO: look intot he default range key

const Touchpoints = ({router, ...otherProps}) => {
	const TableWithData =
		router.query.useDB === 'true' ? TableWithDataNewDB : TableWithDataOldDB;

	return (
		<Card className="site-touchpoints-root" pageDisplay>
			<TableWithData
				{...otherProps}
				entityLabel={Liferay.Language.get('pages')}
				router={router}
			/>
		</Card>
	);
};

export default compose(
	withQueryPagination({initialOrderIOMap: createOrderIOMap(VISITORS_METRIC)}),
	withQueryRangeSelectors()
)(Touchpoints);
