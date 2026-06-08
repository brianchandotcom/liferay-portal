/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {fireEvent, render} from '@testing-library/react';
import React from 'react';
import Form from '~/shared/components/form';
import {mockForm} from '~/test/data';

import PasswordInput from '../PasswordInput';

jest.unmock('react-dom');

const TestComponent = () => (
	<Form>
		<PasswordInput field={{name: 'foo'}} form={mockForm()} />
	</Form>
);

describe('PasswordInput', () => {
	it('renders', () => {
		const {container} = render(<TestComponent />);

		expect(container).toMatchSnapshot();
	});

	it('renders with an input type of "password" if showPassword is false', () => {
		const {container} = render(<TestComponent />);
		const input = container.querySelector('#foo');

		expect(input).toHaveAttribute('type', 'password');
	});

	it('renders with an input type of "text" if showPassword is true', () => {
		const {container, getByRole} = render(<TestComponent />);
		const input = container.querySelector('#foo');

		expect(input).toHaveAttribute('type', 'password');

		fireEvent.click(getByRole('button'));
		jest.runAllTimers();

		expect(input).toHaveAttribute('type', 'text');
		expect(container).toMatchSnapshot();
	});
});
