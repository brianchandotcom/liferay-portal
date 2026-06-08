/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import sendRequest from '~/shared/util/request';

export function preview(params) {
	const {
		contactsCardTemplateSettings = {},
		contactsCardTemplateType,
		contactsEntityId,
		groupId,
	} = params;

	return sendRequest({
		data: {
			contactsCardTemplateSettings,
			contactsCardTemplateType,
			contactsEntityId,
		},
		method: 'GET',
		path: `contacts/${groupId}/contacts_card/preview`,
	});
}
