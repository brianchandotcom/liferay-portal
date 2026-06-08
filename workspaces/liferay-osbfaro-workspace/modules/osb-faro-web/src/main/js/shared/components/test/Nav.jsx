/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayButton from '@clayui/button';
import {cleanup, render} from '@testing-library/react';
import React from 'react';
import {StaticRouter} from 'react-router';

import Nav from '../Nav';

jest.unmock('react-dom');

const items = [
	<Nav.Item active href="#" key={1}>
		foo
	</Nav.Item>,
	<Nav.Item key={2}>bar</Nav.Item>,
	<Nav.Item key={3}>
		<ClayButton className="button-root" displayType="secondary">
			baz
		</ClayButton>
	</Nav.Item>,
];

const DefaultComponent = (props) => (
	<StaticRouter>
		<Nav {...props}>{items}</Nav>
	</StaticRouter>
);

describe('Nav', () => {
	afterEach(cleanup);

	it('renders', () => {
		const {container} = render(<DefaultComponent />);
		expect(container).toMatchSnapshot();
	});

	it('renders stacked', () => {
		const {container} = render(<DefaultComponent display="stacked" />);
		expect(container.querySelector('.nav-stacked')).toBeTruthy();
	});

	it('renders with items as pills', () => {
		const {container} = render(<DefaultComponent display="pills" />);
		expect(container.querySelector('.nav-pills')).toBeTruthy();
	});

	it('renders with items as tabs', () => {
		const {container} = render(<DefaultComponent display="tabs" />);
		expect(container.querySelector('.nav-tabs')).toBeTruthy();
	});

	it('renders with underline class', () => {
		const {container} = render(<DefaultComponent display="underline" />);
		expect(container.querySelector('.nav-underline')).toBeTruthy();
	});
});
