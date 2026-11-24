import Item from '../Item';
import React from 'react';
import {render} from '@testing-library/react';

describe('Item', () => {
	it('should render', () => {
		const {container} = render(<Item />);

		expect(container).toMatchSnapshot();
	});

	it('should render with children', () => {
		const childrenContent = 'Children';

		const {getByText} = render(<Item children={childrenContent} />);

		expect(getByText(childrenContent)).toBeInTheDocument();
	});

	it('should render a header item', () => {
		const {container} = render(<Item header />);

		expect(container.querySelector('.list-group-header')).toBeTruthy();
		expect(container.querySelector('.list-group-item')).toBeFalsy();
	});

	it('should render as disabled', () => {
		const {container} = render(<Item disabled />);

		expect(
			container.querySelector('.list-group-item-disabled')
		).toBeTruthy();
	});

	it('should render as active', () => {
		const {container} = render(<Item active />);

		expect(container.querySelector('.active')).toBeTruthy();
	});
});
