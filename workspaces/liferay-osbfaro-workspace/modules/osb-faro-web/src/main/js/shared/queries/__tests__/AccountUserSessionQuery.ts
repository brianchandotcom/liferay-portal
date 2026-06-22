import AccountUserSessionQuery from '../AccountUserSessionQuery';

describe('AccountUserSessionQuery', () => {
	const queryString =
		// eslint-disable-next-line @typescript-eslint/no-explicit-any
		(AccountUserSessionQuery as any).loc?.source?.body ?? '';

	it('should include includeWebhookEvents', () => {
		expect(queryString).toContain('includeWebhookEvents: true');
	});
});
