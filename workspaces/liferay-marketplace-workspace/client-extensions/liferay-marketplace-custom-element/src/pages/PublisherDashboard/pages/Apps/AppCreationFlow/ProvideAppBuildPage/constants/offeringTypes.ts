/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {PRODUCT_OFFERING_TYPES} from '../../../../../../../enums/Product';
import {ProductType} from '../../../../../../../enums/ProductType';

const ALL_OFFERINGS = [
	PRODUCT_OFFERING_TYPES.LIFERAY_PAAS,
	PRODUCT_OFFERING_TYPES.LIFERAY_SAAS,
	PRODUCT_OFFERING_TYPES.LIFERAY_SELF_HOSTED,
];

const offeringTypes = {
	'client-extensions': ALL_OFFERINGS,
	'cloud': [PRODUCT_OFFERING_TYPES.LIFERAY_SAAS],
	'composite-app': [PRODUCT_OFFERING_TYPES.LIFERAY_SELF_HOSTED],
	'dxp': [
		PRODUCT_OFFERING_TYPES.LIFERAY_PAAS,
		PRODUCT_OFFERING_TYPES.LIFERAY_SELF_HOSTED,
	],
	'low-code-configuration': ALL_OFFERINGS,
};

export function getOfferingTypes(type: ProductType) {
	return offeringTypes[type as keyof typeof offeringTypes];
}
