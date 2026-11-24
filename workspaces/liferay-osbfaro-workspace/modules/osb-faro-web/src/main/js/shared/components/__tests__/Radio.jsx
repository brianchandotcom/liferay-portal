import Radio from '../Radio';
import React from 'react';
import {cleanup, render} from '@testing-library/react';

describe('Radio', () => {
	afterEach(cleanup);

	it('should render', () => {
		const {container} = render(<Radio />);
		expect(container).toMatchSnapshot();
	});

	it('should render with a label', () => {
		const {getByText} = render(<Radio label='foo' />);
		expect(getByText('foo')).toBeInTheDocument();
	});
});
