/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {OperationOption, graphql} from '@apollo/client/react/hoc';
import ClayLink from '@clayui/link';
import React from 'react';
import {useParams} from 'react-router-dom';
import {
	getMapResultToProps,
	mapPropsToOptions,
} from '~/contacts/hoc/mappers/interests-query';
import Card from '~/shared/components/Card';
import NoResultsDisplay from '~/shared/components/NoResultsDisplay';
import {withBaseResults} from '~/shared/hoc';
import {useQueryPagination} from '~/shared/hooks/useQueryPagination';
import IndividualInterestsQuery from '~/shared/queries/IndividualInterestsQuery';
import {CompositionTypes, Sizes} from '~/shared/util/constants';
import {COUNT, createOrderIOMap} from '~/shared/util/pagination';
import {Routes, toRoute} from '~/shared/util/router';
import {compositionListColumns} from '~/shared/util/table-columns';
import URLConstants from '~/shared/util/url-constants';

const withData = () =>
	graphql(IndividualInterestsQuery, {
		options: mapPropsToOptions,
		props: getMapResultToProps(CompositionTypes.IndividualInterests),
	} as OperationOption<object, object>);

const TableWithData = withBaseResults(withData, {
	getColumns: ({
		channelId,
		groupId,
		maxCount,
		totalCount,
	}: {
		channelId: string;
		groupId: string;
		maxCount: number;
		totalCount: number;
	}) => [
		compositionListColumns.getName({
			label: Liferay.Language.get('topic'),
			maxWidth: 200,
			routeFn: ({data: {name}}: {data: {name: string}}) =>
				name &&
				toRoute(Routes.CONTACTS_INDIVIDUALS_INTEREST_DETAILS, {
					channelId,
					groupId,
					interestId: name,
				}),
			sortable: true,
		}),
		compositionListColumns.getRelativeMetricBar({
			label: Liferay.Language.get('total-individuals'),
			maxCount,
			sortable: true,
			totalCount,
		}),
		compositionListColumns.getPercentOf({
			metricName: Liferay.Language.get('total-individuals'),
			totalCount,
		}),
	],
	rowIdentifier: 'name',
	showDropdownRangeKey: false,
});

const Interests: React.FC<React.HTMLAttributes<HTMLElement>> = () => {
	const {channelId = '', groupId = ''} = useParams<{
		channelId: string;
		groupId: string;
	}>();
	const {delta, orderIOMap, page, query} = useQueryPagination({
		initialOrderIOMap: createOrderIOMap(COUNT),
	});

	return (
		<Card pageDisplay>
			<Card.Header className="align-items-center d-flex justify-content-between">
				<Card.Title>
					{Liferay.Language.get('interest-topics')}
				</Card.Title>
			</Card.Header>

			<TableWithData
				channelId={channelId}
				delta={delta}
				groupId={groupId}
				noResultsRenderer={
					<NoResultsDisplay
						description={
							<>
								{Liferay.Language.get(
									'check-back-later-to-verify-if-data-has-been-received-from-your-data-sources'
								)}

								<ClayLink
									className="d-block mb-3"
									href={
										URLConstants.IndividualsDashboardInterestsDocumentation
									}
									key="DOCUMENTATION"
									target="_blank"
								>
									{Liferay.Language.get(
										'learn-more-about-interests'
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
							'there-are-no-interests-found'
						)}
					/>
				}
				orderIOMap={orderIOMap}
				page={page}
				query={query}
				rowBordered={false}
			/>
		</Card>
	);
};

export default Interests;
