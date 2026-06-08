/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import sendRequest from '~/shared/util/request';

export const create = function create({
	currentUrl,
	description,
	groupId,
	title,
}: {
	currentUrl: string;
	description: string;
	groupId: string;
	title: string;
}): Promise<any> {
	return sendRequest({
		data: {currentUrl, description, title},
		method: 'POST',
		path: `main/${groupId}/issue`,
	});
};
