/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import OAuth2Client from './OAuth2Client';
import {
	ConsoleProjectsUsage,
	LicenseKey,
	LicenseTypePayload,
	ProjectDataSource,
	SubscriptionsType,
} from './types';

export default class MarketplaceSpringBootOAuth2 extends OAuth2Client {
	constructor() {
		super(
			'liferay-marketplace-etc-spring-boot-oauth-application-user-agent'
		);
	}

	async getAnalyticsProject(projectId: string): Promise<AnalyticsProject> {
		const response = await this.oAuth2Client.fetch(
			`/analytics/project/${projectId}`
		);

		return response.json() as Promise<AnalyticsProject>;
	}

	async getAnalyticsProjectEmailAddressDomains(projectId: string) {
		const response = await this.oAuth2Client.fetch(
			`/analytics/project/${projectId}/email-address-domains`
		);

		return response.json() as Promise<string[]>;
	}

	async getAnalyticsProjectDataSourceToken(projectId: string) {
		const response = await this.oAuth2Client.fetch(
			`/analytics/project/${projectId}/data-source/token`
		);

		return response.text() as Promise<string>;
	}

	async getAnalyticsProjectDataSource(projectId: string) {
		const response = await this.oAuth2Client.fetch(
			`/analytics/project/${projectId}/data-source`
		);

		return response.json() as Promise<ProjectDataSource>;
	}

	async getAnalyticsPages(
		searchParams: URLSearchParams = new URLSearchParams()
	): Promise<AnalyticsViews> {
		const response = await this.oAuth2Client.fetch(
			`/analytics/pages?${searchParams.toString()}`
		);

		return response.json() as Promise<AnalyticsViews>;
	}

	async provisioningAnalyticsCloud(
		orderId: number,
		data: unknown
	): Promise<{groupId: number}> {
		const response = await this.oAuth2Client.fetch(
			`/analytics/provisioning/${orderId}`,
			{
				body: JSON.stringify(data),
				method: 'POST',
			}
		);

		return response.json();
	}

	async provisioningCloudApp(
		orderId: number,
		data: {orderItemId: number; projectId: string}
	): Promise<any> {
		return this.oAuth2Client.fetch(`/console/provisioning/${orderId}`, {
			body: JSON.stringify(data),
			method: 'POST',
		});
	}

	async provisioningTrial(orderId: number): Promise<any> {

		// No need to await the following request
		// Will be processed as a Job.

		this.oAuth2Client.fetch(`/trial/provisioning/${orderId}`, {
			method: 'POST',
		});
	}

	async createLicenseKey(payload: LicenseTypePayload): Promise<LicenseKey> {
		return this.oAuth2Client.fetch('/provisioning/license-keys', {
			body: JSON.stringify(payload),
			method: 'POST',

			// Necessary due the response comes resolved already, not necessary to parse to .json()

		}) as unknown as Promise<LicenseKey>;
	}

	async deactivateLicenseKey(licenseKey: number) {
		await this.oAuth2Client.fetch(
			`/provisioning/license-keys/${licenseKey}/deactivate`,
			{
				method: 'POST',
			}
		);
	}

	async deleteTrial(orderId: string) {
		await this.oAuth2Client.fetch(`/trial/${orderId}`, {method: 'DELETE'});
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

	async getOrderLicenseKeys(
		orderId: string,
		searchParams: URLSearchParams = new URLSearchParams()
	): Promise<APIResponse<any>> {
		const response = (await (this.oAuth2Client.fetch(
			`/provisioning/order-license-keys/${orderId}?${searchParams.toString()}`
		) as unknown)) as Promise<APIResponse<any>>;

		return response;
	}

	async getProductUsages(): Promise<ConsoleProjectsUsage> {
		const response = await this.oAuth2Client.fetch(
			'/console/projects-usage'
		);

		return response.json();
	}

	async getTrialAvailability(): Promise<Availability> {
		try {
			const response = await this.oAuth2Client.fetch(
				'/trial/availability'
			);

			return response.json();
		}
		catch {
			return {
				active: false,
				available: 0,
				fallback: true,
				max: 0,
			};
		}
	}

	async getSubscriptions(orderId: number): Promise<SubscriptionsType[]> {
		const response = await this.oAuth2Client.fetch(
			`/koroneiki/subscriptions/${orderId}`
		);

		return response.json();
	}

	async syncKoroneikiProduct(productId: number) {
		const response = await this.oAuth2Client.fetch(
			`/koroneiki/product/${productId}`,
			{
				method: 'POST',
			}
		);

		if (!response.ok) {
			throw new Error('Unable to Sync Product');
		}
	}
}
