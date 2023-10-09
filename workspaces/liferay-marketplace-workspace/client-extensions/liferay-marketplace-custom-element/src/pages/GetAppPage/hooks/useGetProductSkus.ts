/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {useEffect, useState} from 'react';

const useGetProductSkus = (
	setEnableTrialMethod: (value: boolean) => void,
	product?: Product
) => {
	const [sku, setSku] = useState<SKU>({
		cost: 0,
		externalReferenceCode: '',
		id: 0,
		price: 0,
		sku: '',
		skuOptions: [],
	});

	useEffect(() => {
		let newSku;

		if (product && product?.skus?.length > 1) {
			const isTrial = !!product?.skus?.find(
				({skuOptions: [skuOption]}) =>
					skuOption?.key === 'trial' && skuOption.value === 'yes'
			);

			setEnableTrialMethod(isTrial);

			newSku = product?.skus?.find(
				(sku: {price: number}) => sku.price === 0
			);
		}
		else {
			newSku = product?.skus[0];
		}

		setSku(newSku as SKU);
	}, [product, setEnableTrialMethod]);

	return {
		sku,
	};
};

export default useGetProductSkus;
