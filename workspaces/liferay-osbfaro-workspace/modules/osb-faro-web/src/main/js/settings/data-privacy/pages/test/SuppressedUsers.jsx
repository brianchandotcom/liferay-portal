/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {MockedProvider} from '@apollo/client/testing';
import {cleanup, render} from '@testing-library/react';
import React from 'react';
import {Provider} from 'react-redux';
import {MemoryRouter, Route} from 'react-router-dom';
import {Routes} from '~/shared/util/router';
import {mockSuppressedUsersListReq} from '~/test/graphql-data';
import {waitForLoadingToBeRemoved} from '~/test/helpers';
import mockStore from '~/test/mock-store';

import {SuppressedUsers} from '../SuppressedUsers';

jest.unmock('react-dom');

jest.mock('~/shared/hooks/useCurrentUser', () => ({
	useCurrentUser: () => ({
		id: '123',
		isAdmin: () => true,
		name: 'Marcos',
		userId: '456',
	}),
}));

jest.mock('~/shared/hooks/useTimeZone', () => ({
	useTimeZone: () => ({timeZoneId: 'UTC'}),
}));

const mockItems = [
	{
		createDate: '2024-01-01T00:00:00Z',
		dataControlTaskBatchId: '123',
		dataControlTaskCreateDate: '2024-01-01T00:00:00Z',
		dataControlTaskStatus: 'PENDING',
		emailAddress: 'Test@liferay.com',
		id: '321',
	},
];

describe('SuppressedUsers', () => {
	afterEach(cleanup);

	it('renders', async () => {
		const mocks = [
			mockSuppressedUsersListReq(mockItems, {
				size: 5,
				sort: {column: 'createDate', type: 'DESC'},
			}),
		];

		const {container} = render(
			<Provider store={mockStore()}>
				<MemoryRouter
					initialEntries={[
						'/workspace/23/settings/data-privacy/suppressed-users?delta=5',
					]}
				>
					<Route path={Routes.SETTINGS_DATA_PRIVACY_SUPPRESSED_USERS}>
						<MockedProvider addTypename={false} mocks={mocks}>
							<SuppressedUsers
								router={{
									params: {groupId: '23'},
									query: {delta: '5', page: '1'},
								}}
							/>
						</MockedProvider>
					</Route>
				</MemoryRouter>
			</Provider>
		);

		await waitForLoadingToBeRemoved(container);

		expect(container).toMatchSnapshot();
	});
});
