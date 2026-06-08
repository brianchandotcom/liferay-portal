/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {InMemoryCache} from '@apollo/client';
import {MockedProvider} from '@apollo/client/testing';
import {render} from '@testing-library/react';
import React from 'react';
import {Provider} from 'react-redux';
import {MemoryRouter, Route} from 'react-router-dom';
import {mockPreferenceReq} from '~/test/graphql-data';
import mockStore from '~/test/mock-store';

import DateFilter from '../DateFilter';

jest.unmock('react-dom');

const WrappedComponent = (props) => (
	<Provider store={mockStore()}>
		<MemoryRouter initialEntries={['/workspace/23/event-analysis']}>
			<Route path="/workspace/:groupId/event-analysis">
				<MockedProvider
					cache={
						new InMemoryCache({
							addTypename: false,
							freezeResults: false,
						})
					}
					mocks={[mockPreferenceReq()]}
				>
					<DateFilter onSubmit={jest.fn()} {...props} />
				</MockedProvider>
			</Route>
		</MemoryRouter>
	</Provider>
);

describe('DateFilter', () => {
	it('renders', () => {
		const {container} = render(<WrappedComponent />);

		expect(container).toMatchSnapshot();
	});
});
