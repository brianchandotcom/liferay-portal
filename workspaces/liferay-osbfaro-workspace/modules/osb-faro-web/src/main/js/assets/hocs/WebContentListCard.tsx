/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {useQuery} from '@apollo/client';
import ClayLink from '@clayui/link';
import React from 'react';
import {useParams} from 'react-router-dom';
import Card from '~/shared/components/Card';
import NoResultsDisplay from '~/shared/components/NoResultsDisplay';
import ListComponent from '~/shared/hoc/ListComponent';
import {useQueryPagination} from '~/shared/hooks/useQueryPagination';
import {useQueryRangeSelectors} from '~/shared/hooks/useQueryRangeSelectors';
import WebContentListQuery from '~/shared/queries/WebContentListQuery';
import {Sizes} from '~/shared/util/constants';
import {mapListResultsToProps} from '~/shared/util/mappers';
import {
	VIEWS_METRIC,
	createOrderIOMap,
	getGraphQLVariablesFromPagination,
} from '~/shared/util/pagination';
import {Routes} from '~/shared/util/router';
import {metricsListColumns} from '~/shared/util/table-columns';
import URLConstants from '~/shared/util/url-constants';
import {getSafeRangeSelectors} from '~/shared/util/util';

const WebContentListCard: React.FC = () => {
	const {delta, orderIOMap, page, query} = useQueryPagination({
		initialOrderIOMap: createOrderIOMap(VIEWS_METRIC),
	});

	const {channelId, groupId} = useParams();
	const rangeSelectors = useQueryRangeSelectors();

	const response = useQuery(WebContentListQuery, {
		variables: {
			channelId,
			...getGraphQLVariablesFromPagination({
				delta,
				orderIOMap,
				page,
				query,
			}),
			...getSafeRangeSelectors(rangeSelectors),
		},
	});

	return (
		<Card className="web-content-root" pageDisplay>
			<ListComponent
				{...mapListResultsToProps(response, (result) => ({
					items: result.journals.assetMetrics,
					total: result.journals.total,
				}))}
				columns={[
					metricsListColumns.getTitleId({
						channelId,
						groupId,
						label: `${Liferay.Language.get(
							'title'
						)} | ${Liferay.Language.get('id').toUpperCase()}`,
						rangeSelectors,
						route: Routes.ASSETS_WEB_CONTENT_OVERVIEW,
					}),
					metricsListColumns.viewsMetric,
				]}
				delta={delta}
				entityLabel={Liferay.Language.get('web-content')}
				legacyDropdownRangeKey={false}
				noResultsRenderer={
					<NoResultsDisplay
						description={
							<>
								<span className="mr-1">
									{Liferay.Language.get(
										'check-back-later-to-verify-if-data-has-been-received-from-your-data-sources,-or-you-can-try-a-different-date-range'
									)}
								</span>

								<ClayLink
									href={
										URLConstants.AssetsWebContentListDocumentation
									}
									key="DOCUMENTATION"
									target="_blank"
								>
									{Liferay.Language.get(
										'learn-more-about-web-content'
									)}
								</ClayLink>
							</>
						}
						icon={{
							border: false,
							size: Sizes.XXXLarge,
							symbol: 'ac_satellite',
						}}
						title={Liferay.Language.get(
							'there-are-no-visitors-data-found'
						)}
					/>
				}
				orderIOMap={orderIOMap}
				page={page}
				query={query}
				rangeSelectors={rangeSelectors}
				rowIdentifier={['assetId', 'assetTitle']}
				showDropdownRangeKey
				showFilterAndOrder={false}
			/>
		</Card>
	);
};

export default WebContentListCard;
