import React from 'react';
import TimePeriodInput from '../TimePeriodInput';
import {cleanup, fireEvent, render} from '@testing-library/react';
import {TimeSpans} from '../../../utils/constants';

jest.unmock('react-dom');

describe('TimePeriodInput', () => {
	afterEach(cleanup);

	it('should render', () => {
		const {container, getAllByText, getByText} = render(
			<TimePeriodInput onChange={jest.fn()} value={TimeSpans.Last7Days} />
		);
		fireEvent.click(getByText('Last 7 days'));

		expect(getByText('Last 24 hours')).toBeTruthy();
		expect(getByText('Yesterday')).toBeTruthy();
		expect(getAllByText('Last 7 days')[1]).toBeTruthy();
		expect(getByText('Last 28 days')).toBeTruthy();
		expect(getByText('Last 30 days')).toBeTruthy();
		expect(getByText('Last 90 days')).toBeTruthy();

		expect(container).toMatchSnapshot();
	});
});
