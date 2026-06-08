/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {fireEvent, render} from '@testing-library/react';
import moment from 'moment';
import React from 'react';
import mockCurrentDate from '~/test/mock-date';

import DatePicker from '../index';

jest.unmock('react-dom');

describe('DatePicker', () => {
	let currentDate;

	afterEach(() => {
		currentDate.mockReset();
		currentDate.mockRestore();
	});

	beforeEach(() => {
		currentDate = mockCurrentDate();
	});

	it('renders', () => {
		const {container} = render(
			<DatePicker date={moment(0)} minDate={moment(0)} />
		);

		jest.runAllTimers();

		expect(container).toMatchSnapshot();
	});

	it('renders the next month', () => {
		const {getByTestId, queryAllByText} = render(
			<DatePicker date={moment(0)} minDate={moment(0)} />
		);

		expect(queryAllByText(/30/).length).toBe(2);

		fireEvent.click(getByTestId('next-month'));

		jest.runAllTimers();

		expect(queryAllByText(/30/).length).toBe(0);
	});

	it('renders label when a range is passed', () => {
		const {queryByText} = render(
			<DatePicker
				date={{
					end: null,
					start: moment(0),
				}}
				minDate={moment(0)}
			/>
		);

		jest.runAllTimers();

		expect(queryByText('End Date')).toBeTruthy();
	});

	fit('renders max range error when range > maxRange', () => {
		const {queryByText} = render(
			<DatePicker
				date={{
					end: moment().add(13, 'months'),
					start: moment(0),
				}}
				maxRange={365}
			/>
		);

		jest.runAllTimers();

		expect(
			queryByText('This exceeds the maximum range of 365 days.')
		).toBeTruthy();
	});
});
