/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {DocumentNode, useQuery} from '@apollo/client';
import {useParams} from 'react-router-dom';
import {ICommonVariables, Interval, RangeSelectors} from '~/shared/types';
import {RawFilters, getFilters} from '~/shared/util/filter';
import {fetchPolicyDefinition} from '~/shared/util/graphql';
import {
	getSafeDecodedURIComponent,
	getSafeRangeSelectors,
	getSafeTouchpoint,
} from '~/shared/util/util';

export const useAssetVariables = function useAssetVariables(
	variables: ICommonVariables
) {
	const {type, ...commonVariables} = variables;
	const {
		assetId = '',
		channelId = '',
		title = '',
		touchpoint = '',
	} = useParams<{
		assetId: string;
		channelId: string;
		title: string;
		touchpoint: string;
	}>();

	return {
		assetId: getSafeDecodedURIComponent(assetId),
		touchpoint: getSafeTouchpoint(touchpoint),
		...(type !== 'objectEntry' && {
			channelId,
			title: getSafeDecodedURIComponent(title),
		}),
		...commonVariables,
	};
};

type TMetricQueryParams = {
	Query: DocumentNode;
	experienceId?: string;
	filters: RawFilters;
	interval: Interval;
	rangeSelectors: RangeSelectors;
	variables: (commonVariables: ICommonVariables) => any;
};

const buildQueryVariables = ({
	experienceId,
	filters,
	interval,
	rangeSelectors,
	variables,
}: Omit<TMetricQueryParams, 'Query'>) =>
	variables({
		interval,
		...getFilters(filters),
		...getSafeRangeSelectors(rangeSelectors),
		...(experienceId && {experienceId}),
	});

export const useMetricQuery = function useMetricQuery({
	Query,
	experienceId,
	filters,
	interval,
	rangeSelectors,
	variables,
}: TMetricQueryParams) {
	const {data, error, loading} = useQuery(Query, {
		fetchPolicy: fetchPolicyDefinition(rangeSelectors),
		variables: buildQueryVariables({
			experienceId,
			filters,
			interval,
			rangeSelectors,
			variables,
		}),
	});

	return {data, error, loading};
};
