/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {downloadFile} from '../../utils/file';
import {MarketplaceSpringBootOAuth2} from './OAuth2Client';
import {LicenseKey, LicenseTypePayload} from './types';

class ProvisioningOAuth2 extends MarketplaceSpringBootOAuth2 {
	async createLicenseKey(payload: LicenseTypePayload) {
		return this.post<LicenseKey>('/license-keys', payload, {
			earlyReturn: true,
		});
	}

	async deactivateLicenseKey(licenseKey: number) {
		await this.post(`/license-keys/${licenseKey}/deactivate`);
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

	async licenseKeysRenew(licenseKey: number) {
		await this.post(`/license-keys/${licenseKey}/renew`);
	}

	async getOrderDXPLicenseKeys(orderId: string) {
		return this.get<LicenseKey[]>(`/order-dxp-license-keys/${orderId}`, {
			earlyReturn: true,
		});
	}

	async getOrderLicenseKeys(
		orderId: string,
		searchParams: URLSearchParams = new URLSearchParams()
	) {
		return this.get<APIResponse>(
			`/order-license-keys/${orderId}?${searchParams.toString()}`,
			{earlyReturn: true}
		);
	}
}

const provisioningOAuth2 = new ProvisioningOAuth2('/provisioning');

export default provisioningOAuth2;
