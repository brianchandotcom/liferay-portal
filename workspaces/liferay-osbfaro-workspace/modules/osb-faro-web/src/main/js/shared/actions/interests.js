/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {createActionTypes} from 'redux-toolbox';
import * as API from '~/shared/api';
import {CALL_API} from '~/shared/middleware/api';
import {interest} from '~/shared/middleware/schema';

export const actionTypes = {
	...createActionTypes('fetch', 'interests'),
};

export function searchInterests(data) {
	return {
		meta: {
			[CALL_API]: {
				data,
				requestFn: API.interests.search,
				schema: interest,
				types: [
					actionTypes.FETCH_INTERESTS_REQUEST,
					actionTypes.FETCH_INTERESTS_SUCCESS,
					actionTypes.FETCH_INTERESTS_FAILURE,
				],
			},
			contactsEntityId: data.contactsEntityId,
		},
		type: 'NO_OP',
	};
}
