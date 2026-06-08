/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {cleanup, render} from '@testing-library/react';
import React from 'react';
import {Provider} from 'react-redux';
import {MemoryRouter, Route} from 'react-router-dom';
import * as useDataSources from '~/shared/context/dataSources';
import {Routes} from '~/shared/util/router';
import {mockEmptyState, mockSuccessState} from '~/test/__mocks__/mock-objects';
import {waitForLoadingToBeRemoved} from '~/test/helpers';
import mockStore from '~/test/mock-store';

import KnownIndividuals from '../KnownIndividuals';

jest.unmock('react-dom');

const mockUseDataSource = useDataSources;

const WrappedComponent = () => (
	<Provider store={mockStore()}>
		<MemoryRouter
			initialEntries={[
				'/workspace/23/321321/contacts/individuals/known-individuals',
			]}
		>
			<Route path={Routes.CONTACTS_INDIVIDUALS_KNOWN_INDIVIDUALS}>
				<KnownIndividuals />
			</Route>
		</MemoryRouter>
	</Provider>
);

describe('Individuals Dashboard KnownIndividuals List', () => {
	afterEach(cleanup);

	it('renders', async () => {
		mockUseDataSource.useDataSources = jest.fn(() => mockSuccessState);

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
		mockUseDataSource.useDataSources = jest.fn(() => mockEmptyState);

		const {getByText} = render(<WrappedComponent />);

		expect(getByText('No Data Sources Connected')).toBeInTheDocument();
	});
});
