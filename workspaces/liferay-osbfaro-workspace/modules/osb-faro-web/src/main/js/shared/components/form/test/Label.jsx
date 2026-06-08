/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {render} from '@testing-library/react';
import React from 'react';

import Label from '../Label';

jest.unmock('react-dom');

describe('Label', () => {
	it('renders', () => {
		const {container} = render(<Label />);

		expect(container).toMatchSnapshot();
	});

	it('renders as required', () => {
		const {container} = render(<Label required />);

		expect(container.querySelector('.required')).toBeTruthy();
	});

	it('renders with a popover component', () => {
		const {container} = render(<Label info="foo bar baz" />);

		expect(container.querySelector('.info-popover-root')).toBeTruthy;
	});
});
