import AccountEventMetricQuery from '../AccountEventMetricQuery';

describe('AccountEventMetricQuery', () => {
	const queryString =
		// eslint-disable-next-line @typescript-eslint/no-explicit-any
		(AccountEventMetricQuery as any).loc?.source?.body ?? '';

	it('should include includeWebhookEvents', () => {
		expect(queryString).toContain('includeWebhookEvents: true');
	});
});
