/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {cleanup, render} from '@testing-library/react';
import React from 'react';
import {StaticRouter} from 'react-router';

import NavigationWarning from '../NavigationWarning';

jest.unmock('react-dom');

describe('NavigationWarning', () => {
	afterEach(cleanup);

	it('renders', () => {
		const {container} = render(
			<StaticRouter>
				<NavigationWarning when />
			</StaticRouter>
		);

		expect(container).toBeTruthy();
	});
});
