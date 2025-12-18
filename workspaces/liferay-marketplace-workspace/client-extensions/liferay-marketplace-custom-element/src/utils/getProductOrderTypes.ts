/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {OrderTypes} from '../enums/Order';
import {ProductType} from '../enums/Product';

const productTypeERC = {
	[ProductType.CLIENT_EXTENSION]: OrderTypes.CLIENT_EXTENSION,
	[ProductType.CLOUD]: OrderTypes.CLOUD_APP,
	[ProductType.COMPOSITE_APP]: OrderTypes.COMPOSITE_APP,
	[ProductType.DXP]: OrderTypes.DXP_APP,
	[ProductType.LOW_CODE_CONFIGURATION]: OrderTypes.LOW_CODE_CONFIGURATION,
	[ProductType.OTHER]: OrderTypes.OTHER,
	[ProductType.SSA_SAAS]: OrderTypes.SSA_SAAS,
} as const;

export function getProductOrderTypes(productSpecificationValue: string) {
	const productSpecification = productSpecificationValue.toLowerCase();

	return {
		externalReferenceCode:
			productTypeERC[productSpecification as ProductType] || 'NOTYPE',
	} as OrderType;
}
