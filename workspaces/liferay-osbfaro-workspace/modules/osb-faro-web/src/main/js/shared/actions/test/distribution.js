/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {isFSA} from 'flux-standard-action';

import {
	fetchDistribution,
	fetchIndividualsDistribution,
} from '../distributions';

describe('Distribution', () => {
	describe('fetchDistribution', () => {
		it('returns an action', () => {
			const action = fetchDistribution({});

			expect(isFSA(action)).toBe(true);
			expect(action.type).toBe('NO_OP');
		});
	});

	describe('fetchIndividualsDistribution', () => {
		it('returns an action', () => {
			const action = fetchIndividualsDistribution({});

			expect(isFSA(action)).toBe(true);
			expect(action.type).toBe('NO_OP');
		});
	});
});
