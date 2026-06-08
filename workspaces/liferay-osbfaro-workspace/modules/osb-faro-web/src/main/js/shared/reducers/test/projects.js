/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {OrderedMap, fromJS} from 'immutable';
import {RemoteData} from '~/shared/util/records';
import {mockProject} from '~/test/data';

import {actionTypes} from '../../actions/projects';
import reducer from '../projects';

describe('Projects Reducer', () => {
	it('is a function', () => {
		expect(typeof reducer).toBe('function');
	});

	it('updates the project key on UPDATE_PROJECT_SUCCESS', () => {
		const newId = 'bar';
		const prevId = 'foo';

		const action = {
			meta: {newId, prevId},
			payload: {
				entities: {projects: {[prevId]: {data: {}}}},
				id: prevId,
			},
			type: actionTypes.UPDATE_PROJECT_SUCCESS,
		};

		const prevState = new OrderedMap({
			[prevId]: new RemoteData({
				data: fromJS(mockProject(1)),
			}),
		});

		expect(prevState.get(newId)).toBeFalsy();
		expect(prevState.get(prevId)).toBeTruthy();

		const newState = reducer(prevState, action);

		expect(newState.get(newId)).toBeTruthy();
		expect(newState.get(prevId)).toBeFalsy();
	});
});
