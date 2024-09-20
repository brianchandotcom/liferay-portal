/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {ORDER_TYPES} from '../../../enums/Order';
import MarketplaceSpringBootOAuth2 from '../../../services/oauth/MarketplaceSpringBootOAuth2';
import ProductPurchase from './ProductPurchase';

const marketplaceSpringBootOAuth2 = new MarketplaceSpringBootOAuth2();

export default class ProductPurchaseSolutionTrial extends ProductPurchase {
	protected orderTypeExternalReferenceCode = ORDER_TYPES.SOLUTIONS7;

	public async createOrder(): Promise<Cart> {
		const order = await super.createOrder();

		await marketplaceSpringBootOAuth2.provisioningTrial(order.id);

		return order;
	}

	public async isTrialInHold() {
		const trialAvailability =
			await marketplaceSpringBootOAuth2.getTrialAvailability();

		return trialAvailability.fallback
			? false
			: trialAvailability.available === 0;
	}
}
