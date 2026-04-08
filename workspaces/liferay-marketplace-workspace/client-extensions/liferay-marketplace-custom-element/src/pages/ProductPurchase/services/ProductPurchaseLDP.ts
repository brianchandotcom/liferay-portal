/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {OrderCustomFields, OrderTypes} from '../../../enums/Order';
import zodSchema, {z} from '../../../schema/zod';
import {getSiteURL} from '../../../utils/site';
import {sanitizeStringForURL} from '../../../utils/string';
import ProductPurchase from './ProductPurchase';

type LDPProvisioningForm = z.infer<typeof zodSchema.ldpProvisioning>;

export default class ProductPurchaseLDP extends ProductPurchase {
	private form?: LDPProvisioningForm & {salesforceProjectId: string};
	protected orderTypeExternalReferenceCode = OrderTypes.ADDONS;

	setForm(form: LDPProvisioningForm & {salesforceProjectId: string}) {
		this.form = form;
	}

	protected getCart() {
		const {productKey, productPurchaseKey} = this.form ?? {};

		const sku = this.product.skus.find(
			({externalReferenceCode}) => externalReferenceCode === productKey
		);

		const baseCart = super.getCart();
		const cartItems = super.getCartItems(sku?.id);

		return {
			...baseCart,
			cartItems,
			customFields: {
				...baseCart?.customFields,
				[OrderCustomFields.ORDER_METADATA]: JSON.stringify({
					productKey,
					productPurchaseKey,
					provisioningForm: {
						allowedEmailDomains: this.form?.allowedEmailDomains,
						corpProjectName: this.form?.workspaceName,
						corpProjectUuid: this.account.externalReferenceCode,
						dataCenterLocation: this.form?.dataCenterLocation,
						friendlyWorkspaceURL: this.form?.friendlyWorkspaceURL
							? `/${sanitizeStringForURL(this.form?.friendlyWorkspaceURL)}`
							: '',
						incidentReportEmailAddresses:
							this.form?.incidentReportContacts,
						ownerEmailAddress: this.form?.workspaceOwnerEmail,
						workspaceName: this.form?.workspaceName,
					},
					salesforceProjectId: this.form?.salesforceProjectId,
				}),
			},
		} as Cart;
	}

	public async getNextStepsLink(cart: Cart) {
		return `${window.location.origin}${getSiteURL()}/next-steps?orderId=${cart.id}`;
	}

	public async createOrder(cart: Cart) {
		if (!this.form) {
			throw new Error('Form is missing.');
		}

		return super.createOrder({...cart, ...this.getCart()});
	}
}
