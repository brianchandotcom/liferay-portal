import React from 'react';
import Row from '../Row';
import {cleanup, render} from '@testing-library/react';
import {DndProvider} from 'react-dnd';
import {HTML5Backend} from 'react-dnd-html5-backend';
import {noop} from 'lodash';

jest.unmock('react-dom');

describe('Row', () => {
	afterEach(cleanup);

	it('renders', () => {
		const {container} = render(
			<DndProvider backend={HTML5Backend}>
				<Row
					columns={[
						{
							accessor: 'title'
						}
					]}
					data={{title: 'Test Test'}}
					index={0}
					name='row'
					onMove={noop}
					rowIdentifier='title'
				/>
			</DndProvider>
		);

		expect(container).toMatchSnapshot();
	});

	it('renders w/o drag handle', () => {
		const {container} = render(
			<DndProvider backend={HTML5Backend}>
				<Row
					columns={[
						{
							accessor: 'title'
						}
					]}
					data={{draggable: false, title: 'Test Test'}}
					index={0}
					name='row'
					onMove={noop}
					rowIdentifier='title'
				/>
			</DndProvider>
		);

		expect(container.querySelector('.drag-handle')).not.toBeNull();
	});
});
