/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {cleanup, render} from '@testing-library/react';
import React from 'react';

import Circle from '../Circle';

jest.unmock('react-dom');

describe('Circle', () => {
	afterEach(cleanup);

	it('renders a Circle component', () => {
		const {container} = render(<Circle />);

		expect(container).toMatchSnapshot();
	});

	it('renders a Circle component with a red color', () => {
		const {container} = render(<Circle color="red" />);

		expect(container).toMatchSnapshot();
	});

	it('renders a Circle component with radius of 15px', () => {
		const {container} = render(<Circle size={15} />);

		expect(container).toMatchSnapshot();
	});
});
