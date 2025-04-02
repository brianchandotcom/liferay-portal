/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {endpoint} from '../utils/constants';
import {request} from '../utils/request';
import {getCurrentSiteId} from '../utils/util';

export async function getLearningPaths(pageSize) {
	const data = await request({
		params: {
			fields: 'id,description,level,persona,title,position',
			pageSize,
			sort: 'position:asc',
		},
		url: `${endpoint.learningPaths}scopes/${getCurrentSiteId()}`,
	});

	return data.items;
}
