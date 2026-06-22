import DataSourceQuery from '../DataSourceQuery';

describe('DataSourceQuery', () => {
	it('should not include credentialsType', () => {
		const queryString =
			// eslint-disable-next-line @typescript-eslint/no-explicit-any
			(DataSourceQuery as any).loc?.source?.body ?? '';

		expect(queryString).not.toContain('credentialsType');
	});
});
