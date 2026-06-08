/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import * as utils from '../utils';

describe('utils', () => {
	describe('getPropertiesFromItems', () => {
		it('converts Filter to JobProperty', () => {
			const filter = 'url ~ .*custom-assets';

			expect(
				utils.getPropertiesFromItems([
					{name: 'includeFilter', value: filter},
				])
			).toEqual([{filter, negate: false}]);
		});
	});

	describe('getFilterValueBreakdown', () => {
		expect(utils.getFilterValueBreakdown('url ~ .*custom-assets')).toEqual({
			exactMatchSign: '~',
			metadataTag: 'url',
			rule: '.*custom-assets',
		});
	});
});
