jest.mock('shared/util/request');

import sendRequest from 'shared/util/request';
import {fetchAccountIndividualMetrics, fetchFieldValues} from '../accounts';

describe('Accounts API', () => {
	beforeEach(() => {
		(sendRequest as jest.Mock).mockClear();
	});

	describe('fetchAccountIndividualMetrics', () => {
		it('reads the metrics from the overview of a single account', () => {
			fetchAccountIndividualMetrics({
				accountId: 'acc-1',
				channelId: '123',
				groupId: '456',
			});

			expect(sendRequest).toHaveBeenCalledWith({
				data: {channelId: '123'},
				method: 'GET',
				path: 'contacts/456/account/acc-1/overview',
			});
		});

		it('scopes the metrics to the given account', () => {
			fetchAccountIndividualMetrics({
				accountId: 'acc-2',
				channelId: '123',
				groupId: '456',
			});

			expect(sendRequest).toHaveBeenCalledWith(
				expect.objectContaining({
					path: 'contacts/456/account/acc-2/overview',
				})
			);
		});

		it('scopes the metrics to the given channel', () => {
			fetchAccountIndividualMetrics({
				accountId: 'acc-1',
				channelId: '789',
				groupId: '456',
			});

			expect(sendRequest).toHaveBeenCalledWith(
				expect.objectContaining({
					data: {channelId: '789'},
				})
			);
		});
	});

	describe('fetchFieldValues', () => {
		it('requests the given page of account field values', () => {
			fetchFieldValues({
				channelId: '123',
				delta: 20,
				fieldMappingFieldName: 'industry',
				groupId: '456',
				page: 3,
				query: 'tech',
			});

			expect(sendRequest).toHaveBeenCalledWith({
				data: {
					channelId: '123',
					cur: 3,
					delta: 20,
					fieldMappingFieldName: 'industry',
					query: 'tech',
				},
				method: 'GET',
				path: 'contacts/456/account/field_values',
			});
		});
	});
});
