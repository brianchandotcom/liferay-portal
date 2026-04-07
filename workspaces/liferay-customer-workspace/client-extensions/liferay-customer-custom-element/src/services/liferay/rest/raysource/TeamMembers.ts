/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

export async function addContactRoleNameByEmailByProject(
	accountKey: string,
	contactRoleNames: string | string[],
	emailURI: string,
	firstName: string,
	lastName: string,
	oAuthToken: string,
	provisioningServerAPI: string
) {
	const params = new URLSearchParams();

	const roles = Array.isArray(contactRoleNames)
		? contactRoleNames
		: contactRoleNames.split(',');

	roles.forEach((role) => params.append('contactRoleNames', role));

	// eslint-disable-next-line @liferay/portal/no-global-fetch
	const response = await fetch(
		`${provisioningServerAPI}/accounts/${accountKey}/contacts/by-email-address/${emailURI}/roles?${params.toString()}&firstName=${firstName}&lastName=${lastName}`,
		{
			headers: {
				'OAuth-Token': oAuthToken,
			},
			method: 'PUT',
		}
	);

	if (!response.ok) {
		throw new Error('Error', {cause: response.status});
	}

	return response;
}

export async function deleteContactRoleNameByEmailByProject(
	accountKey: string,
	contactRoleNames: string | string[],
	emailURI: string,
	oAuthToken: string,
	provisioningServerAPI: string
) {
	const params = new URLSearchParams();

	const roles = Array.isArray(contactRoleNames)
		? contactRoleNames
		: contactRoleNames.split(',');

	roles.forEach((role) => params.append('contactRoleNames', role));

	// eslint-disable-next-line @liferay/portal/no-global-fetch
	const response = await fetch(
		`${provisioningServerAPI}/accounts/${accountKey}/contacts/by-email-address/${emailURI}/roles?${params.toString()}`,
		{
			headers: {
				'OAuth-Token': oAuthToken,
			},
			method: 'DELETE',
		}
	);

	return response;
}
