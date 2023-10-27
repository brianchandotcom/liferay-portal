/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import OAuth2Client from './OAuth2Client';

const sleep = (timer: number) =>
	new Promise((resolve) => setTimeout(resolve, timer));

class ProvisioningKoroneikiOAuth2 extends OAuth2Client {
	constructor() {
		super(
			'liferay-marketplace-etc-spring-boot-oauth-application-user-agent'
		);
	}

	async getLicenseKeys(orderId: number) {
		// const response = await this.oAuth2Client.fetch(
		// 	`/koroneiki/license-keys/${orderId}`,
		// 	{
		// 		method: 'GET',
		// 	}
		// );

		// return response

		// eslint-disable-next-line no-console
		console.log('orderIdAPI', orderId);

		return [
			{
				endDate: 'DNE',
				name: 'standard',
				perpetual: true,
				productPurchasedKey: 'KOR-26360233',
				provisionedCount: 0,
				purchasedCount: 3,
				startDate: '2023-10-24T21:18:43Z',
			},
			{
				endDate: '2024-10-24T21:18:43Z',
				name: 'developer',
				perpetual: false,
				productPurchasedKey: 'KOR-26360233',
				provisionedCount: 0,
				purchasedCount: 3,
				startDate: '2023-10-24T21:18:43Z',
			},
		];
	}

	async createLicenseKey(data: any) {
		await sleep(3000);

		// const payload = {
		// 	description: 'Redacted',
		// 	expirationDate: '2122-10-09T00:00:00Z',
		// 	hostName: 'Redacted',
		// 	ipAddresses: 'Redacted',
		// 	licenseType: 'production',
		// 	macAddresses: 'Redacted',
		// 	startDate: '2021-11-02T00:00:00Z',
		// };

		// await this.oAuth2Client.fetch("/provisioning/license-keys", {
		// 	            body: JSON.stringify(data),
		// 	            method: "POST"
		// 	        })
		// 	    }

		return data;
	}

	async downloadLicenseKey(id: number) {
		// await this.oAuth2Client.fetch(`/provisioning/license-keys/${id}/download`, {
		// 		            body: JSON.stringify(data),
		// 		            method: "POST"
		// 		        })
		// 		    }

		const download = `${id} da license`;

		return download;
	}
}

export default new ProvisioningKoroneikiOAuth2();
