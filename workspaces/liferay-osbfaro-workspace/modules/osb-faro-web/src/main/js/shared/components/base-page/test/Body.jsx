/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {cleanup, render} from '@testing-library/react';
import React from 'react';

import Body from '../Body';

jest.unmock('react-dom');

describe('BasePage.Body', () => {
	afterEach(cleanup);

	it('renders Body', () => {
		const {container} = render(<Body>Test Test</Body>);

		expect(container).toMatchSnapshot();
	});

	it('renders Body w/ disabled', () => {
		const {container} = render(<Body>Test Test</Body>);

		expect(container).toMatchSnapshot();
	});

	it('renders Body w/o pageContainer', () => {
		const {container} = render(
			<Body pageContainer={false}>Test Test</Body>
		);

		expect(container).toMatchSnapshot();
	});
});
