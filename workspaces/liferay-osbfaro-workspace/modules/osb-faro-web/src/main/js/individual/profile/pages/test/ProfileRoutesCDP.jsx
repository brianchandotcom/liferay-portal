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
import {Individual} from '~/shared/util/records';
import * as data from '~/test/data';
import {mockChannelContext} from '~/test/mock-channel-context';
import mockStore, {mockStoreDataLDP} from '~/test/mock-store';

import IndividualProfileRoutesCDP from '../ProfileRoutesCDP';

const defaultProps = {
	channelId: '123',
	groupId: '23',
	id: 'test',
	individual: data.getImmutableMock(Individual, data.mockIndividual),
	location: {pathname: ''},
};

jest.unmock('react-dom');

describe('IndividualProfileRoutes', () => {
	beforeAll(() => {
		delete window.location;
	});

	afterEach(cleanup);

	it('renders', () => {
		window.location = {pathname: '/'};

		const {container} = render(
			<Provider store={mockStore(mockStoreDataLDP)}>
				<ChannelContext.Provider value={mockChannelContext()}>
					<DataSourcesProvider groupId={defaultProps.groupId}>
						<BrowserRouter>
							<IndividualProfileRoutesCDP {...defaultProps} />
						</BrowserRouter>
					</DataSourcesProvider>
				</ChannelContext.Provider>
			</Provider>
		);

		expect(container).toMatchSnapshot();
	});
});
