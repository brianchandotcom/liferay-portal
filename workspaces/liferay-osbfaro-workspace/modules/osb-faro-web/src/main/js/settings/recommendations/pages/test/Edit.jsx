/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {ApolloProvider} from '@apollo/client';
import {MockedProvider} from '@apollo/client/testing';
import {render} from '@testing-library/react';
import React from 'react';
import {Provider} from 'react-redux';
import {StaticRouter} from 'react-router-dom';
import client from '~/shared/apollo/client';
import * as data from '~/test/data';
import {mockRecommendationReq} from '~/test/graphql-data';
import {waitForLoading} from '~/test/helpers';
import mockStore from '~/test/mock-store';

import Edit from '../Edit';

jest.unmock('react-dom');

jest.mock('react-router-dom', () => ({
	...jest.requireActual('react-router-dom'),
	useParams: () => ({
		groupId: '23',
	}),
}));

const defaultProps = {
	router: {params: {groupId: '23'}, query: {delta: '10', page: '1'}},
};

const DefaultComponent = (props) => (
	<ApolloProvider client={client}>
		<Provider store={mockStore()}>
			<StaticRouter>
				<MockedProvider
					mocks={[
						mockRecommendationReq(
							data.mockRecommendationJob('321')
						),
					]}
				>
					<Edit
						{...defaultProps}
						{...props}
						router={{params: {groupId: '123', jobId: '321'}}}
					/>
				</MockedProvider>
			</StaticRouter>
		</Provider>
	</ApolloProvider>
);

describe('Edit', () => {
	it('renders', async () => {
		const {container} = render(<DefaultComponent />);

		await waitForLoading(container);

		jest.runAllTimers();

		expect(container).toMatchSnapshot();
	});
});
