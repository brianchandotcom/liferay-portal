/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {cleanup, render} from '@testing-library/react';
import React from 'react';

import NavBar from '../NavBar';

jest.unmock('react-dom');

describe('NavBar', () => {
	afterEach(cleanup);

	it('renders', () => {
		const {container} = render(<NavBar />);
		expect(container).toMatchSnapshot();
	});

	it('renders Brand as a child', () => {
		const {container} = render(
			<NavBar>{[<NavBar.Brand key="foo">foo bar</NavBar.Brand>]}</NavBar>
		);
		expect(container.querySelector('.navbar-brand')).toBeTruthy();
	});

	it('renders with light display', () => {
		const {container} = render(<NavBar display="light" />);
		expect(container.querySelector('.navbar-light')).toBeTruthy();
	});

	it('renders with dark display', () => {
		const {container} = render(<NavBar display="dark" />);
		expect(container.querySelector('.navbar-dark')).toBeTruthy();
	});

	it('renders with underline', () => {
		const {container} = render(<NavBar underline />);
		expect(container.querySelector('.navbar-underline')).toBeTruthy();
	});

	it('renders with pageNav', () => {
		const {container} = render(<NavBar pageNav />);
		expect(container.querySelector('.page-nav')).toBeTruthy();
	});

	it('renders with justifyContent', () => {
		const {container} = render(<NavBar justifyContent="end" />);
		expect(container.querySelector('.justify-content-end')).toBeTruthy();
	});
});
