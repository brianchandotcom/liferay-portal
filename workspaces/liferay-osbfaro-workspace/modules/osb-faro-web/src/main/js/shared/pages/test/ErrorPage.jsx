/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {render} from '@testing-library/react';
import React from 'react';
import {StaticRouter} from 'react-router';

import ErrorPage from '../ErrorPage';

jest.unmock('react-dom');

const DefaultComponent = (props) => (
	<StaticRouter>
		<ErrorPage {...props} />
	</StaticRouter>
);

describe('ErrorPage', () => {
	it('renders', () => {
		const {container} = render(<DefaultComponent />);

		expect(container).toMatchSnapshot();
	});

	it('renders a custom message', () => {
		const {getByText} = render(<DefaultComponent message="foo bar" />);

		expect(getByText('foo bar')).toBeTruthy();
	});
});
