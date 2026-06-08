/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Map} from 'immutable';
import {createReducer} from 'redux-toolbox';

import {actionTypes} from '../actions/alerts';

const actionHandlers = {
	[actionTypes.ADD_ALERT]: (state, {payload}) => {
		const {alertType, id, message} = payload;

		return state.mergeIn([id], {
			alertType,
			id,
			message,
		});
	},

	[actionTypes.REMOVE_ALERT]: (state, {payload}) => {
		const {id} = payload;

		return state.delete(id);
	},

	[actionTypes.UPDATE_ALERT]: (state, {payload}) => {
		const {alertType, id, message} = payload;

		return state.mergeIn([id], {
			alertType,
			message,
		});
	},
};

export default createReducer(new Map(), actionHandlers);
