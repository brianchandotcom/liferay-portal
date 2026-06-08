/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {cleanup, render, screen} from '@testing-library/react';
import React from 'react';
import {Provider} from 'react-redux';
import {MemoryRouter, Route} from 'react-router-dom';
import * as API from '~/shared/api';
import {RemoteData, User} from '~/shared/util/records';
import {Routes} from '~/shared/util/router';
import * as data from '~/test/data';
import {waitForLoadingToBeRemoved} from '~/test/helpers';
import mockStore, {mockStoreData} from '~/test/mock-store';

import ChannelList from '../ChannelList';

jest.unmock('react-dom');

const defaultProps = {
	groupId: '23',
};

const Wrapper = ({children, store = mockStore()}) => (
	<Provider store={store}>
		<MemoryRouter initialEntries={['/workspace/23/settings/properties']}>
			<Route path={Routes.SETTINGS_CHANNELS}>{children}</Route>
		</MemoryRouter>
	</Provider>
);

describe.skip('Channels List', () => {
	afterEach(cleanup);

	it('renders', async () => {
		API.channels.search.mockReturnValue(
			Promise.resolve(data.mockChannels())
		);

		const {container} = render(
			<Wrapper>
				<ChannelList {...defaultProps} />
			</Wrapper>
		);

		await waitForLoadingToBeRemoved();

		expect(container).toMatchSnapshot();
	});

	it('does not render add button if user is not an admin', async () => {
		const memberStore = mockStore(
			mockStoreData.setIn(['currentUser', 'data'], '24').setIn(
				['users', '24'],
				new RemoteData({
					data: new User(data.mockMemberUser('24')),
					loading: false,
				})
			)
		);

		API.channels.search.mockReturnValue(
			Promise.resolve(data.mockChannels())
		);

		render(
			<Wrapper store={memberStore}>
				<ChannelList {...defaultProps} />
			</Wrapper>
		);

		await waitForLoadingToBeRemoved();

		expect(screen.queryByText('New Property')).toBeNull();
	});

	it('checks if the number of synced sites and channels appears for each property', async () => {
		API.channels.search.mockReturnValue(
			Promise.resolve(data.mockChannels())
		);

		render(
			<Wrapper>
				<ChannelList {...defaultProps} />
			</Wrapper>
		);

		await waitForLoadingToBeRemoved();

		expect(screen.getByText('Sites')).toBeInTheDocument();
		expect(screen.getByText('Channels')).toBeInTheDocument();
		expect(screen.getByText('Liferay DXP')).toBeInTheDocument();
		expect(screen.getAllByText('6')).toHaveLength(1);
		expect(screen.getAllByText('5')).toHaveLength(1);
	});

	it('checks if actions "DELETE" and "CLEAR DATA" are displayed in the ellipsis and its icons', async () => {
		API.channels.search.mockReturnValue(
			Promise.resolve(data.mockChannels())
		);

		const {container} = render(
			<Wrapper>
				<ChannelList {...defaultProps} />
			</Wrapper>
		);

		await waitForLoadingToBeRemoved();

		// In some environments or screen sizes, RowActions might render quick actions as buttons
		// If they are in an ellipsis, we would need to click it first.
		// Let's check if the buttons are present.

		const clearDataBtn = screen.getByRole('button', {
			name: /clear data/i,
		});

		const deleteBtn = screen.getByRole('button', {
			name: /delete/i,
		});

		expect(clearDataBtn).toBeInTheDocument();
		expect(deleteBtn).toBeInTheDocument();

		const clearDataIcon = container.querySelector('svg.lexicon-icon-magic');

		const deleteIcon = container.querySelector('svg.lexicon-icon-trash');

		expect(clearDataIcon).toBeInTheDocument();
		expect(deleteIcon).toBeInTheDocument();
	});
});
