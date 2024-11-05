/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {IOAuth2ClientAgentApplication, Liferay} from '../liferay';

class OAuth2Client {
	public oAuth2Client: IOAuth2ClientAgentApplication;

	constructor(agentName: string) {
		this.oAuth2Client =
			Liferay.OAuth2Client.FromUserAgentApplication(agentName);
	}
}

export default OAuth2Client;
