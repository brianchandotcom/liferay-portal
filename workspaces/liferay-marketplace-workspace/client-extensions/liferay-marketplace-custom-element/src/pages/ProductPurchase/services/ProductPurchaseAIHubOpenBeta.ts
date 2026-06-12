/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {z} from 'zod';

import {OrderCustomFields, OrderTypes} from '../../../enums/Order';
import zodSchema from '../../../schema/zod';
import ProductPurchase from './ProductPurchase';

type AIHubOpenBetaForm = z.infer<typeof zodSchema.aiHubOpenBetaForm> & {
	salesforceProjectId: string;
};

export class ProductPurchaseAIHubOpenBeta extends ProductPurchase {
	private form?: AIHubOpenBetaForm;
	protected orderTypeExternalReferenceCode = OrderTypes.AI_HUB;

	setForm(form: AIHubOpenBetaForm) {
		this.form = form;
	}

	protected getCart() {
		return {
			accountId: this.account?.id,
			customFields: {
				[OrderCustomFields.ORDER_METADATA]: JSON.stringify({
					aiHubForm: this.form,
					salesforceProjectId: this.form?.salesforceProjectId,
				}),
			},
		} as Cart;
	}

	public async createOrder(cart: Cart, cartOptions: any) {
		if (!this.form) {
			throw new Error('Form is missing.');
		}

		return super.createOrder(
			{
				...cart,
				...this.getCart(),
			},
			cartOptions
		);
	}

	public async getNextStepsLink(cart: Cart) {
		return super.getPaymentNextStepsLink(cart);
	}
}
