/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {OrderCustomFields, OrderTypes} from '../../../enums/Order';
import {Liferay} from '../../../liferay/liferay';
import zodSchema, {z} from '../../../schema/zod';
import provisioningOAuth2 from '../../../services/oauth/Provisioning';
import HeadlessDSRRequest from '../../../services/rest/HeadlessDSRRequest';
import {getSiteURL} from '../../../utils/site';
import ProductPurchase from './ProductPurchase';

type DSRForm = z.infer<typeof zodSchema.dsrLicenseKey>;

const digitalSalesRoomERC = crypto.randomUUID();

export default class ProductPurchaseDSR extends ProductPurchase {
	private form?: DSRForm;
	private hasAnalyticsCloud = false;
	protected orderTypeExternalReferenceCode = OrderTypes.DSR;

	setForm(form: DSRForm) {
		this.form = form;
	}

	setHasAnalyticsCloud(hasAnalyticsCloud: boolean) {
		this.hasAnalyticsCloud = hasAnalyticsCloud;
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
					analyticsForm: this.getAnalyticsForm(),
					dsrForm: this.getDSRForm(),
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

		const analyticsForm = this.getAnalyticsForm();
		const dsrForm = this.getDSRForm();

		await HeadlessDSRRequest.putDSRRequest(
			{
				...analyticsForm,
				...dsrForm,
				incidentReportEmailAddresses:
					analyticsForm?.incidentReportEmailAddresses.join(','),
				r_orderToDSRRequest_commerceOrderId: order.id,
			},
			digitalSalesRoomERC
		).catch(console.error);

		await provisioningOAuth2.provisionDSRBeta({
			analyticsForm,
			licenseEntry: {
				description: 'Beta Access DSR',
				hostName: dsrForm.hostName,
				ipAddresses: dsrForm.ipAddresses?.replaceAll('\n', ','),
				macAddresses: dsrForm.macAddresses?.replaceAll('\n', ','),
				orderId: String(order.id),
			},
		});

		return order;
	}

	public async getNextStepsLink(cart: Cart) {
		return `${window.location.origin}${getSiteURL()}/customer-dashboard#/products/${cart.id}?next-steps`;
	}

	private getAnalyticsForm() {
		if (this.hasAnalyticsCloud) {
			return;
		}

		const ownerEmailAddress =
			this.form?.workspaceOwnerEmail?.trim() ||
			Liferay.ThemeDisplay.getUserEmailAddress();

		return {
			corpProjectName: this.form?.workspaceName ?? '',
			corpProjectUuid: this.account.externalReferenceCode,
			incidentReportEmailAddresses: [ownerEmailAddress],
			name: this.form?.workspaceName ?? '',
			ownerEmailAddress,
			serverLocation: this.form?.dataCenterLocation ?? '',
		};
	}

	private getDSRForm() {
		return {
			acceptTermsAndConditions:
				this.form?.acceptTermsAndConditions ?? false,
			dataCenterLocation: this.form?.dataCenterLocation ?? '',
			hostName: this.form?.hostname?.trim() ?? '',
			ipAddresses: this.form?.ipAddress?.replaceAll('\n', ','),
			macAddresses: this.form?.macAddress?.replaceAll('\n', ','),
			workspaceName: this.form?.workspaceName ?? '',
			workspaceOwnerEmail: this.form?.workspaceOwnerEmail ?? '',
		};
	}
}
