/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {cleanup, fireEvent, render} from '@testing-library/react';
import {fromJS} from 'immutable';
import React from 'react';
import {Property} from '~/shared/util/records';

import CustomNumberInput from '../CustomNumberInput';

jest.unmock('react-dom');

describe('CustomNumberInput', () => {
	afterEach(cleanup);

	it('renders', () => {
		const {container, getAllByText, getByText} = render(
			<CustomNumberInput
				property={new Property()}
				touched={false}
				valid={false}
				value={fromJS({
					criterionGroup: {items: [{operatorName: 'eq', value: ''}]},
				})}
			/>
		);
		fireEvent.click(getByText('is equal to'));

		expect(getAllByText('is equal to')[1]).toBeTruthy();
		expect(getByText('greater than')).toBeTruthy();
		expect(getByText('less than')).toBeTruthy();
		expect(getByText('is not equal to')).toBeTruthy();
		expect(getByText('is known')).toBeTruthy();
		expect(getByText('is unknown')).toBeTruthy();
		expect(container).toMatchSnapshot();
	});

	it('renders with data', () => {
		const {container, getAllByText, getByText} = render(
			<CustomNumberInput
				displayValue="Revenue"
				operatorRenderer={() => <div>operator</div>}
				property={new Property()}
				touched={false}
				valid
				value={fromJS({
					criterionGroup: {
						items: [{operatorName: 'eq', value: '123'}],
					},
				})}
			/>
		);
		fireEvent.click(getByText('is equal to'));

		expect(getAllByText('is equal to')[1]).toBeTruthy();
		expect(getByText('greater than')).toBeTruthy();
		expect(getByText('less than')).toBeTruthy();
		expect(getByText('is not equal to')).toBeTruthy();
		expect(getByText('is known')).toBeTruthy();
		expect(getByText('is unknown')).toBeTruthy();
		expect(container).toMatchSnapshot();
	});

	it('renders w/o value input when value is null', () => {
		const {queryByTestId} = render(
			<CustomNumberInput
				displayValue="Revenue"
				operatorRenderer={() => <div>operator</div>}
				property={new Property()}
				touched={false}
				valid
				value={fromJS({
					criterionGroup: {
						items: [{operatorName: 'eq', value: null}],
					},
				})}
			/>
		);

		expect(queryByTestId('number-input')).toBeNull();
	});

	it('renders w/ has-error when touched and not valid', () => {
		const {container} = render(
			<CustomNumberInput
				displayValue="Revenue"
				operatorRenderer={() => <div>operator</div>}
				property={new Property()}
				touched
				valid={false}
				value={fromJS({
					criterionGroup: {items: [{operatorName: 'eq', value: ''}]},
				})}
			/>
		);

		expect(container.querySelector('.has-error')).toBeTruthy();
	});
});
