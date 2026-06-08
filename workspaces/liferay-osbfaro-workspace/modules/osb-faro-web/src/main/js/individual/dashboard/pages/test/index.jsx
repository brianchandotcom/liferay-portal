/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {cleanup, render} from '@testing-library/react';
import React from 'react';
import {Provider} from 'react-redux';
import {BrowserRouter} from 'react-router-dom';
import {ChannelContext} from '~/shared/context/channel';
import DataSourcesProvider from '~/shared/context/dataSources';
import {mockChannelContext} from '~/test/mock-channel-context';
import mockStore from '~/test/mock-store';

import Dashboard from '../index';

jest.unmock('react-dom');

jest.mock('react-router-dom', () => ({
	...jest.requireActual('react-router-dom'),
	useParams: () => ({
		channelId: '456',
		groupId: '123',
	}),
}));

describe('Individuals Dashboard', () => {
	afterEach(cleanup);

	it('renders', () => {
		const {container} = render(
			<Provider store={mockStore()}>
				<BrowserRouter>
					<ChannelContext.Provider value={mockChannelContext()}>
						<DataSourcesProvider groupId="123">
							<Dashboard />
						</DataSourcesProvider>
					</ChannelContext.Provider>
				</BrowserRouter>
			</Provider>
		);

		expect(container).toMatchSnapshot();
	});
});
