/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {cleanup, fireEvent, render} from '@testing-library/react';
import React from 'react';

import Alert, {AlertTypes} from '../Alert';

jest.unmock('react-dom');

describe('Alert', () => {
	afterEach(cleanup);

	it('renders', () => {
		const {container} = render(<Alert />);
		expect(container).toMatchSnapshot();
	});

	it('renders as dismissable', () => {
		const {container} = render(<Alert onClose={jest.fn()} />);
		expect(container.querySelector('.close')).toBeTruthy();
	});

	it('renders with close', () => {
		const spy = jest.fn();

		const {container} = render(<Alert onClose={spy} />);

		expect(spy).not.toBeCalled;

		fireEvent.click(container.querySelector('.close'));

		expect(spy).toBeCalled();
	});

	it('renders with stripe', () => {
		const {container} = render(<Alert stripe />);
		expect(container.querySelector('.container')).toBeTruthy();
	});

	it('renders as dismissable', () => {
		const {container} = render(<Alert onClose={jest.fn()} />);
		expect(container.querySelector('.close')).toBeTruthy();
	});

	it('renders with a title', () => {
		const {getByText} = render(<Alert title="hello world" />);
		expect(getByText(/hello world/)).toBeTruthy();
	});

	it('renders with a specific type', () => {
		const {container} = render(<Alert type={AlertTypes.Info} />);
		expect(container.querySelector('.alert-info')).toBeTruthy();
	});

	it('renders with content', () => {
		const {getByText} = render(<Alert>{['hello children']}</Alert>);
		expect(getByText(/hello children/)).toBeTruthy();
	});

	it('renders with multiple config set', () => {
		const {getByText} = render(
			<Alert onClose={jest.fn()} title="this is a title">
				{['hello children']}
			</Alert>
		);

		expect(getByText(/hello children/)).toBeTruthy();
		expect(getByText(/this is a title/)).toBeTruthy();
	});
});
