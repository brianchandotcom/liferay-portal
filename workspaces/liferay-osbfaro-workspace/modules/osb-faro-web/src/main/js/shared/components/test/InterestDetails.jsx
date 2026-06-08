/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {ApolloProvider} from '@apollo/client';
import {MockedProvider} from '@apollo/client/testing';
import {render} from '@testing-library/react';
import {createMemoryHistory} from 'history';
import React from 'react';
import {MemoryRouter, Route, Router} from 'react-router-dom';
import client from '~/shared/apollo/client';
import {Routes} from '~/shared/util/router';
import {
	mockPreferenceReq,
	mockTimeRangeReq,
	mockTouchpointsReq,
} from '~/test/graphql-data';
import {waitForLoadingToBeRemoved} from '~/test/helpers';

import InterestDetails from '../InterestDetails';

jest.unmock('react-dom');

const mockItems = [
	{
		__typename: 'PageMetric',
		assetId: 'https://www.liferay.com',
		assetTitle: 'Dashboard - Retail',
		avgTimeOnPageMetric: {
			__typename: 'Metric',
			value: 23,
		},
		bounceRateMetric: {
			__typename: 'Metric',
			value: 0.23,
		},
		dataSourceId: '123123',
		entrancesMetric: {
			__typename: 'Metric',
			value: 56,
		},
		exitRateMetric: {
			__typename: 'Metric',
			value: 0.53,
		},
		viewsMetric: {__typename: 'Metric', value: 243.0},
		visitorsMetric: {
			__typename: 'Metric',
			value: 45.0,
		},
	},
];

const defaultProps = {
	router: {
		params: {
			channelId: '321321',
			groupId: '23',
			id: '321',
			interestId: 'test',
		},
	},
};

const DefaultComponent = () => {
	const history = createMemoryHistory();

	return (
		<ApolloProvider client={client}>
			<MockedProvider
				mocks={[
					mockTimeRangeReq(),
					mockPreferenceReq(),
					mockTouchpointsReq(mockItems, {size: 2}),
				]}
			>
				<Router history={history}>
					<MemoryRouter
						initialEntries={[
							'/workspace/23/321321/contacts/accounts/123123/interests/test',
						]}
					>
						<Route path={Routes.CONTACTS_ACCOUNT_INTEREST_DETAILS}>
							<InterestDetails {...defaultProps} />
						</Route>
					</MemoryRouter>
				</Router>
			</MockedProvider>
		</ApolloProvider>
	);
};

describe('InterestDetails', () => {
	it('renders', async () => {
		const {container, getByText} = render(<DefaultComponent />);

		await waitForLoadingToBeRemoved(container);

		expect(getByText('"test"')).toBeInTheDocument();
	});
});
