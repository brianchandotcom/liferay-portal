/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import FaroConstants, {OrderByDirections} from '~/shared/util/constants';

import {
	ACCOUNT_NAME,
	ACTIVITIES_COUNT,
	FAMILY_NAME,
	GIVEN_NAME,
	NAME,
	buildOrderByFields,
	createOrderByField,
	createOrderIOMap,
	getDefaultSortOrder,
	getGraphQLVariablesFromPagination,
	getSortFromOrderIOMap,
	invertSortOrder,
} from '../pagination';

const {orderDescending} = FaroConstants.pagination;

describe('pagination', () => {
	describe('buildOrderByFields', () => {
		it('builds an array of orderByField objects for an individual name', () => {
			expect(
				buildOrderByFields(
					{field: NAME, sortOrder: OrderByDirections.Descending},
					'individuals'
				)
			).toEqual([
				{
					fieldName: GIVEN_NAME,
					orderBy: orderDescending,
					system: false,
				},
				{
					fieldName: FAMILY_NAME,
					orderBy: orderDescending,
					system: false,
				},
			]);
		});

		it('builds an array of orderByField objects for a segment name', () => {
			expect(
				buildOrderByFields(
					{field: NAME, sortOrder: OrderByDirections.Descending},
					'segments'
				)
			).toEqual([
				{
					fieldName: NAME,
					orderBy: orderDescending,
					system: true,
				},
			]);
		});

		it('builds an array of orderByField objects for an account name', () => {
			expect(
				buildOrderByFields(
					{field: NAME, sortOrder: OrderByDirections.Descending},
					'accounts'
				)
			).toEqual([
				{
					fieldName: ACCOUNT_NAME,
					orderBy: orderDescending,
					system: false,
				},
			]);
		});

		it('builds an array of orderByField objects', () => {
			expect(
				buildOrderByFields({
					field: ACTIVITIES_COUNT,
					sortOrder: OrderByDirections.Descending,
				})
			).toEqual([
				{
					fieldName: ACTIVITIES_COUNT,
					orderBy: orderDescending,
					system: true,
				},
			]);
		});
	});

	describe('createOrderByField', () => {
		it('creates an orderByField object', () => {
			expect(
				createOrderByField(ACCOUNT_NAME, OrderByDirections.Descending)
			).toEqual({
				fieldName: ACCOUNT_NAME,
				orderBy: orderDescending,
				system: false,
			});
		});

		it('creates an orderByField object w/ system as true if fieldName is a system field', () => {
			expect(
				createOrderByField(
					ACTIVITIES_COUNT,
					OrderByDirections.Descending
				)
			).toEqual({
				fieldName: ACTIVITIES_COUNT,
				orderBy: orderDescending,
				system: true,
			});
		});
	});

	describe('invertSortOrder', () => {
		it('returns the opposite order from what was received', () => {
			expect(invertSortOrder(OrderByDirections.Ascending)).toEqual(
				OrderByDirections.Descending
			);

			expect(invertSortOrder(OrderByDirections.Descending)).toEqual(
				OrderByDirections.Ascending
			);
		});

		it('returns the default order is the current order is falsey', () => {
			const result = invertSortOrder(null);

			expect(result).toEqual(OrderByDirections.Ascending);
		});
	});

	describe('getDefaultSortOrder', () => {
		it('returns Descending for a fieldName in the INVERTED_SORT_FIELDS array', () => {
			expect(getDefaultSortOrder(ACTIVITIES_COUNT)).toEqual(
				OrderByDirections.Descending
			);
		});

		it('returns Ascending for a fieldName NOT in the INVERTED_SORT_FIELDS array', () => {
			expect(getDefaultSortOrder(ACCOUNT_NAME)).toEqual(
				OrderByDirections.Ascending
			);
		});
	});

	describe('createOrderIOMap', () => {
		it('creates an Immutable OrderedMap with an OrderParams record inside', () => {
			expect(createOrderIOMap('name')).toMatchSnapshot();
		});
	});

	describe('getSortFromOrderIOMap', () => {
		it('returns an object in sort format from an orderIOMap', () => {
			expect(getSortFromOrderIOMap(createOrderIOMap('name'))).toEqual({
				column: 'name',
				type: 'ASC',
			});
		});
	});

	describe('getGraphQLVariablesFromPagination', () => {
		it('returns the pagination params in our standard graphql format', () => {
			expect(
				getGraphQLVariablesFromPagination({
					delta: 10,
					orderIOMap: createOrderIOMap('name'),
					page: 2,
					query: 'test',
				})
			).toEqual({
				keywords: 'test',
				size: 10,
				sort: {
					column: 'name',
					type: 'ASC',
				},
				start: 10,
			});
		});
	});
});
