/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {isFSA} from 'flux-standard-action';

import {actionTypes, clearStore} from '../store';

describe('store', () => {
	describe('clearStore', () => {
		it('returns a clearStore action', () => {
			const action = clearStore();

			expect(isFSA(action)).toBeTrue();
			expect(action.type).toBe(actionTypes.CLEAR_STORE);
		});
	});
});
