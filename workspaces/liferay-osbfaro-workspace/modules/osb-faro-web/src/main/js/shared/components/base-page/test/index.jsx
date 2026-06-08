/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {render} from '@testing-library/react';
import React from 'react';
import {Provider} from 'react-redux';
import {StaticRouter} from 'react-router';
import mockStore from '~/test/mock-store';

import {BasePage} from '../index';

jest.unmock('react-dom');

describe('BasePage', () => {
	it('renders BasePage', () => {
		const {container} = render(
			<Provider store={mockStore()}>
				<StaticRouter>
					<BasePage documentTitle="Test title">Test test</BasePage>
				</StaticRouter>
			</Provider>
		);

		expect(container).toMatchSnapshot();
	});
});
