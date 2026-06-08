/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Map} from 'immutable';
import {createReducer} from 'redux-toolbox';

import {actionTypes} from '../actions/sidebar';

const actionHandlers = {
	[actionTypes.COLLAPSE_SIDEBAR]: (state, {payload}) => {
		const {collapsed, currentUserId} = payload;

		return state.set(String(currentUserId), collapsed);
	},
};

export default createReducer(new Map(), actionHandlers);
