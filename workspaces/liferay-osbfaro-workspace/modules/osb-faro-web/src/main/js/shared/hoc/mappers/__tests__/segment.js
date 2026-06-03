import * as data from 'test/data';
import {mapGrowthHistory} from '../segment';

describe('Segment Mappers', () => {
	describe('mapGrowthHistory', () => {
		it('should remap a Segment growth history API response', () => {
			const mockGrowthAggregation = {
				addedIndividualsCount: 1,
				individualsCount: 2,
				intervalInitDate: data.getTimestamp(),
				removedIndividualsCount: 3
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
							value: mockGrowthAggregation.individualsCount
						})
					])
				})
			);
		});
	});
});
