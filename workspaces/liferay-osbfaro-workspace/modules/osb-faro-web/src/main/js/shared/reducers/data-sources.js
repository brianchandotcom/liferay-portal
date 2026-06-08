/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Map} from 'immutable';
import {createReducer} from 'redux-toolbox';

import {actionTypes} from '../actions/data-sources';
import {handleError, handleLoading} from '../util/redux';

const actionHandlers = {
	[actionTypes.DELETE_DATA_SOURCE_SUCCESS]: (state, {meta}) =>
		state.delete(String(meta.id)),
	[actionTypes.FETCH_DATA_SOURCE_REQUEST]: handleLoading,
	[actionTypes.FETCH_DATA_SOURCE_FAILURE]: handleError,
};

export default createReducer(new Map(), actionHandlers);
