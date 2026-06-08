/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {cleanup, render} from '@testing-library/react';
import React from 'react';

import Checkbox from '../Checkbox';

jest.unmock('react-dom');

describe('Checkbox', () => {
	afterEach(cleanup);

	it('renders', () => {
		const {container} = render(<Checkbox />);
		expect(container).toMatchSnapshot();
	});

	it('renders with a label', () => {
		const {queryByText} = render(<Checkbox label="foo" />);
		expect(queryByText('foo')).toBeTruthy();
	});
});
