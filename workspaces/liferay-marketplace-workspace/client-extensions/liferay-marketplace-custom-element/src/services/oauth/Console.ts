/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {MarketplaceSpringBootOAuth2} from './OAuth2Client';
import {ConsoleProjectsUsage} from './types';

class ConsoleOAuth2 extends MarketplaceSpringBootOAuth2 {
	async getProjectsUsage() {
		return this.get<ConsoleProjectsUsage>('/projects-usage');
	}

	async provisioning(
		orderId: number,
		data: {orderItemId: number; projectId: string}
	): Promise<void> {
		return this.post(`/provisioning/${orderId}`, data);
	}

	async uninstallApp(
		orderId: number,
		data: {
			id: string;
			orderItemId: number;
		}
	): Promise<void> {
		return this.post(`/uninstall-app/${orderId}`, data);
	}
}

const consoleOAuth2 = new ConsoleOAuth2('/console');

export default consoleOAuth2;
