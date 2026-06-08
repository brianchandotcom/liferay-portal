/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {MockedProvider} from '@apollo/client/testing';
import {render} from '@testing-library/react';
import React from 'react';
import {Provider} from 'react-redux';
import {MemoryRouter, Route} from 'react-router-dom';
import {AttributeTypes} from '~/event-analysis/utils/types';
import * as data from '~/test/data';
import {mockEventAttributeDefinitionsReq} from '~/test/graphql-data';
import {waitForLoadingToBeRemoved} from '~/test/helpers';
import mockStore from '~/test/mock-store';

import AttributeList from '../AttributeList';

jest.unmock('react-dom');

const DefaultComponent = ({
	groupId = '23',
	mocks = [
		mockEventAttributeDefinitionsReq(
			[
				data.mockEventAttributeDefinition(0, {
					__typename: 'EventAttributeDefinition',
					dataType: 'STRING',
				}),
			],
			{
				keyword: '',
				page: 0,
				size: 2,
				sort: {column: 'name', type: 'ASC'},
				type: AttributeTypes.Local,
			}
		),
	],
}) => (
	<Provider store={mockStore()}>
		<MemoryRouter
			initialEntries={[
				`/workspace/${groupId}/456/settings/definitions/event-attributes/custom`,
			]}
		>
			<Route path="/workspace/:groupId/:channelId/settings/definitions/event-attributes/custom">
				<MockedProvider addTypename={false} mocks={mocks}>
					<AttributeList />
				</MockedProvider>
			</Route>
		</MemoryRouter>
	</Provider>
);

describe('AttributeList', () => {
	it('renders', async () => {
		const {container} = render(<DefaultComponent />);

		await waitForLoadingToBeRemoved(container);

		expect(container).toMatchSnapshot();
	});

	it('renders Data Typecast column with a label', async () => {
		const {getByText} = render(<DefaultComponent />);

		await waitForLoadingToBeRemoved();

		expect(getByText('STRING').parentElement).toHaveClass('label-info');
	});
});
