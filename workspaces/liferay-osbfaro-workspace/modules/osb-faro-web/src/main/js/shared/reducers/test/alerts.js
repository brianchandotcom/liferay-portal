/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Map, OrderedMap} from 'immutable';

import {actionTypes} from '../../actions/alerts';
import reducer from '../alerts';

describe('Alerts Reducer', () => {
	const message = 'Hello World!';

	it('is a function', () => {
		expect(typeof reducer).toBe('function');
	});

	it('adds an alert to the alerts state', () => {
		const id = '1';

		const action = {
			payload: {
				alertType: 'error',
				id,
				message,
			},
			type: actionTypes.ADD_ALERT,
		};

		const state = reducer(new OrderedMap(), action);

		const alert = state.get(id);

		expect(alert.has('alertType')).toBe(true);
		expect(alert.has('message')).toBe(true);
		expect(state.has(id)).toBe(true);
	});

	it('changes an alert in the alerts state', () => {
		const id = '2';

		const action = {
			payload: {
				alertType: 'confirmation',
				id,
			},
			type: actionTypes.UPDATE_ALERT,
		};

		const prevState = new OrderedMap({
			[id]: new Map({
				alertType: 'pending',
				id,
				message,
			}),
		});

		const newState = reducer(prevState, action);

		expect(newState.getIn([id, 'alertType'])).toBe('confirmation');
		expect(prevState.getIn([id, 'alertType'])).toBe('pending');
	});

	it('removes an alert from the alerts state', () => {
		const id = '2';

		const action = {
			payload: {id},
			type: actionTypes.REMOVE_ALERT,
		};

		const prevState = new OrderedMap({
			[id]: new Map({
				alertType: 'error',
				id,
				message,
			}),
		});

		const newState = reducer(prevState, action);

		expect(newState.has(id)).toBe(false);
		expect(prevState.has(id)).toBe(true);
	});
});
