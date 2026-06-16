import ProgressBar from '../ProgressBar';
import React from 'react';
import {cleanup, render} from '@testing-library/react';

jest.unmock('react-dom');

describe('ProgressBar', () => {
	afterEach(cleanup);

	it('renders', () => {
		const {container} = render(<ProgressBar />);

		expect(container).toMatchSnapshot();
	});

	it('should render a complete progress bar without error', () => {
		const {container} = render(<ProgressBar complete error={false} />);

		expect(container).toMatchSnapshot();
	});

	it('should render a progress bar with error', () => {
		const {container} = render(<ProgressBar complete={false} error />);

		expect(container).toMatchSnapshot();
	});
});
