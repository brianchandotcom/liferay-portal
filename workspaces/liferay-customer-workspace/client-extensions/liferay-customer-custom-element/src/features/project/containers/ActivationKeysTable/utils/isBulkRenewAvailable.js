/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {has100YearsDifference} from './has100YearsDifference';

export function isBulkRenewAvailable(items) {
	const firstItem = items[0];

	for (const item of items) {
		if (
			item.productName !== firstItem.productName ||
			item.expirationDate !== firstItem.expirationDate ||
			item.startDate !== firstItem.startDate ||
			has100YearsDifference(item.startDate, item.expirationDate)
		) {
			return false;
		}
	}

	return true;
}
