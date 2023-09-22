/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import { getOrderTypes } from "../../../utils/api";

export async function getProductOrderTypes(productSpecificationValue: any) {
    const orderTypes = await getOrderTypes();	

    return orderTypes.find(({externalReferenceCode}) => {
			if (productSpecificationValue['en_US'].toLowerCase() === 'cloud') {
				return externalReferenceCode === 'CLOUDAPP';
			}

			return externalReferenceCode === 'DXPAPP';
	}) as OrderType;
}