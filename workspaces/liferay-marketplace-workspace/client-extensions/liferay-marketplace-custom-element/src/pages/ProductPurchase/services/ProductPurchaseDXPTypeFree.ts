/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import { z } from 'zod';

import { OrderCustomFields, OrderTypes } from '../../../enums/Order';
import { Liferay } from '../../../liferay/liferay';
import zodSchema from '../../../schema/zod';
import provisioningOAuth2 from '../../../services/oauth/Provisioning';
import HeadlessDXPFreeRequest from '../../../services/rest/HeadlessDXPFreeRequest';
import { getSiteURL } from '../../../utils/site';
import ProductPurchase from './ProductPurchase';

type ActivationKeyForm = z.infer<typeof zodSchema.activationKey>;

export default class ProductPurchaseDXPTypeFree extends ProductPurchase {
	private form?: ActivationKeyForm;
	protected orderTypeExternalReferenceCode = OrderTypes.DXP;

	setForm(form: ActivationKeyForm) {
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
				[OrderCustomFields.ORDER_METADATA]: JSON.stringify({
					dxpTypeFreeForm: this.form,
				}),
			},
		} as Cart;
	}

	public async createOrder() {
		if (!this.form) {
			throw new Error('Form is missing.');
		}

		const cart = this.getCart();
		console.log("🚀 => cart:", cart)

		const order = await super.createOrder(cart);

		// await provisioningOAuth2.createLicenseKeyTypeFree({
		// 	assetReceiptLicenseUuid: order.id,
		// 	domains: this.form.domain,
		// 	owner:
		// 		this.form.businessEmail ||
		// 		Liferay.ThemeDisplay.getUserEmailAddress(),
		// });

		await HeadlessDXPFreeRequest.createDXPFreeRequest({
			...this.form,
			r_orderToDXPFreeActivationKeyRequest_commerceOrderId: order.id,
		});

		return order;
	}

	public async getNextStepsLink(cart: Cart) {
		if (cart.orderTypeExternalReferenceCode !== OrderTypes.DXP) {
			return super.getNextStepsLink(cart);
		}

		return `${window.location.origin}${getSiteURL()}/customer-dashboard#/products/${cart.id}?next-steps`;
	}
}
