/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import SearchBuilder from '~/common/core/SearchBuilder';

import {baseFetcher} from '../../fetcher';

type ProvisioningLicenseKeysConstructor = {
	oAuthToken: string;
	provisioningServerAPI: string;
};

class ProvisioningLicenseKeys {
	private fetcher: ReturnType<typeof baseFetcher>;

	constructor(props: ProvisioningLicenseKeysConstructor) {
		this.fetcher = baseFetcher(props.provisioningServerAPI, {
			headers: {
				'OAuth-Token': props.oAuthToken,
			},
		});
	}

	public getCommonLicenseKey(
		accountKey: string,
		productName: string,
		environment: string,
		dateEnd: string,
		dateStart: string
	) {
		return this.fetcher(
			`/accounts/${accountKey}/product-groups/${productName}/product-environment/${environment}/common-license-key?dateEnd=${dateEnd}&dateStart=${dateStart}`
		);
	}

	public async getDevelopmentLicenseKey(
		accountKey: string,
		selectedVersion: string,
		productName: string
	) {
		return this.fetcher(
			`/accounts/${accountKey}/product-groups/${productName}/product-version/${selectedVersion}/development-license-key`
		);
	}

	public async getActivationDownloadKey(licenseKey: string) {
		return this.fetcher(`/license-keys/${licenseKey}/download`);
	}

	public async getAggregatedActivationDownloadKey(selectedKeysIDs: string) {
		return this.fetcher(`/license-keys/download?${selectedKeysIDs}`);
	}

	public async getMultipleActivationDownloadKey(selectedKeysIDs: string) {
		return this.fetcher(`/license-keys/download-zip?${selectedKeysIDs}`);
	}

	public async getExportedLicenseKeys(
		accountKey: string,
		productName: string
	) {
		const filter = new SearchBuilder()
			.eq('active', true)
			.and()
			.startsWith('productName', productName)
			.build();

		return this.fetcher(
			`/accounts/${accountKey}/license-keys/export?filter=${filter})`
		);
	}

	public async addContactRoleNameByEmailByProject({
		accountKey,
		emailURI,
		firstName,
		lastName,
		roleName,
	}: {
		accountKey: string;
		emailURI: string;
		firstName: string;
		lastName: string;
		roleName: string;
	}) {
		return this.fetcher(
			`/accounts/${accountKey}/contacts/by-email-address/${emailURI}/roles?contactRoleNames=${roleName}&firstName=${firstName}&lastName=${lastName}`,
			{
				method: 'PUT',
			}
		);
	}

	public async deleteContactRoleNameByEmailByProject(
		accountKey: string,
		emailURI: string,
		rolesToDelete: string
	) {
		return this.fetcher(
			`/accounts/${accountKey}/contacts/by-email-address/${emailURI}/roles?${rolesToDelete}`,
			{
				method: 'DELETE',
			}
		);
	}

	public async getUserInOkta(contactEmailAddress: string) {
		return this.fetcher(`/contacts/${contactEmailAddress}/validate`);
	}

	public async putDeactivateKeys(licenseKeyIds: string) {
		return this.fetcher(`/license-keys/deactivate?${licenseKeyIds}`, {
			method: 'PUT',
		});
	}

	public async getNewGenerateKeyFormValues(
		accountKey: string,
		productGroupName: string
	) {
		return this.fetcher(
			`/accounts/${accountKey}/product-groups/${productGroupName}/generate-form`
		);
	}

	public async createNewGenerateKey(accountKey: string, licenseKey: string) {
		return this.fetcher(`/accounts/${accountKey}/license-keys`, {
			body: JSON.stringify([licenseKey]),
			method: 'POST',
		});
	}

	public async putSubscriptionInKey(licenseKeyIds: string) {
		return this.fetcher(
			`/license-keys/subscriptions?licenseKeyIds=${licenseKeyIds}`,
			{
				method: 'PUT',
			}
		);
	}

	public async deleteSubscriptionInKey(licenseKeyIds: string) {
		return this.fetcher(
			`/license-keys/subscriptions?licenseKeyIds=${licenseKeyIds}`,
			{
				method: 'DELETE',
			}
		);
	}

	public async getSubscriptionInKey(licenseKeyIds: string) {
		return this.fetcher(
			`/license-keys/subscriptions?licenseKeyId=${licenseKeyIds}`
		);
	}

	public async getSingleUserSubscriptions(
		accountKey: string,
		emailAdress: string
	) {
		return this.fetcher(
			`/accounts/${accountKey}/license-keys?pageSize=999&filter=active+eq+true+and+subscriptionContactEmailAddresses/any(s:s+eq+'${emailAdress}')+and+subscriptionContactCount+eq+1`
		);
	}
}

export default ProvisioningLicenseKeys;
