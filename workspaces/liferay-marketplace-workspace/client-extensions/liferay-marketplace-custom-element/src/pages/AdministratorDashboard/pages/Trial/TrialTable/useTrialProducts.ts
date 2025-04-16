/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import useSWR from 'swr';

import SearchBuilder from '../../../../../core/SearchBuilder';
import HeadlessCommerceDeliveryCatalog from '../../../../../services/rest/HeadlessCommerceDeliveryCatalog';

export function useTrialProducts(channelId: number, name: string) {
	return useSWR(`administrator-dashboard/trial/products/${name}`, () =>
		HeadlessCommerceDeliveryCatalog.getProductsByChannelId(
			channelId,
			new URLSearchParams({
				'accountId': '-1',
				'filter': SearchBuilder.contains('name', name),
				'nestedFields': 'productSpecifications,skus',
				'pageSize': '10',
				'skus.accountId': '-1',
			})
		)
	);
}
