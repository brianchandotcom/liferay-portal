/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {config} from '../../utils/constants';

export async function liferayInstantMessagingConnect() {
	try {
		const oAuthApplication = Liferay.OAuth2Client.FromUserAgentApplication(
			config.agentOauthAppId
		);

		const hostname = oAuthApplication.homePageURL.split('//')[1];

		const token = await oAuthApplication._getOrRequestToken();

		const socket = new WebSocket(
			`ws://${hostname}/server?type=im`,
			token.access_token
		);

		return socket;
	}
	catch (error) {
		console.error('Unable to connect to Liferay Stream Hub Server', error);

		return null;
	}
}
