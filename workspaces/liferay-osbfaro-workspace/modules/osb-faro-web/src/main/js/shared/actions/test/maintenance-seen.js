/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {isFSA} from 'flux-standard-action';

import * as actions from '../maintenance-seen';

describe('Maintenance Seen Actions', () => {
	describe('setMaintenanceSeen', () => {
		it('returns an action', () => {
			const action = actions.setMaintenanceSeen();

			expect(isFSA(action)).toBe(true);
			expect(action.type).toBe(actions.actionTypes.SET_MAINTENANCE_SEEN);
		});
	});
});
