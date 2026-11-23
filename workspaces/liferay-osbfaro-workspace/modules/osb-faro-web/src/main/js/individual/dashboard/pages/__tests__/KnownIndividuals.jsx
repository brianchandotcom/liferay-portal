import {useDataSources} from 'shared/context/dataSources';
import * as API from 'shared/api';
import KnownIndividuals from '../KnownIndividuals';
import mockStore from 'test/mock-store';
import React from 'react';
import {cleanup, render} from '@testing-library/react';
import {MemoryRouter, Route, Routes as RouterRoutes} from 'react-router-dom';
import {mockEmptyState, mockSuccessState} from 'test/__mocks__/mock-objects';
import {Provider} from 'react-redux';
import {RangeKeyTimeRanges} from 'shared/util/constants';
import {Routes} from 'shared/util/router';
import {waitForLoadingToBeRemoved} from 'test/helpers';

jest.mock('shared/context/dataSources', () => ({
	...jest.requireActual('shared/context/dataSources'),
	useDataSources: jest.fn(),
}));

const WrappedComponent = () => (
	<Provider store={mockStore()}>
		<MemoryRouter
			initialEntries={[
				'/workspace/23/321321/contacts/individuals/known-individuals'
			]}
		>
			<RouterRoutes>
				<Route
					element={
						<KnownIndividuals />
					}
					path={`${Routes.CONTACTS_INDIVIDUALS_KNOWN_INDIVIDUALS}/*`}
				/>
			</RouterRoutes>
		</MemoryRouter>
	</Provider>
);

describe('Individuals Dashboard KnownIndividuals List', () => {
	afterEach(cleanup);

	it('renders', async () => {
		useDataSources.mockReturnValue(mockSuccessState);

		const {container} = render(<WrappedComponent />);

		await waitForLoadingToBeRemoved(container);

		jest.runAllTimers();

		expect(
			container.querySelector(
				'.individuals-dashboard-known-individuals-root'
			)
		).toBeInTheDocument();
	});

	it('renders with data source empty state', () => {
		useDataSources.mockReturnValue(mockEmptyState);

		const {getByText} = render(<WrappedComponent />);

		expect(getByText(/^no data sources are connected$/i)).toBeInTheDocument();
	});

	it('searches with the last year range key', async () => {
		useDataSources.mockReturnValue(mockSuccessState);

		const {container} = render(<WrappedComponent />);

		await waitForLoadingToBeRemoved(container);

		jest.runAllTimers();

		expect(API.individuals.search).toHaveBeenCalledWith(
			expect.objectContaining({
				rangeKey: RangeKeyTimeRanges.LastYear,
			})
		);
	});
});
