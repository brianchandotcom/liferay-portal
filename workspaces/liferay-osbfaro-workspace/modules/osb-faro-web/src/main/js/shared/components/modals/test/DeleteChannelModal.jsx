/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {InMemoryCache} from '@apollo/client';
import {MockedProvider} from '@apollo/client/testing';
import {cleanup, render} from '@testing-library/react';
import {noop} from 'lodash';
import React from 'react';
import {Provider} from 'react-redux';
import {MemoryRouter, Route} from 'react-router-dom';
import * as API from '~/shared/api';
import {waitForLoadingToBeRemoved} from '~/test/helpers';
import mockStore from '~/test/mock-store';

import DeleteChannelModal from '../DeleteChannelModal';

jest.unmock('react-dom');

const DefaultWrapper = ({children}) => (
	<Provider store={mockStore()}>
		<MemoryRouter initialEntries={['/']}>
			<Route path="/">
				<MockedProvider
					cache={
						new InMemoryCache({
							addTypename: false,
							freezeResults: false,
						})
					}
				>
					{children}
				</MockedProvider>
			</Route>
		</MemoryRouter>
	</Provider>
);

describe('DeleteChannelModal', () => {
	afterEach(cleanup);

	it('renders without data source alert message', async () => {
		API.dataSource.fetchChannels.mockReturnValueOnce(
			Promise.resolve({items: [], total: 0})
		);

		const {container} = render(
			<DefaultWrapper>
				<DeleteChannelModal onClose={noop} onSubmit={noop} />
			</DefaultWrapper>
		);

		await waitForLoadingToBeRemoved(container);

		expect(container).toMatchSnapshot();
	});

	it('renders with data source alert message', async () => {
		API.dataSource.fetchChannels.mockReturnValueOnce(
			Promise.resolve({items: [{id: '1', name: 'Test Source'}], total: 1})
		);

		const {container, getByText} = render(
			<DefaultWrapper>
				<DeleteChannelModal onClose={noop} onSubmit={noop} />
			</DefaultWrapper>
		);

		await waitForLoadingToBeRemoved(container);

		expect(
			getByText(/To reconnect to Analytics Cloud with Test Source/)
		).toBeTruthy();
	});
});
