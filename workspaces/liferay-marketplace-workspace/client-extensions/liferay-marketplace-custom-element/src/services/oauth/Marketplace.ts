/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {downloadFile} from '../../utils/file';
import {MarketplaceSpringBootOAuth2} from './OAuth2Client';

class MarketplaceOAuth2 extends MarketplaceSpringBootOAuth2 {
	async downloadOrderReport(filter: string) {
		const response = await this.get<Response>(
			`/orders/export?filter=${filter}`,
			{earlyReturn: true}
		);

		await downloadFile('orders.csv', response);
	}

	async getMarketplaceProjectsKPI() {
		return this.get<{[key: string]: any[]}>('/projects/kpi').catch(
			() => ({})
		);
	}
}

const marketplaceOAuth2 = new MarketplaceOAuth2('/marketplace');

export default marketplaceOAuth2;
