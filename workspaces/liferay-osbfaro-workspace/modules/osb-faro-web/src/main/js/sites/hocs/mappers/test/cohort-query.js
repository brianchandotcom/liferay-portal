/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {mapPropsToOptions, mapResultToProps} from '../cohort-query';

const mockData = {
	cohort: {
		anonymousCohortHeatMapMetrics: ['test'],
		knownCohortHeatMapMetrics: ['foo'],
		visitorsCohortHeatMapMetrics: ['bar'],
	},
};

describe('Cohort Query Mapper', () => {
	describe('mapPropsToOptions', () => {
		it('maps cohort query props to options', () => {
			const interval = 'D';
			const channelId = '123';

			expect(mapPropsToOptions({channelId, interval})).toEqual(
				expect.objectContaining({
					variables: {
						channelId,
						interval,
					},
				})
			);
		});
	});

	describe('mapResultToProps', () => {
		it('maps cohort query result to props', () => {
			expect(mapResultToProps({data: mockData})).toEqual(
				expect.objectContaining({
					data: {
						anonymousVisitors: {
							items: expect.any(Array),
						},
						knownVisitors: {
							items: expect.any(Array),
						},
						visitors: {
							items: expect.any(Array),
						},
					},
					empty: false,
				})
			);
		});

		it('receives empty as true', () => {
			expect(
				mapResultToProps({
					data: {
						cohort: {
							anonymousCohortHeatMapMetrics: [],
							knownCohortHeatMapMetrics: [],
							visitorsCohortHeatMapMetrics: [],
						},
					},
				})
			).toEqual(
				expect.objectContaining({
					data: {
						anonymousVisitors: {
							items: expect.any(Array),
						},
						knownVisitors: {
							items: expect.any(Array),
						},
						visitors: {
							items: expect.any(Array),
						},
					},
					empty: true,
				})
			);
		});
	});
});
