import {mapResultToProps} from '../sites-dashboard-query';

const mockData = {
	dataSources: [{foo: 'foo'}]
};

describe('Sites Dashboard Query Mapper', () => {
	describe('mapResultToProps', () => {
		it('should map sites dashboard query result to props', () => {
			expect(mapResultToProps({data: mockData})).toEqual(
				expect.objectContaining({sites: expect.any(Array)})
			);
		});
	});
});
