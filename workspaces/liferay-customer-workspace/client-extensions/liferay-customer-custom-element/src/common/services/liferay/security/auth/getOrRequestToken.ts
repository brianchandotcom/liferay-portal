/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Liferay} from '../../../../../common/services/liferay';

export async function getOrRequestToken() {
	const response = await Liferay.OAuth2Client.FromUserAgentApplication(
		'liferay-customer-etc-spring-boot-oaua'
	)._getOrRequestToken();

	return Object(response).access_token ? Object(response).access_token : null;
}
