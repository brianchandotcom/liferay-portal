import {buildQueryString} from '../buildQueryString';

describe('buildQueryString', () => {
	it('should return empty string when all filters are empty', () => {
		expect(buildQueryString({countryFilter: '', industryFilter: ''})).toBe(
			''
		);
	});

	it('should return a single clause when only one filter is set', () => {
		expect(
			buildQueryString({countryFilter: '', industryFilter: 'Tech'})
		).toBe("industry eq 'Tech'");
	});

	it('should join multiple filters with "and" in config order', () => {
		expect(
			buildQueryString({
				countryFilter: 'US',
				industryFilter: 'Tech'
			})
		).toBe("country eq 'US' and industry eq 'Tech'");
	});
});
