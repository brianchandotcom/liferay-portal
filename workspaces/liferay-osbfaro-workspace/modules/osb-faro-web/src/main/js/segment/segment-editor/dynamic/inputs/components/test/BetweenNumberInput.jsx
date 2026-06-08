/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {fireEvent, render} from '@testing-library/react';
import React from 'react';

import BetweenNumberInput from '../BetweenNumberInput';

jest.unmock('react-dom');

describe('BetweenNumberInput', () => {
	it('renders', () => {
		const {container} = render(
			<BetweenNumberInput
				onChange={jest.fn()}
				value={{end: 123, start: 1}}
			/>
		);

		expect(container).toMatchSnapshot();
	});

	fit('renders with error', () => {
		const {container, queryByTestId} = render(
			<BetweenNumberInput
				onChange={jest.fn()}
				value={{end: 12, start: 1}}
			/>
		);

		const startInput = queryByTestId('between-number-start-input');

		fireEvent.change(startInput, {
			target: {value: ''},
		});

		jest.runAllTimers();

		expect(container.querySelector('.has-error')).toBeTruthy();
	});
});
