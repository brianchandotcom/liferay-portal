/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {cleanup, render} from '@testing-library/react';
import React from 'react';
import {RangeKeyTimeRanges} from '~/shared/util/constants';
import {createDateKeysIMap} from '~/shared/util/intervals';
import * as data from '~/test/data';

import ActiveIndividualsChart from '../ActiveIndividualsChart';

jest.unmock('react-dom');

describe('ActiveIndividualsChart', () => {
	afterEach(cleanup);

	it('renders', () => {
		const chartData = [
			{
				anonymousVisitors: 1,
				intervalInitDate: data.getTimestamp(-1),
				knownVisitors: 3,
				visitors: 4,
			},
			{
				anonymousVisitors: 6,
				intervalInitDate: data.getTimestamp(0),
				knownVisitors: 4,
				visitors: 2,
			},
		];

		const {container} = render(
			<ActiveIndividualsChart
				data={chartData}
				dateKeysIMap={createDateKeysIMap(
					RangeKeyTimeRanges.Last30Days,
					chartData,
					'intervalInitDate'
				)}
				rangeSelectors={{rangeKey: '30'}}
			/>
		);
		expect(container).toMatchSnapshot();
	});
});
