import ErrorDisplay from '../ErrorDisplay';
import React from 'react';
import {cleanup, render} from '@testing-library/react';
import {noop} from 'lodash';

describe('ErrorDisplay', () => {
	afterEach(cleanup);

	it('should render', () => {
		const {container} = render(<ErrorDisplay />);
		expect(container).toMatchSnapshot();
	});

	it('should render with button', () => {
		const {getByText} = render(<ErrorDisplay onReload={noop} />);
		expect(getByText(/^reload$/i)).toBeInTheDocument();
	});
});
