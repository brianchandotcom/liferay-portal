import OccurenceConjunctionInput from '../OccurenceConjunctionInput';
import React from 'react';
import {fireEvent, render} from '@testing-library/react';
import {RelationalOperators} from '../../../utils/constants';

describe('OccurenceConjunctionInput', () => {
	it('should render', () => {
		const {container, getByRole, getByText} = render(
			<OccurenceConjunctionInput
				onChange={jest.fn()}
				operatorName={RelationalOperators.LT}
				touched={false}
				valid
				value={123}
			/>
		);
		fireEvent.click(getByRole('combobox'));

		expect(getByText('at least')).toBeInTheDocument();
		expect(getByText('at most')).toBeInTheDocument();

		expect(container).toMatchSnapshot();
	});

	it('should render with error', () => {
		const {container} = render(
			<OccurenceConjunctionInput
				onChange={jest.fn()}
				operatorName={RelationalOperators.GT}
				touched
				valid={false}
				value=''
			/>
		);

		expect(container.querySelector('.has-error')).toBeTruthy();
	});
});
