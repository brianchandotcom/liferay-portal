/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

export function getPascalCase(text: string) {
	return text.replace(
		/(\w)(\w*)/g,
		(_, g1, g2) => g1.toUpperCase() + g2.toLowerCase()
	);
}
