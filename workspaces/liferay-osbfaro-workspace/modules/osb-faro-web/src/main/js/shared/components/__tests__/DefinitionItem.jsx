import DefinitionItem from '../DefinitionItem';
import React from 'react';
import {cleanup, fireEvent, render} from '@testing-library/react';

describe('DefinitionItem', () => {
	afterEach(cleanup);

	it('should render', () => {
		const {container} = render(<DefinitionItem />);

		expect(container).toMatchSnapshot();
	});

	it('should render with a label and value', () => {
		const {getByText} = render(
			<DefinitionItem label='foo' value='bar' />
		);

		expect(getByText('foo')).toBeInTheDocument();
		expect(getByText('bar')).toBeInTheDocument();
	});

	it('should render with an edit button', () => {
		const {getByLabelText} = render(<DefinitionItem editable />);

		expect(getByLabelText(/^edit$/i)).toBeInTheDocument();
	});

	it('should render as an input field with a cancel and submit button', () => {
		const {getByLabelText, queryByLabelText} = render(
			<DefinitionItem editable />
		);

		fireEvent.click(getByLabelText(/^edit$/i));

		jest.runAllTimers();

		expect(getByLabelText(/^cancel$/i)).toBeInTheDocument();
		expect(getByLabelText(/^submit$/i)).toBeInTheDocument();
		expect(queryByLabelText(/^edit$/i)).toBeNull();
	});
});
