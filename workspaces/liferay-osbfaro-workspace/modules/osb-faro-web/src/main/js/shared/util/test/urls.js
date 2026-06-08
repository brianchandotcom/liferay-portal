/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {getUrl} from '../urls';

describe('Url Utils', () => {
	describe('getUrl', () => {
		it('returns the url for touchpoint', () => {
			const path =
				'/workspace/:groupId/pages/known-individuals/:touchpoint/:title';
			const router = {
				params: {
					groupId: '32719',
					title: 'my page',
					touchpoint: 'http://mypage.com/',
				},
				query: {
					rangeKey: '30',
				},
			};

			expect(getUrl(path, router)).toEqual(
				'/workspace/32719/pages/known-individuals/http%3A%2F%2Fmypage.com%2F/my%20page?rangeKey=30'
			);
		});

		it('returns the url for assets', () => {
			const path =
				'/workspace/:groupId/assets/blogs/:assetId/known-individuals/:touchpoint/:title';
			const router = {
				params: {
					assetId: '123',
					groupId: '32719',
					title: 'my asset',
					touchpoint: 'Any',
				},
				query: {
					rangeKey: '30',
				},
			};

			expect(getUrl(path, router)).toEqual(
				'/workspace/32719/assets/blogs/123/known-individuals/Any/my%20asset?rangeKey=30'
			);
		});
	});
});
