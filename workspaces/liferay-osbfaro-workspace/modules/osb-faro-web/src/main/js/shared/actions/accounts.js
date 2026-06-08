/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {createActionTypes} from 'redux-toolbox';
import * as API from '~/shared/api';

import {CALL_API} from '../middleware/api';
import {account} from '../middleware/schema';

export const actionTypes = {
	...createActionTypes('fetch', 'account'),
};

export function fetchAccount(data) {
	return {
		meta: {
			[CALL_API]: {
				data,
				requestFn: API.accounts.fetch,
				schema: account,
				types: [
					actionTypes.FETCH_ACCOUNT_REQUEST,
					actionTypes.FETCH_ACCOUNT_SUCCESS,
					actionTypes.FETCH_ACCOUNT_FAILURE,
				],
			},
		},
		payload: {
			id: data.accountId,
		},
		type: 'NO_OP',
	};
}
