/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {MockedProvider} from '@apollo/client/testing';
import {render} from '@testing-library/react';
import React from 'react';
import {Provider} from 'react-redux';
import {MemoryRouter, Route} from 'react-router-dom';
import {DataSource} from '~/shared/util/records';
import * as data from '~/test/data';
import {waitForLoadingToBeRemoved} from '~/test/helpers';
import mockStore from '~/test/mock-store';

import {ClearData} from '../ClearData';

jest.unmock('react-dom');

const defaultProps = {
	addAlert: jest.fn(),
	dataSource: new DataSource(data.mockLiferayDataSource()),
	entitiesCount: 10,
	groupId: '23',
	history: {push: jest.fn()},
	id: '26',
};

const WrappedComponent = (props) => (
	<Provider store={mockStore()}>
		<MemoryRouter
			initialEntries={[
				'/workspace/23/settings/data-source/26/clear-data',
			]}
		>
			<Route path="/workspace/:groupId/settings/data-source/:id/clear-data">
				<MockedProvider>
					<ClearData {...defaultProps} {...props} />
				</MockedProvider>
			</Route>
		</MemoryRouter>
	</Provider>
);

describe('ClearData', () => {
	it('renders', async () => {
		const {container} = render(<WrappedComponent />);

		await waitForLoadingToBeRemoved(container);

		expect(container).toMatchSnapshot();
	});
});
