import mockStore, {mockStoreDataLDP} from 'test/mock-store';
import React from 'react';
import WorkspaceLayout from '../WorkspaceLayout';
import {ChannelProvider} from 'shared/context/channel';
import {MemoryRouter, Route, Routes} from 'react-router-dom';
import {Provider} from 'react-redux';
import {render, screen} from '@testing-library/react';

jest.unmock('react-dom');

jest.mock('route-middleware/BundleRouter', () => () => (
	<div data-testid="page" />
));

const renderWorkspaceLayout = (path: string) =>
	render(
		<Provider store={mockStore(mockStoreDataLDP)}>
			<ChannelProvider>
				<MemoryRouter initialEntries={[path]}>
					<Routes>
						<Route
							element={<WorkspaceLayout />}
							path="workspace/:groupId/*"
						/>
					</Routes>
				</MemoryRouter>
			</ChannelProvider>
		</Provider>
	);

describe('WorkspaceLayout', () => {
	it('renders the toolbar above the workspace pages', async () => {
		renderWorkspaceLayout('/workspace/23/sites');

		expect(await screen.findByTestId('page')).toBeTruthy();

		expect(screen.getByTitle(/language/i)).toBeTruthy();
	});

	it('does not render the toolbar on the settings pages', async () => {
		renderWorkspaceLayout('/workspace/23/settings/data-source');

		expect(await screen.findByTestId('page')).toBeTruthy();

		expect(screen.queryByTitle(/language/i)).toBeNull();
	});

	it('does not render the toolbar on the edit pages', async () => {
		renderWorkspaceLayout('/workspace/23/event-analysis/create');

		expect(await screen.findByTestId('page')).toBeTruthy();

		expect(screen.queryByTitle(/language/i)).toBeNull();
	});
});
