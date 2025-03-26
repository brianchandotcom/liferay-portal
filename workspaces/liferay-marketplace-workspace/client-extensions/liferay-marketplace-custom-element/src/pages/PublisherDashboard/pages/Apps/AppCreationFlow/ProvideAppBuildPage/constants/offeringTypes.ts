/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {
	ProductOfferingTypes,
	ProductType,
} from '../../../../../../../enums/Product';

const ALL_OFFERINGS = [
	ProductOfferingTypes.LIFERAY_PAAS,
	ProductOfferingTypes.LIFERAY_SAAS,
	ProductOfferingTypes.LIFERAY_SELF_HOSTED,
];

const offeringTypes = {
	'client-extension': ALL_OFFERINGS,
	'cloud': [ProductOfferingTypes.LIFERAY_SAAS],
	'composite-app': [ProductOfferingTypes.LIFERAY_SELF_HOSTED],
	'dxp': [
		ProductOfferingTypes.LIFERAY_PAAS,
		ProductOfferingTypes.LIFERAY_SELF_HOSTED,
	],
	'low-code-configuration': ALL_OFFERINGS,
};

export function getOfferingTypes(type: ProductType) {
	return offeringTypes[type as keyof typeof offeringTypes];
}
