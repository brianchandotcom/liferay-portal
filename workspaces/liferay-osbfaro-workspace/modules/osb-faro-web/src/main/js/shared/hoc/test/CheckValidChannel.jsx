/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {cleanup, render} from '@testing-library/react';
import React from 'react';
import {StaticRouter} from 'react-router';

import CheckValidChannel from '../CheckValidChannel';

jest.unmock('react-dom');

const wrappedComponentText = () => 'wrapped component text';

describe('CheckValidChannel', () => {
	afterEach(cleanup);

	it('renders a wrapped component', () => {
		const WrappedComponent = CheckValidChannel(wrappedComponentText);

		const {container} = render(
			<WrappedComponent
				channelId="123"
				channels={[{id: '123'}]}
				location={{pathname: 'test'}}
			/>
		);

		expect(container.textContent).toBe('wrapped component text');
	});

	it('renders an error page', () => {
		const WrappedComponent = CheckValidChannel(wrappedComponentText);

		const {container} = render(
			<StaticRouter>
				<WrappedComponent
					channelId="123"
					channels={[{id: '456'}]}
					location={{pathname: 'test'}}
				/>
			</StaticRouter>
		);

		expect(container.textContent).toBe(
			'404Page Not FoundThe page you are looking for does not exist.Go to Home'
		);
	});
});
