/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {useQuery} from '@apollo/client';
import {uniqueId} from 'lodash';
import React, {useMemo} from 'react';
import {useParams} from 'react-router-dom';
import {Attribute, Breakdown, Filter} from '~/event-analysis/utils/types';
import Loading from '~/shared/components/Loading';
import ErrorPage from '~/shared/pages/ErrorPage';
import {deletePropertyFromObject} from '~/shared/util/object';
import {Routes, toRoute} from '~/shared/util/router';
import {normalizeRangeSelectors} from '~/shared/util/util';

import BaseEventAnalysisPage from '../components/BaseEventAnalysisPage';
import {
	AttributesProvider,
	AttributesState,
} from '../components/event-analysis-editor/context/attributes';
import {
	EventAnalysisData,
	EventAnalysisQuery,
	EventAnalysisVariables,
} from '../queries/EventAnalysisQuery';

function normalizeItems<T extends {__typename?: string; id: string}>(
	data: T[]
): {[key: string]: T} {
	return data.reduce(
		(acc, item) => ({
			...acc,
			[item.id]: deletePropertyFromObject('__typename', item),
		}),
		{}
	);
}

function getItemsWithUniqueId<T>(
	items: T[],
	key: string
): Array<T & {id: string}> {
	return items.map((item) => ({
		...item,
		id: uniqueId(key),
	}));
}

interface BreakdownWithId extends Breakdown {
	id: string;
}

interface FilterWithId extends Filter {
	id: string;
}

const Edit: React.FC<React.HTMLAttributes<HTMLElement>> = () => {
	const {
		channelId,
		groupId,
		id: eventAnalysisId = '',
	} = useParams<{
		channelId: string;
		groupId: string;
		id: string;
	}>();
	const {data, error, loading} = useQuery<
		EventAnalysisData,
		EventAnalysisVariables
	>(EventAnalysisQuery, {
		fetchPolicy: 'network-only',
		variables: {
			eventAnalysisId,
		},
	});

	const initialAttributesState = useMemo(() => {
		if (data) {
			const {
				eventAnalysis: {
					eventAnalysisBreakdowns,
					eventAnalysisFilters,
					referencedObjects: {eventAttributeDefinitions},
				},
			} = data;

			const breakdowns: BreakdownWithId[] =
				getItemsWithUniqueId<Breakdown>(
					eventAnalysisBreakdowns,
					'breakdown'
				);
			const filters: FilterWithId[] = getItemsWithUniqueId<Filter>(
				eventAnalysisFilters,
				'filter'
			);

			const attributesState: AttributesState = {
				attributes: normalizeItems<Attribute>(
					eventAttributeDefinitions
				),
				breakdownOrder: breakdowns.map(({id}) => id),
				breakdowns: normalizeItems<BreakdownWithId>(breakdowns),
				filterOrder: filters.map(({id}) => id),
				filters: normalizeItems<FilterWithId>(filters),
			};

			return attributesState;
		}

		// eslint-disable-next-line react-hooks/exhaustive-deps
	}, [
		data?.eventAnalysis?.eventAnalysisBreakdowns,
		data?.eventAnalysis?.eventAnalysisFilters,
	]);

	if (loading) {
		return <Loading key="LOADING" />;
	}

	if (error) {
		return (
			<ErrorPage
				href={toRoute(Routes.EVENT_ANALYSIS, {
					channelId,
					groupId,
				})}
				linkLabel={Liferay.Language.get('go-to-event-analysis')}
				message={Liferay.Language.get(
					'the-analysis-you-are-looking-for-does-not-exist'
				)}
				subtitle={Liferay.Language.get('analysis-not-found')}
			/>
		);
	}

	const {
		eventAnalysis: {
			compareToPrevious,
			name,
			rangeEnd,
			rangeKey,
			rangeStart,
			referencedObjects: {eventDefinition},
		},
	} = data!;

	return (
		<AttributesProvider initialState={initialAttributesState}>
			<BaseEventAnalysisPage
				compareToPrevious={compareToPrevious}
				event={eventDefinition}
				name={name}
				rangeSelectors={normalizeRangeSelectors({
					rangeEnd,
					rangeKey,
					rangeStart,
				})}
			/>
		</AttributesProvider>
	);
};

export default Edit;
