/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {ApolloProvider} from '@apollo/client';
import {render} from '@testing-library/react';
import React from 'react';
import {StaticRouter} from 'react-router-dom';
import client from '~/shared/apollo/client';
import {ChannelContext} from '~/shared/context/channel';
import {mockChannelContext} from '~/test/mock-channel-context';

import InterestDetails from '../InterestDetails';

jest.unmock('react-dom');

jest.mock('react-router-dom', () => ({
	...jest.requireActual('react-router-dom'),
	useLocation: () => ({
		search: '?rangeKey=30',
	}),
	useParams: () => ({
		channelId: '456',
		groupId: '123',
		query: {
			rangeKey: '30',
		},
	}),
}));

describe('Sites Dashboard InterestDetails', () => {
	it('render', () => {
		const {container} = render(
			<ApolloProvider client={client}>
				<StaticRouter>
					<ChannelContext.Provider value={mockChannelContext()}>
						<InterestDetails
							channelName="Test Channel"
							router={{
								params: {channelId: '456', groupId: '123'},
								query: {rangeKey: '30'},
							}}
						/>
					</ChannelContext.Provider>
				</StaticRouter>
			</ApolloProvider>
		);

		expect(container).toMatchSnapshot();
	});
});
