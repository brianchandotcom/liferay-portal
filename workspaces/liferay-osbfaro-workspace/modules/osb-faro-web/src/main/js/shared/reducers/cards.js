/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Map, fromJS} from 'immutable';
import {createReducer} from 'redux-toolbox';

import {actionTypes} from '../actions/cards';
import {actionTypes as layoutActionTypes} from '../actions/layouts';

const actionHandlers = {
	[actionTypes.FETCH_CARD_SUCCESS]: (state, action) => {
		const {
			meta: {contactsEntityId},
			payload: {
				result: {contactsCardData, contactsCardTemplate},
			},
		} = action;

		return state.mergeIn(
			[contactsEntityId],
			fromJS({[contactsCardTemplate]: contactsCardData})
		);
	},
	[layoutActionTypes.FETCH_LAYOUT_SUCCESS]: (state, {meta, payload}) =>
		state.mergeIn(
			[meta.contactsEntityId],
			fromJS(payload.result.contactsCardData)
		),
};

export default createReducer(new Map(), actionHandlers);
