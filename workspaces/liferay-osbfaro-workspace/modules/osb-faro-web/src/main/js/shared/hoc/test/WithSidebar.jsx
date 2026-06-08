/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {cleanup, render} from '@testing-library/react';
import React from 'react';
import {Provider} from 'react-redux';
import {BrowserRouter} from 'react-router-dom';
import {compose} from 'redux';
import {ChannelContext} from '~/shared/context/channel';
import {waitForLoadingToBeRemoved} from '~/test/helpers';
import {
	mockChannelContext,
	withChannelProvider,
} from '~/test/mock-channel-context';
import {withStaticRouter} from '~/test/mock-router';
import mockStore from '~/test/mock-store';

import withSidebar from '../WithSidebar';

jest.unmock('react-dom');

describe('withSidebar', () => {
	afterEach(cleanup);

	it('renders loading', () => {
		const WrappedComponent = withSidebar(() => <div>foobar</div>);

		const {container} = render(
			<Provider store={mockStore()}>
				<ChannelContext.Provider value={mockChannelContext()}>
					<BrowserRouter>
						<WrappedComponent />
					</BrowserRouter>
				</ChannelContext.Provider>
			</Provider>
		);

		expect(container).toMatchSnapshot();
	});

	it('renders with the sidebar', async () => {
		const WrappedComponent = compose(
			withChannelProvider,
			withStaticRouter,
			withSidebar
		)(() => <p>bizbaz</p>);

		const {container} = render(
			<Provider store={mockStore()}>
				<BrowserRouter>
					<WrappedComponent
						groupId="23"
						location={{pathname: 'foo'}}
					/>
				</BrowserRouter>
			</Provider>
		);

		jest.runAllTimers();

		await waitForLoadingToBeRemoved(container);

		expect(container).toMatchSnapshot();
	});
});
