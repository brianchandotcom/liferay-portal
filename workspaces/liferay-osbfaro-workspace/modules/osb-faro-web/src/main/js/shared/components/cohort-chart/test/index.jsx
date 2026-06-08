/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {render} from '@testing-library/react';
import React from 'react';

import CohortChart from '../index';

jest.unmock('react-dom');

describe('CohortChart', () => {
	it('renders', () => {
		const dateLabelFn = (value, shouldRender) =>
			shouldRender ? value : '-';

		const aggregatedCounts = [
			{
				retention: 12.123123,
				value: 10,
			},
			{
				retention: 42.123123,
				value: 56,
			},
		];

		const data = [
			[
				{
					colorHex: '#000000',
					dateLabel: 'February',
					dateLabelFn,
					periodLabel: 'Month 0',
					retention: 12.123123,
					rowKey: '2019-02-01',
					value: 10,
				},
				{
					colorHex: '#000000',
					dateLabel: 'March',
					dateLabelFn,
					periodLabel: 'Month 1',
					retention: 42.123123,
					rowKey: '2019-03-01',
					value: 56,
				},
			],
			[
				{
					colorHex: '#000000',
					dateLabel: 'February',
					dateLabelFn,
					periodLabel: 'Month 0',
					retention: 22.113123,
					rowKey: '2019-02-01',
					value: 60,
				},
			],
		];

		const {container} = render(
			<CohortChart
				aggregatedCounts={aggregatedCounts}
				data={data}
				dateLabels={['February', 'March']}
				periodLabels={['Month 0', 'Month 1']}
			/>
		);

		expect(container).toMatchSnapshot();
	});
});
