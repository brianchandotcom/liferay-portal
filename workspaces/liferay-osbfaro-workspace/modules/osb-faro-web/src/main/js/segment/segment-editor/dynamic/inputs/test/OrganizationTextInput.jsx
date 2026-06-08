/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {cleanup, fireEvent, render} from '@testing-library/react';
import React from 'react';
import {Property} from '~/shared/util/records';

import {createCustomValueMap} from '../../utils/custom-inputs';
import OrganizationTextInput from '../OrganizationTextInput';

jest.unmock('react-dom');

const mockValue = createCustomValueMap([
	{key: 'criterionGroup', value: [{operatorName: 'eq', value: ''}]},
]);

const defaultProps = {
	displayValue: 'Address',
	operatorRenderer: () => <div>operator</div>,
	property: new Property(),
	touched: false,
	valid: false,
	value: mockValue,
};

describe('OrganizationTextInput', () => {
	afterEach(cleanup);

	it('renders', () => {
		const {container, getAllByText, getByText} = render(
			<OrganizationTextInput {...defaultProps} />
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
});
