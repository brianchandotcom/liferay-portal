import AppliedFilters from '../AppliedFilters';
import React from 'react';
import {fireEvent, render} from '@testing-library/react';

jest.unmock('react-dom');

const filters = {
	Devices: ['Desktop'],
	Location: ['Brazil']
};

describe('AppliedFilters', () => {
	it('should render', () => {
		const {container} = render(<AppliedFilters filters={filters} />);

		expect(container).toMatchSnapshot();
	});

	it('should render with no filters applied', () => {
		const {queryByText} = render(<AppliedFilters />);

		expect(queryByText('Brazil')).not.toBeInTheDocument();
	});

	it('should deactivate the filter when clicking on btn close label', () => {
		const spy = jest.fn();

		const {container} = render(
			<AppliedFilters filters={filters} onChange={spy} />
		);

		fireEvent.click(container.querySelectorAll('button')[1]);

		expect(spy).toBeCalledWith({Devices: ['Desktop'], Location: []});
	});

	it('should deactivate all filters when clicking on "Clear Filter"', () => {
		const spy = jest.fn();

		const {getByText} = render(
			<AppliedFilters filters={filters} onChange={spy} />
		);

		fireEvent.click(getByText('Clear Filter'));

		expect(spy).toBeCalled();
	});
});
