const CustomAssetsListResolver = function CustomAssetsListResolver() {
	return {
		__typename: 'Dashboard',

		dashboards: [
			{
				__typename: 'DashboardItem',
				assetId: 'customId1',
				assetTitle: 'Track Custom Login',
				createDate: '2022-11-11T01:00:00.000',
				id: '123',
				modifiedByUserName: null,
				modifiedDate: null,
			},
		],

		total: 1,
	};
};

/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

export default CustomAssetsListResolver;
