/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {MarketplaceSpringBootOAuth2} from './OAuth2Client';
import {LicenseKey, LicenseTypePayload} from './types';

class ProvisioningOAuth2 extends MarketplaceSpringBootOAuth2 {
	async createLicenseKey(payload: LicenseTypePayload) {
		return this.post<LicenseKey>('/license-keys', payload, {
			earlyReturn: true,
		});
	}

	async downloadLicenseKey(id: number) {
		const response = await this.get<Response>(
			`/license-keys/${id}/download`,
			{
				earlyReturn: true,
			}
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

	async deactivateLicenseKey(licenseKey: number) {
		await this.post(`/license-keys/${licenseKey}/deactivate`);
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
