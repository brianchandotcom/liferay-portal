/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {render} from '@testing-library/react';
import React from 'react';

import BarComparisonTable from '../BarComparisonTable';

jest.unmock('react-dom');

const getData = (comparePrevious = false) =>
	[
		{
			isPreviousValue: false,
			name: 'Item 0',
			percent: 1,
			style: {
				'background-color': '#187FFF',
			},
			value: 100,
		},
		...(comparePrevious
			? [
					{
						isPreviousValue: true,
						name: 'Previous Value',
						percent: 0.7,
						style: {
							'background-color': '#31BE88',
						},
						value: 70,
					},
				]
			: []),
	].filter(Boolean);

describe('BarComparisonTable', () => {
	const event = {name: 'View Article'};

	it('render', () => {
		const items = getData();

		const {container} = render(
			<BarComparisonTable
				event={event}
				isComparingSegment={false}
				items={items}
			/>
		);

		expect(container).toMatchSnapshot();
	});

	it('render comparing previous', () => {
		const items = getData(true);

		const {container} = render(
			<BarComparisonTable
				event={event}
				isComparingSegment={false}
				items={items}
			/>
		);

		expect(container).toMatchSnapshot();

		expect(container.querySelector('.metric-bar-root .lines')).toBeTruthy();
	});

	it('change the header when comparing segments', () => {
		const items = getData(true);

		const {container} = render(
			<BarComparisonTable
				event={event}
				isComparingSegment
				items={items}
			/>
		);

		expect(
			container.querySelector('.table-column-event-name')
		).toHaveTextContent(event.name);
	});
});
