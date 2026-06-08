/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {cleanup, fireEvent, render} from '@testing-library/react';
import {fromJS} from 'immutable';
import React from 'react';
import {Property} from '~/shared/util/records';

import {PropertyTypes, RelationalOperators} from '../../utils/constants';
import AccountInput from '../AccountInput';

jest.unmock('react-dom');

const {EQ} = RelationalOperators;

describe('AccountInput', () => {
	afterEach(cleanup);

	it('renders', () => {
		const {container, getAllByText, getByText} = render(
			<AccountInput
				property={new Property()}
				value={fromJS({criterionGroup: {items: [{operatorName: EQ}]}})}
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

	it('renders a CustomNumberInput', () => {
		const {queryByText} = render(
			<AccountInput
				property={new Property({type: PropertyTypes.AccountNumber})}
				value={fromJS({criterionGroup: {items: [{operatorName: EQ}]}})}
			/>
		);

		expect(queryByText('is equal to')).not.toBeNull();
	});
});
