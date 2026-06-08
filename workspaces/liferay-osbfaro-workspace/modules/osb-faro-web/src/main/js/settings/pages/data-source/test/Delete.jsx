/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {MockedProvider} from '@apollo/client/testing';
import {cleanup, render} from '@testing-library/react';
import {fromJS} from 'immutable';
import React from 'react';
import {Provider} from 'react-redux';
import {MemoryRouter, Route} from 'react-router-dom';
import * as API from '~/shared/api';
import {DataSource} from '~/shared/util/records';
import * as data from '~/test/data';
import mockStore from '~/test/mock-store';

import {Delete as DataSourceDelete} from '../Delete';

jest.unmock('react-dom');

const defaultProps = {
	addAlert: jest.fn(),
	dataSource: new DataSource(data.mockLiferayDataSource()),
	deleteDataSource: jest.fn(),
	entitiesCount: {
		2: 10,
		4: 20,
	},
	groupId: '23',
	history: {push: jest.fn()},
	id: '26',
};

const Wrapper = ({children}) => (
	<Provider
		store={mockStore(
			fromJS({
				projects: {
					23: {
						data: {
							timeZone: {
								timeZoneId: 'UTC',
							},
						},
					},
				},
			})
		)}
	>
		<MemoryRouter
			initialEntries={['/workspace/23/settings/data-source/26/delete']}
		>
			<Route path="/workspace/:groupId/settings/data-source/:id/delete">
				<MockedProvider addTypename={false}>{children}</MockedProvider>
			</Route>
		</MemoryRouter>
	</Provider>
);

describe('DataSourceDelete', () => {
	beforeEach(() => {
		API.dataSource.fetchDeletePreview.mockReturnValue(
			Promise.resolve({
				2: 10,
				4: 20,
			})
		);
	});

	afterEach(cleanup);

	it('renders', async () => {
		const {container} = render(
			<Wrapper>
				<DataSourceDelete {...defaultProps} />
			</Wrapper>
		);

		// We use DataSourceDelete directly which bypasses the withRequest HOC that sets loading state.
		// So we don't need waitForLoadingToBeRemoved if we pass entitiesCount directly.

		expect(container).toMatchSnapshot();
	});
});
