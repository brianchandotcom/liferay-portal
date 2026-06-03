import Label from '../Label';
import React from 'react';
import {cleanup, render} from '@testing-library/react';

jest.unmock('react-dom');

describe('Label', () => {
	afterEach(cleanup);

	it('should render', () => {
		const {container} = render(<Label />);
		expect(container).toMatchSnapshot();
	});

	it('should render a primary label', () => {
		const {container} = render(<Label display='primary' />);
		expect(container.querySelector('.label-primary')).toBeTruthy();
	});
});
