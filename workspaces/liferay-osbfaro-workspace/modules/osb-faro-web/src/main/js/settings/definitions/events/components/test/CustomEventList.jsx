/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {InMemoryCache} from '@apollo/client';
import {MockedProvider} from '@apollo/client/testing';
import {cleanup, render} from '@testing-library/react';
import {range} from 'lodash';
import React from 'react';
import {Provider} from 'react-redux';
import {MemoryRouter, Route} from 'react-router-dom';
import * as API from '~/shared/api';
import * as NotificationAlertList from '~/shared/components/NotificationAlertList';
import {useCurrentUser} from '~/shared/hooks/useCurrentUser';
import {NotificationSubtypes} from '~/shared/util/records/Notification';
import {Routes} from '~/shared/util/router';
import * as data from '~/test/data';
import {mockEventDefinitionsReq} from '~/test/graphql-data';
import {waitForLoadingToBeRemoved} from '~/test/helpers';
import mockStore from '~/test/mock-store';

import CustomEventList from '../CustomEventList';

jest.unmock('react-dom');

jest.mock('~/shared/hooks/useCurrentUser', () => ({
	useCurrentUser: jest.fn(),
}));

const mockNotificationAlertList = NotificationAlertList;

const WrappedComponent = ({
	children,
	initialEntries = [
		'/workspace/23/settings/definitions/events/custom?delta=1',
	],
	mocks = [
		mockEventDefinitionsReq(
			[
				data.mockEventDefinition(0, {
					__typename: 'EventDefinition',
					type: 'CUSTOM',
				}),
			],
			{
				blocked: false,
				eventType: 'CUSTOM',
			}
		),
	],
}) => (
	<Provider store={mockStore()}>
		<MemoryRouter initialEntries={initialEntries}>
			<Route path={Routes.SETTINGS_DEFINITIONS_EVENTS_CUSTOM}>
				<MockedProvider
					cache={new InMemoryCache({freezeResults: false})}
					mocks={mocks}
				>
					{children}
				</MockedProvider>
			</Route>
		</MemoryRouter>
	</Provider>
);

describe('CustomEventList', () => {
	afterEach(cleanup);

	it('renders', async () => {
		useCurrentUser.mockImplementation(() => ({isAdmin: () => true}));

		const {container, queryByTestId} = render(
			<WrappedComponent>
				<CustomEventList groupId="23" />
			</WrappedComponent>
		);

		await waitForLoadingToBeRemoved(container);

		expect(queryByTestId('select-all-checkbox')).toBeTruthy();
		expect(container).toMatchSnapshot();
	});

	it('renders w/o select all checkbox if user is not an admin', async () => {
		useCurrentUser.mockImplementation(() => ({isAdmin: () => false}));

		API.user.fetchCurrentUser.mockReturnValueOnce(
			Promise.resolve(data.mockMemberUser('23'))
		);

		const {container, queryByTestId} = render(
			<WrappedComponent>
				<CustomEventList groupId="23" />
			</WrappedComponent>
		);

		await waitForLoadingToBeRemoved(container);

		expect(queryByTestId('select-all-checkbox')).toBeNull();
	});

	it('renders alert notification', async () => {
		useCurrentUser.mockImplementation(() => ({isAdmin: () => true}));

		mockNotificationAlertList.useNotificationsAPI = jest.fn(() => ({
			data: range(1).map((i) =>
				data.mockNotification(i, {
					subtype: NotificationSubtypes.BlockedEventsLimit,
				})
			),
			loading: false,
			refetch: () => {},
		}));

		const {container} = render(
			<WrappedComponent>
				<CustomEventList groupId="23" />
			</WrappedComponent>
		);

		await waitForLoadingToBeRemoved(container);

		expect(container).toMatchSnapshot();
	});
});
