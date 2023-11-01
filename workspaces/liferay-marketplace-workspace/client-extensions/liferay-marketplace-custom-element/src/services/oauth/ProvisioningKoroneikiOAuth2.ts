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
	};
	productPurchaseKey: string;
	skuId: number;
	type: string;
};

type LicenseKey = {
	active: boolean;
	complimentary: boolean;
	createDate: string;
	description: string;
	expirationDate: string;
	hostName: string;
	id: number;
	ipAddresses: string;
	key: string;
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

	async getOrderLicenseKeys(orderId: string) {
		return this.oAuth2Client.fetch(
			`/provisioning/order-license-keys/${orderId}`
		);
	}

	async createLicenseKey(payload: LicenseTypePayload): Promise<LicenseKey> {
		const response = await this.oAuth2Client.fetch(
			'/provisioning/license-keys',
			{
				body: JSON.stringify(payload),
				method: 'POST',
			}
		);

		return response.json();
	}

	downloadLicenseKey(id: number) {
		const anchor = document.createElement('a');

		anchor.href =
			this.oAuth2Client.homePageURL +
			`/provisioning/license-keys/${id}/download`;

		document.body.appendChild(anchor);
		anchor.click();
		anchor.remove();
	}
}

export default ProvisioningKoroneikiOAuth2;
