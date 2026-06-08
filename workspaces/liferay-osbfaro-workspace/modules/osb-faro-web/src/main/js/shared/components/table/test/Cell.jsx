/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {render} from '@testing-library/react';
import React from 'react';

import Cell from '../Cell';

jest.unmock('react-dom');

describe('Cell', () => {
	it('renders', () => {
		const {container} = render(
			<Cell title>
				<span>Test</span>
			</Cell>
		);
		expect(container).toMatchSnapshot();
	});

	it('renders as a normal cell', () => {
		const {container} = render(
			<Cell>
				<span>Test</span>
			</Cell>
		);

		expect(container.querySelector('.table-title')).toBeFalsy();
	});
});
