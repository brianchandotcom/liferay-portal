/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {List, Map} from 'immutable';
import {createReducer} from 'redux-toolbox';

import {actionTypes} from '../actions/modals';

export default createReducer(new List(), {
	[actionTypes.CLOSE_ALL_MODALS](state) {
		return state.clear();
	},

	[actionTypes.CLOSE_MODAL](state) {
		return state.pop();
	},

	[actionTypes.OPEN_MODAL](state, action) {
		const {closeOnBlur, props, type} = action.payload;

		return state.push(
			new Map({
				closeOnBlur,
				props: new Map(props),
				type,
			})
		);
	},
});
