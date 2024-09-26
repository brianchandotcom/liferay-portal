/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {MarketplaceSpringBootOAuth2} from './OAuth2Client';

class TrialOAuth2 extends MarketplaceSpringBootOAuth2 {
	async getAvailability(): Promise<Availability> {
		try {
			return this.get('/availability');
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

	async deleteTrial(orderId: string) {
		await this.delete(`/${orderId}`);
	}

	async provisioningTrial(orderId: number): Promise<any> {

		// No need to await the following request
		// Will be processed as a Job.

		this.post(`/provisioning/${orderId}`);
	}
}

const trialOAuth2 = new TrialOAuth2('/trial');

export default trialOAuth2;
