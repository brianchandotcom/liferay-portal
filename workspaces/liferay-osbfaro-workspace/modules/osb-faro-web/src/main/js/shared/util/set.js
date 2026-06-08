/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Set} from 'immutable';

/**
 * Removes the item from the set if it exists, or adds it
 * if it does not.
 */
export function toggle(set, item) {
	return set.has(item) ? set.delete(item) : set.add(item);
}

/**
 * Returns an empty set if the item exists, or returns a
 * set of just that item if it does not.
 */
export function toggleSingleton(set, item) {
	return set.has(item) ? new Set() : Set.of(item);
}
