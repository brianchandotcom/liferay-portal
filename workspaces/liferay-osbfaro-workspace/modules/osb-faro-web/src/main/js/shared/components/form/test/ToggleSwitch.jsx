/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {render} from '@testing-library/react';

import toggleSwitch from '../ToggleSwitch';

jest.unmock('react-dom');

describe('ToggleSwitch', () => {
	it('renders', () => {
		const {container} = render(toggleSwitch({field: {}}));

		expect(container).toMatchSnapshot();
	});

	it('renders with an initial value', () => {
		const {getByTestId} = render(toggleSwitch({field: {value: true}}));

		expect(getByTestId('toggle-switch-input')).toHaveAttribute(
			'value',
			'true'
		);
	});
});
