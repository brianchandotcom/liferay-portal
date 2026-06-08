/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {ApolloProvider} from '@apollo/client';
import {MockedProvider} from '@apollo/client/testing';
import {cleanup, render} from '@testing-library/react';
import React from 'react';
import {StaticRouter} from 'react-router-dom';
import client from '~/shared/apollo/client';
import {ViewsMetric} from '~/shared/components/metric-card/metrics';
import {RangeKeyTimeRanges, THIRTEEN_MONTHS} from '~/shared/util/constants';
import {
	mockAssetMetricReq,
	mockAssetTabsReq,
	mockPreferenceReq,
	mockTimeRangeReq,
} from '~/test/graphql-data';
import {waitForLoadingToBeRemoved} from '~/test/helpers';

import WebContentMetricCard from '../WebContentMetricCard';

jest.unmock('react-dom');

jest.mock('react-router-dom', () => ({
	...jest.requireActual('react-router-dom'),
	useParams: () => ({
		assetId: '123',
		channelId: '456',
		groupId: '2000',
		query: {
			rangeKey: RangeKeyTimeRanges.Last30Days,
		},
		title: 'My awesome asset',
		touchpoint: 'https://liferay.com',
	}),
}));

/**
 * Override Recharts Responsive Container
 * width dimensions fixed to be able to render charts
 */

jest.mock('recharts', () => {
	const OriginalModule = jest.requireActual('recharts');

	return {
		...OriginalModule,
		ResponsiveContainer: ({children}: {children: React.ReactNode}) => (
			<OriginalModule.ResponsiveContainer height={350} width={800}>
				{children}
			</OriginalModule.ResponsiveContainer>
		),
		Tooltip: ({children, ...props}: {children: React.ReactNode}) => (
			<OriginalModule.Tooltip {...props} active>
				{children}
			</OriginalModule.Tooltip>
		),
	};
});

const NAME = 'journal';

const WrappedComponent = ({empty = false}) => (
	<ApolloProvider client={client}>
		<StaticRouter>
			<MockedProvider
				mocks={[
					mockTimeRangeReq(),
					mockPreferenceReq(THIRTEEN_MONTHS),
					mockAssetTabsReq({
						metrics: [ViewsMetric],
						name: NAME,
						rangeKey: Number(RangeKeyTimeRanges.Last30Days),
					}),
					mockAssetMetricReq({
						empty,
						metricName: ViewsMetric.name,
						queryName: NAME,
						rangeKey: Number(RangeKeyTimeRanges.Last30Days),
					}),
				]}
			>
				<WebContentMetricCard
					label={Liferay.Language.get('visitors-behavior')}
				/>
			</MockedProvider>
		</StaticRouter>
	</ApolloProvider>
);

describe('WebContentMetricCard', () => {
	afterEach(cleanup);

	it('renders', async () => {
		const {getAllByText, getByText} = render(<WrappedComponent />);

		await waitForLoadingToBeRemoved(document.body);

		expect(getByText('Visitors Behavior')).toBeInTheDocument();
		expect(getAllByText('Views').length).toBeGreaterThan(0);
	});

	it('renders with empty state', async () => {
		const {container, getByText} = render(<WrappedComponent empty />);

		await waitForLoadingToBeRemoved(container);

		expect(
			getByText('There are no visitors data found.')
		).toBeInTheDocument();

		const linkToTheDocumentation = getByText(
			'Learn more about visitor behavior.'
		);

		expect(linkToTheDocumentation).toBeInTheDocument();
		expect(linkToTheDocumentation).toHaveAttribute(
			'href',
			'https://learn.liferay.com/w/dxp/personalization/analytics-cloud/touchpoints/assets-analytics/web-content-analytics#visitor-behavior'
		);
	});
});
