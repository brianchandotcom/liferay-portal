import AttributeBreakdownChip from '../AttributeBreakdownChip';
import client from 'shared/apollo/client';
import mockStore from 'test/mock-store';
import React from 'react';
import {ApolloProvider} from '@apollo/client';
import {DndProvider} from 'react-dnd';
import {HTML5Backend} from 'react-dnd-html5-backend';
import {Provider} from 'react-redux';
import {fireEvent, render, screen} from '@testing-library/react';

jest.unmock('react-dom');

describe('AttributeBreakdownChip', () => {
	it('renders a draggable chip with its owner type and name', () => {
		const onCloseClick = jest.fn();

		const {container} = render(
			<ApolloProvider client={client}>
				<Provider store={mockStore()}>
					<DndProvider backend={HTML5Backend}>
						<AttributeBreakdownChip
							attribute={{
								dataType: 'STRING',
								displayName: 'Article View',
								id: '0',
								name: 'articleView'
							}}
							breakdown={{
								attributeId: '0',
								attributeType: 'EVENT',
								dataType: 'STRING',
								id: '7'
							}}
							index={1}
							onCloseClick={onCloseClick}
						/>
					</DndProvider>
				</Provider>
			</ApolloProvider>
		);

		expect(container.querySelector('.drag-handle')).toBeInTheDocument();
		expect(container.querySelector('.text-uppercase')).toHaveTextContent(
			/event/i
		);
		expect(container.querySelector('.condition-chip')).toHaveTextContent(
			/article view/i
		);

		fireEvent.click(
			screen.getByRole('button', {name: /remove.article view/i})
		);

		expect(onCloseClick).toHaveBeenCalledWith({id: '7'});
	});
});
