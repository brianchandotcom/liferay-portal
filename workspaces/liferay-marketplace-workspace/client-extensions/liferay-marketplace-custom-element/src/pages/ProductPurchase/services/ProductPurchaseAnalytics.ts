/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {ORDER_TYPES} from '../../../enums/Order';
import zodSchema, {z} from '../../../schema/zod';
import analyticsOAuth2 from '../../../services/oauth/Analytics';
import {sanitizeStringForURL} from '../../../utils/string';
import ProductPurchase from './ProductPurchase';

export default class ProductPurchaseAnalytics extends ProductPurchase {
	protected orderTypeExternalReferenceCode = ORDER_TYPES.ADDONS;

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

	public async create(form: z.infer<typeof zodSchema.analyticsProvisioning>) {
		const order = await super.createOrder();

		await this.startProvisioning(form, order.id);

		return order;
	}
}
