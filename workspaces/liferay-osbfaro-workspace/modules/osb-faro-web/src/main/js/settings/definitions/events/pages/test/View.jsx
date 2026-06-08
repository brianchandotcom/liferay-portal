/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {ApolloProvider} from '@apollo/client';
import {MockedProvider} from '@apollo/client/testing';
import {render} from '@testing-library/react';
import React from 'react';
import {Provider} from 'react-redux';
import {StaticRouter} from 'react-router';
import client from '~/shared/apollo/client';
import * as data from '~/test/data';
import {mockEventDefinitionReq} from '~/test/graphql-data';
import {waitForLoading} from '~/test/helpers';
import mockStore from '~/test/mock-store';

import View from '../View';

jest.unmock('react-dom');

jest.mock('react-router-dom', () => ({
	...jest.requireActual('react-router-dom'),
	useParams: () => ({
		eventId: '0',
		groupId: '23',
	}),
}));

describe('Event View page', () => {
	it('renders', async () => {
		const {container} = render(
			<ApolloProvider client={client}>
				<Provider store={mockStore()}>
					<MockedProvider
						mocks={[
							mockEventDefinitionReq(
								data.mockEventDefinition(0, {
									eventAttributeDefinitions: [
										{
											__typename:
												'EventAttributeDefinition',
											...data.mockEventAttributeDefinition(
												1
											),
										},
									],
								}),
								{id: '0'}
							),
						]}
					>
						<StaticRouter>
							<View eventId="0" groupId="23" />
						</StaticRouter>
					</MockedProvider>
				</Provider>
			</ApolloProvider>
		);

		await waitForLoading(container);

		jest.runAllTimers();

		expect(container).toMatchSnapshot();
	});
});
