/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {render} from '@testing-library/react';
import {createMemoryHistory} from 'history';
import {Map, Set} from 'immutable';
import React from 'react';
import {Router} from 'react-router';
import * as API from '~/shared/api';
import * as useStatefulPaginationModule from '~/shared/hooks/useStatefulPagination';
import {RangeKeyTimeRanges} from '~/shared/util/constants';
import {NAME, createOrderIOMap} from '~/shared/util/pagination';
import {waitForLoadingToBeRemoved} from '~/test/helpers';

import IndividualsList from '../IndividualsList';

const defaultRangeSelectors = {
	rangeEnd: null,
	rangeKey: RangeKeyTimeRanges.Last30Days,
	rangeStart: null,
};

jest.unmock('react-dom');

jest.mock('react-router-dom', () => ({
	...jest.requireActual('react-router-dom'),
	useParams: () => ({
		channelId: '123',
		groupId: '23',
	}),
}));

describe('Individuals List', () => {
	beforeEach(() => {

		// @ts-ignore

		API.individuals.fetchFieldValues.mockReturnValue(
			Promise.resolve({items: ['United States', 'Canada']})
		);
	});

	it('renders', async () => {

		// @ts-ignore

		API.individuals.search.mockReturnValue(
			Promise.resolve({
				items: [
					{
						accountName: 'Liferay Inc.',
						activitiesCount: 8,
						activityStatus: 'ACTIVE',
						dateCreated: 1769697362927,
						firstActivityDate: 1769697128235,
						id: '47ff64395860b1d498241d907069f649b98c198a95b3ba5303b87094058590c1',
						lastActivityDate: 1769697160365,
						name: 'Test Test',
						profileType: 'KNOWN',
						properties: {
							country: 'United States',
							email: 'test@liferay.com',
						},
					},
					{
						accountName: 'Liferay',
						activitiesCount: 8,
						activityStatus: 'ACTIVE',
						dateCreated: 1769697362927,
						firstActivityDate: 1769697128235,
						id: '47ff64395860b1d498241d907069f649b98c198a95b3ba5303b87094058590c3',
						lastActivityDate: 1769697160365,
						name: 'John Doe',
						profileType: 'KNOWN',
						properties: {
							country: 'Canada',
							email: 'john.doe@liferay.com',
						},
					},
					{
						activitiesCount: 3,
						activityStatus: 'INACTIVE',
						dateCreated: 1769697362927,
						firstActivityDate: 1769697128235,
						id: '47ff64395860b1d498241d907069f649b98c198a95b3ba5303b87094058590c2',
						lastActivityDate: 1769697160365,
						name: 'AC-79742349',
						profileType: 'ANONYMOUS',
						properties: {},
					},
				],
				total: 3,
			})
		);

		const history = createMemoryHistory();

		const {getByText} = render(
			<Router history={history}>
				<IndividualsList rangeSelectors={defaultRangeSelectors} />
			</Router>
		);

		await waitForLoadingToBeRemoved(document.body);

		expect(getByText('Individual Profiles')).toBeInTheDocument();
		expect(getByText('Test Test')).toBeInTheDocument();
	});

	it('renders empty state when no individuals are synced', async () => {

		// @ts-ignore

		API.individuals.search.mockReturnValue(
			Promise.resolve({items: [], total: 0})
		);

		const history = createMemoryHistory();

		const {getByText} = render(
			<Router history={history}>
				<IndividualsList rangeSelectors={defaultRangeSelectors} />
			</Router>
		);

		await waitForLoadingToBeRemoved(document.body);

		expect(
			getByText('No Individuals Synced from Data Sources')
		).toBeInTheDocument();

		expect(
			getByText('Connect a data source with people data.')
		).toBeInTheDocument();

		expect(
			getByText('Access our documentation to learn more.')
		).toBeInTheDocument();
	});

	it('passes range params to the search API', async () => {

		// @ts-ignore

		API.individuals.search.mockReturnValue(
			Promise.resolve({items: [], total: 0})
		);

		const history = createMemoryHistory();

		render(
			<Router history={history}>
				<IndividualsList rangeSelectors={defaultRangeSelectors} />
			</Router>
		);

		await waitForLoadingToBeRemoved(document.body);

		// @ts-ignore

		expect(API.individuals.search).toHaveBeenCalledWith(
			expect.objectContaining({
				rangeEnd: null,
				rangeKey: 30,
				rangeStart: null,
			})
		);
	});

	it('passes activityStatus ACTIVE to the search API by default', async () => {
		(API.individuals.search as jest.Mock).mockReturnValue(
			Promise.resolve({items: [], total: 0})
		);

		const history = createMemoryHistory();

		render(
			<Router history={history}>
				<IndividualsList rangeSelectors={defaultRangeSelectors} />
			</Router>
		);

		await waitForLoadingToBeRemoved(document.body);

		expect(API.individuals.search as jest.Mock).toHaveBeenCalledWith(
			expect.objectContaining({
				activityStatus: 'ACTIVE',
			})
		);
	});

	it('renders the activity status column header', async () => {
		(API.individuals.search as jest.Mock).mockReturnValue(
			Promise.resolve({items: [], total: 0})
		);

		const history = createMemoryHistory();

		const {getByText} = render(
			<Router history={history}>
				<IndividualsList rangeSelectors={defaultRangeSelectors} />
			</Router>
		);

		await waitForLoadingToBeRemoved(document.body);

		expect(getByText('Activity Status')).toBeInTheDocument();
	});

	it('renders active and inactive activity status labels', async () => {
		(API.individuals.search as jest.Mock).mockReturnValue(
			Promise.resolve({
				items: [
					{
						activitiesCount: 1,
						activityStatus: 'ACTIVE',
						dateCreated: 1769697362927,
						firstActivityDate: 1769697128235,
						id: 'id-active',
						lastActivityDate: 1769697160365,
						name: 'Active User',
						profileType: 'KNOWN',
						properties: {},
					},
					{
						activitiesCount: 0,
						activityStatus: 'INACTIVE',
						dateCreated: 1769697362927,
						firstActivityDate: 1769697128235,
						id: 'id-inactive',
						lastActivityDate: 1769697160365,
						name: 'Inactive User',
						profileType: 'KNOWN',
						properties: {},
					},
				],
				total: 2,
			})
		);

		const history = createMemoryHistory();

		render(
			<Router history={history}>
				<IndividualsList rangeSelectors={defaultRangeSelectors} />
			</Router>
		);

		await waitForLoadingToBeRemoved(document.body);

		expect(
			document.querySelector('.label-success .label-item')
		).toHaveTextContent('Active');

		expect(
			document.querySelector('.label-secondary .label-item')
		).toHaveTextContent('Inactive');
	});

	it('passes undefined activityStatus when both active and inactive are selected', async () => {
		(API.individuals.search as jest.Mock).mockClear();
		(API.individuals.search as jest.Mock).mockReturnValue(
			Promise.resolve({items: [], total: 0})
		);

		const spy = jest
			.spyOn(useStatefulPaginationModule, 'useStatefulPagination')
			.mockReturnValue({
				delta: 20,
				filterBy: Map({
					activityStatus: Set(['ACTIVE', 'INACTIVE']),
				}) as any,
				onDeltaChange: jest.fn(),
				onFilterByChange: jest.fn(),
				onOrderIOMapChange: jest.fn(),
				onPageChange: jest.fn(),
				onQueryChange: jest.fn(),
				orderIOMap: createOrderIOMap(NAME),
				page: 1,
				query: '',
				resetPage: jest.fn(),
			});

		const history = createMemoryHistory();

		render(
			<Router history={history}>
				<IndividualsList rangeSelectors={defaultRangeSelectors} />
			</Router>
		);

		await waitForLoadingToBeRemoved(document.body);

		const callArgs = (API.individuals.search as jest.Mock).mock.calls[0][0];

		expect(callArgs.activityStatus).toBeUndefined();

		spy.mockRestore();
	});
});
