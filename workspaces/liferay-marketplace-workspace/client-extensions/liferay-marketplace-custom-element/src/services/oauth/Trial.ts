/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {MarketplaceSpringBootOAuth2} from './OAuth2Client';

export type Availability = {
	active: boolean;
	available: number;
	fallback: boolean;
	max: number;
};

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

	async getDemoAvailability(projectId: string) {
		try {
			return this.get(`/demo-availability/${projectId}`);
		}
		catch (error) {
			console.error(error);
		}
	}

	async deleteTrial(orderId: number | string) {
		await this.delete(`/${orderId}`);
	}

	async extendTrial(orderId: number | string, duration: number = 7) {
		return this.post(`/extend/${orderId}`, {duration});
	}

	async extendTrialRequest(orderId: number | string, duration: number = 7) {
		return this.post(`/extend-admin-request/${orderId}`, {duration});
	}

	async provisioningTrial(orderId: number): Promise<any> {

		// No need to await the following request
		// Will be processed as a Job.

		this.post(`/provisioning/${orderId}`);
	}
}

const trialOAuth2 = new TrialOAuth2('/trial');

export default trialOAuth2;
