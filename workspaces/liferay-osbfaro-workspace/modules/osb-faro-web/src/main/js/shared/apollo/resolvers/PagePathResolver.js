/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

const PagePathResolver = function PagePathResolver() {
	return {
		__typename: 'PagePath',
		canonicalUrl: 'https://www.liferay.com',

		followingPagePathNodes: [
			{
				__typename: 'PagePathNode',
				canonicalUrl: 'https://www.site1.com',
				external: false,
				title: 'Site 1',
				views: 10000,
			},
			{
				__typename: 'PagePathNode',
				canonicalUrl: 'https://www.site2.com',
				external: false,
				title: 'Site 2',
				views: 10000,
			},
			{
				__typename: 'PagePathNode',
				canonicalUrl: 'https://www.site3.com',
				external: false,
				title: 'Site 3',
				views: 5000,
			},
			{
				__typename: 'PagePathNode',
				canonicalUrl: 'others',
				external: false,
				title: 'others',
				views: 500,
			},
			{
				__typename: 'PagePathNode',
				canonicalUrl: 'drop-offs',
				external: false,
				title: 'drop-offs',
				views: 8000,
			},
		],

		previousPagePathNodes: [
			{
				__typename: 'PagePathNode',
				canonicalUrl: 'https://www.google.com',
				external: true,
				title: 'Google',
				views: 10000,
			},
			{
				__typename: 'PagePathNode',
				canonicalUrl: 'https://www.facebook.com',
				external: true,
				title: 'Facebook',
				views: 10000,
			},
			{
				__typename: 'PagePathNode',
				canonicalUrl: 'https://www.instagram.com',
				external: true,
				title: 'Instagram',
				views: 8000,
			},
			{
				__typename: 'PagePathNode',
				canonicalUrl: 'direct',
				external: false,
				title: 'direct',
				views: 5000,
			},
			{
				__typename: 'PagePathNode',
				canonicalUrl: 'others',
				external: false,
				title: 'others',
				views: 1000,
			},
		],

		title: 'Liferay Home Page',
		views: 100000,
	};
};

/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

export default PagePathResolver;
