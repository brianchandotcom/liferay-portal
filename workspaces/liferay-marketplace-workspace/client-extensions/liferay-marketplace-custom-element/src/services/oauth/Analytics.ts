/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {MarketplaceSpringBootOAuth2} from './OAuth2Client';
import {ProjectDataSource} from './types';

class AnalyticsOAuth2 extends MarketplaceSpringBootOAuth2 {
	async getPages(
		searchParams: URLSearchParams = new URLSearchParams()
	): Promise<AnalyticsViews> {
		return this.get<AnalyticsViews>(`/pages?${searchParams.toString()}`);
	}

	async getProject(projectId: string) {
		return this.get<AnalyticsProject>(`/project/${projectId}`);
	}

	async getProjectEmailAddressDomains(projectId: string) {
		return this.get<string[]>(
			`/project/${projectId}/email-address-domains`
		);
	}

	async getProjectDataSourceToken(projectId: string) {
		return this.get<Promise<string>>(
			`/project/${projectId}/data-source/token`,
			{
				parseResponse: (response) => response.text(),
			}
		);
	}

	async getProjectDataSource(projectId: string) {
		return this.get<ProjectDataSource>(`/project/${projectId}/data-source`);
	}

	async provisioning(
		orderId: number,
		data: unknown
	): Promise<{groupId: number}> {
		return this.post(`/provisioning/${orderId}`, data);
	}
}

const analyticsOAuth2 = new AnalyticsOAuth2('/analytics');

export default analyticsOAuth2;
