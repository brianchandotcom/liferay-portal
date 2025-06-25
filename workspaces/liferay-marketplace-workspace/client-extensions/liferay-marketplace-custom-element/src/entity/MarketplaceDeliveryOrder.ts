/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {OrderTypes, OrderWorkflowStatusCode} from '../enums/Order';

export default class MarketplaceDeliveryOrder {
	constructor(private order: PlacedOrder) {}

	get createDate() {
		return this.order.createDate;
	}

	get canDownload() {
		return [
			OrderTypes.CLIENT_EXTENSION,
			OrderTypes.COMPOSITE_APP,
			OrderTypes.DXPAPP,
			OrderTypes.LOW_CODE_CONFIGURATION,
			OrderTypes.OTHER,
		].includes(this.order.orderTypeExternalReferenceCode as OrderTypes);
	}

	get isFreeApp() {
		return this.order.placedOrderItems?.[0]?.price?.price === 0;
	}

	get isOrderCompleted() {
		return (
			this.order.orderStatusInfo?.code ===
			OrderWorkflowStatusCode.COMPLETED
		);
	}

	get dxpProvisioningEnabled() {
		return (
			this.order.orderTypeExternalReferenceCode === OrderTypes.DXPAPP &&
			!this.isFreeApp
		);
	}
}
