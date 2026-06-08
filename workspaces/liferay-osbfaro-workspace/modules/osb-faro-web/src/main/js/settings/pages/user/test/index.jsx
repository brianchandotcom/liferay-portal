/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {cleanup, render} from '@testing-library/react';
import React from 'react';
import {Provider} from 'react-redux';
import {BrowserRouter} from 'react-router-dom';
import {useCurrentUser} from '~/shared/hooks/useCurrentUser';
import {User} from '~/shared/util/records';
import * as data from '~/test/data';
import mockStore from '~/test/mock-store';

import {User as UserRoutes} from '../index';

jest.unmock('react-dom');

jest.mock('~/shared/hooks/useCurrentUser', () => ({
	useCurrentUser: jest.fn(),
}));

jest.mock('react-router-dom', () => ({
	...jest.requireActual('react-router-dom'),
	useParams: () => ({
		groupId: '23',
	}),
}));

const DefaultComponent = (props) => (
	<Provider store={mockStore()}>
		<BrowserRouter>
			<UserRoutes {...props} />
		</BrowserRouter>
	</Provider>
);

describe('UserRoutes', () => {
	afterEach(cleanup);

	it('renders', () => {
		useCurrentUser.mockImplementation(() => ({
			isAdmin: () => true,
		}));

		const {container} = render(<DefaultComponent />);

		expect(container).toMatchSnapshot();
	});

	it('if the user is AC Admin, then the tabs for toggling between users and user requests should render', () => {
		useCurrentUser.mockImplementation(() => ({
			isAdmin: () => true,
		}));

		const {queryAllByText, queryByText} = render(<DefaultComponent />);

		expect(queryAllByText('Manage Users')).toBeTruthy();
		expect(queryByText('Requests')).toBeTruthy();
	});

	it('if the user is NOT an AC Admin, then the tabs for toggling between users and user requests should NOT render', () => {
		useCurrentUser.mockImplementation(() => ({
			isAdmin: () => false,
		}));

		const {queryByText} = render(
			<DefaultComponent
				currentUser={data.getImmutableMock(User, data.mockMemberUser)}
			/>
		);

		expect(queryByText('Manager Users')).toBeNull();
		expect(queryByText('Requests')).toBeNull();
	});
});
