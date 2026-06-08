/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {cleanup, render} from '@testing-library/react';
import {range} from 'lodash';
import React from 'react';

import HeatMapChart, {
	getNicedExtent,
	getThresholdsFromData,
} from '../HeatmapChart';

jest.unmock('react-dom');

describe('HeatMapChart', () => {
	afterEach(cleanup);

	it('renders', () => {
		const {container} = render(<HeatMapChart data={[]} />);

		expect(container).toMatchSnapshot();
	});
});

describe('getNicedExtent', () => {
	it.each`
		extent                    | nicedExtent
		${[47, 539]}              | ${[0, 600]}
		${[500, 1450]}            | ${[600, 1400]}
		${[undefined, undefined]} | ${[undefined, undefined]}
		${[0, 10]}                | ${[0, 10]}
	`(
		'should modify the $extent to be the nice, round values in $nicedExtent',
		({extent, nicedExtent}) => {
			expect(getNicedExtent(extent)).toEqual(
				expect.arrayContaining(nicedExtent)
			);
		}
	);
});

describe('getThresholdsFromData', () => {
	it('takes the min and max of the data and create 4 equally-spaced threshold bins', () => {
		const mockData = range(168).map((i) => ({value: i}));
		expect(getThresholdsFromData(mockData)).toEqual(
			expect.arrayContaining([1, 50, 100, 150, 200])
		);
	});

	it('takes the min and max of the data and create 4 equally-spaced threshold bins when the min of the data is greater than 0', () => {
		const mockData = range(168).map((i) => ({value: 150 + i}));
		expect(getThresholdsFromData(mockData)).toEqual(
			expect.arrayContaining([150, 200, 250, 300, 350])
		);
	});
});
