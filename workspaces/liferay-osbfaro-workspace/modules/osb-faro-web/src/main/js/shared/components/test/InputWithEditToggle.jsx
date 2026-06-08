/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {cleanup, fireEvent, render} from '@testing-library/react';
import React from 'react';

import InputWithEditToggle from '../InputWithEditToggle';

jest.unmock('react-dom');

describe('InputWithEditToggle', () => {
	afterEach(cleanup);

	it('renders', () => {
		const {container} = render(<InputWithEditToggle />);
		expect(container).toMatchSnapshot();
	});

	it('renders with a label and value', () => {
		const {getByDisplayValue, getByTestId, getByText} = render(
			<InputWithEditToggle label="foo" value="bar" />
		);

		fireEvent.click(getByTestId('edit'));

		jest.runAllTimers();

		expect(getByText('foo')).toBeTruthy();
		expect(getByDisplayValue('bar')).toBeTruthy();
	});

	it('renders with a cancel and submit button after the edit button is pressed', () => {
		const {getByLabelText, queryByLabelText} = render(
			<InputWithEditToggle />
		);

		const editButton = getByLabelText(/edit/i);

		expect(editButton).toBeTruthy();

		fireEvent.click(editButton);

		jest.runAllTimers();

		expect(queryByLabelText(/edit/i)).toBeFalsy();
		expect(getByLabelText(/submit/i)).toBeTruthy();
		expect(getByLabelText(/cancel/i)).toBeTruthy();
	});

	it('renders as disabled if editable is false', () => {
		const {getByTestId} = render(
			<InputWithEditToggle editable={false} value="bar" />
		);

		expect(getByTestId('edit')).toBeDisabled();
	});
});
