/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {ApolloProvider} from '@apollo/client';
import {MockedProvider} from '@apollo/client/testing';
import {act, render} from '@testing-library/react';
import React from 'react';
import {Provider} from 'react-redux';
import {MemoryRouter} from 'react-router';
import {Route} from 'react-router-dom';
import client from '~/shared/apollo/client';
import {getISODate} from '~/shared/util/date';
import {Routes} from '~/shared/util/router';
import * as data from '~/test/data';
import {mockEventAttributeDefinitionWithRecentValuesReq} from '~/test/graphql-data';
import {waitForLoading} from '~/test/helpers';
import mockStore from '~/test/mock-store';

import AttributeView from '../AttributeView';

jest.unmock('react-dom');

const RenderWithRouter = ({children, recentValue}) => (
	<MemoryRouter
		initialEntries={[
			'/workspace/23/settings/definitions/event-attributes/0',
		]}
	>
		<Route path={Routes.SETTINGS_DEFINITIONS_EVENT_ATTRIBUTES_VIEW}>
			<ApolloProvider client={client}>
				<Provider store={mockStore()}>
					<MockedProvider
						mocks={[
							mockEventAttributeDefinitionWithRecentValuesReq(
								data.mockEventAttributeDefinition(0, {
									recentValues: [
										{
											__typename: 'EventAttributeValue',
											lastSeenDate: getISODate(
												data.getTimestamp()
											),
											value: 'RecentValue',
											...recentValue,
										},
									],
								}),
								{id: '0'}
							),
						]}
					>
						{children}
					</MockedProvider>
				</Provider>
			</ApolloProvider>
		</Route>
	</MemoryRouter>
);

describe('AttributeView', () => {
	it('renders', async () => {
		const {container} = render(
			<RenderWithRouter>
				<AttributeView attributeId="0" groupId="23" />
			</RenderWithRouter>
		);

		await waitForLoading(container);

		jest.runAllTimers();

		expect(container).toMatchSnapshot();
	});

	it('renders with a table', async () => {
		const {getByText} = render(
			<RenderWithRouter>
				<AttributeView attributeId="0" groupId="23" />
			</RenderWithRouter>
		);

		await act(async () => {
			jest.runOnlyPendingTimers();
		});

		expect(getByText('Sample Raw Data')).toBeTruthy();
	});

	it('renders table with empty data if there is no column accessor value', async () => {
		const {getByText} = render(
			<RenderWithRouter recentValue={{value: ''}}>
				<AttributeView attributeId="0" groupId="23" />
			</RenderWithRouter>
		);

		await act(async () => {
			jest.runOnlyPendingTimers();
		});

		expect(getByText('-')).toBeTruthy();
	});
});
