/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {MockedProvider} from '@apollo/client/testing';
import {cleanup, render} from '@testing-library/react';
import React from 'react';
import {Provider} from 'react-redux';
import {MemoryRouter, Route} from 'react-router-dom';
import {useRequest} from '~/shared/hooks/useRequest';
import {DataSource} from '~/shared/util/records';
import * as data from '~/test/data';
import {waitForLoadingToBeRemoved} from '~/test/helpers';
import mockStore from '~/test/mock-store';

import SalesforceOverview from '../SalesforceOverview';

jest.unmock('react-dom');

jest.mock('~/shared/hooks/useRequest', () => ({
	useRequest: jest.fn(),
}));

jest.mock('~/shared/hooks/useCurrentUser', () => ({
	useCurrentUser: () => ({isAdmin: () => true}),
}));

const defaultProps = {
	dataSource: data.getImmutableMock(
		DataSource,
		data.mockSalesforceDataSource
	),
};

const WrappedComponent = (props) => (
	<Provider store={mockStore()}>
		<MemoryRouter
			initialEntries={['/workspace/23/settings/data-source/test']}
		>
			<Route path="/workspace/:groupId/settings/data-source/:id">
				<MockedProvider addTypename={false}>
					<SalesforceOverview {...defaultProps} {...props} />
				</MockedProvider>
			</Route>
		</MemoryRouter>
	</Provider>
);

describe('SalesforceOverview', () => {
	beforeEach(() => {
		useRequest.mockReturnValue({
			data: 10,
			loading: false,
		});
	});

	afterEach(cleanup);

	it('renders', async () => {
		const {container} = render(<WrappedComponent />);

		await waitForLoadingToBeRemoved(container);

		expect(container).toMatchSnapshot();
	});
});
