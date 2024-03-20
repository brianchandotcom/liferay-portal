/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import fetcher from '../fetcher';

export type Region = {
	a2: string;
	a3: string;
	active: boolean;
	name: string;
	regions: {
		id: number;
		name: string;
		regionCode: string;
		title_i18n: {
			[key: string]: string;
		};
	}[];
	title_i18n: {
		[key: string]: string;
	};
};

export type RegionsAPI = APIResponse<Region>;

class HeadlessCommerceAdminAddress {
	async getRegions(searchParams = new URLSearchParams()) {
		return fetcher<RegionsAPI>(
			`/o/headless-admin-address/v1.0/countries?${searchParams.toString()}`
		);
	}
}

const headlessCommerceAdminAddress = new HeadlessCommerceAdminAddress();

export default headlessCommerceAdminAddress;
