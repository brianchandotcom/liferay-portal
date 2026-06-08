/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {cleanup, render} from '@testing-library/react';
import React from 'react';

import Card from '../Card';

jest.unmock('react-dom');

describe('Card', () => {
	afterEach(cleanup);

	it('renders', () => {
		const {container} = render(<Card />);
		expect(container).toMatchSnapshot();
	});

	it('renders horizontal', () => {
		const {container} = render(<Card horizontal />);
		expect(container.querySelector('.horizontal')).toBeTruthy();
	});
});
