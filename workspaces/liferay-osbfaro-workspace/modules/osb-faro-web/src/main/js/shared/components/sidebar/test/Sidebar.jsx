/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {render} from '@testing-library/react';
import React from 'react';
import {Provider} from 'react-redux';
import {StaticRouter} from 'react-router';
import {User} from '~/shared/util/records';
import mockStore, {mockStoreDataLDP} from '~/test/mock-store';

import Sidebar from '../index';

const defaultProps = {
	activePathname: '',
	channelId: '123',
	currentUser: new User({emailAddress: 'test@test.com', name: 'Test Test'}),
	groupId: '23',
};

jest.unmock('react-dom');

describe('Sidebar', () => {
	it('renders', () => {
		const {container} = render(
			<Provider store={mockStore(mockStoreDataLDP)}>
				<StaticRouter>
					<Sidebar {...defaultProps} />
				</StaticRouter>
			</Provider>
		);

		expect(container).toMatchSnapshot();
	});

	it('renders as collapsed', () => {
		const {container} = render(
			<Provider store={mockStore(mockStoreDataLDP)}>
				<StaticRouter>
					<Sidebar {...defaultProps} collapsed />
				</StaticRouter>
			</Provider>
		);

		expect(container.querySelector('.sidebar-root')).toHaveClass(
			'collapsed'
		);
	});

	it('renders with a specific sidebar id active', () => {
		const activePathName = '/workspace/23/123/contacts/individuals';

		const {container} = render(
			<Provider store={mockStore(mockStoreDataLDP)}>
				<StaticRouter>
					<Sidebar
						{...defaultProps}
						activePathname={activePathName}
					/>
				</StaticRouter>
			</Provider>
		);

		expect(
			container.querySelector('.sidebar-item-root.active').firstChild
		).toHaveAttribute('href', activePathName);
	});

	it('renders lifecycle and accounts items when LDP is enabled', () => {
		const {queryByText} = render(
			<Provider store={mockStore(mockStoreDataLDP)}>
				<StaticRouter>
					<Sidebar {...defaultProps} />
				</StaticRouter>
			</Provider>
		);

		expect(queryByText('Lifecycles')).toBeTruthy();
		expect(queryByText('Accounts')).toBeTruthy();
	});

	it('does not render lifecycle and accounts items when LDP is not enabled', () => {
		const {queryByText} = render(
			<Provider store={mockStore()}>
				<StaticRouter>
					<Sidebar {...defaultProps} />
				</StaticRouter>
			</Provider>
		);

		expect(queryByText('Lifecycles')).toBeNull();
		expect(queryByText('Accounts')).toBeNull();
	});
});
