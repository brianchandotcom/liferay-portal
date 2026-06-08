/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {cleanup, fireEvent, render} from '@testing-library/react';
import React from 'react';
import {Property} from '~/shared/util/records';

import BooleanInput from '../BooleanInput';

jest.unmock('react-dom');

describe('BooleanInput', () => {
	afterEach(cleanup);

	it('renders', () => {
		const {container, getByText} = render(
			<BooleanInput
				operatorRenderer={() => <div>operator</div>}
				property={new Property()}
			/>
		);
		fireEvent.click(getByText('Select an option'));

		expect(getByText('True')).toBeTruthy();
		expect(getByText('False')).toBeTruthy();
		expect(container).toMatchSnapshot();
	});

	it('renders with data', () => {
		const {container, getAllByText, getByText} = render(
			<BooleanInput
				displayValue="Do Not Call"
				operatorRenderer={() => <div>operator</div>}
				property={new Property()}
				value="true"
			/>
		);
		fireEvent.click(getByText('True'));

		expect(getAllByText('True')[1]).toBeTruthy();
		expect(getByText('False')).toBeTruthy();
		expect(container).toMatchSnapshot();
	});
});
