/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

export function getInitials(userName) {
	const names = userName.trim().split(' ');
	const lastNameIndex = names.length - 1;

	const initials = names.reduce((initials, currentName, index) => {
		if (!index || index === lastNameIndex) {
			initials = `${initials}${currentName.charAt(0).toUpperCase()}`;
		}

		return initials;
	});

	return initials;
}
