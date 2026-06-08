/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {InMemoryCache} from '@apollo/client';
import {MockedProvider} from '@apollo/client/testing';
import {render} from '@testing-library/react';
import {range} from 'lodash';
import React from 'react';
import {Provider} from 'react-redux';
import {MemoryRouter, Route} from 'react-router-dom';
import {Routes} from '~/shared/util/router';
import * as data from '~/test/data';
import {mockJobBag} from '~/test/graphql-data';
import {waitForLoadingToBeRemoved} from '~/test/helpers';
import mockStore from '~/test/mock-store';

import RecommendationListQuery from '../../queries/RecommendationListQuery';
import Recommendations from '../Recommendations';

jest.unmock('react-dom');

const mockRecommendationListReq = () => ({
	request: {
		query: RecommendationListQuery,
		variables: {
			keywords: '',
			size: 10,
			sort: {column: 'name', type: 'DESC'},
			start: 0,
		},
	},
	result: {
		data: mockJobBag(range(10).map((i) => data.mockRecommendationJob(i))),
	},
});

const defaultProps = {
	router: {params: {groupId: '23'}, query: {delta: '10', page: '1'}},
};

const DefaultComponent = (props) => (
	<Provider store={mockStore()}>
		<MemoryRouter
			initialEntries={[
				'/workspace/23/settings/recommendations?delta=10&page=1&sortOrder=DESC&field=name',
			]}
		>
			<MockedProvider
				cache={
					new InMemoryCache({
						addTypename: false,
						freezeResults: false,
					})
				}
				mocks={[mockRecommendationListReq()]}
			>
				<Route path={Routes.SETTINGS_RECOMMENDATIONS}>
					<Recommendations {...defaultProps} {...props} />
				</Route>
			</MockedProvider>
		</MemoryRouter>
	</Provider>
);

describe('Recommendations', () => {
	it('renders', async () => {
		const {container} = render(<DefaultComponent />);

		await waitForLoadingToBeRemoved(container);

		expect(container).toMatchSnapshot();
	});
});
