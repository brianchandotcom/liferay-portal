/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {OrderCustomFields, OrderTypes} from '../../../enums/Order';
import zodSchema, {z} from '../../../schema/zod';
import HeadlessCommerceDeliveryCart from '../../../services/rest/HeadlessCommerceDeliveryCart';
import {getSiteURL} from '../../../utils/site';
import {sanitizeStringForURL} from '../../../utils/string';
import ProductPurchase from './ProductPurchase';

type CDPProvisioningForm = z.infer<typeof zodSchema.cdpProvisioning>;

export default class ProductPurchaseCDP extends ProductPurchase {
	private form?: CDPProvisioningForm;
	protected orderTypeExternalReferenceCode = OrderTypes.ADDONS;

	setForm(form: CDPProvisioningForm) {
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
					provisioning: {
						corpProjectName: this.form?.workspaceName,
						corpProjectUuid: this.account.externalReferenceCode,
						emailAddressDomains: this.form?.allowedEmailDomains,
						friendlyURL: this.form?.friendlyWorkspaceURL
							? `/${sanitizeStringForURL(this.form?.friendlyWorkspaceURL)}`
							: '',
						incidentReportEmailAddresses:
							this.form?.incidentReportContacts,
						name: this.form?.workspaceName,
						ownerEmailAddress: this.form?.workspaceOwnerEmail,
						serverLocation: this.form?.dataCenterLocation,
					},
					productKey,
					productPurchaseKey,
				}),
			},
		} as Cart;
	}

	public async getNextStepsLink(cart: Cart) {
		return `${window.location.origin}${getSiteURL()}/next-steps?orderId=${cart.id}`;
	}

	public async createOrder() {
		if (!this.form) {
			throw new Error('Form is missing.');
		}

		const order = await super.createOrder();

		return order;
	}
}
