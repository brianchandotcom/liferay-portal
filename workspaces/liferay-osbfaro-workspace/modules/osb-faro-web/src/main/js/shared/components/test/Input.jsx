/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {cleanup, render} from '@testing-library/react';
import React from 'react';

import Input from '../Input';

jest.unmock('react-dom');

describe('Input', () => {
	afterEach(cleanup);

	it('renders input', () => {
		const {container} = render(<Input />);
		expect(container).toMatchSnapshot();
	});

	it('renders input with a different size', () => {
		const {container} = render(<Input size="lg" />);
		expect(container.querySelector('.form-control-lg')).toBeTruthy();
	});

	it('renders input group', () => {
		const {container} = render(<Input.Group />);
		expect(container).toMatchSnapshot();
	});

	it('renders input group with different size', () => {
		const {container} = render(<Input.Group size="lg" />);
		expect(container.querySelector('.input-group-lg')).toBeTruthy();
	});

	it('renders input group with inset', () => {
		const {container} = render(<Input.Group inset />);
		expect(container).toMatchSnapshot();
	});

	it('renders input group item', () => {
		const {container} = render(<Input.GroupItem />);
		expect(container).toMatchSnapshot();
	});

	it('renders input inset', () => {
		const {container} = render(<Input.Inset />);
		expect(container).toMatchSnapshot();
	});

	it('renders input button', () => {
		const {container} = render(
			<Input.Button displayType="secondary" position="append" />
		);
		expect(container).toMatchSnapshot();
	});

	it('renders input text', () => {
		const {container} = render(<Input.Text />);
		expect(container).toMatchSnapshot();
	});
});
