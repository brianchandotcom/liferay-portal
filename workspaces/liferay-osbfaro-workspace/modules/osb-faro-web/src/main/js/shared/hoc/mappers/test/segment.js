/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import * as data from '~/test/data';

import {mapGrowthHistory} from '../segment';

describe('Segment Mappers', () => {
	describe('mapGrowthHistory', () => {
		it('remaps a Segment growth history API response', () => {
			const mockGrowthAggregation = {
				addedIndividualsCount: 1,
				individualsCount: 2,
				intervalInitDate: data.getTimestamp(),
				removedIndividualsCount: 3,
			};

			const mockAPIResponse = [mockGrowthAggregation];

			expect(mapGrowthHistory(mockAPIResponse)).toEqual(
				expect.objectContaining({
					data: expect.arrayContaining([
						expect.objectContaining({
							added: mockGrowthAggregation.addedIndividualsCount,
							modifiedDate:
								mockGrowthAggregation.intervalInitDate,
							removed:
								mockGrowthAggregation.removedIndividualsCount,
							value: mockGrowthAggregation.individualsCount,
						}),
					]),
				})
			);
		});
	});
});
