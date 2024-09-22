/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

export function swapImageElements<T>(
	imagesArray: T[],
	currentIndex: number,
	newIndex: number
) {
	const value = imagesArray[currentIndex];
	imagesArray[currentIndex] = imagesArray[newIndex];
	imagesArray[newIndex] = value;

	return imagesArray;
}
