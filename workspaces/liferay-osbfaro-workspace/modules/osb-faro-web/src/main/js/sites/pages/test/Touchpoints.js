/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {InMemoryCache} from '@apollo/client';
import {MockedProvider} from '@apollo/client/testing';
import {render} from '@testing-library/react';
import React from 'react';
import {Provider} from 'react-redux';
import {MemoryRouter} from 'react-router-dom';
import {mockPreferenceReq, mockTimeRangeReq} from '~/test/graphql-data';
import {waitForLoadingToBeRemoved} from '~/test/helpers';
import mockStore from '~/test/mock-store';

import Touchpoints from '../Touchpoints';

jest.unmock('react-dom');

const DefaultComponent = () => (
	<Provider store={mockStore()}>
		<MemoryRouter>
			<MockedProvider
				cache={
					new InMemoryCache({
						addTypename: false,
						freezeResults: false,
					})
				}
				mocks={[mockTimeRangeReq(), mockPreferenceReq()]}
			>
				<Touchpoints router={{params: {}, query: {}}} />
			</MockedProvider>
		</MemoryRouter>
	</Provider>
);

describe('Sites Dashboard Touchpoints Page', () => {
	it('render', async () => {
		const {container} = render(<DefaultComponent />);

		await waitForLoadingToBeRemoved(container);

		expect(container).toMatchSnapshot();
	});
});
