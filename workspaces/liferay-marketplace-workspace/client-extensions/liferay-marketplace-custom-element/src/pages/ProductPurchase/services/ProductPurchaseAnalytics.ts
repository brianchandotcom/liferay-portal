/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {OrderTypes} from '../../../enums/Order';
import zodSchema, {z} from '../../../schema/zod';
import analyticsOAuth2 from '../../../services/oauth/Analytics';
import {sanitizeStringForURL} from '../../../utils/string';
import ProductPurchase from './ProductPurchase';

type AnalyticsProvisioningForm = z.infer<
	typeof zodSchema.analyticsProvisioning
>;

export default class ProductPurchaseAnalytics extends ProductPurchase {
	private form?: AnalyticsProvisioningForm;
	protected orderTypeExternalReferenceCode = OrderTypes.ADDONS;

	private async startProvisioning(
		form: z.infer<typeof zodSchema.analyticsProvisioning>,
		orderId: number
	) {
		return analyticsOAuth2.provisioning(orderId, {
			corpProjectName: form.workspaceName,
			corpProjectUuid: `KOR-${new Date().getTime()}`,
			emailAddressDomains: form.allowedEmailDomains,
			friendlyURL: form.friendlyWorkspaceURL
				? `/${sanitizeStringForURL(form.friendlyWorkspaceURL)}`
				: 'TBD',
			incidentReportEmailAddresses: form.incidentReportContacts,
			name: form.workspaceName,
			ownerEmailAddress: form.workspaceOwnerEmail,
			timeZoneId: form.timezone,
		});
	}

	setForm(form: AnalyticsProvisioningForm) {
		this.form = form;
	}

	public async createOrder() {
		if (!this.form) {
			throw new Error('Form is missing.');
		}

		const order = await super.createOrder();

		await this.startProvisioning(this.form, order.id);

		return order;
	}
}
