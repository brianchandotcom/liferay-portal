/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import Pusher from 'pusher';

import {
	PUSHER_APP_ID,
	PUSHER_APP_KEY,
	PUSHER_APP_REGION,
	PUSHER_APP_SECRET,
} from '../utils/env';

const getPusherClient = () => {
	if (PUSHER_APP_KEY && PUSHER_APP_ID && PUSHER_APP_SECRET) {
		return new Pusher({
			appId: PUSHER_APP_ID,
			cluster: PUSHER_APP_REGION,
			key: PUSHER_APP_KEY,
			secret: PUSHER_APP_SECRET,
			useTLS: true,
		});
	}
};

export {getPusherClient};
