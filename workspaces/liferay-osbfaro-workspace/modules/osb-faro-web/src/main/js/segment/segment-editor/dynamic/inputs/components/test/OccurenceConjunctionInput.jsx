/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {fireEvent, render} from '@testing-library/react';
import React from 'react';

import {RelationalOperators} from '../../../utils/constants';
import OccurenceConjunctionInput from '../OccurenceConjunctionInput';

jest.unmock('react-dom');

describe('OccurenceConjunctionInput', () => {
	it('renders', () => {
		const {container, getByRole, getByText} = render(
			<OccurenceConjunctionInput
				onChange={jest.fn()}
				operatorName={RelationalOperators.LT}
				touched={false}
				valid
				value={123}
			/>
		);
		fireEvent.click(getByRole('combobox'));

		expect(getByText('at least')).toBeTruthy();
		expect(getByText('at most')).toBeTruthy();

		expect(container).toMatchSnapshot();
	});

	it('renders with error', () => {
		const {container} = render(
			<OccurenceConjunctionInput
				onChange={jest.fn()}
				operatorName={RelationalOperators.GT}
				touched
				valid={false}
				value=""
			/>
		);

		expect(container.querySelector('.has-error')).toBeTruthy();
	});
});
