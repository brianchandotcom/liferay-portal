/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {cleanup, render, waitFor} from '@testing-library/react';
import {times} from 'lodash';
import React from 'react';
import {Provider} from 'react-redux';
import {MemoryRouter, Route} from 'react-router-dom';
import {ChannelContext} from '~/shared/context/channel';
import * as useDataSources from '~/shared/context/dataSources';
import {createOrderIOMap} from '~/shared/util/pagination';
import {User} from '~/shared/util/records';
import {Routes} from '~/shared/util/router';
import {mockEmptyState, mockSuccessState} from '~/test/__mocks__/mock-objects';
import * as data from '~/test/data';
import {waitForLoadingToBeRemoved} from '~/test/helpers';
import {mockChannelContext} from '~/test/mock-channel-context';
import mockStore from '~/test/mock-store';

import BaseListPage from '../BaseListPage';

const TOTAL = 5;

const ACCOUNTS = times(TOTAL, (i) => data.mockAccount(i));

const USER = new User(data.mockUser());

const defaultProps = (empty = false) => ({
	channelId: '123123',
	columns: [
		{
			accessor: 'name',
			label: 'Name',
		},
		{
			accessor: 'id',
			label: 'Id',
		},
	],
	currentUser: USER,
	dataSourceFn: jest.fn(() =>
		Promise.resolve(
			empty ? {items: [], total: 0} : {items: ACCOUNTS, total: TOTAL}
		)
	),
	entityLabel: 'Accounts',
	groupId: '23',
	noResultsConfig: {
		description: 'There is no account data from existing data sources.',
		title: 'No account data available.',
	},
	orderIOMap: createOrderIOMap('name'),
});

const WrappedComponent = ({alerts, empty, query}) => (
	<Provider store={mockStore()}>
		<MemoryRouter
			initialEntries={['/workspace/23/123123/contacts/segments']}
		>
			<Route path={Routes.CONTACTS_LIST_SEGMENT}>
				<ChannelContext.Provider value={mockChannelContext()}>
					<BaseListPage
						alerts={alerts}
						{...defaultProps(empty)}
						query={query}
					/>
				</ChannelContext.Provider>
			</Route>
		</MemoryRouter>
	</Provider>
);

jest.unmock('react-dom');

const mockUseDataSource = useDataSources;

describe('BaseListPage', () => {
	afterEach(cleanup);
	mockUseDataSource.useDataSources = jest.fn(() => mockSuccessState);

	it('renders', async () => {
		const {getByText} = render(<WrappedComponent />);

		await waitForLoadingToBeRemoved(document.body);

		expect(getByText('Accounts')).toBeInTheDocument();
	});

	it('loads accounts', async () => {
		const {container, getByText} = render(<WrappedComponent />);

		await waitForLoadingToBeRemoved(container);

		expect(container.querySelectorAll('.table-head-title')).toHaveLength(2);
		expect(container.querySelector('tbody').children).toHaveLength(5);
		expect(getByText('account0')).toBeInTheDocument();
		expect(getByText('Showing 1 to 2 of 5 entries.')).toBeInTheDocument();
	});

	it('renders "No Account" empty state with no query', async () => {
		const {container, getByText} = render(<WrappedComponent empty />);

		await waitForLoadingToBeRemoved(container);

		expect(
			getByText('There is no account data from existing data sources.')
		).toBeInTheDocument();
		expect(getByText('No account data available.')).toBeInTheDocument();
	});

	it('renders different message in the empty state with query', async () => {
		const {container, getByText} = render(
			<WrappedComponent empty query="test" />
		);

		await waitForLoadingToBeRemoved(container);

		expect(container.querySelector('.tbar-nav').children).toHaveLength(2);

		expect(
			container.querySelectorAll('.tbar-section.text-truncate')[0]
		).toHaveTextContent('0 Results for "test"');
		expect(
			container.querySelectorAll('.tbar-section.text-truncate')[1]
		).toHaveTextContent('Clear');

		expect(getByText('There are no results found.')).toBeInTheDocument();
	});

	it('renders with alerts', async () => {
		const {getByRole, getByText} = render(
			<WrappedComponent alerts={[{message: 'foo alert'}]} />
		);

		await waitFor(() => getByRole('alert'));

		expect(getByText('foo alert')).toBeInTheDocument();
	});
});

describe('BaseListPage with no Data Source', () => {
	it('renders EmptyState', () => {
		mockUseDataSource.useDataSources = jest.fn(() => mockEmptyState);

		const {getByText} = render(<WrappedComponent />);

		expect(getByText('No Data Sources Connected')).toBeInTheDocument();
		expect(
			getByText('Connect a data source to get started.')
		).toBeInTheDocument();
		expect(
			getByText('Access our documentation to learn more.')
		).toBeInTheDocument();
	});
});
