/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {OrderCustomFields, OrderTypes} from '../../../enums/Order';
import {Liferay} from '../../../liferay/liferay';
import zodSchema, {z} from '../../../schema/zod';
import provisioningOAuth2 from '../../../services/oauth/Provisioning';
import {getSiteURL} from '../../../utils/site';
import {sanitizeStringForURL} from '../../../utils/string';
import ProductPurchase from './ProductPurchase';

type LDPProvisioningForm = z.infer<typeof zodSchema.ldpProvisioning>;

export default class ProductPurchaseLDP extends ProductPurchase {
	private form?: LDPProvisioningForm & {salesforceProjectId?: string};
	protected orderTypeExternalReferenceCode = OrderTypes.ADDONS;

	setForm(form: LDPProvisioningForm & {salesforceProjectId?: string}) {
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
					analyticsForm: this.getAnalyticsForm(),
					productKey,
					productPurchaseKey,
					salesforceProjectId: this.form?.salesforceProjectId,
				}),
			},
		} as Cart;
	}

	public async getNextStepsLink(cart: Cart) {
		return `${window.location.origin}${getSiteURL()}/next-steps?orderId=${cart.id}`;
	}

	private getAnalyticsForm() {
		return {
			allowedEmailDomains: this.form?.allowedEmailDomains,
			corpProjectName: this.form?.workspaceName,
			corpProjectUuid: this.account.externalReferenceCode,
			friendlyURL: this.form?.friendlyWorkspaceURL
				? `/${sanitizeStringForURL(this.form?.friendlyWorkspaceURL)}`
				: '',
			incidentReportEmailAddresses: this.form?.incidentReportContacts,
			name: this.form?.workspaceName,
			ownerEmailAddress:
				this.form?.workspaceOwnerEmail ||
				Liferay.ThemeDisplay.getUserEmailAddress(),
			serverLocation: this.form?.dataCenterLocation,
			workspaceName: this.form?.workspaceName,
		};
	}

	public async createOrder(cart: Cart) {
		if (!this.form) {
			throw new Error('Form is missing.');
		}

		const order = await super.createOrder({...cart, ...this.getCart()});

		const analyticsForm = this.getAnalyticsForm();

		await provisioningOAuth2.provisionLDPBeta({
			analyticsForm,
			orderId: order.id,
		});

		return order;
	}
}
