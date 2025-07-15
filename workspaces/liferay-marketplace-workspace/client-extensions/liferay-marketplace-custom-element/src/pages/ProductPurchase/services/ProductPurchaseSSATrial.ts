/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {OrderTypes} from '../../../enums/Order';
import trialOAuth2 from '../../../services/oauth/Trial';
import ProductPurchase from './ProductPurchase';

export default class ProductPurchaseSSATrial extends ProductPurchase {
	protected orderTypeExternalReferenceCode = OrderTypes.SSA_SAAS;

	public async createOrder(cart?: Cart): Promise<Cart> {
		const order = await super.createOrder(cart);

		// await trialOAuth2.provisioningTrial(order.id);

		return order;
	}

	public async getDemoAvailability(projectId: string) {
		return trialOAuth2.getDemoAvailability(projectId);
	}

	public async isTrialOnHold() {
		const trialAvailability = await trialOAuth2.getAvailability();

		return trialAvailability.fallback
			? false
			: trialAvailability.available === 0;
	}
}
