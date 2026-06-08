/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {cleanup, render} from '@testing-library/react';
import React from 'react';
import {Property} from '~/shared/util/records';

import NumberInput from '../NumberInput';

jest.unmock('react-dom');

describe('NumberInput', () => {
	afterEach(cleanup);

	it('renders', () => {
		const {container} = render(
			<NumberInput
				operatorRenderer={() => <div>operator</div>}
				property={new Property()}
				touched={false}
				valid={false}
			/>
		);

		expect(container).toMatchSnapshot();
	});

	it('renders with data', () => {
		const {container} = render(
			<NumberInput
				displayValue="Name"
				operatorRenderer={() => <div>operator</div>}
				property={new Property()}
				touched={false}
				valid
				value="Test Test"
			/>
		);

		expect(container).toMatchSnapshot();
	});

	it('renders w/o value input when value is null', () => {
		const {queryByTestId} = render(
			<NumberInput
				displayValue="Name"
				operatorRenderer={() => <div>operator</div>}
				property={new Property()}
				touched={false}
				valid
				value={null}
			/>
		);

		expect(queryByTestId('number-input')).toBeNull();
	});

	it('renders w/ has-error when touched and not valid', () => {
		const {container} = render(
			<NumberInput
				displayValue="Name"
				operatorRenderer={() => <div>operator</div>}
				property={new Property()}
				touched
				valid={false}
				value=""
			/>
		);

		expect(container.querySelector('.has-error')).toBeTruthy();
	});
});
