/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {mapPropsToOptions, mapResultToProps} from '../visitors-by-time-query';

describe('VisitorsByTimeQuery Mappers', () => {
	it('maps results to props', () => {
		const mockResult = {
			data: {siteVisitorHeatMap: []},
		};

		expect(mapResultToProps(mockResult)).toEqual(
			expect.objectContaining({data: expect.any(Array)})
		);
	});

	it('maps empty results', () => {
		const mockResult = {
			data: {siteVisitorHeatMap: [{value: 0}]},
		};

		expect(mapResultToProps(mockResult)).toEqual(
			expect.objectContaining({data: expect.any(Array), total: 0})
		);
	});

	it('maps props to options', () => {
		const mockProps = {
			rangeSelectors: {rangeKey: '30'},
			router: {params: {channelId: 123}},
		};

		expect(mapPropsToOptions(mockProps)).toEqual(
			expect.objectContaining({
				variables: {
					channelId: 123,
					rangeEnd: null,
					rangeKey: parseInt('30'),
					rangeStart: null,
				},
			})
		);
	});
});
