/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import sendRequest from '~/shared/util/request';

export function search({
	channelId,
	groupId,
	keywords = '',
	page = 1,
	pageSize = 12,
}) {
	return sendRequest({
		data: {channelId, keywords, page, pageSize},
		method: 'GET',
		path: `contacts/${groupId}/asset-summary-vocabularies`,
	});
}
