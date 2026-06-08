/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {ApolloProvider} from '@apollo/client';
import {cleanup, render} from '@testing-library/react';
import {Map} from 'immutable';
import {noop} from 'lodash';
import React from 'react';
import {Provider} from 'react-redux';
import {BrowserRouter} from 'react-router-dom';
import client from '~/shared/apollo/client';
import {useFetchProjects} from '~/shared/hooks/useProjects';
import {Project} from '~/shared/util/records';
import {Routes, toRoute} from '~/shared/util/router';
import {SubscriptionNames} from '~/shared/util/subscriptions';
import * as data from '~/test/data';
import mockStore from '~/test/mock-store';

import Workspaces, {routingFn} from '../Workspaces';

const corpProjectUuid = 'corpProjectUuid24';

const mockBasicProject = (id) =>
	data.getImmutableMock(Project, data.mockProject, id, {
		faroSubscription: new Map(
			data.mockSubscription({
				name: SubscriptionNames.LiferayAnalyticsCloudBasic,
			})
		),
	});

const mockBasicProjectUnconfigured = (id) =>
	data.getImmutableMock(Project, data.mockProject, id, {
		corpProjectUuid,
		faroSubscription: new Map(
			data.mockSubscription({
				name: SubscriptionNames.LiferayAnalyticsCloudBasic,
			})
		),
	});

const DefaultComponent = (props) => (
	<ApolloProvider client={client}>
		<Provider store={mockStore()}>
			<BrowserRouter>
				<Workspaces {...props} clearStore={jest.fn()} />
			</BrowserRouter>
		</Provider>
	</ApolloProvider>
);

jest.mock('~/shared/hooks/useProjects', () => ({
	useFetchJoinableProjects: () => ({data: [], loading: false}),
	useFetchProjects: jest.fn(),
}));

jest.unmock('react-dom');

jest.mock('~/shared/hooks/useIncidentAlert', () => ({
	useIncidentAlert: jest.fn(() => ({
		data: {incidentAlertEnabled: false},
		loading: false,
	})),
}));

describe('Workspaces', () => {
	afterEach(cleanup);

	const client = {
		clearStore: noop,
	};

	it('renders empty state', () => {
		useFetchProjects.mockImplementation(() => ({data: [], loading: false}));

		const {container} = render(<DefaultComponent />);

		expect(container).toMatchSnapshot();
	});

	it('renders with the incident alert message notification', () => {
		const {useIncidentAlert} = require('~/shared/hooks/useIncidentAlert');

		useFetchProjects.mockImplementation(() => ({data: [], loading: false}));

		useIncidentAlert.mockImplementation(() => ({
			data: {incidentAlertEnabled: true},
			loading: false,
		}));

		const {container, getByText} = render(<DefaultComponent />);

		expect(
			getByText(
				'Maintenance is scheduled for November 13 and may impact your workflow.'
			)
		).toBeInTheDocument();

		expect(
			getByText('Visit our Status Page for more details.')
		).toBeInTheDocument();

		const statusPageAnnouncementsLink = container.querySelector(
			'a[href="https://status.liferay.cloud/incidents/hxw439lxhs26"]'
		);

		expect(statusPageAnnouncementsLink).toBeInTheDocument();

		useIncidentAlert.mockImplementation(() => ({
			data: {incidentAlertEnabled: false},
			loading: false,
		}));
	});

	it('renders with a message that one or more basic tier workspaces are available to be configured', () => {
		const mockProjects = [
			mockBasicProjectUnconfigured(124),
			data.getImmutableMock(Project, data.mockProject, 123, {
				faroSubscription: new Map(
					data.mockSubscription({
						name: SubscriptionNames.LiferayAnalyticsCloudBusiness,
					})
				),
			}),
		];

		useFetchProjects.mockImplementation(() => ({
			data: mockProjects,
			loading: false,
		}));

		const {container} = render(<DefaultComponent />);

		expect(container).toMatchSnapshot();
	});

	it('renders with a message that all basic tier plans have been configured if all of the basic tier plans have been configured and the allBasicConfigured url prop is true', () => {
		useFetchProjects.mockImplementation(() => ({
			data: [mockBasicProject(123), mockBasicProject(124)],
			loading: false,
		}));

		const {container} = render(
			<DefaultComponent allBasicConfigured client={client} />
		);

		expect(container).toMatchSnapshot();
	});

	it('renders a list of projects if there is only one project and it is configured', () => {
		useFetchProjects.mockImplementation(() => ({
			data: [mockBasicProject(23)],
			loading: false,
		}));

		const {container} = render(<DefaultComponent />);

		expect(container).toMatchSnapshot();
	});
});

describe('routingFn', () => {
	it('routes the user to the create workspace page if there is only one workspace and it is NOT configured', () => {
		const expectedRoute = toRoute(
			Routes.WORKSPACE_ADD_WITH_CORP_PROJECT_UUID,
			{
				corpProjectUuid,
			}
		);

		expect(routingFn({projects: [mockBasicProjectUnconfigured(0)]})).toBe(
			expectedRoute
		);
	});
});
