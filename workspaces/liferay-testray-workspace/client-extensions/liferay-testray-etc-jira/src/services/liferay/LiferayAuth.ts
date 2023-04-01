/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

import { baseFetcher } from '../../lib/Fetch';

const {
    LIFERAY_AUTH_CLIENT_ID,
    LIFERAY_AUTH_CLIENT_SECRET,
    LIFERAY_AUTH_TOKEN,
    LIFERAY_BASE_URL,
} = Bun.env;

class LiferayAuth {
    protected fetcher = baseFetcher(LIFERAY_BASE_URL as string, {
        headers: {
            "Authorization": LIFERAY_AUTH_TOKEN as string,
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
