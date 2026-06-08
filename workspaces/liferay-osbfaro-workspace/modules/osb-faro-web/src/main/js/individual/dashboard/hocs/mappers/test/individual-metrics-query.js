/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {range} from 'lodash';

import {mapPropsToOptions, mapResultToProps} from '../individual-metrics-query';

const mockMetric = {
	histogram: {metrics: range(5).map((i) => ({value: i}))},
	trend: {percentage: 25.4},
	value: 10,
};

const mockData = {
	data: {
		individualMetric: {
			anonymousIndividualsMetric: mockMetric,
			knownIndividualsMetric: mockMetric,
			totalIndividualsMetric: mockMetric,
		},
	},
};

describe('Individual Metrics Query Mapper', () => {
	describe('mapPropsToOptions', () => {
		it('maps props to options', () => {
			const props = {
				interval: 'day',
				rangeSelectors: {
					rangeKey: '30',
				},
			};

			expect(mapPropsToOptions(props)).toEqual(
				expect.objectContaining({
					variables: {
						interval: 'day',
						rangeEnd: null,
						rangeKey: 30,
						rangeStart: null,
					},
				})
			);
		});
	});

	describe('mapResultToProps', () => {
		xit('maps results to props', () => {
			expect(mapResultToProps(mockData).items).toEqual(
				expect.arrayContaining([
					expect.objectContaining({
						change: expect.any(Number),
						data: expect.any(Array),
						id: expect.any(String),
						info:
							expect.objectContaining({
								content: expect.any(String),
								title: expect.any(String),
							}) || undefined,
						title: expect.any(String),
						total: expect.any(Number),
					}),
				])
			);
		});
	});
});
