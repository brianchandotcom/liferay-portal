/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {MockedProvider} from '@apollo/client/testing';
import {cleanup, render} from '@testing-library/react';
import React from 'react';
import {Provider} from 'react-redux';
import {MemoryRouter, Route} from 'react-router-dom';
import {Routes} from '~/shared/util/router';
import {mockTimeRangeReq, mockTouchpointsReq} from '~/test/graphql-data';
import {waitForLoading} from '~/test/helpers';
import mockStore from '~/test/mock-store';

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

describe('Individuals Dashboard Individuals Interest Details', () => {
	afterEach(cleanup);

	it('renders', async () => {
		const {getByText} = render(
			<Provider store={mockStore()}>
				<MockedProvider
					mocks={[
						mockTouchpointsReq(mockItems, {size: 2}),
						mockTimeRangeReq(),
					]}
				>
					<MemoryRouter
						initialEntries={[
							'/workspace/23/321321/contacts/individuals/interests/test',
						]}
					>
						<Route
							path={Routes.CONTACTS_INDIVIDUALS_INTEREST_DETAILS}
						>
							<InterestDetails
								router={{
									params: {
										channelId: '321321',
										groupId: '23',
										interestId: 'test',
									},
									query: {
										delta: '2',
										page: '1',
										rangeEnd: null,
										rangeKey: '30',
										rangeStart: null,
									},
								}}
							/>
						</Route>
					</MemoryRouter>
				</MockedProvider>
			</Provider>
		);

		await waitForLoading(document.body);

		expect(getByText('Back to Interests')).toBeInTheDocument();
	});
});
