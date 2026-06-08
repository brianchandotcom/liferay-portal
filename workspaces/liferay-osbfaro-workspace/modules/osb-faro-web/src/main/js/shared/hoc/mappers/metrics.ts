/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {get, isEmpty} from 'lodash';
import {
	GQLQuery,
	getVariableDefinitions,
	removeUnusedVariables,
} from '~/shared/util/graphql';
import {
	formatItem,
	getVariables,
	safeResultToProps,
} from '~/shared/util/mappers';
import {getSortFromOrderIOMap} from '~/shared/util/pagination';

type GraphQLOptions = {variables: {[key: string]: any}};

export const getMapPropsToOptions: (
	gqlQuery: GQLQuery,
	options?: object
) => (props: {[key: string]: any}) => GraphQLOptions =
	function getMapPropsToOptions(gqlQuery, options = {}) {
		return ({
			delta,
			filters,
			interestId,
			orderIOMap,
			page,
			query,
			rangeSelectors,
			router: {params, query: routerQuery},
		}) => {
			const {variables} = getVariables({
				filters,
				params,
				rangeSelectors,
			});

			// LRAC-6976 POC TEMP

			const useDB = get(routerQuery, 'useDB', null) === 'true';

			let unfilteredVariables: any = {
				...variables,
				keywords: query,
				size: delta,
				sort: getSortFromOrderIOMap(orderIOMap),
				start: (page - 1) * delta,
				terms: interestId,
			};

			// LRAC-6976 POC TEMP

			if (useDB) {
				unfilteredVariables = {...unfilteredVariables, useDB};
			}

			const validVariables: Record<string, boolean> = gqlQuery
				? getVariableDefinitions(gqlQuery)
				: {};

			return {
				variables: isEmpty(validVariables)
					? unfilteredVariables
					: removeUnusedVariables(
							unfilteredVariables,
							validVariables
						),
				...options,
			};
		};
	};

export const getMapResultToProps = function getMapResultToProps(
	getResults: (result: any) => {items: any; total: any}
) {
	return safeResultToProps((result) => {
		const {items, total} = getResults(result);

		const formattedItems = items && items.map(formatItem);

		return {
			empty: !items.length,
			items: formattedItems,
			total,
		};
	}) as any;
};

const getMetricsMapper = (
	getResults: (result: any) => {items: any; total: any},
	options: object = {},
	gqlQuery: GQLQuery | null = null
) => ({
	options: getMapPropsToOptions(gqlQuery as GQLQuery, options),
	props: getMapResultToProps(getResults),
});

export default getMetricsMapper;
