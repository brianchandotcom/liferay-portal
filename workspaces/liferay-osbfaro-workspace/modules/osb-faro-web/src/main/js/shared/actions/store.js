/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

export const actionTypes = {
	CLEAR_STORE: 'CLEAR_STORE',
};

export const clearStore = function clearStore() {
	return {
		type: actionTypes.CLEAR_STORE,
	};
};
