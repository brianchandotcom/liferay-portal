/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {cleanup, render, screen} from '@testing-library/react';
import {createMemoryHistory} from 'history';
import React from 'react';
import {Provider} from 'react-redux';
import {Router, useHistory} from 'react-router-dom';
import {ChannelContext} from '~/shared/context/channel';
import {useRequest} from '~/shared/hooks/useRequest';
import {waitForLoadingToBeRemoved} from '~/test/helpers';
import {mockChannelContext} from '~/test/mock-channel-context';
import mockStore from '~/test/mock-store';

import List from '../List';

jest.unmock('react-dom');

type FakeFilter = {
	id: string;
	preloadedData?: {
		exclude: boolean;
		selectedItems: Array<{label?: string; value: string}>;
	};
};

let lastFilters: FakeFilter[] | undefined;

jest.mock('@liferay/frontend-data-set-web', () => ({
	...jest.requireActual('@liferay/frontend-data-set-web'),
	FrontendDataSet: ({filters, id}: {filters: FakeFilter[]; id: string}) => {
		lastFilters = filters;

		return <div data-testid="fds-component" id={id} />;
	},
}));

jest.mock('~/shared/components/dropdown-range-key/DropdownRangeKey', () => ({
	DropdownRangeKey: () => null,
}));

jest.mock('~/shared/hooks/useRequest', () => ({
	useRequest: jest.fn(),
}));

jest.mock('~/shared/util/breadcrumbs', () => ({
	getHome: jest.fn(({label}: {label?: string} = {}) => ({
		active: false,
		label: label || 'Home',
	})),
}));

jest.mock('react-router-dom', () => ({
	...jest.requireActual('react-router-dom'),
	useHistory: jest.fn(),
	useParams: () => ({
		channelId: '123',
		groupId: '23',
	}),
}));

const mockedUseHistory = useHistory as jest.Mock;
const mockedUseRequest = useRequest as jest.Mock;

const mockHistoryPush = jest.fn();

const buildHistory = (path = '/workspace/23/123/accounts') => {
	const history = createMemoryHistory({initialEntries: [path]});

	history.push = mockHistoryPush;

	return history;
};

const store = mockStore();

// `useRequest` is consumed by both `List` (fetchChannels, expects an object
// with `total`) and `TotalAccounts` (account metrics, expects an array of
// `IAccountMetric`). Differentiate by `variables.channelIds`, which is only
// present in the fetchChannels call.

const accountMetricsMock = [
	{
		metricType: 'totalCount',
		trend: {percentage: 0, trendClassification: 'NEUTRAL'},
		value: 0,
	},
	{
		metricType: 'newCount',
		trend: {percentage: 0, trendClassification: 'NEUTRAL'},
		value: 0,
	},
	{
		metricType: 'activeCount',
		trend: {percentage: 0, trendClassification: 'NEUTRAL'},
		value: 0,
	},
];

const useRequestImpl =
	({total = 1}: {total?: number} = {}) =>
	({variables}: {variables: {[key: string]: any}}) =>
		variables?.channelIds !== undefined
			? {data: {total}}
			: {data: accountMetricsMock};

const renderList = (
	{queryString = ''}: {queryString?: string} = {},
	history = buildHistory(`/workspace/23/123/accounts${queryString}`)
) =>
	render(
		<Provider store={store}>
			<ChannelContext.Provider value={mockChannelContext() as any}>
				<Router history={history}>
					<List channelId="123" groupId="23" />
				</Router>
			</ChannelContext.Provider>
		</Provider>
	);

describe('List', () => {
	beforeEach(() => {
		jest.clearAllMocks();
		lastFilters = undefined;

		mockedUseHistory.mockReturnValue({push: mockHistoryPush});
		mockedUseRequest.mockImplementation(useRequestImpl());
	});

	afterEach(cleanup);

	describe('rendering', () => {
		it('renders without crashing', () => {
			const {container} = renderList();

			expect(container).toBeInTheDocument();
		});

		it('renders the page title "Accounts"', () => {
			renderList();

			expect(screen.getByText('Accounts')).toBeInTheDocument();
		});

		it('renders the empty state when there are no data sources connected', () => {
			mockedUseRequest.mockImplementation(useRequestImpl({total: 0}));

			renderList();
		});

		it('renders the FrontendDataSet component', () => {
			renderList();

			expect(screen.getByTestId('fds-component')).toBeInTheDocument();
		});

		it('renders the FrontendDataSet with id "accounts-list-dataset"', () => {
			renderList();

			expect(screen.getByTestId('fds-component')).toHaveAttribute('id');
		});

		it('passes activityStatusFilter "ACTIVE" to AccountsDataSet by default', () => {
			renderList();

			const activityStatusFilter = lastFilters?.find(
				(f) => f.id === 'activityStatus'
			);

			expect(activityStatusFilter?.preloadedData).toEqual({
				exclude: false,
				selectedItems: [{label: 'Active', value: 'ACTIVE'}],
			});
		});

		it('matches the snapshot', async () => {
			const {container} = renderList();

			await waitForLoadingToBeRemoved();

			expect(container).toMatchSnapshot();
		});
	});
});
