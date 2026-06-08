/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {fireEvent, render} from '@testing-library/react';
import React from 'react';
import {DataTypes, Operators} from '~/event-analysis/utils/types';

import OperatorSelect from '../OperatorSelect';
import {ATTRIBUTES_NUMBER_OPERATOR_LONGHAND_LABELS_MAP} from '../utils';

jest.unmock('react-dom');

describe('OperatorSelect', () => {
	it('renders', () => {
		const {container, getByText} = render(
			<OperatorSelect
				dataType={DataTypes.Number}
				onChange={jest.fn()}
				operatorsName={
					ATTRIBUTES_NUMBER_OPERATOR_LONGHAND_LABELS_MAP[Operators.EQ]
				}
			/>
		);
		fireEvent.click(getByText('Select an option'));

		expect(getByText('greater than')).toBeTruthy();
		expect(getByText('less than')).toBeTruthy();
		expect(getByText('is equal to')).toBeTruthy();
		expect(getByText('is not equal to')).toBeTruthy();

		expect(container).toMatchSnapshot();
	});
});
