/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {cleanup, render} from '@testing-library/react';
import React from 'react';

import Radio from '../Radio';

jest.unmock('react-dom');

describe('Radio', () => {
	afterEach(cleanup);

	it('renders', () => {
		const {container} = render(<Radio />);
		expect(container).toMatchSnapshot();
	});

	it('renders with a label', () => {
		const {queryByText} = render(<Radio label="foo" />);
		expect(queryByText('foo')).toBeTruthy();
	});
});
