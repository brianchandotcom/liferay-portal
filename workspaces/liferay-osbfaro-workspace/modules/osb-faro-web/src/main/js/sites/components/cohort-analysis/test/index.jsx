/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {render} from '@testing-library/react';
import React from 'react';

import CohortAnalysis from '../index';
import {DAY, VISITORS} from '../utils';

jest.unmock('react-dom');

describe('CohortAnalysis', () => {
	it('renders', () => {
		const mockData = {
			visitors: {
				items: [
					{
						colDimension: '0',
						retention: 0.0,
						rowDimension: '0',
						rowKey: null,
						value: 0.0,
					},
					{
						colDimension: '0',
						retention: 0.0,
						rowDimension: '1',
						rowKey: '2019-08-14',
						value: 0.0,
					},
					{
						colDimension: '0',
						retention: 0.0,
						rowDimension: '2',
						rowKey: '2019-08-15',
						value: 0.0,
					},
					{
						colDimension: '0',
						retention: 0.0,
						rowDimension: '3',
						rowKey: '2019-08-16',
						value: 0.0,
					},
					{
						colDimension: '1',
						retention: 0.0,
						rowDimension: '0',
						rowKey: null,
						value: 0.0,
					},
					{
						colDimension: '1',
						retention: 0.0,
						rowDimension: '1',
						rowKey: '2019-08-14',
						value: 0.0,
					},
					{
						colDimension: '1',
						retention: 0.0,
						rowDimension: '2',
						rowKey: '2019-08-15',
						value: 0.0,
					},
					{
						colDimension: '1',
						retention: 0.0,
						rowDimension: '3',
						rowKey: '2019-08-16',
						value: 0.0,
					},
					{
						colDimension: '2',
						retention: 0.0,
						rowDimension: '0',
						rowKey: null,
						value: 0.0,
					},
					{
						colDimension: '2',
						retention: 0.0,
						rowDimension: '1',
						rowKey: '2019-08-14',
						value: 0.0,
					},
					{
						colDimension: '2',
						retention: 0.0,
						rowDimension: '2',
						rowKey: '2019-08-15',
						value: 0.0,
					},
				],
			},
		};

		const {container} = render(
			<CohortAnalysis
				data={mockData}
				interval={DAY}
				visitorsType={VISITORS}
			/>
		);

		expect(container).toMatchSnapshot();
	});
});
