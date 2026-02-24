/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import * as OAuth2 from '@liferay/oauth2-provider-web/client';

export async function getOrRequestToken() {
	const oauth2Client = await OAuth2.FromUserAgentApplication(
		'liferay-customer-etc-spring-boot-oaua'
	);
	const response = await oauth2Client._getOrRequestToken();

	return Object(response).access_token ? Object(response).access_token : null;
}
