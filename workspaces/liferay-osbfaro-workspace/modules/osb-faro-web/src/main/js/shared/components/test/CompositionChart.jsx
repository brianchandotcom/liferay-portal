/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {cleanup, render} from '@testing-library/react';
import React from 'react';

import CompositionChart, {CompositionLegend} from '../CompositionChart';

jest.unmock('react-dom');

describe('CompositionChart', () => {
	afterEach(cleanup);

	it('renders', () => {
		const {container} = render(<CompositionChart />);

		expect(container).toBeTruthy();
	});
});

describe('CompositionLegend', () => {
	afterEach(cleanup);

	it('renders', () => {
		const {container} = render(
			<CompositionLegend
				items={[
					{color: 'blue', label: 'foo', value: 25},
					{color: 'pink', label: 'bar', value: 75},
				]}
				total={100}
			/>
		);

		expect(container).toMatchSnapshot();
	});

	it('renders "< 1%" if percentage is less than 1%', () => {
		const {container} = render(
			<CompositionLegend
				items={[
					{color: 'blue', label: 'foo', value: 1},
					{color: 'pink', label: 'bar', value: 1},
				]}
				total={1000}
			/>
		);

		container
			.querySelectorAll('b')
			.forEach((element) => expect(element).toHaveTextContent('< 1%'));
	});

	it('renders "0%" if percentage is 0%', () => {
		const {container} = render(
			<CompositionLegend
				items={[
					{color: 'blue', label: 'foo', value: 0},
					{color: 'pink', label: 'bar', value: 0},
				]}
				total={1000}
			/>
		);

		container
			.querySelectorAll('b')
			.forEach((element) => expect(element).toHaveTextContent('0%'));
	});
});
