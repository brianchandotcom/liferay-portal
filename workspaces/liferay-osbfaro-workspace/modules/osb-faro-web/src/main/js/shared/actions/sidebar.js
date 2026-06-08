/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

export const actionTypes = {
	COLLAPSE_SIDEBAR: 'COLLAPSE_SIDEBAR',
};

export function collapseSidebar(payload) {
	return {
		payload,
		type: actionTypes.COLLAPSE_SIDEBAR,
	};
}
