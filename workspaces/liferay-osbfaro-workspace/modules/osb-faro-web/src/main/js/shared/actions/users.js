/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {createActionTypes} from 'redux-toolbox';
import * as API from '~/shared/api';

import {CALL_API} from '../middleware/api';
import {user} from '../middleware/schema';

export const actionTypes = {
	...createActionTypes('fetch', 'current_user'),
};

export function fetchCurrentUser(groupId) {
	return {
		meta: {
			[CALL_API]: {
				data: {groupId},
				requestFn: API.user.fetchCurrentUser,
				schema: user,
				types: [
					actionTypes.FETCH_CURRENT_USER_REQUEST,
					actionTypes.FETCH_CURRENT_USER_SUCCESS,
					actionTypes.FETCH_CURRENT_USER_FAILURE,
				],
			},
		},
		type: 'NO_OP',
	};
}
