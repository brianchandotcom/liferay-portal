/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {isFSA} from 'flux-standard-action';

import {fetchCard} from '../cards';

describe('Card Actions', () => {
	describe('fetchCard', () => {
		it('returns an action', () => {
			const action = fetchCard({
				contactsCardTemplateId: 'test',
				contactsEntityId: '123',
				contactsEntityType: 1,
			});

			expect(isFSA(action)).toBe(true);
			expect(action.type).toBe('NO_OP');
		});
	});
});
