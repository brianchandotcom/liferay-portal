/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {useQuery} from '@apollo/client';
import React from 'react';
import {useParams} from 'react-router-dom';
import EventAttributeDefinitionsQuery, {
	EventAttributeDefinitionsData,
	EventAttributeDefinitionsVariables,
} from '~/event-analysis/queries/EventAttributeDefinitionsQuery';
import {Attribute, AttributeTypes} from '~/event-analysis/utils/types';
import NoResultsDisplay from '~/shared/components/NoResultsDisplay';
import ListComponent from '~/shared/hoc/ListComponent';
import {useQueryPagination} from '~/shared/hooks/useQueryPagination';
import {Sort} from '~/shared/types';
import {mapListResultsToProps} from '~/shared/util/mappers';
import {
	NAME,
	createOrderIOMap,
	getSortFromOrderIOMap,
} from '~/shared/util/pagination';
import {attributeListColumns} from '~/shared/util/table-columns';

interface EventAttributeDefinitionsResult {
	eventAttributeDefinitions: {
		eventAttributeDefinitions: Attribute[];
		total: number;
	};
}

const GlobalAttributeList: React.FC = () => {
	const {delta, orderIOMap, page, query} = useQueryPagination({
		initialOrderIOMap: createOrderIOMap(NAME),
	});

	const {channelId = '', groupId = ''} = useParams<{
		channelId: string;
		groupId: string;
	}>();

	const response = useQuery<
		EventAttributeDefinitionsData,
		EventAttributeDefinitionsVariables
	>(EventAttributeDefinitionsQuery, {
		variables: {
			keyword: query,
			page: page - 1,
			size: delta,
			sort: getSortFromOrderIOMap(orderIOMap) as Sort,
			type: AttributeTypes.Global,
		},
	});

	return (
		<ListComponent
			{...mapListResultsToProps(response, (result) => {
				const typedResult =
					result as unknown as EventAttributeDefinitionsResult;

				return {
					items: typedResult.eventAttributeDefinitions
						.eventAttributeDefinitions,
					total: typedResult.eventAttributeDefinitions.total,
				};
			})}
			columns={[
				attributeListColumns.getName({channelId, groupId}),
				attributeListColumns.displayName,
				attributeListColumns.description,
				attributeListColumns.sampleValue,
				attributeListColumns.dataType,
			]}
			delta={delta}
			entityLabel={Liferay.Language.get(
				'global-attributes'
			).toLowerCase()}
			noResultsRenderer={
				<NoResultsDisplay
					title={Liferay.Language.get('empty-title-pages')}
				/>
			}
			orderIOMap={orderIOMap}
			page={page}
			query={query}
			rowIdentifier="id"
			showFilterAndOrder={false}
		/>
	);
};

export default GlobalAttributeList;
