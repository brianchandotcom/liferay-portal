import React from 'react';
import TimePeriodInput from '../TimePeriodInput';
import {cleanup, fireEvent, render} from '@testing-library/react';
import {TimeSpans} from '../../../utils/constants';

describe('TimePeriodInput', () => {
	afterEach(cleanup);

	it('should render', () => {
		const {container, getAllByText, getByText} = render(
			<TimePeriodInput onChange={jest.fn()} value={TimeSpans.Last7Days} />
		);
		fireEvent.click(getByText(/^last 7 days$/i));

		expect(getByText(/^last 24 hours$/i)).toBeInTheDocument();
		expect(getByText(/^yesterday$/i)).toBeInTheDocument();
		expect(getAllByText(/^last 7 days$/i)[1]).toBeTruthy();
		expect(getByText(/^last 28 days$/i)).toBeInTheDocument();
		expect(getByText(/^last 30 days$/i)).toBeInTheDocument();
		expect(getByText(/^last 90 days$/i)).toBeInTheDocument();

		expect(container).toMatchSnapshot();
	});
});
