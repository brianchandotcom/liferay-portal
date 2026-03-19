/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {downloadFile} from '../../utils/file';
import {MarketplaceSpringBootOAuth2} from './OAuth2Client';
import {LicenseKey, LicenseTypePayload} from './types';

class ProvisioningOAuth2 extends MarketplaceSpringBootOAuth2 {
	async createAppLicenseKey(payload: LicenseTypePayload) {
		return this.post<LicenseKey>('/app-license-keys', payload, {
			earlyReturn: true,
		});
	}

	async createLicenseKeyTypeFree(payload: {
		assetReceiptLicenseUuid: number;
		domains: string;
		owner: string;
	}) {
		return this.post<Response>('/license-key-type-free', payload, {
			earlyReturn: false,
		});
	}

	async downloadLicenseKey(id: number) {
		const response = await this.get<Response>(
			`/license-keys/${id}/download`,
			{
				earlyReturn: true,
			}
		);

		await downloadFile('license.xml', response);
	}

	async downloadAppLicenseKey(id: number) {
		const response = await this.get<Response>(
			`/app-license-keys/${id}/download`,
			{
				earlyReturn: true,
			}
		);

		await downloadFile('license.xml', response);
	}

	async deactivateAppLicenseKey(licenseKey: number) {
		await this.post(`/app-license-keys/${licenseKey}/deactivate`);
	}

	async getOrderLicenseKeys(orderId: string) {
		return this.get<APIResponse<LicenseKey>>(
			`/order-license-keys/${orderId}`
		);
	}

	async getOrderAppLicenseKeys(
		orderId: string,
		searchParams: URLSearchParams = new URLSearchParams()
	) {
		return this.get<APIResponse>(
			`/order-app-license-keys/${orderId}?${searchParams.toString()}`,
			{earlyReturn: true}
		);
	}

	async licenseKeyTypeFreeRenew(licenseKey: number) {
		await this.post(`/license-key-type-free/${licenseKey}/renew`);
	}

	async provisionCMPBeta(payload: any) {
		return this.post('/cmp-beta-license-key', payload, {
			earlyReturn: true,
		});
	}
}

const provisioningOAuth2 = new ProvisioningOAuth2('/provisioning');

export default provisioningOAuth2;
