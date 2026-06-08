/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {range} from 'lodash';

import {
	getFilterItem,
	getFilters,
	hasCategoryFilters,
	hasSearch,
	isClearFilterVisible,
} from '../filter';

describe('Filter Utils', () => {
	describe('getFilters', () => {
		it('returns all filters with default value of "Any" as a fallback when filters is empty', () => {
			expect(getFilters()).toEqual({
				devices: 'Any',
				location: 'Any',
			});
		});

		it('maps filters from key:array to key:string', () => {
			const filters = {
				devices: ['Desktop'],
				location: ['Brazil'],
			};

			expect(getFilters(filters)).toEqual({
				devices: 'Desktop',
				location: 'Brazil',
			});
		});

		it('sets a default value of "Any" for the location filter if no value was given', () => {
			const filters = {
				devices: ['Desktop'],
			};

			expect(getFilters(filters)).toEqual({
				devices: 'Desktop',
				location: 'Any',
			});
		});

		it('sets a default value of "Any" for the devices filter if no value was given', () => {
			const filters = {
				location: ['Brazil'],
			};

			expect(getFilters(filters)).toEqual({
				devices: 'Any',
				location: 'Brazil',
			});
		});

		it('returns all filters with default value of "Any" as a fallback when filter keys are empty', () => {
			const filters = {
				devices: [],
				location: [],
			};

			expect(getFilters(filters)).toEqual({
				devices: 'Any',
				location: 'Any',
			});
		});

		it('sets default value of "Any" for all filters if the filter object passed contains no matching keys', () => {
			const filters = {
				others: [],
			};

			expect(getFilters(filters)).toEqual({
				devices: 'Any',
				location: 'Any',
			});
		});
	});

	describe('getFilterItem', () => {
		it('returns a filter item from a list of filter values', () => {
			expect(
				getFilterItem(
					[
						{metrics: 1000, valueKey: 'Foo'},
						{metrics: 500, valueKey: 'Bar'},
					],
					'devices'
				)
			).toEqual({
				hasSearch: false,
				items: [
					{
						category: 'devices',
						checked: false,
						inputType: 'radio',
						label: 'Bar',
						value: '',
					},
					{
						category: 'devices',
						checked: false,
						inputType: 'radio',
						label: 'Foo',
						value: '',
					},
				],
				label: 'Devices',
				name: 'Devices',
				value: '2',
			});
		});
	});

	describe('hasCategoryFilters', () => {
		it('returns false if there is no category in a filter', () => {
			expect(
				hasCategoryFilters(
					{
						bar: [],
						foo: [],
					},
					'categoryName'
				)
			).toBeFalsy();
		});

		it('returns true if it contains category in a filter', () => {
			expect(
				hasCategoryFilters(
					{
						bar: [],
						categoryName: 'foo',
						foo: [],
					},
					'categoryName'
				)
			).toBeTruthy();
		});
	});

	describe('hasSearch', () => {
		it('returns false when there are less than or equal to 15 filter items', () => {
			expect(hasSearch(range(15))).toBeFalse();
			expect(hasSearch(range(10))).toBeFalse();
		});

		it('returns true when there are more than 15 filter items', () => {
			expect(hasSearch(range(16))).toBeTrue();
		});
	});

	describe('isClearFilterVisible', () => {
		it('returns false if there is no filter', () => {
			expect(isClearFilterVisible()).toBeFalsy();
			expect(isClearFilterVisible([])).toBeFalsy();
			expect(isClearFilterVisible({})).toBeFalsy();
			expect(
				isClearFilterVisible({
					bar: [],
					foo: [],
				})
			).toBeFalsy();
		});

		it('returns false if it contains only one filter', () => {
			expect(
				isClearFilterVisible({
					Devices: ['Desktop'],
				})
			).toBeFalsy();
		});

		it('returns false if it contains only one valid filter', () => {
			expect(
				isClearFilterVisible({
					Devices: ['Desktop'],
					Location: [],
				})
			).toBeFalsy();
		});

		it('returns true if it contains more than one filter', () => {
			expect(
				isClearFilterVisible({
					Devices: ['Desktop'],
					Location: ['Brazil'],
				})
			).toBeTruthy();
		});
	});
});
