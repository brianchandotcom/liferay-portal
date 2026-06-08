/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {cleanup, fireEvent, render} from '@testing-library/react';
import React from 'react';
import {Provider} from 'react-redux';
import {BrowserRouter} from 'react-router-dom';
import mockStore from '~/test/mock-store';

import ChannelsMenu from '../index';

jest.unmock('react-dom');

const mockMenuItems = () => [
	{
		id: '1',
		name: 'Link 1',
		url: '/1',
	},
	{
		id: '2',
		name: 'Link 2',
		url: '/2',
	},
];

describe('ChannelsMenu', () => {
	afterEach(cleanup);

	it('renders the first item as label', () => {
		const {container} = render(
			<Provider store={mockStore()}>
				<BrowserRouter>
					<ChannelsMenu channels={mockMenuItems()} groupId="123456" />
				</BrowserRouter>
			</Provider>
		);

		expect(container).toHaveTextContent('Link 1');
		expect(container).toMatchSnapshot();
	});

	it('renders the empty state label when channels is not provided', () => {
		const {container} = render(
			<Provider store={mockStore()}>
				<BrowserRouter>
					<ChannelsMenu groupId="123456" />
				</BrowserRouter>
			</Provider>
		);

		expect(container).toHaveTextContent('No Properties');
	});

	it('renders dropdown menu when clicked', () => {
		const {container} = render(
			<Provider store={mockStore()}>
				<BrowserRouter>
					<ChannelsMenu
						channels={mockMenuItems()}
						defaultChannelId="1"
						groupId="123456"
					/>
				</BrowserRouter>
			</Provider>
		);

		const toggleButton = container.querySelector('.channels-menu');

		fireEvent.click(toggleButton);

		expect(
			document.body.querySelector('.channels-menu-dropdown')
		).toBeTruthy();
	});
});
