import FilterOptions from '../index';
import mockStore from 'test/mock-store';
import React from 'react';
import {InMemoryCache} from '@apollo/client';
import {MemoryRouter, Route} from 'react-router-dom';
import {MockedProvider} from '@apollo/client/testing';
import {Provider} from 'react-redux';
import {render} from '@testing-library/react';
import {withAttributesProvider} from '../../../../context/attributes';

jest.unmock('react-dom');

const WrappedFilterOptions = withAttributesProvider(FilterOptions);

const DefaultComponent = props => (
	<Provider store={mockStore()}>
		<MemoryRouter
			initialEntries={['/workspace/123/456/event-analysis/789']}
		>
			<Route path='/workspace/:groupId/:channelId/event-analysis/:id'>
				<MockedProvider
					cache={
						new InMemoryCache({
							addTypename: false,
							freezeResults: false
						})
					}
				>
					<WrappedFilterOptions
						attribute={{
							dataType: 'STRING',
							displayName: 'Filed Ticket',
							id: '4',
							name: 'filedTicket'
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
	it('should render', () => {
		const {container} = render(<DefaultComponent />);

		expect(container).toMatchSnapshot();
	});
});
