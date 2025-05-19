/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {request} from '../../utils/request';

export async function getUserRoles(page = 0, pageSize = 0) {
	const endPoint = `/o/headless-admin-user/v1.0/roles?page=${page}&pageSize=${pageSize}`;

	return request({
		method: 'GET',
		url: `${endPoint}`,
	});
}
