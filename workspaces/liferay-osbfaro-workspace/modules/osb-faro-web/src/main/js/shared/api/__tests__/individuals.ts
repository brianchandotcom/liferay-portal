jest.mock('shared/util/request');

import sendRequest from 'shared/util/request';
import {fetchFieldValues} from '../individuals';

describe('Individuals API', () => {
	beforeEach(() => {
		(sendRequest as jest.Mock).mockClear();
	});

	describe('fetchFieldValues', () => {
		it('requests the given page of individual field values', () => {
			fetchFieldValues({
				channelId: '123',
				delta: 20,
				fieldMappingFieldName: 'jobTitle',
				groupId: '456',
				page: 3,
				query: 'engineer',
			});

			expect(sendRequest).toHaveBeenCalledWith({
				data: {
					channelId: '123',
					cur: 3,
					delta: 20,
					fieldMappingFieldName: 'jobTitle',
					query: 'engineer',
				},
				method: 'GET',
				path: 'contacts/456/individual/field_values',
			});
		});
	});
});
