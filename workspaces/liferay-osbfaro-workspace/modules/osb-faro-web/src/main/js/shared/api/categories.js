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
	pageSize = 20,
	vocabularyId = '',
}) {
	return sendRequest({
		data: {channelId, keywords, page, pageSize, vocabularyId},
		method: 'GET',
		path: `contacts/${groupId}/asset-summary-categories`,
	});
}

export function fetchAccountTopCategories({
	accountId,
	channelId,
	groupId,
	selectedMetric,
}) {
	return sendRequest({
		data: {
			accountId,
			channelId,
			pageSize: 5,
			selectedMetric,
			sort: `${selectedMetric},desc`,
		},
		method: 'GET',
		path: `contacts/${groupId}/asset-summary-categories`,
	});
}
