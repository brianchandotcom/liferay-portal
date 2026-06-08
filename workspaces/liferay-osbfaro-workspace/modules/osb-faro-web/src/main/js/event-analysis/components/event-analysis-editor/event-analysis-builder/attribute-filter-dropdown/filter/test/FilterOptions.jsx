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
import mockStore from '~/test/mock-store';

import {withAttributesProvider} from '../../../../context/attributes';
import FilterOptions from '../index';

jest.unmock('react-dom');

const WrappedFilterOptions = withAttributesProvider(FilterOptions);

const DefaultComponent = (props) => (
	<Provider store={mockStore()}>
		<MemoryRouter
			initialEntries={['/workspace/123/456/event-analysis/789']}
		>
			<Route path="/workspace/:groupId/:channelId/event-analysis/:id">
				<MockedProvider
					cache={
						new InMemoryCache({
							addTypename: false,
							freezeResults: false,
						})
					}
				>
					<WrappedFilterOptions
						attribute={{
							dataType: 'STRING',
							displayName: 'Filed Ticket',
							id: '4',
							name: 'filedTicket',
						}}
						onActiveChange={jest.fn()}
						onAttributeChange={jest.fn()}
						onEditClick={jest.fn()}
						{...props}
					/>
				</MockedProvider>
			</Route>
		</MemoryRouter>
	</Provider>
);

describe('FilterOptions', () => {
	it('renders', () => {
		const {container} = render(<DefaultComponent />);

		expect(container).toMatchSnapshot();
	});
});
