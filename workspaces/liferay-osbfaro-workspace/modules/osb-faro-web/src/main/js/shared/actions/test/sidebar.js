/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {isFSA} from 'flux-standard-action';

import * as actions from '../sidebar';

describe('Sidebar Actions', () => {
	describe('collapseSidebar', () => {
		it('returns an action', () => {
			const action = actions.collapseSidebar();

			expect(isFSA(action)).toBe(true);
			expect(action.type).toBe(actions.actionTypes.COLLAPSE_SIDEBAR);
		});
	});
});
