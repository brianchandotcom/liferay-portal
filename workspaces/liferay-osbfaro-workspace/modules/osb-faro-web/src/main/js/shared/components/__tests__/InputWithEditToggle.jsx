import InputWithEditToggle from '../InputWithEditToggle';
import React from 'react';
import {cleanup, fireEvent, render} from '@testing-library/react';

describe('InputWithEditToggle', () => {
	afterEach(cleanup);

	it('should render', () => {
		const {container} = render(<InputWithEditToggle />);
		expect(container).toMatchSnapshot();
	});

	it('should render with a label and value', () => {
		const {getByDisplayValue, getByTestId, getByText} = render(
			<InputWithEditToggle label='foo' value='bar' />
		);

		fireEvent.click(getByTestId('edit'));

		jest.runAllTimers();

		expect(getByText('foo')).toBeInTheDocument();
		expect(getByDisplayValue('bar')).toBeInTheDocument();
	});

	it('should render with a cancel and submit button after the edit button is pressed', () => {
		const {getByLabelText, queryByLabelText} = render(
			<InputWithEditToggle />
		);

		const editButton = getByLabelText(/edit/i);

		expect(editButton).toBeTruthy();

		fireEvent.click(editButton);

		jest.runAllTimers();

		expect(queryByLabelText(/edit/i)).toBeFalsy();
		expect(getByLabelText(/submit/i)).toBeInTheDocument();
		expect(getByLabelText(/cancel/i)).toBeInTheDocument();
	});

	it('should render as disabled if editable is false', () => {
		const {getByTestId} = render(
			<InputWithEditToggle editable={false} value='bar' />
		);

		expect(getByTestId('edit')).toBeDisabled();
	});
});
