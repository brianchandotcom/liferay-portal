import sendRequest from '~/shared/util/request';

import {createLiferay, updateLiferay} from '../data-source';

/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

jest.mock('~/shared/util/request');

const commonLiferayArgs = {
	credentials: {
		login: 'test',
		password: 'testPassword',
	},
	fieldMappingMaps: [],
	name: 'test',
	url: 'test.com',
};

describe('Data Source API', () => {
	describe('Liferay Data Sources', () => {
		it('is called with data to CREATE to a liferay data source', () => {
			createLiferay({...commonLiferayArgs, groupId: '23'});

			expect(sendRequest).toHaveBeenCalledWith({
				data: commonLiferayArgs,
				method: 'POST',
				path: 'contacts/23/data_source/liferay',
			});
		});

		it('is called with data to UPDATE to a liferay data source', () => {
			const dataArgs = {
				...commonLiferayArgs,
				analyticsConfiguration: {},
				contactsConfiguration: {},
			};

			updateLiferay({...dataArgs, groupId: '23', id: '1'});

			expect(sendRequest).toHaveBeenCalledWith({
				data: dataArgs,
				method: 'PATCH',
				path: 'contacts/23/data_source/1/liferay',
			});
		});
	});
});
