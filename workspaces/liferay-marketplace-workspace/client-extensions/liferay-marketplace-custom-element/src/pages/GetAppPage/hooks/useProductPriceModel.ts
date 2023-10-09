/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {useEffect, useState} from 'react';

const useProductPriceModel = (product: Product | undefined) => {
	const [isFreeApp, setIsFreeApp] = useState<boolean>(false);
	const [isPaidApp, setIsPaidApp] = useState<boolean>(false);
	const [priceModel, setPriceModel] = useState<string | undefined>('');

	useEffect(() => {
		const productPriceModel = product?.productSpecifications?.find(
			(specification) => specification?.specificationKey === 'price-model'
		);

		setPriceModel(productPriceModel?.value.en_US);

		if (productPriceModel?.value.en_US.toLowerCase() === 'free') {
			setIsFreeApp(true);
		}
		if (productPriceModel?.value.en_US.toLowerCase() === 'paid') {
			setIsPaidApp(true);
		}
	}, [product?.productSpecifications]);

	return {
		isFreeApp,
		isPaidApp,
		priceModel,
	};
};

export default useProductPriceModel;
