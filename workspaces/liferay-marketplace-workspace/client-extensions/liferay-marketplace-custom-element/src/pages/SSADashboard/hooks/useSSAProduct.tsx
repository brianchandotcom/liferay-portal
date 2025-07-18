/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {useEffect, useState} from 'react';

import SearchBuilder from '../../../core/SearchBuilder';
import {ProductType} from '../../../enums/Product';
import HeadlessCommerceDeliveryCatalog from '../../../services/rest/HeadlessCommerceDeliveryCatalog';

const useSSAProduct = (channelId?: number) => {
	const [product, setProduct] = useState<DeliveryProduct>();

	useEffect(() => {
		if (!channelId) {
			return;
		}

		const fetchProduct = async () => {
			const result =
				await HeadlessCommerceDeliveryCatalog.getProductsPage(
					channelId,
					new URLSearchParams({
						'accountId': '-1',
						'filter': SearchBuilder.lambda(
							'specificationValues',
							ProductType.SSA_SAAS
						),
						'nestedFields': 'skus',
						'skus.accountId': '-1',
					})
				);

			setProduct(result?.items?.[0]);
		};

		fetchProduct();
	}, [channelId]);

	return product;
};

export default useSSAProduct;
