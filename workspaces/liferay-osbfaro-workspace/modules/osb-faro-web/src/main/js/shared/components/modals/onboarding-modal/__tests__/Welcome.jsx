import React from 'react';
import Welcome from '../Welcome';
import {cleanup, fireEvent, render} from '@testing-library/react';
import {noop} from 'lodash';

jest.unmock('react-dom');

describe('Welcome', () => {
	afterEach(cleanup);

	it('renders', () => {
		const {container} = render(<Welcome onClose={noop} onNext={noop} />);

		expect(container).toMatchSnapshot();
	});

	it('calls onNext when "Next" is clicked', () => {
		const spy = jest.fn();

		const {queryByText} = render(<Welcome onClose={noop} onNext={spy} />);

		expect(spy).not.toBeCalled();

		fireEvent.click(queryByText('Next'));

		expect(spy).toBeCalled();
	});
});
