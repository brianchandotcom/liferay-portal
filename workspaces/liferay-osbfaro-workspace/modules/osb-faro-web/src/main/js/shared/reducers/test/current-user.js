/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {actionTypes as userActionTypes} from '~/shared/actions/users';
import {RemoteData} from '~/shared/util/records';

import reducer from '../current-user';

describe('currentUser reducer', () => {
	it('sets current user id', () => {
		const id = 1234;

		const action = {
			payload: {
				result: id,
			},
			type: userActionTypes.FETCH_CURRENT_USER_SUCCESS,
		};

		const state = reducer(new RemoteData(), action);

		expect(state.data).toBe(String(id));
	});
});
