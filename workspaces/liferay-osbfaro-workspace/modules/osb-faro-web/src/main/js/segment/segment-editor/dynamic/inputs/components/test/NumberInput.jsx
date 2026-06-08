/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {render} from '@testing-library/react';
import React from 'react';

import {FunctionalOperators} from '../../../utils/constants';
import NumberInput from '../NumberInput';

jest.unmock('react-dom');

describe('NumberInput', () => {
	it('renders', () => {
		const {container} = render(
			<NumberInput
				onChange={jest.fn()}
				touched={false}
				valid
				value={123}
			/>
		);

		expect(container).toMatchSnapshot();
	});

	it('renders with error', () => {
		const {container} = render(
			<NumberInput onChange={jest.fn()} touched valid={false} value="" />
		);

		expect(container.querySelector('.has-error')).toBeTruthy();
	});

	it('renders with BetweenNumberInput', () => {
		const {queryByTestId} = render(
			<NumberInput
				onChange={jest.fn()}
				operatorName={FunctionalOperators.Between}
				touched
				valid={false}
				value={{end: 20, start: 1}}
			/>
		);

		expect(queryByTestId('between-number-start-input')).toBeTruthy();
		expect(queryByTestId('between-number-end-input')).toBeTruthy();
	});
});
