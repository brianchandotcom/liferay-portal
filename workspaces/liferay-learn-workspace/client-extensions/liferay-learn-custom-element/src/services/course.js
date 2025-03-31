/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

export const getCoursesAndFirstLessons = async () => {
	const data = await request({
		url: `${
			config.moduleEndpoint
		}scopes/${getCurrentSiteId()}?filter=position eq 0&nestedFields=course,lesson&pageSize=-1`,
		method: 'get',
	});

	return data.items;
};
