/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {mapResultToProps} from '../sites-dashboard-query';

const mockData = {
	dataSources: [{foo: 'foo'}],
};

describe('Sites Dashboard Query Mapper', () => {
	describe('mapResultToProps', () => {
		it('maps sites dashboard query result to props', () => {
			expect(mapResultToProps({data: mockData})).toEqual(
				expect.objectContaining({sites: expect.any(Array)})
			);
		});
	});
});
