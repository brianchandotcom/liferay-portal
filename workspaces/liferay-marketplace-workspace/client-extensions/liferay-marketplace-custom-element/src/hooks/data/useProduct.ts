/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import useSWR from 'swr';

import {Liferay} from '../../liferay/liferay';
import HeadlessCommerceDeliveryCatalogImpl from '../../services/rest/HeadlessCommerceDeliveryCatalog';

const useDeliveryProduct = (productId: string) => {
	return useSWR(`/delivery-product/${productId}`, () =>
		HeadlessCommerceDeliveryCatalogImpl.getProduct(
			Liferay.CommerceContext.commerceChannelId,
			productId,
			new URLSearchParams({
				'accountId': '-1',
				'attachments.accountId': '-1',
				'images.accountId': '-1',
				'nestedFields':
					'attachments,categories,images,productSpecifications,skus',
				'skus.accountId': '-1',
			})
		)
	);
};

export {useDeliveryProduct};
