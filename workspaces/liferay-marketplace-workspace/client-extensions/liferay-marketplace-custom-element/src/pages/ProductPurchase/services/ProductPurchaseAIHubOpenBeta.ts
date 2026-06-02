/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {z} from 'zod';

import {OrderCustomFields, OrderTypes} from '../../../enums/Order';
import {SkuOptions} from '../../../enums/Product';
import zodSchema from '../../../schema/zod';
import {getSkuByOptionValueKey} from '../../../utils/productUtils';
import {getSiteURL} from '../../../utils/site';
import ProductPurchase from './ProductPurchase';

type AIHubOpenBetaForm = z.infer<typeof zodSchema.aiHubOpenBetaForm>;

export class ProductPurchaseAIHubOpenBeta extends ProductPurchase {
	private form?: AIHubOpenBetaForm;
	protected orderTypeExternalReferenceCode = OrderTypes.AI_HUB;

	setForm(form: AIHubOpenBetaForm) {
		this.form = form;
	}

	protected getCart() {
		const sku = getSkuByOptionValueKey(this.product, SkuOptions.OPEN_BETA);

		const baseCart = super.getCart();
		const cartItems = super.getCartItems(sku?.id);

		return {
			...baseCart,
			cartItems,
			customFields: {
				...baseCart?.customFields,
				[OrderCustomFields.ORDER_METADATA]: JSON.stringify({
					aiHubForm: this.form,
				}),
			},
		} as Cart;
	}

	public async createOrder() {
		if (!this.form) {
			throw new Error('Form is missing.');
		}

		const cart = this.getCart();

		const order = await super.createOrder(cart);

		return order;
	}

	public async getNextStepsLink(cart: Cart) {
		if (cart.orderTypeExternalReferenceCode !== OrderTypes.AI_HUB) {
			return super.getNextStepsLink(cart);
		}

		return `${window.location.origin}${getSiteURL()}/next-steps?orderId=${cart.id}`;
	}
}
