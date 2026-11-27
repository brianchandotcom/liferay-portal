import DurationInput from '../DurationInput';
import React from 'react';
import {cleanup, render} from '@testing-library/react';

describe('DurationInput', () => {
	afterEach(cleanup);

	it('should render', () => {
		const {container} = render(<DurationInput />);

		expect(container).toMatchSnapshot();
	});

	it('should render default unit as seconds', () => {
		const {getByDisplayValue} = render(<DurationInput value={1000} />);

		expect(getByDisplayValue('00:00:01')).toBeInTheDocument();
	});

	it('should render default unit as minutes', () => {
		const {getByDisplayValue} = render(<DurationInput value={60000} />);

		expect(getByDisplayValue('00:01:00')).toBeInTheDocument();
	});

	it('should render default unit as hours', () => {
		const {getByDisplayValue} = render(<DurationInput value={3600000} />);

		expect(getByDisplayValue('01:00:00')).toBeInTheDocument();
	});
});
