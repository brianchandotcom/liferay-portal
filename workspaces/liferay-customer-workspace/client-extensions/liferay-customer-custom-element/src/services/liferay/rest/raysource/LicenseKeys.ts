/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

export async function getCommonLicenseKey(
	accountKey,
	dateEnd,
	dateStart,
	environment,
	oAuthToken,
	provisioningServerAPI,
	productName
) {

	// eslint-disable-next-line @liferay/portal/no-global-fetch
	const response = await fetch(
		`${provisioningServerAPI}/accounts/${accountKey}/product-groups/${productName}/product-environment/${environment}/common-license-key?dateEnd=${dateEnd}&dateStart=${dateStart}`,
		{
			headers: {
				'OAuth-Token': oAuthToken,
			},
		}
	);

	return response;
}

export async function getDevelopmentLicenseKey(
	accountKey,
	oAuthToken,
	provisioningServerAPI,
	selectedVersion,
	productName
) {

	// eslint-disable-next-line @liferay/portal/no-global-fetch
	const response = await fetch(
		`${provisioningServerAPI}/accounts/${accountKey}/product-groups/${productName}/product-version/${selectedVersion}/development-license-key`,

		{
			headers: {
				'OAuth-Token': oAuthToken,
			},
		}
	);

	return response;
}

export async function getActivationDownloadKey(
	licenseKey,
	oAuthToken,
	provisioningServerAPI
) {

	// eslint-disable-next-line @liferay/portal/no-global-fetch
	const response = await fetch(
		`${provisioningServerAPI}/license-keys/${licenseKey}/download`,

		{
			headers: {
				'OAuth-Token': oAuthToken,
			},
		}
	);

	return response;
}

export async function getAggregatedActivationDownloadKey(
	selectedKeysIDs,
	oAuthToken,
	provisioningServerAPI
) {

	// eslint-disable-next-line @liferay/portal/no-global-fetch
	const response = await fetch(
		`${provisioningServerAPI}/license-keys/download?${selectedKeysIDs}`,

		{
			headers: {
				'OAuth-Token': oAuthToken,
			},
		}
	);

	return response;
}

export async function getMultipleActivationDownloadKey(
	selectedKeysIDs,
	oAuthToken,
	provisioningServerAPI
) {

	// eslint-disable-next-line @liferay/portal/no-global-fetch
	const response = await fetch(
		`${provisioningServerAPI}/license-keys/download-zip?${selectedKeysIDs}`,

		{
			headers: {
				'OAuth-Token': oAuthToken,
			},
		}
	);

	return response;
}

export async function getExportedLicenseKeys(
	accountKey,
	oAuthToken,
	provisioningServerAPI,
	productName
) {

	// eslint-disable-next-line @liferay/portal/no-global-fetch
	const response = await fetch(
		`${provisioningServerAPI}/accounts/${accountKey}/license-keys/export?filter=active+eq+true+and+startswith(productName,'${productName}')`,

		{
			headers: {
				'OAuth-Token': oAuthToken,
			},
		}
	);

	return response;
}

export async function getExportedSelectedLicenseKeys(
	selectedKeysIDs,
	oAuthToken,
	provisioningServerAPI
) {

	// eslint-disable-next-line @liferay/portal/no-global-fetch
	const response = await fetch(
		`${provisioningServerAPI}/license-keys/export?${selectedKeysIDs}`,

		{
			headers: {
				'OAuth-Token': oAuthToken,
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
	oAuthToken,
	provisioningServerAPI,
	roleName,
}) {

	// eslint-disable-next-line @liferay/portal/no-global-fetch
	const response = await fetch(
		`${provisioningServerAPI}/accounts/${accountKey}/contacts/by-email-address/${emailURI}/roles?contactRoleNames=${roleName}&firstName=${firstName}&lastName=${lastName}`,
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

export async function deleteContactRoleNameByEmailByProject({
	accountKey,
	emailURI,
	oAuthToken,
	provisioningServerAPI,
	rolesToDelete,
}) {

	// eslint-disable-next-line @liferay/portal/no-global-fetch
	const response = await fetch(
		`${provisioningServerAPI}/accounts/${accountKey}/contacts/by-email-address/${emailURI}/roles?contactRoleNames=${rolesToDelete}`,
		{
			headers: {
				'OAuth-Token': oAuthToken,
			},
			method: 'DELETE',
		}
	);

	return response;
}

export async function putDeactivateKeys(
	oAuthToken,
	provisioningServerAPI,
	licenseKeyIds
) {

	// eslint-disable-next-line @liferay/portal/no-global-fetch
	const response = await fetch(
		`${provisioningServerAPI}/license-keys/deactivate?${licenseKeyIds}`,

		{
			headers: {
				'OAuth-Token': oAuthToken,
			},
			method: 'PUT',
		}
	);

	return response;
}

export async function getNewGenerateKeyFormValues(
	accountKey,
	oAuthToken,
	provisioningServerAPI,
	productGroupName
) {

	// eslint-disable-next-line @liferay/portal/no-global-fetch
	const response = await fetch(
		`${provisioningServerAPI}/accounts/${accountKey}/product-groups/${productGroupName}/generate-form`,
		{
			headers: {
				'OAuth-Token': oAuthToken,
			},
		}
	);

	return response.json();
}

export async function createNewGenerateKey(
	accountKey,
	oAuthToken,
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
				'OAuth-Token': oAuthToken,
			},
			method: 'POST',
		}
	);

	return response.json();
}

export async function putSubscriptionInKey(
	oAuthToken,
	provisioningServerAPI,
	licenseKeyIds
) {

	// eslint-disable-next-line @liferay/portal/no-global-fetch
	const response = await fetch(
		`${provisioningServerAPI}/license-keys/subscriptions?licenseKeyIds=${licenseKeyIds}`,

		{
			headers: {
				'OAuth-Token': oAuthToken,
			},
			method: 'PUT',
		}
	);

	return response;
}

export async function deleteSubscriptionInKey(
	oAuthToken,
	provisioningServerAPI,
	licenseKeyIds
) {

	// eslint-disable-next-line @liferay/portal/no-global-fetch
	const response = await fetch(
		`${provisioningServerAPI}/license-keys/subscriptions?licenseKeyIds=${licenseKeyIds}`,

		{
			headers: {
				'OAuth-Token': oAuthToken,
			},
			method: 'DELETE',
		}
	);

	return response;
}

export async function getSubscriptionInKey(
	oAuthToken,
	provisioningServerAPI,
	licenseKeyIds
) {

	// eslint-disable-next-line @liferay/portal/no-global-fetch
	const response = await fetch(
		`${provisioningServerAPI}/license-keys/subscriptions?licenseKeyId=${licenseKeyIds}`,
		{
			headers: {
				'OAuth-Token': oAuthToken,
			},
			method: 'GET',
		}
	);

	return response.json();
}
