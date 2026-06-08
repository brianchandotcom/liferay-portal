/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {render} from '@testing-library/react';
import React from 'react';
import {StaticRouter} from 'react-router';

import HeaderCell from '../HeaderCell';

jest.unmock('react-dom');

describe('HeaderCell', () => {
	it('renders', () => {
		const {container} = render(
			<HeaderCell>
				<p>children</p>
			</HeaderCell>
		);

		expect(container).toMatchSnapshot();
	});

	it('renders with sort disabled', () => {
		const {container} = render(<HeaderCell sortable={false} />);

		expect(container.querySelector('.button-root')).toBeFalsy();
	});

	it('renders the header cell as a link if headerLink is true', () => {
		const {container} = render(
			<StaticRouter>
				<HeaderCell headerLink />
			</StaticRouter>
		);

		expect(container.querySelector('.button-root')).toHaveAttribute(
			'href',
			'/?page=1&sortOrder=ASC'
		);
	});
});
