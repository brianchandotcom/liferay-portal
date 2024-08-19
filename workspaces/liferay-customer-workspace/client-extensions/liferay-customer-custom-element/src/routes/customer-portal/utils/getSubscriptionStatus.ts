/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {SUBSCRIPTIONS_STATUS} from './constants';

export function getSubscriptionStatus(startDate: Date, endDate: Date) {
	const now = new Date();

	if (now > endDate) {
		return SUBSCRIPTIONS_STATUS.expired;
	}

	if (now < startDate) {
		return SUBSCRIPTIONS_STATUS.future;
	}

	return SUBSCRIPTIONS_STATUS.active;
}
