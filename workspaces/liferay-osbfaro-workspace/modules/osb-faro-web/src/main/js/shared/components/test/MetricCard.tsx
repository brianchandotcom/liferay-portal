/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {cleanup, render} from '@testing-library/react';
import React from 'react';
import {TrendClassification} from '~/segment/types';

import MetricCard from '../MetricCard';

jest.unmock('react-dom');

describe('MetricCard', () => {
	afterEach(cleanup);

	const defaultProps = {
		description: 'Test description',
		renderTrendLabel: (percentageNode: React.ReactNode) => (
			<>{percentageNode} vs last period</>
		),
		title: 'Test title',
		value: 42,
	};

	it('renders', () => {
		const {container} = render(<MetricCard {...defaultProps} />);

		expect(container).toMatchSnapshot();
	});

	it('renders the title, description, and value', () => {
		const {getByText} = render(<MetricCard {...defaultProps} />);

		expect(getByText('Test title')).toBeTruthy();
		expect(getByText('Test description')).toBeTruthy();
		expect(getByText('42')).toBeTruthy();
	});

	it('renders Loading when loading is true', () => {
		const {container, queryByText} = render(
			<MetricCard {...defaultProps} loading />
		);

		expect(queryByText('Test title')).toBeNull();
		expect(queryByText('Test description')).toBeNull();
		expect(container.querySelector('.loading-root')).toBeTruthy();
	});

	it('renders a positive trend with the up caret icon', () => {
		const {container, getByText} = render(
			<MetricCard
				{...defaultProps}
				trend={{
					percentage: 10,
					trendClassification: TrendClassification.Positive,
				}}
			/>
		);

		expect(
			container.querySelector('.lexicon-icon-caret-top-l')
		).toBeTruthy();
		expect(getByText('10%')).toBeTruthy();
		expect(getByText('vs last period')).toBeTruthy();
	});

	it('renders a negative trend with the down caret icon', () => {
		const {container, getByText} = render(
			<MetricCard
				{...defaultProps}
				trend={{
					percentage: -5,
					trendClassification: TrendClassification.Negative,
				}}
			/>
		);

		expect(
			container.querySelector('.lexicon-icon-caret-bottom-l')
		).toBeTruthy();
		expect(getByText('5%')).toBeTruthy();
	});

	it('renders a neutral trend without an icon', () => {
		const {container, getByText} = render(
			<MetricCard
				{...defaultProps}
				trend={{
					percentage: 0,
					trendClassification: TrendClassification.Neutral,
				}}
			/>
		);

		expect(container.querySelector('.lexicon-icon-caret-top-l')).toBeNull();
		expect(
			container.querySelector('.lexicon-icon-caret-bottom-l')
		).toBeNull();
		expect(getByText('0%')).toBeTruthy();
	});

	it('does not render a trend icon when trend is undefined', () => {
		const {container} = render(<MetricCard {...defaultProps} />);

		expect(container.querySelector('.lexicon-icon-caret-top-l')).toBeNull();
		expect(
			container.querySelector('.lexicon-icon-caret-bottom-l')
		).toBeNull();
	});

	it('applies className and trendClassName', () => {
		const {container} = render(
			<MetricCard
				{...defaultProps}
				className="custom-card"
				trend={{
					percentage: 10,
					trendClassification: TrendClassification.Positive,
				}}
				trendClassName="custom-trend"
			/>
		);

		expect(container.querySelector('.custom-card')).toBeTruthy();
		expect(container.querySelector('.custom-trend')).toBeTruthy();
	});

	it('renders a ReactNode value', () => {
		const {getByTestId} = render(
			<MetricCard
				{...defaultProps}
				value={<span data-testid="custom-value">5 accounts</span>}
			/>
		);

		expect(getByTestId('custom-value')).toBeTruthy();
	});

	it('rounds the percentage to two decimals', () => {
		const {getByText} = render(
			<MetricCard
				{...defaultProps}
				trend={{
					percentage: 12.3456,
					trendClassification: TrendClassification.Positive,
				}}
			/>
		);

		expect(getByText('12.35%')).toBeTruthy();
	});
});
