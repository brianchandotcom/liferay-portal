/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

export async function getCommonLicenseKey(
	accountKey,
	dateEnd,
	dateStart,
	environment,
	oauthToken,
	provisioningServerAPI,
	productName
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
	oauthToken,
	provisioningServerAPI,
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
	oauthToken,
	provisioningServerAPI
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
	oauthToken,
	provisioningServerAPI
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
	oauthToken,
	provisioningServerAPI
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
	oauthToken,
	provisioningServerAPI,
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
	oauthToken,
	provisioningServerAPI
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
	oauthToken,
	provisioningServerAPI,
	roleName,
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
	oauthToken,
	provisioningServerAPI,
	rolesToDelete,
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
	oauthToken,
	provisioningServerAPI,
	licenseKeyIds
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
	oauthToken,
	provisioningServerAPI,
	productGroupName
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
	oauthToken,
	provisioningServerAPI,
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
	oauthToken,
	provisioningServerAPI,
	licenseKeyIds
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
	oauthToken,
	provisioningServerAPI,
	licenseKeyIds
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
	oauthToken,
	provisioningServerAPI,
	licenseKeyIds
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
