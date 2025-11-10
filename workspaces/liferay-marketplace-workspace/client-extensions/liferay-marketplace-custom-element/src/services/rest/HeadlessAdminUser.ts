/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import fetcher from '../fetcher';

export default class HeadlessAdminUser {
	static async deleteAccountUserAccountByEmailAddress(
		accountExternalReferenceCode: string,
		emailAddress: string
	) {
		return fetcher.delete(
			`/o/headless-admin-user/v1.0/accounts/by-external-reference-code/${accountExternalReferenceCode}/user-accounts/by-email-address/${emailAddress}`
		);
	}

	static async deleteRoleAccountUser(
		accountId: number,
		roleId: number,
		userId: number
	) {
		return fetcher.delete(
			`/o/headless-admin-user/v1.0/accounts/${accountId}/account-roles/${roleId}/user-accounts/${userId}`
		);
	}

	static async getAccount(accountId: string | number) {
		return fetcher<Account>(
			`/o/headless-admin-user/v1.0/accounts/${accountId}`
		);
	}

	static async getAccountByExternalReferenceCode(
		externalReferenceCode: string
	) {
		return fetcher<Account>(
			`/o/headless-admin-user/v1.0/accounts/by-external-reference-code/${externalReferenceCode}`
		);
	}

	static async getAccountPostalAddresses(accountId: string | number) {
		return fetcher<APIResponse<AccountPostalAddresses>>(
			`/o/headless-admin-user/v1.0/accounts/${accountId}/postal-addresses`
		);
	}

	static async getAccountRoles(accountExternalReferenceCode: string) {
		return fetcher<APIResponse<AccountRole>>(
			`/o/headless-admin-user/v1.0/accounts/by-external-reference-code/${accountExternalReferenceCode}/account-roles`
		);
	}

	static async getAccounts(searchParams = new URLSearchParams()) {
		return fetcher<APIResponse<Account>>(
			`/o/headless-admin-user/v1.0/accounts?${searchParams.toString()}`
		);
	}

	static async getMyUserAccount() {
		return fetcher<UserAccount>(
			'/o/headless-admin-user/v1.0/my-user-account'
		);
	}

	static async getRolesPage(searchParams = new URLSearchParams()) {
		return fetcher<APIResponse<RoleBrief>>(
			`/o/headless-admin-user/v1.0/roles?${searchParams}`
		);
	}

	static async getUserAccountById(accountId: string | number) {
		return fetcher<UserAccount>(
			`/o/headless-admin-user/v1.0/user-accounts/${accountId}`
		);
	}

	static async getUserEmailAddress(emailAddress: string) {
		return fetcher<UserAccount>(
			`/o/headless-admin-user/v1.0/user-accounts/by-email-address/${emailAddress}`
		);
	}

	static async getUserAccounts() {
		return fetcher(`/o/headless-admin-user/v1.0/user-accounts`);
	}

	static async getUserAccountsByAccountId(accountId: string | number) {
		return fetcher(
			`/o/headless-admin-user/v1.0/accounts/${accountId}/user-accounts`
		);
	}

	static async postAccountUserAccountByEmailAddress(
		accountExternalReferenceCode: string,
		emailAddress: string
	) {
		return fetcher.post<UserAccount>(
			`/o/headless-admin-user/v1.0/accounts/by-external-reference-code/${accountExternalReferenceCode}/user-accounts/by-email-address/${emailAddress}`
		);
	}

	static async postAddress(accountId: number, body: any) {
		return fetcher.post(
			`/o/headless-admin-user/v1.0/accounts/${accountId}/postal-addresses`,
			body
		);
	}

	static async sendRoleAccountUser(
		accountId: number,
		roleId: number,
		userId: number
	) {
		return fetcher.post(
			`/o/headless-admin-user/v1.0/accounts/${accountId}/account-roles/${roleId}/user-accounts/${userId}`
		);
	}

	static async updateAccount(
		accountId: number | string,
		data: Partial<Account>
	) {
		return fetcher.patch(
			`/o/headless-admin-user/v1.0/accounts/${accountId}`,
			data
		);
	}

	static async updateUserAccount(data: unknown, accountId: number) {
		return fetcher.patch(
			`/o/headless-admin-user/v1.0/user-accounts/${accountId}`,
			data
		);
	}

	static async updateUserImage(userId: number, formData: FormData) {
		return fetcher.post(
			`/o/headless-admin-user/v1.0/user-accounts/${userId}/image`,
			formData,
			{shouldStringify: false}
		);
	}
}
