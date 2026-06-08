/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {isFSA} from 'flux-standard-action';

import {fetchLayout} from '../layouts';

describe('Layout Actions', () => {
	describe('fetchLayout', () => {
		it('returns an action', () => {
			const action = fetchLayout({contactsEntityId: '123', type: 1});

			expect(isFSA(action)).toBe(true);
			expect(action.type).toBe('NO_OP');
		});
	});
});
