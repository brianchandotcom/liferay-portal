/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {cleanup, render} from '@testing-library/react';
import React from 'react';
import {StaticRouter} from 'react-router';

import SourceCell from '../Source';

jest.unmock('react-dom');

describe('SourceCell', () => {
	afterEach(cleanup);

	it('renders', () => {
		const {container} = render(
			<StaticRouter>
				<SourceCell
					data={{
						dataSourceId: '456',
						dataSourceName: 'Test Data Source',
					}}
					groupId="123"
				/>
			</StaticRouter>
		);

		expect(container).toMatchSnapshot();
	});

	it('renders without an href', () => {
		const {container} = render(
			<StaticRouter>
				<SourceCell
					data={{
						dataSourceName: 'Test Data Source',
					}}
					groupId="123"
				/>
			</StaticRouter>
		);

		expect(container.querySelector('a')).toBeNull();
	});
});
