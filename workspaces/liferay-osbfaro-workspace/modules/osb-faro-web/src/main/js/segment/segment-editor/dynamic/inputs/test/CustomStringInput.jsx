/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {cleanup, fireEvent, render} from '@testing-library/react';
import React from 'react';
import {Property} from '~/shared/util/records';

import {
	createCustomValueMap,
	setPropertyValue,
} from '../../utils/custom-inputs';
import CustomStringInput from '../CustomStringInput';

jest.unmock('react-dom');

const mockValue = createCustomValueMap([
	{key: 'criterionGroup', value: [{operatorName: 'eq', value: ''}]},
]);

const defaultProps = {
	displayValue: 'Address',
	operatorRenderer: () => <div>operator</div>,
	property: new Property(),
	touched: false,
	valid: true,
	value: mockValue,
};

const DefaultComponent = (props) => (
	<CustomStringInput {...defaultProps} {...props} />
);

describe('CustomStringInput', () => {
	afterEach(cleanup);

	it('renders', () => {
		const {container, getAllByText, getByText} = render(
			<DefaultComponent valid={false} />
		);
		fireEvent.click(getByText('is'));

		expect(getAllByText('is')[1]).toBeTruthy();
		expect(getByText('is not')).toBeTruthy();
		expect(getByText('contains')).toBeTruthy();
		expect(getByText('does not contain')).toBeTruthy();
		expect(getByText('is known')).toBeTruthy();
		expect(getByText('is unknown')).toBeTruthy();
		expect(container).toMatchSnapshot();
	});

	it('renders with data', () => {
		const {container, getAllByText, getByText} = render(
			<DefaultComponent
				value={setPropertyValue(mockValue, 'value', 0, '123 West Road')}
			/>
		);
		fireEvent.click(getByText('is'));

		expect(getAllByText('is')[1]).toBeTruthy();
		expect(getByText('is not')).toBeTruthy();
		expect(getByText('contains')).toBeTruthy();
		expect(getByText('does not contain')).toBeTruthy();
		expect(getByText('is known')).toBeTruthy();
		expect(getByText('is unknown')).toBeTruthy();
		expect(container).toMatchSnapshot();
	});

	it('renders w/o value input when value is null', () => {
		const {queryByTestId} = render(
			<DefaultComponent
				value={setPropertyValue(mockValue, 'value', 0, null)}
			/>
		);

		expect(queryByTestId('attribute-value-string-input')).toBeNull();
	});

	it('renders w/ has-error when touched and not valid', () => {
		const {container} = render(<DefaultComponent touched valid={false} />);

		expect(container.querySelector('.has-error')).toBeTruthy();
	});

	it('renders as a regular input if autocomplete is false', () => {
		const {container} = render(<DefaultComponent autocomplete={false} />);

		expect(container.querySelector('.base-select-input-root')).toBeNull();
	});
});
