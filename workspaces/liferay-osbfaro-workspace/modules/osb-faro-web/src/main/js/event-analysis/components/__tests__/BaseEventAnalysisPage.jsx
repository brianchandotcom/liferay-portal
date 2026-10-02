jest.mock('../event-analysis-editor', () => ({
	__esModule: true,
	default: jest.fn(() => 'event analysis editor')
}));

jest.mock(
	'shared/components/download-report/DownloadPDFReport',
	() => () => null
);

jest.mock('shared/components/NavigationWarning', () => () => null);

import * as modalActions from 'shared/actions/modals';
import BaseEventAnalysisPage from '../BaseEventAnalysisPage';
import DataSourcesProvider from 'shared/context/dataSources';
import EventAnalysisEditor from '../event-analysis-editor';
import mockStore, {mockStoreData} from 'test/mock-store';
import React from 'react';
import {CreateEventAnalysisMutation} from 'event-analysis/queries/EventAnalysisQuery';
import {fireEvent, render, screen, waitFor} from '@testing-library/react';
import {MemoryRouter, Route, Routes as RouterRoutes} from 'react-router-dom';
import {MockedProvider} from '@apollo/client/testing';
import {Provider} from 'react-redux';

jest.unmock('react-dom');

const EVENT = {displayName: 'Page Viewed', id: '1', name: 'pageViewed'};

const renderPage = () =>
	render(
		<Provider store={mockStore(mockStoreData)}>
			<MockedProvider
				addTypename={false}
				mocks={[
					{
						request: {query: CreateEventAnalysisMutation},
						result: {data: {createEventAnalysis: {id: '1'}}},
						variableMatcher: () => true
					}
				]}
			>
				<MemoryRouter
					initialEntries={['/workspace/23/1/event-analysis/create']}
				>
					<RouterRoutes>
						<Route
							element={
								<DataSourcesProvider groupId="23">
									<BaseEventAnalysisPage event={EVENT} />
								</DataSourcesProvider>
							}
							path="workspace/:groupId/:channelId/event-analysis/create"
						/>

						<Route
							element={<p>{'event analysis list'}</p>}
							path="workspace/:groupId/:channelId/event-analysis"
						/>
					</RouterRoutes>
				</MemoryRouter>
			</MockedProvider>
		</Provider>
	);

describe('BaseEventAnalysisPage', () => {
	beforeEach(() => {
		jest.clearAllMocks();
	});

	it('shows the creating title while saving a new analysis', async () => {
		const openSpy = jest.spyOn(modalActions, 'open');

		renderPage();

		fireEvent.change(screen.getByLabelText(/^title/i), {
			target: {value: 'My Analysis'}
		});

		fireEvent.click(screen.getByText('Save Analysis'));

		await waitFor(() =>
			expect(openSpy).toHaveBeenCalledWith(
				modalActions.modalTypes.LOADING_MODAL,
				expect.objectContaining({title: 'Creating...'}),
				{closeOnBlur: false}
			)
		);

		expect(
			await screen.findByText('event analysis list')
		).toBeInTheDocument();
	});
});
