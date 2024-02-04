/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import fetcher from '../fetcher';

class HeadlessCommerceAdminUser {
	async getAccountInfo(
		accountId: number | string,
		searchParams = new URLSearchParams()
	) {
		return fetcher<Account>(
			`/o/headless-admin-user/v1.0/accounts/${accountId}?${searchParams.toString()}`
		);
	}
}

const headlessCommerceAdminUser = new HeadlessCommerceAdminUser();

export default headlessCommerceAdminUser;
