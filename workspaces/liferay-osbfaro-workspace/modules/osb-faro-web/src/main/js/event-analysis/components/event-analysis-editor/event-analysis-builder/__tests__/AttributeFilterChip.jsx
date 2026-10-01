import AttributeFilterChip from '../AttributeFilterChip';
import client from 'shared/apollo/client';
import mockStore from 'test/mock-store';
import React from 'react';
import {ApolloProvider} from '@apollo/client';
import {DndProvider} from 'react-dnd';
import {HTML5Backend} from 'react-dnd-html5-backend';
import {Provider} from 'react-redux';
import {render} from '@testing-library/react';

jest.unmock('react-dom');

jest.mock('react-router-dom', () => ({
	...jest.requireActual('react-router-dom'),
	useParams: () => ({
		channelId: '456'
	})
}));

describe('AttributeFilterChip', () => {
	it('renders the filter condition without a drag handle', () => {
		const {container} = render(
			<ApolloProvider client={client}>
				<Provider store={mockStore()}>
					<DndProvider backend={HTML5Backend}>
						<AttributeFilterChip
							attribute={{
								dataType: 'STRING',
								displayName: 'Article View',
								id: '0',
								name: 'articleView'
							}}
							filter={{
								attributeId: '0',
								dataType: 'STRING',
								operator: 'eq',
								type: 'event',
								values: ['Stuff']
							}}
						/>
					</DndProvider>
				</Provider>
			</ApolloProvider>
		);

		expect(container.querySelector('.drag-handle')).toBeNull();
		expect(container.querySelector('.condition-chip')).toHaveTextContent(
			/stuff/i
		);
	});
});
