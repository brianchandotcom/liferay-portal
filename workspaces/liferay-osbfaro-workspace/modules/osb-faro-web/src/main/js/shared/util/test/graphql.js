/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {getVariableDefinitions, removeUnusedVariables} from '../graphql';

const mockGQLQuery = {
	definitions: [
		{
			variableDefinitions: [
				{
					variable: {
						name: {
							value: 'name',
						},
					},
				},
				{
					variable: {
						name: {
							value: 'rangeKey',
						},
					},
				},
			],
		},
	],
};

const mockVariables = {
	name: 'Tester',
	rangeKey: '30',
	test: 'no',
};

describe('GraphQL Utils', () => {
	describe('getVariableDefinitions', () => {
		it('Returns the variable definitions from a GQLQuery', () => {
			expect(getVariableDefinitions(mockGQLQuery)).toEqual({
				name: true,
				rangeKey: true,
			});
		});
	});

	describe('removeUnusedVariables', () => {
		it('Returns only the variables that exist in the variableDefinitions', () => {
			expect(
				removeUnusedVariables(mockVariables, {
					name: true,
					rangeKey: true,
				})
			).toEqual({
				name: 'Tester',
				rangeKey: '30',
			});
		});
	});
});
