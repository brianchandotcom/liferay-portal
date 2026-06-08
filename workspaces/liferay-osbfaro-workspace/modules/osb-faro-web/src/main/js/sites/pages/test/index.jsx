/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {ApolloProvider} from '@apollo/client';
import {cleanup, render} from '@testing-library/react';
import React from 'react';
import {Provider} from 'react-redux';
import {MemoryRouter, Route} from 'react-router-dom';
import client from '~/shared/apollo/client';
import BasePage from '~/shared/components/base-page';
import {ChannelContext} from '~/shared/context/channel';
import * as useDataSources from '~/shared/context/dataSources';
import {UserRoleNames} from '~/shared/util/constants';
import {User} from '~/shared/util/records';
import {Routes} from '~/shared/util/router';
import {mockEmptyState, mockSuccessState} from '~/test/__mocks__/mock-objects';
import * as data from '~/test/data';
import {mockChannelContext} from '~/test/mock-channel-context';
import mockStore from '~/test/mock-store';

import {Dashboard} from '../index';

jest.unmock('react-dom');

const MEMBER_USER = new User(
	data.mockUser(23, {roleName: UserRoleNames.Member})
);

const MOCK_CONTEXT = {
	rangeKey: {defaultValue: '30'},
	router: {
		params: {
			channelId: '123',
			groupId: '2000',
		},
		query: {
			rangeKey: '30',
		},
	},
};

const mockUseDataSource = useDataSources;

const WrappedComponent = (props) => (
	<ApolloProvider client={client}>
		<Provider store={mockStore()}>
			<MemoryRouter initialEntries={['/workspace/2000/123/sites']}>
				<Route path={Routes.SITES}>
					<ChannelContext.Provider value={mockChannelContext()}>
						<BasePage.Context.Provider value={MOCK_CONTEXT}>
							<Dashboard
								currentUser={MEMBER_USER}
								router={MOCK_CONTEXT.router}
								{...props}
							/>
						</BasePage.Context.Provider>
					</ChannelContext.Provider>
				</Route>
			</MemoryRouter>
		</Provider>
	</ApolloProvider>
);

describe('Sites Dashboard Index', () => {
	afterEach(cleanup);
	mockUseDataSource.useDataSources = jest.fn(() => mockSuccessState);

	beforeAll(() => {
		delete window.location;
	});

	it('renders w/ "No Sites Connected" as title', () => {
		window.location = {
			pathname: '/workspace/2000/123/sites',
		};

		const CHANNEL_CONTEXT_MOCK = {
			channelDispatch: () => {},
			channels: [],
			selectedChannel: null,
		};

		const WrappedComponentWithContext = (props) => (
			<ApolloProvider client={client}>
				<Provider store={mockStore()}>
					<MemoryRouter
						initialEntries={['/workspace/2000/123/sites']}
					>
						<Route path={Routes.SITES}>
							<ChannelContext.Provider
								value={CHANNEL_CONTEXT_MOCK}
							>
								<BasePage.Context.Provider value={MOCK_CONTEXT}>
									<Dashboard
										currentUser={MEMBER_USER}
										router={MOCK_CONTEXT.router}
										{...props}
									/>
								</BasePage.Context.Provider>
							</ChannelContext.Provider>
						</Route>
					</MemoryRouter>
				</Provider>
			</ApolloProvider>
		);

		const {container} = render(<WrappedComponentWithContext />);

		expect(container.querySelector('.title-section')).toHaveTextContent(
			'No Sites Connected'
		);
	});
});

describe('sites with no Data Source', () => {
	it('renders EmptyState', () => {
		window.location = {
			pathname: '/workspace/2000/123/sites',
		};
		mockUseDataSource.useDataSources = jest.fn(() => mockEmptyState);

		const {getByText} = render(<WrappedComponent />);

		expect(
			getByText('No Sites Synced from Data Sources')
		).toBeInTheDocument();
		expect(
			getByText('Connect a data source with sites data.')
		).toBeInTheDocument();
		expect(
			getByText('Access our documentation to learn more.')
		).toBeInTheDocument();
	});
});
