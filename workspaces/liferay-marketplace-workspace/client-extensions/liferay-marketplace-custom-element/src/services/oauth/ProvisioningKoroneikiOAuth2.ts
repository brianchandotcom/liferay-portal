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
		return [
			{
				endDate: new Date().toISOString(),
				id: 54333,
				orderId,
				perpetual: true,
				provisionedCount: 2,
				purchasedCount: 3,
				startDate: new Date().toISOString(),
				subscriptionName: 'Trial',
			},
		];
	}

	async createLicenseKey(data: any) {
		await sleep(3000);

		return data;
	}

	async downloadLicenseKey(id: number) {
		const download = `${id} da license`;

		return download;
	}
}

export default new ProvisioningKoroneikiOAuth2();
