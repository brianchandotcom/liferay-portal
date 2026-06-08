/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {CompositionTypes} from '~/shared/util/constants';

import {
	getMapResultToProps,
	mapCardPropsToOptions,
	mapPropsToOptions,
} from '../composition-query';

const channelId = '321';

const mockData = {
	siteInterests: {
		compositions: [{foo: 'bar'}],
		maxCount: 85,
		total: 123,
		totalCount: 321,
	},
};

const mockProps = {
	channelId,
	delta: 5,
	page: 2,
	rangeSelectors: {
		rangeEnd: null,
		rangeKey: '90',
		rangeStart: null,
	},
};

describe('Composition Query Mapper', () => {
	describe('getMapResultToProps', () => {
		it('maps interests list query result to props', () => {
			expect(
				getMapResultToProps(CompositionTypes.SiteInterests)({
					data: mockData,
				})
			).toEqual(
				expect.objectContaining({
					items: expect.any(Array),
					maxCount: expect.any(Number),
					total: expect.any(Number),
					totalCount: expect.any(Number),
				})
			);
		});
	});

	describe('mapCardPropsToOptions', () => {
		it('maps interests list query card props to options', () => {
			const rangeKey = '30';
			const channelId = '321';

			expect(
				mapCardPropsToOptions({
					channelId,
					rangeSelectors: {
						rangeKey,
					},
				})
			).toEqual(
				expect.objectContaining({
					variables: expect.objectContaining({
						channelId,
						rangeEnd: null,
						rangeKey: parseInt(rangeKey, 10),
						rangeStart: null,
						size: 5,
						start: 0,
					}),
				})
			);
		});
	});

	describe('mapPropsToOptions', () => {
		it('maps interests list query props to options', () => {
			const {
				delta,
				rangeSelectors: {rangeKey},
			} = mockProps;

			expect(mapPropsToOptions(mockProps)).toEqual(
				expect.objectContaining({
					variables: expect.objectContaining({
						channelId,
						rangeKey: parseInt(rangeKey, 10),
						size: parseInt(delta, 10),
						start: 5,
					}),
				})
			);
		});
	});
});
