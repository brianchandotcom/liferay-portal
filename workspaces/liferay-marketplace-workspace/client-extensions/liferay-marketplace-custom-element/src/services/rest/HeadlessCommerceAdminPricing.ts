/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import fetcher from '../fetcher';

class HeadlessCommerceAdminPricing {
	getPricelist() {
		return fetcher('o/headless-commerce-admin-pricing/v2.0/price-lists');
	}
}

export {HeadlessCommerceAdminPricing};
