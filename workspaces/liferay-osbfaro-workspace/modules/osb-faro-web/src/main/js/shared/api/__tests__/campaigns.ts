jest.mock('shared/util/request');

import sendRequest from 'shared/util/request';
import {fetchTopCampaigns} from '../campaigns';

describe('Campaigns API', () => {
	beforeEach(() => {
		(sendRequest as jest.Mock).mockClear();
	});

	it('should request the top five campaigns filtered by the account', () => {
		fetchTopCampaigns({
			accountId: 'acc-1',
			channelId: '456',
			groupId: '23',
			rangeQueryValues: {rangeKey: '30'},
		});

		expect(sendRequest).toHaveBeenCalledWith({
			data: {
				channelId: '456',
				filter: "accountId in ('acc-1')",
				rangeKey: '30',
				size: 5,
			},
			method: 'GET',
			path: 'contacts/23/campaigns/top',
		});
	});
});
