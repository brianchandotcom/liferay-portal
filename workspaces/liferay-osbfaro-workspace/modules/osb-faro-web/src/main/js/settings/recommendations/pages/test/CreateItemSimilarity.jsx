/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {ApolloProvider} from '@apollo/client';
import {render} from '@testing-library/react';
import React from 'react';
import {Provider} from 'react-redux';
import {StaticRouter} from 'react-router-dom';
import client from '~/shared/apollo/client';
import mockStore from '~/test/mock-store';

import CreateItemSimilarity from '../CreateItemSimilarity';

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
				<CreateItemSimilarity {...defaultProps} {...props} />
			</StaticRouter>
		</Provider>
	</ApolloProvider>
);

describe('Recommendations', () => {
	it('renders', async () => {
		const {container} = render(<DefaultComponent />);

		jest.runAllTimers();

		expect(container).toMatchSnapshot();
	});
});
