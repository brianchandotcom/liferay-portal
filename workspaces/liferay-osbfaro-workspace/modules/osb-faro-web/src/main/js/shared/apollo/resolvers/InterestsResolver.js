const InterestsResolver = function InterestsResolver() {
	return {
		__typename: 'CompositionBag',

		compositions: [
			{
				__typename: 'Composition',
				count: 1,
				name: 'lorem ipsum dolor sit amet consectetur adipiscing elit sed do eiusmod tempor incididunt ut labore et dolore magna aliqua ut enim ad minim veniam quis.',
			},
			{__typename: 'Composition', count: 1, name: 'dxp'},
			{__typename: 'Composition', count: 1, name: 'lorem'},
		],

		maxCount: 1,
		total: 3,
		totalCount: 1,
	};
};

/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

export default InterestsResolver;
