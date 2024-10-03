/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

export async function getCommonLicenseKey(
	accountKey,
	dateEnd,
	dateStart,
	environment,
	provisioningServerAPI,
	productName,
	oauthToken
) {

	// eslint-disable-next-line @liferay/portal/no-global-fetch
	const response = await fetch(
		`${provisioningServerAPI}/accounts/${accountKey}/product-groups/${productName}/product-environment/${environment}/common-license-key?dateEnd=${dateEnd}&dateStart=${dateStart}`,
		{
			headers: {
				'OAuth-Token': oauthToken,
			},
		}
	);

	return response;
}

export async function getDevelopmentLicenseKey(
	accountKey,
	provisioningServerAPI,
	oauthToken,
	selectedVersion,
	productName
) {

	// eslint-disable-next-line @liferay/portal/no-global-fetch
	const response = await fetch(
		`${provisioningServerAPI}/accounts/${accountKey}/product-groups/${productName}/product-version/${selectedVersion}/development-license-key`,

		{
			headers: {
				'OAuth-Token': oauthToken,
			},
		}
	);

	return response;
}

export async function getActivationDownloadKey(
	licenseKey,
	provisioningServerAPI,
	oauthToken
) {

	// eslint-disable-next-line @liferay/portal/no-global-fetch
	const response = await fetch(
		`${provisioningServerAPI}/license-keys/${licenseKey}/download`,

		{
			headers: {
				'OAuth-Token': oauthToken,
			},
		}
	);

	return response;
}

export async function getAggregatedActivationDownloadKey(
	selectedKeysIDs,
	provisioningServerAPI,
	oauthToken
) {

	// eslint-disable-next-line @liferay/portal/no-global-fetch
	const response = await fetch(
		`${provisioningServerAPI}/license-keys/download?${selectedKeysIDs}`,

		{
			headers: {
				'OAuth-Token': oauthToken,
			},
		}
	);

	return response;
}

export async function getMultipleActivationDownloadKey(
	selectedKeysIDs,
	provisioningServerAPI,
	oauthToken
) {

	// eslint-disable-next-line @liferay/portal/no-global-fetch
	const response = await fetch(
		`${provisioningServerAPI}/license-keys/download-zip?${selectedKeysIDs}`,

		{
			headers: {
				'OAuth-Token': oauthToken,
			},
		}
	);

	return response;
}

export async function getExportedLicenseKeys(
	accountKey,
	provisioningServerAPI,
	oauthToken,
	productName
) {

	// eslint-disable-next-line @liferay/portal/no-global-fetch
	const response = await fetch(
		`${provisioningServerAPI}/accounts/${accountKey}/license-keys/export?filter=active+eq+true+and+startswith(productName,'${productName}')`,

		{
			headers: {
				'OAuth-Token': oauthToken,
			},
		}
	);

	return response;
}

export async function getExportedSelectedLicenseKeys(
	selectedKeysIDs,
	provisioningServerAPI,
	oauthToken
) {

	// eslint-disable-next-line @liferay/portal/no-global-fetch
	const response = await fetch(
		`${provisioningServerAPI}/license-keys/export?${selectedKeysIDs}`,

		{
			headers: {
				'OAuth-Token': oauthToken,
			},
		}
	);

	return response;
}

export async function addContactRoleNameByEmailByProject({
	accountKey,
	emailURI,
	firstName,
	lastName,
	provisioningServerAPI,
	roleName,
	oauthToken,
}) {

	// eslint-disable-next-line @liferay/portal/no-global-fetch
	const response = await fetch(
		`${provisioningServerAPI}/accounts/${accountKey}/contacts/by-email-address/${emailURI}/roles?contactRoleNames=${roleName}&firstName=${firstName}&lastName=${lastName}`,
		{
			headers: {
				'OAuth-Token': oauthToken,
			},
			method: 'PUT',
		}
	);

	if (!response.ok) {
		throw new Error('Error', {cause: response.status});
	}

	return response;
}

export async function deleteContactRoleNameByEmailByProject({
	accountKey,
	emailURI,
	provisioningServerAPI,
	rolesToDelete,
	oauthToken,
}) {

	// eslint-disable-next-line @liferay/portal/no-global-fetch
	const response = await fetch(
		`${provisioningServerAPI}/accounts/${accountKey}/contacts/by-email-address/${emailURI}/roles?contactRoleNames=${rolesToDelete}`,
		{
			headers: {
				'OAuth-Token': oauthToken,
			},
			method: 'DELETE',
		}
	);

	return response;
}

export async function putDeactivateKeys(
	provisioningServerAPI,
	licenseKeyIds,
	oauthToken
) {

	// eslint-disable-next-line @liferay/portal/no-global-fetch
	const response = await fetch(
		`${provisioningServerAPI}/license-keys/deactivate?${licenseKeyIds}`,

		{
			headers: {
				'OAuth-Token': oauthToken,
			},
			method: 'PUT',
		}
	);

	return response;
}

export async function getNewGenerateKeyFormValues(
	accountKey,
	provisioningServerAPI,
	productGroupName,
	oauthToken
) {

	// eslint-disable-next-line @liferay/portal/no-global-fetch
	const response = await fetch(
		`${provisioningServerAPI}/accounts/${accountKey}/product-groups/${productGroupName}/generate-form`,
		{
			headers: {
				'OAuth-Token': oauthToken,
			},
		}
	);

	return response.json();
}

export async function createNewGenerateKey(
	accountKey,
	provisioningServerAPI,
	oauthToken,
	licenseKey
) {

	// eslint-disable-next-line @liferay/portal/no-global-fetch
	const response = await fetch(
		`${provisioningServerAPI}/accounts/${accountKey}/license-keys`,
		{
			body: JSON.stringify([licenseKey]),
			headers: {
				'Content-Type': 'application/json',
				'OAuth-Token': oauthToken,
			},
			method: 'POST',
		}
	);

	return response.json();
}

export async function putSubscriptionInKey(
	provisioningServerAPI,
	licenseKeyIds,
	oauthToken
) {

	// eslint-disable-next-line @liferay/portal/no-global-fetch
	const response = await fetch(
		`${provisioningServerAPI}/license-keys/subscriptions?licenseKeyIds=${licenseKeyIds}`,

		{
			headers: {
				'OAuth-Token': oauthToken,
			},
			method: 'PUT',
		}
	);

	return response;
}

export async function deleteSubscriptionInKey(
	provisioningServerAPI,
	licenseKeyIds,
	oauthToken
) {

	// eslint-disable-next-line @liferay/portal/no-global-fetch
	const response = await fetch(
		`${provisioningServerAPI}/license-keys/subscriptions?licenseKeyIds=${licenseKeyIds}`,

		{
			headers: {
				'OAuth-Token': oauthToken,
			},
			method: 'DELETE',
		}
	);

	return response;
}

export async function getSubscriptionInKey(
	provisioningServerAPI,
	licenseKeyIds,
	oauthToken
) {

	// eslint-disable-next-line @liferay/portal/no-global-fetch
	const response = await fetch(
		`${provisioningServerAPI}/license-keys/subscriptions?licenseKeyId=${licenseKeyIds}`,
		{
			headers: {
				'OAuth-Token': oauthToken,
			},
			method: 'GET',
		}
	);

	return response.json();
}
