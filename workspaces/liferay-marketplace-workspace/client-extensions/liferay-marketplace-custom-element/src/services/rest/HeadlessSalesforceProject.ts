/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import SearchBuilder from '../../core/SearchBuilder';
import fetcher from '../fetcher';

export default class HeadlessSalesforceProject {
	static async getAccountSalesforceProjects(accountKey: string) {
		const response = await fetcher<APIResponse<SalesforceProject>>(
			`o/c/salesforceprojects?filter=${SearchBuilder.eq('r_salesforceProjectToAccounts_accountEntryERC', accountKey)}`
		);

		return response.items;
	}
}
