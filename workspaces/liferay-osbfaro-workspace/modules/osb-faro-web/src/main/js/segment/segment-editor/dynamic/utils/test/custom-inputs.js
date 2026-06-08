/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {RelationalOperators, TimeSpans} from '../../utils/constants';
import {
	createCustomValueMap,
	getCompleteDate,
	getFilterCriterionIMap,
	getIndexFromPropertyName,
	getOperator,
	getPropertyValue,
	removeItemsByIndex,
	setCompleteDate,
	setOperator,
	setPropertyValue,
} from '../custom-inputs';

const mockValue = createCustomValueMap([
	{
		key: 'criterionGroup',
		value: [
			{
				operatorName: RelationalOperators.EQ,
				propertyName: 'context/city',
				value: 'foo',
			},
			{
				operatorName: RelationalOperators.GT,
				propertyName: 'completeDate',
				value: TimeSpans.Last7Days,
			},
		],
	},
]);

describe('Custom Inputs Util', () => {
	describe('createCustomValueMap', () => {

		// This unit test is skipped because uuid is generated every time test is run.

		it.skip('creates an immutable valueIMap from a given param array', () => {
			expect(
				createCustomValueMap([
					{
						key: 'criterionGroup',
						value: [
							{
								operatorName: RelationalOperators.EQ,
								propertyName: 'context/city',
								value: 'foo',
							},
							{
								operatorName: RelationalOperators.GT,
								propertyName: 'completeDate',
								value: TimeSpans.Last7Days,
							},
						],
					},
				])
			).toMatchSnapshot();
		});
	});

	describe('getFilterCriterionIMap', () => {
		it('returns the Filter Criterion Immutable Map', () => {
			expect(
				getFilterCriterionIMap(mockValue, 1).get('propertyName')
			).toBe('completeDate');
		});
	});

	describe('getIndexFromPropertyName', () => {
		it('returns the index of the first entry in the criterion list that matches the propertyName', () => {
			expect(getIndexFromPropertyName(mockValue, 'completeDate')).toBe(1);
		});
	});

	describe('getOperator', () => {
		it('returns the operator', () => {
			expect(getOperator(mockValue, 0)).toBe(RelationalOperators.EQ);
		});
	});

	describe('getCompleteDate', () => {
		it('returns the time period', () => {
			expect(getCompleteDate(mockValue)).toBe('last7Days');
		});
	});

	describe('getPropertyValue', () => {
		it('returns the value', () => {
			expect(getPropertyValue(mockValue, 'value', 0)).toBe('foo');
		});
	});

	describe('removeItemsByIndex', () => {
		it('removes items by index from the criteria list', () => {
			const indexToRemove = getIndexFromPropertyName(
				mockValue,
				'completeDate'
			);

			const updatedMockValue = removeItemsByIndex(mockValue, [
				indexToRemove,
			]);

			expect(
				getIndexFromPropertyName(updatedMockValue, 'completeDate')
			).toBe(-1);
		});
	});

	describe('setOperator', () => {
		it('sets the operator', () => {
			const updatedValue = setOperator(
				mockValue,
				0,
				RelationalOperators.NE
			);

			expect(getOperator(updatedValue, 0)).toBe(RelationalOperators.NE);
		});
	});

	describe('setPropertyValue', () => {
		it('updates the value', () => {
			const newValue = 'new value foo';
			const updatedValue = setPropertyValue(
				mockValue,
				'value',
				0,
				newValue
			);

			expect(getPropertyValue(updatedValue, 'value', 0)).toBe(newValue);
		});
	});

	describe('setCompleteDate', () => {
		it('updates the time period', () => {
			const newTimePeriod = 'fooTimePeriod';
			const updatedValue = setCompleteDate(mockValue, newTimePeriod);

			expect(getCompleteDate(updatedValue)).toBe(newTimePeriod);
		});
	});
});
