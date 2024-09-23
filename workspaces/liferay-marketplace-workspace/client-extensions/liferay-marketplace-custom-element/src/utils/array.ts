/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

export function swapElements<T>(
	array: T[],
	currentIndex: number,
	newIndex: number
) {
	const value = array[currentIndex];
	array[currentIndex] = array[newIndex];
	array[newIndex] = value;

	return array;
}
