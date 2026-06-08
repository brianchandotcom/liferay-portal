/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {cleanup, render} from '@testing-library/react';
import React from 'react';
import {MemoryRouter} from 'react-router';

import RouteNotFound from '../RouteNotFound';

jest.unmock('react-dom');

describe('RouteNotFound', () => {
	afterEach(cleanup);

	it('renders', () => {
		const {container} = render(
			<MemoryRouter>
				<RouteNotFound />
			</MemoryRouter>
		);
		expect(container).toBeTruthy();
	});
});
