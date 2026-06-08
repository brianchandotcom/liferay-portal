/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {ApolloProvider} from '@apollo/client';
import {MockedProvider} from '@apollo/client/testing';
import {cleanup, render} from '@testing-library/react';
import moment from 'moment';
import React from 'react';
import {Provider} from 'react-redux';
import client from '~/shared/apollo/client';
import {mockPreferenceReq} from '~/test/graphql-data';
import mockStore from '~/test/mock-store';

import DateRangeInput from '../DateRangeInput';

jest.unmock('react-dom');

jest.mock('~/shared/hooks/useTimeZone', () => ({
	useTimeZone: () => ({timeZoneId: 'UTC'}),
}));

jest.mock('react-router-dom', () => ({
	...jest.requireActual('react-router-dom'),
	useParams: () => ({
		channelId: '456',
		groupId: '2000',
		query: {
			rangeKey: '30',
		},
	}),
}));

describe('DateRangeInput', () => {
	afterEach(cleanup);

	it('renders', () => {
		const {getByTestId} = render(
			<ApolloProvider client={client}>
				<Provider store={mockStore()}>
					<MockedProvider mocks={[mockPreferenceReq()]}>
						<DateRangeInput
							value={{
								end: moment(100000000000).format('YYYY-MM-DD'),
								start: moment(0).format('YYYY-MM-DD'),
							}}
						/>
					</MockedProvider>
				</Provider>
			</ApolloProvider>
		);

		expect(getByTestId('date-range-input')).toBeInTheDocument();
	});
});
