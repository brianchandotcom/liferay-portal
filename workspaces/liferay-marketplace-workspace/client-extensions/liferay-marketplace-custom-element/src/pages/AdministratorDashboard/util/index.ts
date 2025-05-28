/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

const percentage = (total: number, partial: number): number => {
	if (!total) {
		return 0;
	}

	return Math.round((partial / total) * 100);
};

export {percentage};
