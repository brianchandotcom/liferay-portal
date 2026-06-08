/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {render} from '@testing-library/react';
import React from 'react';

import optional from '../Optional';

jest.unmock('react-dom');

describe('Optional', () => {
	it('renders the original component', () => {
		const hoc = jest.fn(() => 'hoc component');
		const Optional = optional(hoc)(jest.fn(() => 'wrapped component'));

		const {queryByText} = render(<Optional id={null} />);

		expect(queryByText('hoc component')).toBeNull();
		expect(queryByText('wrapped component')).toBeTruthy();
	});

	it('renders the HOC component instead', () => {
		const hoc = jest.fn(() => () => 'hoc component');
		const Optional = optional(hoc)(jest.fn(() => 'wrapped component'));
		const {queryByText} = render(<Optional id={23} />);

		expect(queryByText('hoc component')).toBeTruthy();
		expect(queryByText('wrapped component')).toBeNull();
	});
});
