/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {cleanup, render} from '@testing-library/react';
import React from 'react';
import {TrendClassification} from '~/segment/types';

import {IOverviewMetric, OverviewMetricType} from '../../utils/types';
import OverviewSection from '../OverviewSection';

jest.unmock('react-dom');

const buildMetric = (
	metricType: OverviewMetricType,
	value: number
): IOverviewMetric => ({
	metricType,
	trend: {
		percentage: 0,
		trendClassification: TrendClassification.Neutral,
	},
	value,
});

describe('OverviewSection', () => {
	afterEach(cleanup);

	it('renders the overview header and all three card titles', () => {
		const {getByText} = render(<OverviewSection />);

		expect(getByText('OVERVIEW')).toBeInTheDocument();
		expect(getByText('Net New Pipeline Generated')).toBeInTheDocument();
		expect(getByText('Stalled Accounts')).toBeInTheDocument();
		expect(getByText('At Risk Accounts')).toBeInTheDocument();
	});

	it('falls back to 0 for each card when no metrics are provided', () => {
		const {getAllByText} = render(<OverviewSection />);

		expect(getAllByText('0')).toHaveLength(3);
	});

	it('maps each metric to its card by metricType', () => {
		const metrics = [
			buildMetric(OverviewMetricType.NewPipeline, 11),
			buildMetric(OverviewMetricType.Stalled, 22),
			buildMetric(OverviewMetricType.AtRisk, 33),
		];

		const {getByText} = render(<OverviewSection metrics={metrics} />);

		const cardOf = (title: string) => getByText(title).closest('.card')!;

		expect(cardOf('Net New Pipeline Generated').textContent).toContain(
			'11'
		);
		expect(cardOf('Stalled Accounts').textContent).toContain('22');
		expect(cardOf('At Risk Accounts').textContent).toContain('33');
	});

	it('falls back to 0 for a card without a matching metric', () => {
		const metrics = [buildMetric(OverviewMetricType.NewPipeline, 11)];

		const {getAllByText, getByText} = render(
			<OverviewSection metrics={metrics} />
		);

		expect(getByText('11')).toBeInTheDocument();
		expect(getAllByText('0')).toHaveLength(2);
	});

	it('renders the trend label comparing against the last 90 days', () => {
		const {container} = render(<OverviewSection />);

		expect(container.textContent).toContain('vs. Last 90 Days');
		expect(container.textContent).not.toContain('Months');
	});

	it('renders a loading spinner in each card when loading is true', () => {
		const {container, queryByText} = render(<OverviewSection loading />);

		expect(container.querySelectorAll('.loading-root')).toHaveLength(3);
		expect(queryByText('Net New Pipeline Generated')).toBeNull();
		expect(queryByText('Stalled Accounts')).toBeNull();
		expect(queryByText('At Risk Accounts')).toBeNull();
	});
});
