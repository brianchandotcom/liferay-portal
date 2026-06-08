import sendRequest from '~/shared/util/request';

import {create, update} from '../individual-segment';

/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

jest.mock('~/shared/util/request');

const createArgs = {
	channelId: '123',
	criteriaString: "(name eq 'test test')",
	groupId: '30555',
	individualIds: [1, 2, 3],
	name: 'segment name',
};

const updateArgs = {
	channelId: '123',
	criteriaString: "(name eq 'test test')",
	groupId: '30555',
	id: '23',
	name: 'segment name',
};

const newRequestParams = {
	data: createArgs,
	method: 'POST',
	path: 'contacts/30555/individual_segment',
};

const updateRequestParams = {
	data: updateArgs,
	method: 'PUT',
	path: 'contacts/30555/individual_segment/23',
};

describe('Individual Segment API', () => {
	describe('Create Segment', () => {
		it('does not pass individualIds in the data object to sendRequest if the segmentType is BATCH', () => {
			const segmentType = 'BATCH';

			create({...createArgs, segmentType});

			expect(sendRequest).toHaveBeenCalledWith({
				...newRequestParams,
				data: {
					channelId: '123',
					externalReferenceCode: '',
					filter: "(name eq 'test test')",
					includeAnonymousUsers: false,
					name: createArgs.name,
					segmentType,
					sequential: false,
				},
			});
		});

		it('forwards externalReferenceCode to sendRequest when provided', () => {
			const externalReferenceCode = 'vip-users_2026';

			create({
				...createArgs,
				externalReferenceCode,
				segmentType: 'BATCH',
			});

			expect(sendRequest).toHaveBeenCalledWith({
				...newRequestParams,
				data: {
					channelId: '123',
					externalReferenceCode,
					filter: "(name eq 'test test')",
					includeAnonymousUsers: false,
					name: createArgs.name,
					segmentType: 'BATCH',
					sequential: false,
				},
			});
		});
	});

	describe('Update Segment', () => {
		it('passes filter in data object to sendRequest if the segmentType is BATCH', () => {
			const segmentType = 'BATCH';

			const data = {
				channelId: '123',
				externalReferenceCode: '',
				filter: "(name eq 'test test')",
				includeAnonymousUsers: false,
				name: updateArgs.name,
				sequential: false,
			};

			update({...updateArgs, segmentType});

			expect(sendRequest).toHaveBeenCalledWith({
				...updateRequestParams,
				data: {...data, segmentType},
			});
		});

		it('forwards externalReferenceCode to sendRequest when provided', () => {
			const externalReferenceCode = 'vip-users_2026';

			update({
				...updateArgs,
				externalReferenceCode,
				segmentType: 'BATCH',
			});

			expect(sendRequest).toHaveBeenCalledWith({
				...updateRequestParams,
				data: {
					channelId: '123',
					externalReferenceCode,
					filter: "(name eq 'test test')",
					includeAnonymousUsers: false,
					name: updateArgs.name,
					segmentType: 'BATCH',
					sequential: false,
				},
			});
		});
	});
});
