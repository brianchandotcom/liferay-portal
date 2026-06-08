/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {isFSA} from 'flux-standard-action';
import {Alert} from '~/shared/types';

import {actionTypes, addAlert, removeAlert, updateAlert} from '../alerts';

describe('alerts', () => {
	const alertType = Alert.Types.Alert;
	const id = 123;
	const timeout = false;

	describe('addAlert', () => {
		it('returns an addAlert action', () => {
			const action = addAlert({alertType, timeout});

			expect(isFSA(action)).toBe(true);
			expect(action.type).toBe(actionTypes.ADD_ALERT);
		});

		it('applies a default timeout', () => {
			const action = addAlert({alertType});

			expect(typeof action).toBe('function');
		});

		it('returns a defult id', () => {
			const action = addAlert({alertType, timeout});

			expect(action.payload.id).toBe('3');
		});

		it('allows for a custom id', () => {
			const action = addAlert({alertType, id: 'customId', timeout});

			expect(action.payload.id).toBe('customId');
		});
	});

	describe('updateAlert', () => {
		it('returns a updateAlert action', () => {
			const action = updateAlert({alertType, id, timeout});

			expect(isFSA(action)).toBe(true);
			expect(action.type).toBe(actionTypes.UPDATE_ALERT);
		});

		it('applies a default timeout', () => {
			const action = updateAlert({alertType});

			expect(typeof action).toBe('function');
		});
	});

	describe('removeAlert', () => {
		it('returns a removeAlert action', () => {
			const action = removeAlert(id);

			expect(isFSA(action)).toBe(true);
			expect(action.type).toBe(actionTypes.REMOVE_ALERT);
		});
	});

	it('dispatches the action after a timeout', () => {
		const action = addAlert({alertType});

		const dispatchSpy = jest.fn();

		action(dispatchSpy);

		expect(dispatchSpy).toHaveBeenCalledTimes(1);

		jest.runAllTimers();

		expect(dispatchSpy).toHaveBeenCalledTimes(2);
	});

	it('allows for a custom timeout', () => {
		const action = addAlert({alertType, timeout: 300});

		expect(typeof action).toBe('function');
	});
});
