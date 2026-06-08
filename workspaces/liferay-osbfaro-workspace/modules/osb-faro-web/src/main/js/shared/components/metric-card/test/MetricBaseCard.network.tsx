/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {MockedProvider} from '@apollo/client/testing';
import {render, waitFor} from '@testing-library/react';
import React from 'react';
import {MemoryRouter, Route} from 'react-router-dom';
import BasePage from '~/shared/components/base-page';
import {getSiteMetricsChartData} from '~/shared/components/metric-card/util';
import {RangeKeyTimeRanges} from '~/shared/util/constants';

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

jest.mock('~/shared/hooks/useRequest', () => ({
	useRequest: jest.fn(() => ({
		data: 13,
		loading: false,
	})),
}));

const metrics: Metric[] = [
	CompositeMetric,
	SessionsPerVisitorMetric,
	SessionDurationMetric,
	BounceRateMetric,
];

const sharedRequestVariables = {
	channelId: '456',
	devices: 'Any',
	interval: 'D',
	location: 'Any',
	rangeEnd: null,
	rangeKey: parseInt(RangeKeyTimeRanges.Last30Days, 10),
	rangeStart: null,
};

const tabsResult = {
	site: {
		__typename: 'SiteMetric',
		bounceRateMetric: {
			__typename: 'Metric',
			previousValue: null,
			trend: {
				__typename: 'Trend',
				percentage: null,
				trendClassification: 'NEUTRAL',
			},
			value: 0,
		},
		sessionDurationMetric: {
			__typename: 'Metric',
			previousValue: null,
			trend: {
				__typename: 'Trend',
				percentage: null,
				trendClassification: 'NEUTRAL',
			},
			value: 0,
		},
		sessionsPerVisitorMetric: {
			__typename: 'Metric',
			previousValue: null,
			trend: {
				__typename: 'Trend',
				percentage: null,
				trendClassification: 'NEUTRAL',
			},
			value: 0,
		},
		visitorsMetric: {
			__typename: 'Metric',
			previousValue: null,
			trend: {
				__typename: 'Trend',
				percentage: null,
				trendClassification: 'NEUTRAL',
			},
			value: 0,
		},
	},
};

const compositeMetricResult = {
	site: {
		__typename: 'SiteMetric',
		anonymousVisitorsMetric: {
			__typename: 'HistogramMetric',
			histogram: {
				__typename: 'Histogram',
				asymmetricComparison: false,
				metrics: [],
			},
			previousValue: null,
			trend: {
				__typename: 'Trend',
				percentage: null,
				trendClassification: 'NEUTRAL',
			},
			value: 0,
		},
		knownVisitorsMetric: {
			__typename: 'HistogramMetric',
			histogram: {
				__typename: 'Histogram',
				asymmetricComparison: false,
				metrics: [],
			},
			previousValue: null,
			trend: {
				__typename: 'Trend',
				percentage: null,
				trendClassification: 'NEUTRAL',
			},
			value: 0,
		},
		visitorsMetric: {
			__typename: 'HistogramMetric',
			histogram: {
				__typename: 'Histogram',
				asymmetricComparison: false,
				metrics: [],
			},
			previousValue: null,
			trend: {
				__typename: 'Trend',
				percentage: null,
				trendClassification: 'NEUTRAL',
			},
			value: 0,
		},
	},
};

const buildMocks = (counter: {metric: number; tabs: number}) => [
	{
		newData: () => {
			counter.tabs += 1;

			return {data: tabsResult};
		},
		request: {
			query: SitesTabsQuery,
			variables: sharedRequestVariables,
		},
	},
	{
		newData: () => {
			counter.metric += 1;

			return {data: compositeMetricResult};
		},
		request: {
			query: SitesMetricQuery('visitorsMetric'),
			variables: sharedRequestVariables,
		},
	},
];

const renderWithProviders = (
	mocks: ReturnType<typeof buildMocks>,
	{visible = true}: {visible?: boolean} = {}
) =>
	render(
		<MockedProvider addTypename={false} mocks={mocks}>
			<BasePage.Context.Provider
				value={{
					filters: {},
					router: {
						params: {channelId: '456', groupId: '2000'},
						query: {rangeKey: RangeKeyTimeRanges.Last30Days},
					},
				}}
			>
				<MemoryRouter
					initialEntries={[
						`/workspace/2000/456/sites?rangeKey=${RangeKeyTimeRanges.Last30Days}`,
					]}
				>
					<Route path="/workspace/:groupId/:channelId/sites">
						{visible && (
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
									...sharedRequestVariables,
								})}
							/>
						)}
					</Route>
				</MemoryRouter>
			</BasePage.Context.Provider>
		</MockedProvider>
	);

describe('MetricBaseCard network behavior', () => {
	it('fires only one TabsQuery on initial mount', async () => {
		const counter = {metric: 0, tabs: 0};

		renderWithProviders(buildMocks(counter));

		await waitFor(() => {
			expect(counter.tabs).toBeGreaterThan(0);
		});

		expect(counter.tabs).toBe(1);
		expect(counter.metric).toBe(1);
	});
});
