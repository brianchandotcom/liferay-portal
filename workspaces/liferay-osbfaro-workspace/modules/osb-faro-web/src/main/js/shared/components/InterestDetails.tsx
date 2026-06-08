/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {graphql} from '@apollo/client/react/hoc';
import ClayLink from '@clayui/link';
import getCN from 'classnames';
import {OrderedMap} from 'immutable';
import React from 'react';
import {useParams} from 'react-router-dom';
import Card from '~/shared/components/Card';
import {withBaseResults, withRangeKey} from '~/shared/hoc';
import getMetricsMapper from '~/shared/hoc/mappers/metrics';
import {useQueryPagination} from '~/shared/hooks/useQueryPagination';
import {useQueryRangeSelectors} from '~/shared/hooks/useQueryRangeSelectors';
import TouchpointsQuery from '~/shared/queries/TouchpointsQuery';
import {RangeSelectors, Router} from '~/shared/types';
import {RangeKeyTimeRanges} from '~/shared/util/constants';
import {sub} from '~/shared/util/lang';
import {VISITORS_METRIC, createOrderIOMap} from '~/shared/util/pagination';
import {OrderParams} from '~/shared/util/records';
import {Routes} from '~/shared/util/router';
import {
	metricsListColumns,
	sitePagesListColumns,
} from '~/shared/util/table-columns';
import URLConstants from '~/shared/util/url-constants';

const withData = () =>
	graphql(
		TouchpointsQuery,
		getMetricsMapper((result) => ({
			items: result.pages.assetMetrics,
			total: result.pages.total,
		}))
	);

interface ITableWithDataProps {
	channelId: string;
	delta: number;
	groupId: string;
	interestId: string;
	orderIOMap: OrderedMap<string, OrderParams>;
	page: number;
	query: string;
	rangeSelectors: RangeSelectors;
	router: Router;
}

const {Last7Days, Last30Days, Last90Days, Yesterday} = RangeKeyTimeRanges;

const TableWithData: React.FC<ITableWithDataProps> = withRangeKey(
	withBaseResults(withData, {
		emptyDescription: sub(
			Liferay.Language.get('empty-message-lists'),
			[
				<ClayLink
					href={URLConstants.DocumentationLink}
					key="DOCUMENTATION"
					target="_blank"
				>
					{Liferay.Language.get('documentation').toLowerCase()}
				</ClayLink>,
			],
			false
		),
		emptyTitle: Liferay.Language.get('empty-title-pages'),
		getColumns: ({
			channelId,
			groupId,
			rangeSelectors,
		}: ITableWithDataProps) => [
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
		],
		legacyDropdownRangeKey: false,
		rangeKeys: [Yesterday, Last7Days, Last30Days, Last90Days],
		rowIdentifier: 'assetId',
		showDropdownRangeKey: true,
	})
);

interface IInterestDetailsProps {
	className?: string;
	router: Router;
}

const InterestDetails: React.FC<IInterestDetailsProps> = ({
	className,
	router,
}) => {
	const {channelId, groupId, interestId} = useParams();
	const {delta, orderIOMap, page, query} = useQueryPagination({
		initialOrderIOMap: createOrderIOMap(VISITORS_METRIC),
	});

	const rangeSelectors = useQueryRangeSelectors();

	return (
		<Card className={getCN(className)} pageDisplay>
			<Card.Header className="align-items-center d-flex justify-content-between">
				<Card.Title>
					{sub(
						Liferay.Language.get('pages-containing-x'),
						[
							<span className="interest-title" key="INTEREST_ID">
								{`"${interestId}"`}
							</span>,
						],
						false
					)}
				</Card.Title>
			</Card.Header>

			<TableWithData
				channelId={channelId!}
				delta={delta}
				groupId={groupId!}
				interestId={interestId!}
				orderIOMap={orderIOMap}
				page={page}
				query={query}
				rangeSelectors={rangeSelectors}
				router={router}
			/>
		</Card>
	);
};

export default InterestDetails;
