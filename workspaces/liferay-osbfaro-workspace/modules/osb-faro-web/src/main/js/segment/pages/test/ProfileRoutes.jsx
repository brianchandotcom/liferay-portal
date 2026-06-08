/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {cleanup, render, screen} from '@testing-library/react';
import React from 'react';
import {Provider} from 'react-redux';
import {BrowserRouter} from 'react-router-dom';
import * as API from '~/shared/api';
import {ChannelContext} from '~/shared/context/channel';
import DataSourcesProvider from '~/shared/context/dataSources';
import {mockSegment} from '~/test/data';
import {waitForLoadingToBeRemoved} from '~/test/helpers';
import {mockChannelContext} from '~/test/mock-channel-context';
import mockStore from '~/test/mock-store';

import {SegmentProfileRoutes} from '../ProfileRoutes';

jest.unmock('react-dom');

jest.mock('react-router-dom', () => ({
	...jest.requireActual('react-router-dom'),
	useParams: () => ({
		channelId: '123',
		groupId: '23',
		id: 'test',
	}),
}));

describe('SegmentProfileRoutes', () => {
	afterEach(cleanup);

	beforeAll(() => {
		delete window.location;
	});

	it('renders', async () => {
		window.location = {pathname: '/'};

		const {container} = render(
			<Provider store={mockStore()}>
				<BrowserRouter>
					<ChannelContext.Provider value={mockChannelContext()}>
						<DataSourcesProvider groupId="23">
							<SegmentProfileRoutes />
						</DataSourcesProvider>
					</ChannelContext.Provider>
				</BrowserRouter>
			</Provider>
		);

		await waitForLoadingToBeRemoved(container);

		expect(screen.getAllByText('Seattle0').length).toBeGreaterThan(0);
	});

	it('renders the external reference code with its label', async () => {
		window.location = {pathname: '/'};

		API.individualSegment.fetch.mockReturnValueOnce(
			Promise.resolve(mockSegment(0, {externalReferenceCode: 'my-erc'}))
		);

		const {container} = render(
			<Provider store={mockStore()}>
				<BrowserRouter>
					<ChannelContext.Provider value={mockChannelContext()}>
						<DataSourcesProvider groupId="23">
							<SegmentProfileRoutes />
						</DataSourcesProvider>
					</ChannelContext.Provider>
				</BrowserRouter>
			</Provider>
		);

		await waitForLoadingToBeRemoved(container);

		expect(screen.getByText('ERC: my-erc')).toBeTruthy();
	});
});
