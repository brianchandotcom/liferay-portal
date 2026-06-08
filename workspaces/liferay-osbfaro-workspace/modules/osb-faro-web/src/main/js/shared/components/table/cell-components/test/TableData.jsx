/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {render} from '@testing-library/react';
import React from 'react';
import {StaticRouter} from 'react-router';

import TableData from '../TableData';

jest.unmock('react-dom');

describe('TableData', () => {
	it('renders', () => {
		const {container} = render(<TableData />);

		expect(container).toMatchSnapshot();
	});

	it('renders with Link', () => {
		const {container, getAllByText} = render(
			<StaticRouter>
				<TableData title="My Title" url="foo/bar" />
			</StaticRouter>
		);

		expect(container.querySelector('a')).toHaveAttribute(
			'href',
			'/foo/bar'
		);
		expect(getAllByText('My Title')).toBeTruthy();
	});

	it('renders with Empty message', () => {
		const {container, getByText} = render(
			<TableData emptyMessage="Empty Message" />
		);

		expect(container.querySelector('.text-secondary')).toBeTruthy();
		expect(getByText('Empty Message')).toBeTruthy();
	});
});
