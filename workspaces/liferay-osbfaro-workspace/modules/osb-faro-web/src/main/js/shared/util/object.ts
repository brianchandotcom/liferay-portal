/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

export const deletePropertyFromObject = function deletePropertyFromObject(
	key: string,
	object: {[key: string]: any}
) {
	const retVal = {...object};

	delete retVal[key];

	return retVal;
};
