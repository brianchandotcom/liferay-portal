/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import fetcher from '../fetcher';

class HeadlessAdminUser {
	async getAccount(accountId: string | number) {
		return fetcher<UserAccount>(
			`/o/headless-admin-user/v1.0/accounts/${accountId}`
		);
	}

	async getAccounts(searchParams = new URLSearchParams()) {
		return fetcher<APIResponse<UserAccount>>(
			`/o/headless-admin-user/v1.0/accounts?${searchParams.toString()}`
		);
	}

	async getMyUserAccount() {
		return fetcher<UserAccount>(
			'/o/headless-admin-user/v1.0/my-user-account'
		);
	}

	async getUserAccounts() {
		return fetcher(`/o/headless-admin-user/v1.0/user-accounts`);
	}

	async getAccountPostalAddresses(accountId: string | number) {
		return fetcher<APIResponse<AccountPostalAddresses>>(
			`/o/headless-admin-user/v1.0/accounts/${accountId}/postal-addresses`
		);
	}

	async getUserAccountById(accountId: string | number) {
		return fetcher(
			`/o/headless-admin-user/v1.0/user-accounts/${accountId}`
		);
	}

	async getUserAccountsByAccountId(accountId: string | number) {
		return fetcher(
			`/o/headless-admin-user/v1.0/accounts/${accountId}/user-accounts`
		);
	}

	async updateUserAccount(data: unknown, accountId: number) {
		return fetcher.patch(
			`/o/headless-admin-user/v1.0/user-accounts/${accountId}`,
			data
		);
	}

	async sendRoleAccountUser(
		accountId: number,
		roleId: number,
		userId: number
	) {
		return fetcher.post(
			`/o/headless-admin-user/v1.0/accounts/${accountId}/account-roles/${roleId}/user-accounts/${userId}`
		);
	}

	async updateUserImage(userId: number, formData: FormData) {
		return fetcher.post(
			`/o/headless-admin-user/v1.0/user-accounts/${userId}/image`,
			formData,
			{shouldStringify: false}
		);
	}
}

const HeadlessAdminUserImpl = new HeadlessAdminUser();

export {HeadlessAdminUser};

export default HeadlessAdminUserImpl;
