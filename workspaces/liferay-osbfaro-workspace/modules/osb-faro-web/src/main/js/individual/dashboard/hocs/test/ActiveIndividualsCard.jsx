/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {ApolloProvider} from '@apollo/client';
import {cleanup, render} from '@testing-library/react';
import React from 'react';
import client from '~/shared/apollo/client';

import ActiveIndividualsCard from '../ActiveIndividualsCard';

jest.unmock('react-dom');

jest.mock('react-router-dom', () => ({
	...jest.requireActual('react-router-dom'),
	useLocation: () => ({
		search: '?rangeKey=30',
	}),
	useParams: () => ({
		channelId: '456',
	}),
}));

describe('ActiveIndividualsCard', () => {
	afterEach(cleanup);

	it('renders', () => {
		const {container} = render(
			<ApolloProvider client={client}>
				<ActiveIndividualsCard />
			</ApolloProvider>
		);
		expect(container).toMatchSnapshot();
	});
});
