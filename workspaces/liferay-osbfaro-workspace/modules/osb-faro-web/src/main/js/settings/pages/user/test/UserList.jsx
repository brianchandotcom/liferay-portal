/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {InMemoryCache} from '@apollo/client';
import {MockedProvider} from '@apollo/client/testing';
import {render} from '@testing-library/react';
import React from 'react';
import {Provider} from 'react-redux';
import {MemoryRouter, Route} from 'react-router-dom';
import {UserRoleNames} from '~/shared/util/constants';
import {User} from '~/shared/util/records';
import {Routes} from '~/shared/util/router';
import * as data from '~/test/data';
import {waitForLoadingToBeRemoved} from '~/test/helpers';
import mockStore from '~/test/mock-store';

import UserList from '../UserList';

jest.unmock('react-dom');

const defaultProps = {
	currentUser: new User(data.mockUser()),
	groupId: '23',
};

const DefaultComponent = (props) => (
	<Provider store={mockStore()}>
		<MemoryRouter initialEntries={['/workspace/23/settings/users']}>
			<Route path={Routes.SETTINGS_USERS}>
				<MockedProvider
					cache={
						new InMemoryCache({
							addTypename: false,
							freezeResults: false,
						})
					}
				>
					<UserList {...defaultProps} {...props} />
				</MockedProvider>
			</Route>
		</MemoryRouter>
	</Provider>
);

describe('UserList', () => {
	it('renders', async () => {
		const {container} = render(<DefaultComponent />);

		await waitForLoadingToBeRemoved(container);

		expect(container).toMatchSnapshot();
	});

	it("renders rows as disabled without row actions, invite members button, or checkboxes if the current user's role is member", async () => {
		const {container, queryByTestId, queryByText} = render(
			<DefaultComponent
				currentUser={
					new User(data.mockUser(0, {roleName: UserRoleNames.Member}))
				}
			/>
		);

		await waitForLoadingToBeRemoved(container);

		expect(
			container.querySelector(
				'.table > tbody:nth-of-type(1) > tr.disabled'
			)
		).not.toBeNull();

		expect(queryByTestId('select-all-checkbox')).not.toBeInTheDocument();

		expect(queryByText('Invite Users')).not.toBeInTheDocument();
	});
});
