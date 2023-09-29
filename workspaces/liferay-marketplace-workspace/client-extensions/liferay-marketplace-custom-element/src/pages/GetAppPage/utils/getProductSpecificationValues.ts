/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {getProductSpecifications} from '../../../utils/api';

export async function getProductSpecificationValues(productId?: number) {
	const productSpecifications = await getProductSpecifications({
		appProductId: productId,
	});

	const {value: productSpecificationValue} = productSpecifications.find(
		({value}) =>
			value['en_US'].toLowerCase() === 'cloud' ||
			value['en_US'].toLowerCase() === 'dxp'
	) as ProductSpecification;

	return productSpecificationValue;
}
