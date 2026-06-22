import Modal from '../index';
import React from 'react';
import {render} from '@testing-library/react';

jest.unmock('react-dom');

describe('Modal', () => {
	it('should render', () => {
		const {container} = render(<Modal />);

		expect(container).toMatchSnapshot();
	});

	it('should render as a large warning modal', () => {
		const {container} = render(<Modal size='lg' type='warning' />);

		expect(container.querySelector('.modal-lg')).toBeTruthy();
		expect(container.querySelector('.modal-warning')).toBeTruthy();
	});

	it('should render with children', () => {
		const {queryByText} = render(
			<Modal>
				<span>{'Modal child'}</span>
			</Modal>
		);

		expect(queryByText('Modal child')).toBeTruthy();
	});
});
