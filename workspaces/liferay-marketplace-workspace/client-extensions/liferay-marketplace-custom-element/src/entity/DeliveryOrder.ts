/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {ORDER_TYPES} from '../enums/Order';
import {safeJSONParse} from '../utils/util';
import {Statuses as OrderStatuses} from '../components/OrderStatus';

export default class MarketplaceDeliveryOrder {
	constructor(private order: PlacedOrder) {}

	get createDate() {
		return this.order.createDate;
	}

	get orderOptions() {
		return safeJSONParse<Array<{key: string; value: string[]}>>(
			this.order.placedOrderItems?.[0]?.options,
			[]
		);
	}

	get isDownloadable() {
		return [
			ORDER_TYPES.CLIENT_EXTENSION,
			ORDER_TYPES.COMPOSITE_APP,
			ORDER_TYPES.DXPAPP,
		].includes(this.order.orderTypeExternalReferenceCode as ORDER_TYPES);
	}

	get isFreeApp() {
		return (
			this.order.placedOrderItems?.[0]?.price?.price === 0 &&
			!this.orderOptions.some(({value}) => value.includes('trial'))
		);
	}

	get orderStatusIsNotCompleted() {
		return this.order.orderStatusInfo?.label !== OrderStatuses.COMPLETED;
	}
}
