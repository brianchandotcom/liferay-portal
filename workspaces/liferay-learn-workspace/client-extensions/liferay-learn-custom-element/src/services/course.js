/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {endpoints} from '../utils/constants';
import {request} from '../utils/request';

export async function getCoursesAndFirstLessons() {
	const data = await request({
		params: {
			filter: 'position eq 0',
			nestedFields: 'course,lesson',
			pageSize: -1,
		},
		url: `${endpoints.modules}scopes/${Liferay.ThemeDisplay.getScopeGroupId()}`,
	});

	return data.items;
}
