/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {render} from '@testing-library/react';
import React from 'react';
import {Provider} from 'react-redux';
import {MemoryRouter, Route} from 'react-router-dom';
import * as API from '~/shared/api';
import {useRequest} from '~/shared/hooks/useRequest';
import {CredentialTypes} from '~/shared/util/constants';
import {DataSource} from '~/shared/util/records';
import * as data from '~/test/data';
import {waitForLoadingToBeRemoved} from '~/test/helpers';
import mockStore from '~/test/mock-store';

import {View} from '../View';

jest.unmock('react-dom');

jest.mock('~/shared/hooks/useRequest', () => ({
	useRequest: jest.fn(),
}));

const DefaultComponent = ({dataSource}) => (
	<Provider store={mockStore()}>
		<MemoryRouter
			initialEntries={['/workspace/23/settings/data-source/24']}
		>
			<Route path="/workspace/:groupId/settings/data-source/:id">
				<View dataSource={dataSource} groupId="23" id="24" />
			</Route>
		</MemoryRouter>
	</Provider>
);

describe('View', () => {
	beforeEach(() => {
		useRequest.mockReturnValue({
			data: {
				channelsCount: 0,
				groupsCount: 0,
				individualsCount: 0,
				items: [],
				total: 0,
			},
			loading: false,
		});

		API.dataSource.fetchMappingsLite.mockReturnValue(
			Promise.resolve([
				{
					mapping: {name: 'MappingName', values: ['mappingValue']},
					name: 'Test0',
					suggestions: [
						{name: 'MappingName', values: ['mappingValue']},
					],
					values: ['value'],
				},
			])
		);
	});

	afterEach(() => {
		jest.clearAllMocks();
	});

	it('renders a CSV data-source page', async () => {
		const dataSource = data.getImmutableMock(
			DataSource,
			data.mockCSVDataSource
		);

		const {container} = render(
			<DefaultComponent dataSource={dataSource} />
		);

		await waitForLoadingToBeRemoved(container);

		expect(container).toMatchSnapshot();
	});

	it('renders a Liferay data-source page', async () => {
		const dataSource = data.getImmutableMock(
			DataSource,
			data.mockLiferayDataSource,
			'123',
			{
				credentials: {
					type: CredentialTypes.Token,
				},
			}
		);

		const {container} = render(
			<DefaultComponent dataSource={dataSource} />
		);

		await waitForLoadingToBeRemoved(container);

		expect(container).toMatchSnapshot();
	});

	it('renders a Salesforce data-source page', async () => {
		const dataSource = data.getImmutableMock(
			DataSource,
			data.mockSalesforceDataSource
		);

		const {container} = render(
			<DefaultComponent dataSource={dataSource} />
		);

		await waitForLoadingToBeRemoved(container);

		expect(container).toMatchSnapshot();
	});
});
