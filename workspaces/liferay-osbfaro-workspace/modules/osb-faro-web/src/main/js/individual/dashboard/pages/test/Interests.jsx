/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {MockedProvider} from '@apollo/client/testing';
import {cleanup, render} from '@testing-library/react';
import React from 'react';
import {Provider} from 'react-redux';
import {MemoryRouter, Route} from 'react-router-dom';
import * as useDataSources from '~/shared/context/dataSources';
import {Routes} from '~/shared/util/router';
import {mockEmptyState, mockSuccessState} from '~/test/__mocks__/mock-objects';
import {mockIndividualInterestsReq} from '~/test/graphql-data';
import {waitForLoadingToBeRemoved} from '~/test/helpers';
import mockStore from '~/test/mock-store';

import Interests from '../Interests';

jest.unmock('react-dom');

const mockUseDataSource = useDataSources;

const WrappedComponent = () => (
	<MockedProvider
		mocks={[
			mockIndividualInterestsReq((defaultVars) => ({
				...defaultVars,
				id: undefined,
				keywords: '',
				size: 2,
			})),
		]}
	>
		<Provider store={mockStore()}>
			<MemoryRouter
				initialEntries={[
					'/workspace/123/456/contacts/individuals/interests',
				]}
			>
				<Route path={Routes.CONTACTS_INDIVIDUALS_INTERESTS}>
					<Interests />
				</Route>
			</MemoryRouter>
		</Provider>
	</MockedProvider>
);

describe('Individuals Dashboard Individuals Interests', () => {
	afterEach(cleanup);

	it('renders', async () => {
		mockUseDataSource.useDataSources = jest.fn(() => mockSuccessState);

		const {getByText} = render(<WrappedComponent />);

		await waitForLoadingToBeRemoved(document.body);

		jest.runAllTimers();

		expect(getByText('Interest Topics')).toBeInTheDocument();
	});

	it('renders with data source empty state', () => {
		mockUseDataSource.useDataSources = jest.fn(() => mockEmptyState);

		const {getByText} = render(<WrappedComponent />);

		expect(
			getByText('No Sites Synced from Data Sources')
		).toBeInTheDocument();
	});
});
