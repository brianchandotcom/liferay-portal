/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Set} from 'immutable';

import {toggle, toggleSingleton} from '../set';

describe('set', () => {
	describe('toggle', () => {
		it('removes the item from the set', () => {
			expect(toggle(new Set([1, 2, 3]), 2)).toEqual(new Set([1, 3]));

			expect(toggle(new Set([1]), 1)).toEqual(new Set());
		});

		it('adds the item to the set', () => {
			expect(toggle(new Set([1, 2, 3]), 5)).toEqual(
				new Set([1, 2, 3, 5])
			);

			expect(toggle(new Set([]), 1)).toEqual(Set.of(1));
		});
	});

	describe('toggleSingleton', () => {
		it('removes the item from the set', () => {
			expect(toggleSingleton(new Set([1]), 1)).toEqual(new Set());
		});

		it('adds the item to the set', () => {
			expect(toggleSingleton(new Set(), 5)).toEqual(Set.of(5));
		});

		it('returns a set of a single item', () => {
			expect(toggleSingleton(new Set([1, 2, 3]), 5)).toEqual(Set.of(5));
		});
	});
});
