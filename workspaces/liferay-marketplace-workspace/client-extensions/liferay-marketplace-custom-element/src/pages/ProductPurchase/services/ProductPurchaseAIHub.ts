/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {z} from 'zod';

import {OrderCustomFields, OrderTypes} from '../../../enums/Order';
import zodSchema from '../../../schema/zod';
import {getSiteURL} from '../../../utils/site';
import ProductPurchase from './ProductPurchase';

type AIHubForm = z.infer<typeof zodSchema.aiHubForm>;

export class ProductPurchaseAIHub extends ProductPurchase {
	private form?: AIHubForm;
	protected orderTypeExternalReferenceCode = OrderTypes.AI_HUB;

	setForm(form: AIHubForm) {
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
					aiHubForm: {
						administrationEmail: this.form?.administrationEmail,
						aiHubAccountName: this.form?.aiHubAccountName,
						businessEmail: this.form?.businessEmail,
						companyName: this.form?.companyName,
						country: this.form?.country,
						extension: this.form?.extension,
						fullName: this.form?.fullname,
						intlCode: this.form?.intlCode.code,
						jobTitle: this.form?.jobTitle,
						phoneNumber: this.form?.phoneNumber,
						purpose: this.form?.purpose,
						purposeOther: this.form?.purposeOther,
					},
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
