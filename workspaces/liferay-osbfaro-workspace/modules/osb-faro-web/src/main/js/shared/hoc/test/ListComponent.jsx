/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {cleanup, render} from '@testing-library/react';
import React from 'react';
import {StaticRouter} from 'react-router';

import ListComponent from '../ListComponent';

jest.unmock('react-dom');

describe('ListComponent', () => {
	afterEach(cleanup);

	it('renders', () => {
		const {container} = render(
			<StaticRouter>
				<ListComponent items={[]} total={0} />
			</StaticRouter>
		);

		expect(container).toMatchSnapshot();
	});
});
