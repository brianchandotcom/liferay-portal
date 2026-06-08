/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {cleanup, render} from '@testing-library/react';
import React from 'react';

import TypeTrend, {TrendItem} from '../TypeTrend';

jest.unmock('react-dom');

describe('TypeTrend', () => {
	afterEach(cleanup);

	it('renders', () => {
		const {container} = render(<TypeTrend items={[]} />);
		expect(container).toMatchSnapshot();
	});
});

describe('TrendItem', () => {
	const mockTrendItem = {
		change: -0.02,
		data: [8, 5, 3, 2, 1],
		id: 'totalIndividuals',
		title: Liferay.Language.get('total-individuals'),
		total: 1341321,
	};

	afterEach(cleanup);

	xit('renders', () => {
		const {container} = render(<TrendItem {...mockTrendItem} />);
		expect(container).toMatchSnapshot();
	});

	it('renders a negative change', () => {
		const {container} = render(
			<TrendItem {...mockTrendItem} change={-0.2} />
		);
		expect(container.querySelector('.change .decrease')).not.toBeNull();
		expect(
			container.querySelector('.change .lexicon-icon-caret-bottom')
		).not.toBeNull();
	});

	it('renders a positive change', () => {
		const {container} = render(
			<TrendItem {...mockTrendItem} change={0.2} />
		);
		expect(container.querySelector('.change .increase')).not.toBeNull();
		expect(
			container.querySelector('.change .lexicon-icon-caret-top')
		).not.toBeNull();
	});

	it('renders a 0 change', () => {
		const {container} = render(<TrendItem {...mockTrendItem} change={0} />);
		expect(container.querySelector('.change .increase')).toBeNull();
		expect(container.querySelector('.change .decrease')).toBeNull();
		expect(container.querySelector('.change .icon-root')).toBeNull();
	});

	it('renders a fallback display if change is infinite', () => {
		const {container} = render(
			<TrendItem {...mockTrendItem} change={Infinity} />
		);
		expect(container.querySelector('.change b')).toHaveTextContent('--');
	});
});
