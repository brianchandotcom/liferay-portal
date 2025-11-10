/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

export function getFilteredItems(
	selectedItems: {[key: string]: string}[] | undefined,
	defaultItems: {key: string; value: string}[]
) {
	return defaultItems?.filter(
		(defaultItem) =>
			!selectedItems?.some(
				(selectedItem) => defaultItem.key === selectedItem.key
			)
	);
}
