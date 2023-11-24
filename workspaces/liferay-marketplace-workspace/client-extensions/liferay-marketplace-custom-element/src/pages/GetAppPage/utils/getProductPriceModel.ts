/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

const getProductPriceModel = (product?: DeliveryProduct) => {
	const priceModel =
		product?.productSpecifications
			?.find(
				(specification) =>
					specification?.specificationKey === 'price-model'
			)
			?.value?.toLowerCase() ?? '';

	return {
		isFreeApp: priceModel === 'free',
		isPaidApp: priceModel === 'paid',
		priceModel,
	};
};

export default getProductPriceModel;
