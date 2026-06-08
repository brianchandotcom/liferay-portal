/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {OrderedMap, fromJS} from 'immutable';
import {createReducer} from 'redux-toolbox';
import {Project, RemoteData} from '~/shared/util/records';

import {actionTypes} from '../actions/projects';
import {handleError, handleLoading} from '../util/redux';

const actionHandlers = {
	[actionTypes.FETCH_PROJECT_REQUEST]: handleLoading,
	[actionTypes.FETCH_PROJECT_FAILURE]: handleError,
	[actionTypes.FETCH_PROJECT_VIA_CORP_PROJECT_UUID_FAILURE]: handleError,
	[actionTypes.FETCH_PROJECT_VIA_CORP_PROJECT_UUID_REQUEST]: handleLoading,
	[actionTypes.UPDATE_PROJECT_FAILURE]: (state) =>
		state.merge({loading: false}),
	[actionTypes.UPDATE_PROJECT_REQUEST]: handleLoading,
	[actionTypes.UPDATE_PROJECT_SUCCESS]: (
		state,
		{meta: {newId, prevId}, payload}
	) => {
		const project = payload.entities.projects[prevId];

		const {
			data: {groupId},
		} = project;

		const updatedState = state.set(
			newId || String(groupId),
			new RemoteData({
				data: new Project(fromJS(project.data)),
				loading: false,
			})
		);

		if (newId && prevId !== newId) {
			return updatedState.delete(prevId);
		}

		return updatedState;
	},
};

export default createReducer(new OrderedMap(), actionHandlers);
