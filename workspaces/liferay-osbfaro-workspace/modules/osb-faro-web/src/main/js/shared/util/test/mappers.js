/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {
	getVariables,
	mapListResultsToProps,
	safeResultToProps,
} from '../mappers';

const mapResultToProps = safeResultToProps(({myData}) => {
	const {myValue} = myData;

	return {
		myValue,
	};
});

describe('mappers', () => {
	describe('mapListResultsToProps', () => {
		it('maps the query results to props for ListComponent', () => {
			const refetch = jest.fn;
			const moreStuff = [{name: 'test'}, {name: 'test2'}];
			const total = 2;

			const props = mapListResultsToProps(
				{
					data: {stuff: {moreStuff, total}},
					error: false,
					loading: false,
					refetch,
				},
				(data) => ({
					items: data.stuff.moreStuff,
					total: data.stuff.total,
				})
			);

			expect(props).toEqual({
				empty: false,
				error: false,
				items: moreStuff,
				loading: false,
				refetch,
				total,
			});
		});

		it('returns default props for ListComponent if data is null', () => {
			const refetch = jest.fn;

			const props = mapListResultsToProps(
				{
					data: null,
					error: false,
					loading: true,
					refetch,
				},
				(data) => ({
					items: data.stuff.moreStuff,
					total: data.stuff.total,
				})
			);

			expect(props).toEqual({
				empty: true,
				error: false,
				items: [],
				loading: true,
				refetch,
				total: 0,
			});
		});
	});

	describe('safeResultToProps', () => {
		it("does not extract data when there's an error in the result", () => {
			const props = mapResultToProps({
				data: {
					error: {
						message: 'fake error message',
					},
				},
			});

			expect(props).toEqual({
				error: {
					message: 'fake error message',
				},
			});
		});

		it('does not extract data while result is still loading', () => {
			const props = mapResultToProps({
				data: {
					loading: true,
				},
			});

			expect(props).toEqual({
				loading: true,
			});
		});

		it('returns with an error when exception is thrown', () => {
			const error = new Error('error');
			const errorMapResultToProps = safeResultToProps(() => {
				throw error;
			});

			const props = errorMapResultToProps({
				data: {},
			});

			expect(props).toEqual({
				error,
			});
		});
	});

	describe('getVariables', () => {
		const filters = {
			devices: ['Desktop'],
			location: ['Brazil'],
		};

		const params = {
			assetId: '12345',
			title: 'Liferay',
			touchpoint: 'Any',
		};

		const rangeKey = '7';

		it('includes variables passing all necessary parameters', () => {
			const variables = getVariables({
				filters,
				params,
				rangeSelectors: {rangeKey},
			});

			expect(variables).toEqual({
				variables: {
					assetId: '12345',
					devices: 'Desktop',
					location: 'Brazil',
					rangeEnd: null,
					rangeKey: 7,
					rangeStart: null,
					title: 'Liferay',
					touchpoint: null,
				},
			});
		});

		it('includes variables without filter parameter', () => {
			const variables = getVariables({
				params,
				rangeSelectors: {rangeKey},
			});

			expect(variables).toEqual({
				variables: {
					assetId: '12345',
					rangeEnd: null,
					rangeKey: 7,
					rangeStart: null,
					title: 'Liferay',
					touchpoint: null,
				},
			});
		});

		it('includes variables passing filter parameter as empty object', () => {
			const variables = getVariables({
				filters: {},
				params,
				rangeSelectors: {rangeKey},
			});

			expect(variables).toEqual({
				variables: {
					assetId: '12345',
					devices: 'Any',
					location: 'Any',
					rangeEnd: null,
					rangeKey: 7,
					rangeStart: null,
					title: 'Liferay',
					touchpoint: null,
				},
			});
		});

		it('includes variables without params parameter', () => {
			const variables = getVariables({
				filters,
				params: {touchpoint: 'https://liferay.com'},
				rangeSelectors: {rangeKey},
			});

			expect(variables).toEqual({
				variables: {
					devices: 'Desktop',
					location: 'Brazil',
					rangeEnd: null,
					rangeKey: 7,
					rangeStart: null,
					title: '',
					touchpoint: 'https://liferay.com',
				},
			});
		});

		it('includes variables passing params parameter without assetId', () => {
			const variables = getVariables({
				filters,
				params: {title: 'Liferay', touchpoint: 'https://liferay.com'},
				rangeSelectors: {rangeKey},
			});

			expect(variables).toEqual({
				variables: {
					devices: 'Desktop',
					location: 'Brazil',
					rangeEnd: null,
					rangeKey: 7,
					rangeStart: null,
					title: 'Liferay',
					touchpoint: 'https://liferay.com',
				},
			});
		});

		it('includes variables passing params parameter without title', () => {
			const variables = getVariables({
				filters,
				params: {assetId: '12345', touchpoint: 'Any'},
				rangeSelectors: {rangeKey},
			});

			expect(variables).toEqual({
				variables: {
					assetId: '12345',
					devices: 'Desktop',
					location: 'Brazil',
					rangeEnd: null,
					rangeKey: 7,
					rangeStart: null,
					title: '',
					touchpoint: null,
				},
			});
		});

		it('includes interval when returning variables', () => {
			const {variables} = getVariables({
				filters,
				interval: 'foo',
				params: {},
				rangeSelectors: {rangeKey},
			});

			expect(variables.interval).toBe('foo');
		});

		it('includes channelId in the variables object if it was passed', () => {
			const {variables} = getVariables({
				filters,
				params: {assetId: '12345', channelId: '12345'},
				rangeSelectors: {rangeKey},
			});

			expect(variables.channelId).toEqual('12345');
		});
	});
});
