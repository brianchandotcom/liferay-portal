/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import useSWR from 'swr';

import headlessCommerceAdminAddress from '../services/rest/HeadlessCommerceAdminAddress';

const useCommerceRegions = (
	searchParams = new URLSearchParams({
		fields: 'a2,active,name,regions.name,regions.regionCode,title_i18n',
		pageSize: '-1',
	})
) => {
	return useSWR(`/commerce-regions/${searchParams.get('fields') ?? ''}`, () =>
		headlessCommerceAdminAddress.getRegions(searchParams)
	);
};

export default useCommerceRegions;
