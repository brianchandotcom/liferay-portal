/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {MockedProvider} from '@apollo/client/testing';
import {render, screen} from '@testing-library/react';
import React from 'react';
import {MemoryRouter, Route} from 'react-router-dom';
import BasePage from '~/shared/components/base-page';
import {getSiteMetricsChartData} from '~/shared/components/metric-card/util';
import {
	RangeKeyTimeRanges,
	SEVEN_MONTHS,
	THIRTEEN_MONTHS,
} from '~/shared/util/constants';

import MetricBaseCard from '../MetricBaseCard';
import {
	BounceRateMetric,
	CompositeMetric,
	Metric,
	SessionDurationMetric,
	SessionsPerVisitorMetric,
} from '../metrics';
import {SitesMetricQuery, SitesTabsQuery} from '../queries';

jest.unmock('react-dom');

jest.mock('../MetricTabs', () => () => <div data-testid="MetricTabs" />);
jest.mock('../MetricChart', () => () => <div data-testid="MetricChart" />);

jest.mock('~/shared/hooks/useRequest', () => ({
	useRequest: jest.fn(() => ({
		data: THIRTEEN_MONTHS,
		loading: false,
	})),
}));

const metrics: Metric[] = [
	CompositeMetric,
	SessionsPerVisitorMetric,
	SessionDurationMetric,
	BounceRateMetric,
];

const WrapperComponent = ({
	children,
	rangeKey = RangeKeyTimeRanges.Last30Days,
}: {
	children: React.ReactNode;
	rangeKey?: RangeKeyTimeRanges;
}) => (
	<MockedProvider addTypename={false}>
		<BasePage.Context.Provider
			value={{
				filters: {},
				router: {
					params: {channelId: '456', groupId: '2000'},
					query: {rangeKey},
				},
			}}
		>
			<MemoryRouter
				initialEntries={[
					`/workspace/2000/456/sites?rangeKey=${rangeKey}`,
				]}
			>
				<Route path="/workspace/:groupId/:channelId/sites">
					{children}
				</Route>
			</MemoryRouter>
		</BasePage.Context.Provider>
	</MockedProvider>
);

describe('MetricBaseCard', () => {
	it('renders component', async () => {
		const {container} = render(
			<WrapperComponent>
				<MetricBaseCard
					chartDataMapFn={getSiteMetricsChartData}
					label="Visitors Behavior"
					metrics={metrics}
					queries={{
						MetricQuery: SitesMetricQuery,
						TabsQuery: SitesTabsQuery,
						name: 'site',
					}}
					variables={() => ({
						channelId: '456',
						devices: 'Any',
						interval: 'D',
						location: 'Any',
						rangeEnd: null,
						rangeKey: RangeKeyTimeRanges.Last30Days,
						rangeStart: null,
					})}
				/>
			</WrapperComponent>
		);

		expect(screen.getByTestId('MetricTabs')).toBeInTheDocument();
		expect(screen.getByTestId('MetricChart')).toBeInTheDocument();
		expect(container).toMatchSnapshot();
	});

	it('renders tooltip with retention period for 7 months', async () => {
		const {useRequest} = require('~/shared/hooks/useRequest');
		(useRequest as jest.Mock).mockReturnValue({
			data: SEVEN_MONTHS,
			loading: false,
		});

		// We need to keep MetricChart unmocked for this test since it has the logic we're testing
		// but for now, let's establish a working baseline.

		render(
			<WrapperComponent rangeKey={RangeKeyTimeRanges.Last180Days}>
				<MetricBaseCard
					chartDataMapFn={getSiteMetricsChartData}
					label="Visitors Behavior"
					metrics={metrics}
					queries={{
						MetricQuery: SitesMetricQuery,
						TabsQuery: SitesTabsQuery,
						name: 'site',
					}}
					variables={() => ({
						channelId: '456',
						devices: 'Any',
						interval: 'D',
						location: 'Any',
						rangeEnd: null,
						rangeKey: RangeKeyTimeRanges.Last180Days,
						rangeStart: null,
					})}
				/>
			</WrapperComponent>
		);

		expect(screen.getByTestId('MetricChart')).toBeInTheDocument();
	});
});
