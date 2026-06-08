/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {cleanup, render} from '@testing-library/react';
import React from 'react';

import Label from '../Label';

jest.unmock('react-dom');

describe('Label', () => {
	afterEach(cleanup);

	it('renders', () => {
		const {container} = render(<Label />);
		expect(container).toMatchSnapshot();
	});

	it('renders a primary label', () => {
		const {container} = render(<Label display="primary" />);
		expect(container.querySelector('.label-primary')).toBeTruthy();
	});
});
