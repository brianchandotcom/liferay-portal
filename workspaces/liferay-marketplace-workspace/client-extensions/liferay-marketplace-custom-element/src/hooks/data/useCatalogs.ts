/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import useSWR from 'swr';

import HeadlessCommerceAdminCatalogImpl from '../../services/rest/HeadlessCommerceAdminCatalog';

const useCatalogs = () => {
	return useSWR('/catalogs', async () => {
		const catalogResponse =
			await HeadlessCommerceAdminCatalogImpl.getCatalogs(
				new URLSearchParams({fields: 'accountId,id', pageSize: '-1'})
			);

		return catalogResponse.items;
	});
};

export {useCatalogs};
