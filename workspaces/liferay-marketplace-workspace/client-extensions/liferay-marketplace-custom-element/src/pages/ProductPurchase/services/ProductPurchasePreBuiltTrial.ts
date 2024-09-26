/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {ORDER_TYPES} from '../../../enums/Order';
import trialOAuth2 from '../../../services/oauth/Trial';
import ProductPurchase from './ProductPurchase';

export default class ProductPurchaseSolutionTrial extends ProductPurchase {
	protected orderTypeExternalReferenceCode = ORDER_TYPES.SOLUTIONS7;

	public async createOrder(): Promise<Cart> {
		const order = await super.createOrder();

		await trialOAuth2.provisioningTrial(order.id);

		return order;
	}

	public async isTrialInHold() {
		const trialAvailability = await trialOAuth2.getAvailability();

		return trialAvailability.fallback
			? false
			: trialAvailability.available === 0;
	}
}
