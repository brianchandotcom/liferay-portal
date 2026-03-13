/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {z} from 'zod';

import {OrderTypes} from '../../../enums/Order';
import zodSchema from '../../../schema/zod';
import marketplaceOAuth2 from '../../../services/oauth/Marketplace';
import {getSiteURL} from '../../../utils/site';
import ProductPurchase from './ProductPurchase';

type getLicenseKeyForm = z.infer<typeof zodSchema.generateLicenseKey>;

export class ProductPurchaseCMP extends ProductPurchase {
	private form?: getLicenseKeyForm;
	protected orderTypeExternalReferenceCode = OrderTypes.CMP;

	setForm(form: getLicenseKeyForm) {
		this.form = form;
	}

	protected getCart() {
		const baseCart = super.getCart();
		const cartItems = super.getCartItems();

		return {
			...baseCart,
			cartItems,
			customFields: {
				...baseCart?.customFields,
			},
		} as Cart;
	}

	public async createOrder() {
		if (!this.form) {
			throw new Error('Form is missing.');
		}

		const cart = this.getCart();

		const order = await super.createOrder(cart);

		await marketplaceOAuth2.provisionCMPBeta({
			description: this.form.description,
			hostName: this.form.hostname,
			ipAddresses: this.form.ipAddress,
			macAddresses: this.form.macAddress,
			orderId: order.id,
			productId: this.product.id,
		});

		return order;
	}

	public async getNextStepsLink(cart: Cart) {
		if (cart.orderTypeExternalReferenceCode !== OrderTypes.CMP) {
			return super.getNextStepsLink(cart);
		}

		return `${window.location.origin}${getSiteURL()}/customer-dashboard#/products/${cart.id}/activation-keys`;
	}
}
