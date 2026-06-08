/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {cleanup, render} from '@testing-library/react';
import React from 'react';

import ToggleSwitch from '../ToggleSwitch';

jest.unmock('react-dom');

describe('ToggleSwitch', () => {
	afterEach(cleanup);

	it('renders', () => {
		const {container} = render(<ToggleSwitch label="foo" name="foo" />);
		expect(container).toMatchSnapshot();
	});

	it('renders with an initial value', () => {
		const {getByTestId} = render(
			<ToggleSwitch checked label="foo" name="foo" />
		);
		expect(getByTestId('toggle-switch-input').checked).toBeTrue();
	});
});
