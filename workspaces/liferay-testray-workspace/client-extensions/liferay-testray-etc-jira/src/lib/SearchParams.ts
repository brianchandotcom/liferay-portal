/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

const getSearchParams = <T>(object: T) => {
	const searchParams = new URLSearchParams();

	for (const key in object) {
		searchParams.set(key, object[key] as string);
	}

	return searchParams.toString();
};

export {getSearchParams};
