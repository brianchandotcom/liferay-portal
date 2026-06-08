/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {InMemoryCache} from '@apollo/client';
import {MockedProvider} from '@apollo/client/testing';
import {cleanup, render} from '@testing-library/react';
import React from 'react';
import {Provider} from 'react-redux';
import {MemoryRouter, Route} from 'react-router-dom';
import {GDPRRequestStatuses} from '~/shared/util/constants';
import {Routes} from '~/shared/util/router';
import {mockSuppressedUsersListReq} from '~/test/graphql-data';
import {waitForLoadingToBeRemoved} from '~/test/helpers';
import mockStore from '~/test/mock-store';

import SuppressedUserList from '../SuppressedUserList';

jest.unmock('react-dom');

const mockItems = [
	{
		createDate: '2019-09-10T00:00',
		dataControlTaskBatchId: '00001',
		dataControlTaskCreateDate: '2019-09-09T00:00',
		dataControlTaskStatus: GDPRRequestStatuses.Pending,
		emailAddress: 'foo@email',
		id: '12345',
	},
	{
		createDate: '2019-09-11T00:00',
		dataControlTaskBatchId: '00002',
		dataControlTaskCreateDate: '2019-09-09T00:00',
		dataControlTaskStatus: GDPRRequestStatuses.Completed,
		emailAddress: 'bar@email',
		id: '6789',
	},
];

const WrappedComponent = (props) => (
	<Provider store={mockStore()}>
		<MemoryRouter
			initialEntries={[
				'/workspace/23/settings/data-privacy/suppressed-users?delta=5&page=1',
			]}
		>
			<Route path={Routes.SETTINGS_DATA_PRIVACY_SUPPRESSED_USERS}>
				<MockedProvider
					cache={
						new InMemoryCache({
							addTypename: false,
							freezeResults: false,
						})
					}
					mocks={[mockSuppressedUsersListReq(mockItems)]}
				>
					<SuppressedUserList
						currentUser={{isAdmin: () => true}}
						router={{
							params: {groupId: '23'},
							query: {delta: '5', page: '1'},
						}}
						{...props}
					/>
				</MockedProvider>
			</Route>
		</MemoryRouter>
	</Provider>
);

describe('Suppressed User List', () => {
	afterEach(cleanup);

	it('renders', async () => {
		const {container} = render(<WrappedComponent />);

		await waitForLoadingToBeRemoved(container);

		expect(container).toMatchSnapshot();
	});
});
