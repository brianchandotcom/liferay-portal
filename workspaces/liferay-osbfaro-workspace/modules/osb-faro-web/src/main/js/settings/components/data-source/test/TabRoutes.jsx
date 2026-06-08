/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {render} from '@testing-library/react';
import React from 'react';
import {StaticRouter} from 'react-router';

import TabRoutes from '../TabRoutes';

jest.unmock('react-dom');

describe('TabRoutes', () => {
	it('renders', () => {
		const Component = () => <div>Foo Bar</div>;

		const {container, getByText} = render(
			<StaticRouter location="foo/path">
				<TabRoutes
					routes={[{component: Component, path: 'foo/path'}]}
				/>
			</StaticRouter>
		);

		expect(getByText('Foo Bar')).toBeTruthy();
		expect(container).toMatchSnapshot();
	});
});
