jest.mock(
	'shared/components/workspaces/SuccessDisplay',
	() => () => 'SuccessDisplay'
);

jest.mock(
	'shared/components/workspaces/ErrorDisplay',
	() =>
		({errorType}: {errorType: string}) =>
			`WorkspacesErrorDisplay ${errorType}`
);

jest.mock(
	'shared/components/workspaces/ActivatingDisplay',
	() => () => 'ActivatingDisplay'
);

jest.mock('shared/pages/WorkspaceNotFound', () => () => 'WorkspaceNotFound');

jest.mock('shared/components/MaintenanceAlert', () => () => null);

jest.mock('shared/components/NotificationAlertList', () => ({
	__esModule: true,
	default: () => null,
	useNotificationsAPI: () => ({data: [], loading: false}),
}));

jest.mock('shared/context/dataSources', () => ({
	__esModule: true,
	default: ({children}: {children: React.ReactNode}) => <>{children}</>,
}));

jest.mock('shared/pages/NoPropertiesAvailable', () => () => 'NoProperties');

jest.mock('shared/pages/ErrorPage', () => () => 'ErrorPage');

import * as API from 'shared/api';
import * as data from 'test/data';
import EditLayout, {LDPEditLayout} from '../EditLayout';
import mockStore, {mockStoreData, mockStoreDataLDP} from 'test/mock-store';
import React from 'react';
import {ChannelProvider} from 'shared/context/channel';
import {MemoryRouter, Route, Routes as RouterRoutes} from 'react-router-dom';
import {ProjectStates} from 'shared/util/constants';
import {Provider} from 'react-redux';
import {render, screen} from '@testing-library/react';

jest.unmock('react-dom');

const CHANNELS = [
	data.mockChannel(1, 0, {id: '1'}),
	data.mockChannel(2, 0, {id: '2'}),
];

const renderLayout = (path: string, storeData = mockStoreData) =>
	render(
		<Provider store={mockStore(storeData)}>
			<ChannelProvider>
				<MemoryRouter initialEntries={[path]}>
					<RouterRoutes>
						<Route path="workspace/:groupId/*">
							<Route element={<EditLayout />}>
								<Route
									element={
										<div>{'event analysis editor'}</div>
									}
									path=":channelId?/event-analysis/create"
								/>

								<Route element={<LDPEditLayout />}>
									<Route
										element={
											<div>{'lifecycle editor'}</div>
										}
										path=":channelId?/lifecycle/new"
									/>
								</Route>
							</Route>
						</Route>
					</RouterRoutes>
				</MemoryRouter>
			</ChannelProvider>
		</Provider>
	);

describe('EditLayout', () => {
	beforeEach(() => {
		(API.channels.fetchAll as jest.Mock).mockReturnValue(
			Promise.resolve({items: CHANNELS})
		);
		(API.projects.fetch as jest.Mock).mockImplementation(({groupId}) =>
			Promise.resolve(
				data.mockProject(groupId, {
					state:
						groupId === '25'
							? ProjectStates.Maintenance
							: ProjectStates.Ready,
				})
			)
		);
	});

	it('renders the editor for a valid channel', async () => {
		renderLayout('/workspace/23/2/event-analysis/create');

		expect(
			await screen.findByText('event analysis editor')
		).toBeInTheDocument();
	});

	it('renders the editor when the URL has no channel', async () => {
		renderLayout('/workspace/23/event-analysis/create');

		expect(
			await screen.findByText('event analysis editor')
		).toBeInTheDocument();
	});

	it('does not render the product menu', async () => {
		const {container} = renderLayout(
			'/workspace/23/1/event-analysis/create'
		);

		await screen.findByText('event analysis editor');

		expect(container.querySelector('.sidebar-root')).toBeNull();
		expect(container.querySelector('.top-bar-root')).toBeNull();
	});

	it('renders the project state screen when the workspace is not ready', async () => {
		renderLayout('/workspace/25/1/event-analysis/create');

		expect(
			await screen.findByText(/WorkspacesErrorDisplay/)
		).toBeInTheDocument();
		expect(
			screen.queryByText('event analysis editor')
		).not.toBeInTheDocument();
	});

	it('renders the error page for an unknown channel', async () => {
		renderLayout('/workspace/23/999/event-analysis/create');

		expect(await screen.findByText('ErrorPage')).toBeInTheDocument();
	});

	it('renders NoPropertiesAvailable when the workspace has no channels', async () => {
		(API.channels.fetchAll as jest.Mock).mockReturnValue(
			Promise.resolve({items: []})
		);

		renderLayout('/workspace/23/event-analysis/create');

		expect(await screen.findByText('NoProperties')).toBeInTheDocument();
	});

	it('renders the lifecycle editor on LDP plans', async () => {
		(API.projects.fetch as jest.Mock).mockImplementation(({groupId}) =>
			Promise.resolve(
				data.mockProject(groupId, {
					faroSubscription: {
						name: 'Liferay Data Platform (Private Beta)',
					},
				})
			)
		);

		renderLayout('/workspace/23/1/lifecycle/new', mockStoreDataLDP);

		expect(await screen.findByText('lifecycle editor')).toBeInTheDocument();
	});

	it('renders the activating screen while the workspace is being set up', async () => {
		(API.projects.fetch as jest.Mock).mockImplementation(({groupId}) =>
			Promise.resolve(
				data.mockProject(groupId, {state: ProjectStates.Activating})
			)
		);

		renderLayout('/workspace/26/1/event-analysis/create');

		expect(
			await screen.findByText('ActivatingDisplay')
		).toBeInTheDocument();
	});

	it('renders WorkspaceNotFound when the workspace cannot be loaded', async () => {
		(API.projects.fetch as jest.Mock).mockReturnValue(
			Promise.reject({status: 404})
		);

		renderLayout('/workspace/27/1/event-analysis/create');

		expect(
			await screen.findByText('WorkspaceNotFound')
		).toBeInTheDocument();
	});

	it('renders the error page for lifecycle editors outside LDP plans', async () => {
		renderLayout('/workspace/23/1/lifecycle/new');

		expect(await screen.findByText('ErrorPage')).toBeInTheDocument();
	});
});
