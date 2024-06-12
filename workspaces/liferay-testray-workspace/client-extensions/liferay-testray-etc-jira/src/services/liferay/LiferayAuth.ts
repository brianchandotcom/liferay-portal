/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {baseFetcher} from '../../lib/Fetch';
import {
	LIFERAY_AUTH_CLIENT_ID,
	LIFERAY_AUTH_CLIENT_SECRET,
	LIFERAY_AUTH_TOKEN,
	LIFERAY_BASE_URL,
} from '../../utils/env';

class LiferayAuth {
	protected fetcher = baseFetcher(LIFERAY_BASE_URL as string, {
		headers: {

			// eslint-disable-next-line quote-props
			'Authorization': LIFERAY_AUTH_TOKEN as string,
			'Content-Type': 'application/json',
		},
	});

	public async getAuthToken() {
		const response = await this.fetcher('/o/oauth2/token', {
			body: JSON.stringify({
				client_id: LIFERAY_AUTH_CLIENT_ID,
				client_secret: LIFERAY_AUTH_CLIENT_SECRET,
				grant_type: 'client_credentials',
			}),
		});

		return {
			access_token: response.access_token,
			expires_in: response.expires_in,
		};
	}
}

export default LiferayAuth;
