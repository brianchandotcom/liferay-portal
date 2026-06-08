/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {useQuery} from '@apollo/client';
import {render} from '@testing-library/react';
import React from 'react';
import {Provider} from 'react-redux';
import {BrowserRouter} from 'react-router-dom';
import {useDataSources} from '~/shared/context/dataSources';
import {useCurrentUser} from '~/shared/hooks/useCurrentUser';
import {useRequest} from '~/shared/hooks/useRequest';
import mockStore from '~/test/mock-store';

import IndividualsOverviewCDP from '../IndividualsOverviewCDP';

jest.unmock('react-dom');

jest.mock('@apollo/client', () => ({
	...jest.requireActual('@apollo/client'),
	useQuery: jest.fn(),
}));

jest.mock('~/shared/hooks/useRequest', () => ({
	useRequest: jest.fn(),
}));

jest.mock('~/shared/hooks/useCurrentUser', () => ({
	useCurrentUser: jest.fn(),
}));

jest.mock('~/shared/context/dataSources', () => ({
	useDataSources: jest.fn(),
}));

jest.mock('react-router-dom', () => ({
	...jest.requireActual('react-router-dom'),
	useParams: () => ({
		channelId: '123',
		groupId: '456',
	}),
}));

jest.mock('~/shared/components/dropdown-range-key/DropdownRangeKey', () => ({
	DropdownRangeKey: () => null,
}));

jest.mock('../IndividualsList', () => ({
	__esModule: true,
	default: () => null,
}));

const mockedIndividualMetrics = {
	data: {
		individualMetric: {
			__typename: 'IndividualMetric',

			anonymousIndividualsMetric: {
				__typename: 'Metric',
				trend: {
					__typename: 'Trend',
					percentage: 11.123,
					trendClassification: 'POSITIVE',
				},
				value: 5123,
			},
			knownIndividualsMetric: {
				__typename: 'Metric',
				trend: {
					__typename: 'Trend',
					percentage: 28.12,
					trendClassification: 'NEGATIVE',
				},
				value: 1103,
			},
			totalIndividualsMetric: {
				__typename: 'Metric',
				trend: {
					__typename: 'Trend',
					percentage: 30.23,
					trendClassification: 'POSITIVE',
				},
				value: 2120,
			},
		},
	},
};

const renderIndividualsOverviewCDP = () =>
	render(
		<Provider store={mockStore()}>
			<BrowserRouter>
				<IndividualsOverviewCDP />
			</BrowserRouter>
		</Provider>
	);

describe('IndividualsOverviewCDP', () => {
	beforeEach(() => {
		(useRequest as jest.Mock).mockReturnValue({
			data: {total: 1},
			loading: false,
		});
		(useCurrentUser as jest.Mock).mockReturnValue({isAdmin: () => true});
		(useDataSources as jest.Mock).mockReturnValue({empty: false});
	});

	afterEach(() => {
		jest.resetAllMocks();
	});

	it('renders Individuals Metrics Cards', () => {
		(useQuery as jest.Mock).mockReturnValue({
			data: mockedIndividualMetrics.data,
			loading: false,
		});

		const {getByText} = renderIndividualsOverviewCDP();

		expect(getByText('Total Individuals')).toBeInTheDocument();
		expect(getByText('Known Individuals')).toBeInTheDocument();
		expect(getByText('Anonymous Individuals')).toBeInTheDocument();
	});

	it('renders a centered loader inside each metric card while metrics are loading', () => {
		(useQuery as jest.Mock).mockReturnValue({
			data: undefined,
			loading: true,
		});

		const {container, queryByText} = renderIndividualsOverviewCDP();

		expect(queryByText(/Individuals$/)).not.toBeInTheDocument();

		expect(container.querySelectorAll('.loading-root')).toHaveLength(3);
	});
});
