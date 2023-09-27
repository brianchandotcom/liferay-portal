/* eslint-disable quote-props */
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayAlert from '@clayui/alert';

import {Liferay} from '../../liferay/liferay';

const getSiteURL = () => {
	const layoutRelativeURL = Liferay.ThemeDisplay.getLayoutRelativeURL();

	if (layoutRelativeURL.includes('web')) {
		return layoutRelativeURL.split('/').slice(0, 3).join('/');
	}

	return '';
};

export async function getAccountRolesOnAPI(accountId: number) {
	const accountRoles = await fetch(
		`/o/headless-admin-user/v1.0/accounts/${accountId}/account-roles`,
		{
			headers: {
				'accept': 'application/json',
				'x-csrf-token': Liferay.authToken,
			},
		}
	);
	if (accountRoles.ok) {
		const data = await accountRoles.json();

		return data.items;
	}
}

export async function createNewUser(requestBody: RequestBody) {
	try {
		const response = await fetch(
			`/o/headless-admin-user/v1.0/user-accounts`,
			{
				body: JSON.stringify(requestBody),
				headers: {
					'Content-Type': 'application/json',
					'accept': 'application/json',
					'x-csrf-token': Liferay.authToken,
				},
				method: 'POST',
			}
		);

		return response.json();
	}
	catch (error) {
		<ClayAlert.ToastContainer>
			<ClayAlert
				autoClose={5000}
				displayType="danger"
				title="error"
			></ClayAlert>
		</ClayAlert.ToastContainer>;
	}
}

export async function addExistentUserIntoAccount(
	accountId: number,
	userEmail: string
) {
	try {
		await fetch(
			`/o/headless-admin-user/v1.0/accounts/${accountId}/user-accounts/by-email-address/${userEmail}`,
			{
				headers: {
					'accept': 'application/json',
					'x-csrf-token': Liferay.authToken,
				},
				method: 'POST',
			}
		);
	}
	catch (error) {
		<ClayAlert.ToastContainer>
			<ClayAlert autoClose={5000} displayType="danger" title="error" />
		</ClayAlert.ToastContainer>;
	}
}

export async function getUserByEmail(userEmail: String) {
	try {
		const responseFilteredUserList = await fetch(
			`/o/headless-admin-user/v1.0/user-accounts?filter=emailAddress eq '${userEmail}'`,
			{
				headers: {
					'accept': 'application/json',
					'x-csrf-token': Liferay.authToken,
				},
			}
		);

		if (responseFilteredUserList.ok) {
			const data = await responseFilteredUserList.json();
			if (data.items.length) {
				return data.items[0];
			}
		}
	}
	catch (error) {
		<ClayAlert.ToastContainer>
			<ClayAlert
				autoClose={5000}
				displayType="danger"
				title="error"
			></ClayAlert>
		</ClayAlert.ToastContainer>;
	}
}

export async function sendRoleAccountUser(
	accountId: number,
	roleId: number,
	userId: number
) {
	await fetch(
		`/o/headless-admin-user/v1.0/accounts/${accountId}/account-roles/${roleId}/user-accounts/${userId}`,
		{
			headers: {
				'Content-Type': 'application/json',
				'accept': 'application/json',
				'x-csrf-token': Liferay.authToken,
			},
			method: 'POST',
		}
	);
}

export async function addAdditionalInfo(
	additionalInfoBody: AdditionalInfoBody
) {
	return fetch(`/o/c/useradditionalinfos/`, {
		body: JSON.stringify(additionalInfoBody),
		headers: {
			'Content-Type': 'application/json',
			'accept': 'application/json',
			'x-csrf-token': Liferay.authToken,
		},
		method: 'POST',
	});
}

export async function addAdminRegularRole(userID: number) {
	let adminRegularRole: RoleBrief[] = [];
	const responseGetRegularRoles = await fetch(
		`/o/headless-admin-user/v1.0/roles`,
		{
			headers: {
				'accept': 'application/json',
				'x-csrf-token': Liferay.authToken,
			},
		}
	);

	if (responseGetRegularRoles.ok) {
		const regularRoles = await responseGetRegularRoles.json();

		adminRegularRole = regularRoles.items.filter(
			(role: RoleBrief) => role.name === 'Account Administrator (Regular)'
		);
	}
	if (adminRegularRole.length >= 0) {
		return fetch(
			`/o/headless-admin-user/v1.0/roles/${adminRegularRole[0].id}/association/user-account/${userID}`,
			{
				headers: {
					'Content-Type': 'application/json',
					'accept': 'application/json',
					'x-csrf-token': Liferay.authToken,
				},
				method: 'POST',
			}
		);
	}
}

export {getSiteURL};
