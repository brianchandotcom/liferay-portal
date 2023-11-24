/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import OAuth2Client from './OAuth2Client';

export type SubscriptionsType = {
	endDate?: string;
	name: string;
	perpetual: boolean;
	productPurchasedKey: string;
	provisionedCount: number;
	purchasedCount: number;
	startDate: string;
};

type LicenseTypePayload = {
	licenseEntry: {
		description: string;
		hostName: string;
		ipAddresses: string;
		macAddresses: string;
		orderId: string;
		productPurchaseKey: string;
	};
	skuId: number;
	type: string;
};

export type LicenseKey = {
	active: boolean;
	complimentary: boolean;
	createDate: string;
	description: string;
	expirationDate: string;
	hostName: string;
	id: number;
	ipAddresses: string;
	key: string;
	keyType: string;
	licenseType: string;
	macAddresses: string;
	modifiedDate: string;
	modifiedUserName: string;
	modifiedUserUuid: string;
	orderId: string;
	owner: string;
	productId: string;
	productName: string;
	productVersion: string;
	startDate: string;
	userName: string;
	userUuid: string;
};
class ProvisioningKoroneikiOAuth2 extends OAuth2Client {
	constructor() {
		super(
			'liferay-marketplace-etc-spring-boot-oauth-application-user-agent'
		);
	}

	async getSubscriptions(orderId: number): Promise<SubscriptionsType[]> {
		const response = await this.oAuth2Client.fetch(
			`/koroneiki/subscriptions/${orderId}`
		);

		return response.json();
	}

	async deactivateLicenseKey(licenseKey: number) {
		await this.oAuth2Client.fetch(
			`/provisioning/license-keys/${licenseKey}/deactivate`,
			{
				method: 'POST',
			}
		);
	}

	async getOrderLicenseKeys(
		orderId: string,
		searchParams: URLSearchParams = new URLSearchParams()
	): Promise<APIResponse<any>> {
		const response = (await (this.oAuth2Client.fetch(
			`/provisioning/order-license-keys/${orderId}?${searchParams.toString()}`
		) as unknown)) as Promise<APIResponse<any>>;

		return response;
	}

	async createLicenseKey(payload: LicenseTypePayload): Promise<LicenseKey> {
		return this.oAuth2Client.fetch('/provisioning/license-keys', {
			body: JSON.stringify(payload),
			method: 'POST',

			// Necessary due the response comes resolved already, not necessary to parse to .json()

		}) as unknown as Promise<LicenseKey>;
	}

	async downloadLicenseKey(id: number) {
		const response = await this.oAuth2Client.fetch(
			`/provisioning/license-keys/${id}/download`
		);

		const blob = await response.blob();

		let filename = 'license.xml';

		const contentDisposition = response.headers.get('content-disposition');

		if (contentDisposition) {
			filename = (
				contentDisposition
					.split(';')
					.find((n) => n.includes('filename=')) ?? ''
			)
				.replace('filename=', '')
				.replaceAll('"', '')
				.trim();
		}

		const anchor = document.createElement('a');

		anchor.download = filename;
		anchor.href = URL.createObjectURL(blob);

		document.body.appendChild(anchor);

		anchor.click();
		anchor.remove();
	}
}

export default ProvisioningKoroneikiOAuth2;
