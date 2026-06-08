/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {createReducer} from 'redux-toolbox';

import {actionTypes} from '../actions/users';
import {RemoteData} from '../util/records';

const actionHandlers = {
	[actionTypes.FETCH_CURRENT_USER_SUCCESS]: (state, action) =>
		state.merge({
			data: String(action.payload.result),
			error: false,
			loading: false,
		}),
	[actionTypes.FETCH_CURRENT_USER_REQUEST]: (state) =>
		state.merge({error: false, loading: true}),
	[actionTypes.FETCH_CURRENT_USER_FAILURE]: (state) =>
		state.merge({
			error: true,
			loading: false,
		}),
};

export default createReducer(new RemoteData(), actionHandlers);
