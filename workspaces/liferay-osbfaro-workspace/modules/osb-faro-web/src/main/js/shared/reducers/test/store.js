/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Map} from 'immutable';

import {actionTypes} from '../../actions/store';
import reducer from '../store';

describe('store', () => {
	afterAll(() => {
		global.localStorage.removeItem('maintenanceSeen');
		global.localStorage.removeItem('sidebar');
	});

	beforeAll(() => {
		global.localStorage.setItem(
			'maintenanceSeen',
			btoa(JSON.stringify({321: true}))
		);
		global.localStorage.setItem(
			'sidebar',
			btoa(JSON.stringify({123: false, 321: true}))
		);
	});

	it('is a function', () => {
		expect(typeof reducer).toBe('function');
	});

	it('clears the store to its initial state', () => {
		const action = {
			type: actionTypes.CLEAR_STORE,
		};

		const state = reducer(
			new Map({
				segment: new Map({123: new Map()}),
			}),
			action
		);

		expect(state.get('segment')).toBeUndefined();
		expect(state.get('maintenanceSeen').size).toBe(1);
		expect(state.get('sidebar').size).toBe(2);
	});
});
