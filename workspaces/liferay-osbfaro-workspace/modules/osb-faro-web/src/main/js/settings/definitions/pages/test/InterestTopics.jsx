/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {cleanup, render} from '@testing-library/react';
import React from 'react';
import {Provider} from 'react-redux';
import {MemoryRouter, Route} from 'react-router-dom';
import * as API from '~/shared/api';
import {Routes} from '~/shared/util/router';
import * as data from '~/test/data';
import {waitForLoadingToBeRemoved} from '~/test/helpers';
import mockStore, {mockStoreData, toRD} from '~/test/mock-store';

import InterestTopics from '../InterestTopics';

jest.unmock('react-dom');

const defaultProps = {
	groupId: '23',
};

const Wrapper = ({children, queryString = '', store = mockStore()}) => (
	<Provider store={store}>
		<MemoryRouter
			initialEntries={[
				`/workspace/23/settings/definitions/interest-topics${queryString}`,
			]}
		>
			<Route path={Routes.SETTINGS_DEFINITIONS_INTEREST_TOPICS}>
				{children}
			</Route>
		</MemoryRouter>
	</Provider>
);

describe('InterestTopics', () => {
	afterEach(cleanup);

	it('renders', async () => {
		API.blockedKeywords.search.mockReturnValue(
			Promise.resolve({items: [data.mockBlockedKeyword(1)], total: 1})
		);

		const {container} = render(
			<Wrapper>
				<InterestTopics {...defaultProps} />
			</Wrapper>
		);

		await waitForLoadingToBeRemoved(container);

		expect(container).toMatchSnapshot();
	});

	it('renders page not found if an invalid page is provided', async () => {
		API.blockedKeywords.search.mockReturnValue(
			Promise.resolve({items: [], total: 0})
		);

		const {container} = render(
			<Wrapper queryString="?page=33">
				<InterestTopics {...defaultProps} />
			</Wrapper>
		);

		await waitForLoadingToBeRemoved(container);

		expect(container.querySelector('.no-results-root')).toMatchSnapshot();
	});

	it('renders without an "add keyword" button if the user role is member', async () => {
		API.blockedKeywords.search.mockReturnValue(
			Promise.resolve({items: [], total: 0})
		);

		const memberStore = mockStore(
			mockStoreData.setIn(['currentUser'], toRD('24'))
		);

		const {container, queryByText} = render(
			<Wrapper store={memberStore}>
				<InterestTopics {...defaultProps} />
			</Wrapper>
		);

		await waitForLoadingToBeRemoved(container);

		expect(queryByText('Add Keyword')).toBeNull();
	});

	it('renders with an empty state', async () => {
		API.blockedKeywords.search.mockReturnValue(
			Promise.resolve({items: [], total: 0})
		);

		const {container} = render(
			<Wrapper queryString="?query=foo">
				<InterestTopics {...defaultProps} />
			</Wrapper>
		);

		await waitForLoadingToBeRemoved(container);

		expect(container.querySelector('.no-results-root')).toMatchSnapshot();
	});

	it('renders with a message to add keywords if there are none', async () => {
		API.blockedKeywords.search.mockReturnValue(
			Promise.resolve({items: [], total: 0})
		);

		const {container} = render(
			<Wrapper>
				<InterestTopics {...defaultProps} />
			</Wrapper>
		);

		await waitForLoadingToBeRemoved(container);

		expect(container.querySelector('.no-results-root')).toMatchSnapshot();
	});

	it('renders with a member-specific message to add keywords if there are none', async () => {
		API.blockedKeywords.search.mockReturnValue(
			Promise.resolve({items: [], total: 0})
		);

		const memberStore = mockStore(
			mockStoreData.setIn(['currentUser'], toRD('24'))
		);

		const {container} = render(
			<Wrapper store={memberStore}>
				<InterestTopics {...defaultProps} />
			</Wrapper>
		);

		await waitForLoadingToBeRemoved(container);

		expect(container.querySelector('.no-results-root')).toMatchSnapshot();
	});
});
