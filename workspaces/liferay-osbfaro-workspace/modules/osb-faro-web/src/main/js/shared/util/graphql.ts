/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {pickBy} from 'lodash';
import {RangeSelectors} from '~/shared/types';

import {RangeKeyTimeRanges} from './constants';

export type GQLQuery = {
	definitions: {
		variableDefinitions: {
			variable: {
				name: {
					value: string;
				};
			};
		}[];
	}[];
};

/**
 * Returns an object of variable keys used in the graphQL query.
 */
export const getVariableDefinitions = function getVariableDefinitions(
	gqlQuery: GQLQuery
): Record<string, boolean> {
	return gqlQuery.definitions.reduce<Record<string, boolean>>(
		(acc, {variableDefinitions}) => {
			variableDefinitions.forEach(({variable}) => {
				const {
					name: {value},
				} = variable;

				acc[value] = true;
			});

			return acc;
		},
		{}
	);
};

export const removeUnusedVariables = function removeUnusedVariables(
	variables: Record<string, unknown>,
	validVariables: Record<string, boolean>
) {
	return pickBy(variables, (_, key) => validVariables[key]);
};

export const fetchPolicyDefinition = function fetchPolicyDefinition(
	rangeSelectors: RangeSelectors
) {
	return rangeSelectors.rangeKey === RangeKeyTimeRanges.Last24Hours
		? 'network-only'
		: 'cache-first';
};
