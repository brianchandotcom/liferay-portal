/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Map} from 'immutable';
import {createReducer} from 'redux-toolbox';

import {actionTypes} from '../actions/maintenance-seen';

const actionHandlers = {
	[actionTypes.SET_MAINTENANCE_SEEN]: (state, {payload}) => {
		const {currentUserId, groupId, stateStartDate} = payload;

		return state.set(`${groupId}-${currentUserId}`, stateStartDate);
	},
};

export default createReducer(new Map(), actionHandlers);
