/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import sendRequest from '~/shared/util/request';

export function fetchPageExperience({
	canonicalUrl,
	channelId,
	groupId,
	pageTitle,
}: {
	canonicalUrl: string;
	channelId: string;
	groupId: string;
	pageTitle: string;
}) {
	return sendRequest({
		method: 'GET',
		path: `main/${groupId}/page-experiences?canonicalUrl=${canonicalUrl}&pageTitle=${pageTitle}&channelId=${channelId}`,
	});
}
