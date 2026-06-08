/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {cleanup, render} from '@testing-library/react';
import React from 'react';

import PopoverBase from '../PopoverBase';

jest.unmock('react-dom');

describe('PopoverBase', () => {
	afterEach(cleanup);

	it('renders', () => {
		const {container} = render(<PopoverBase placement="bottom" visible />);

		expect(container).toMatchSnapshot();
	});

	it('renders hidden', () => {
		const {container} = render(<PopoverBase visible={false} />);

		expect(container.querySelector('.hide')).toBeTruthy();
	});
});

describe('PopoverBase.Header', () => {
	afterEach(cleanup);

	it('renders', () => {
		const {container} = render(
			<PopoverBase.Header>Header</PopoverBase.Header>
		);

		expect(container).toMatchSnapshot();
	});
});

describe('PopoverBase.Body', () => {
	afterEach(cleanup);

	it('renders', () => {
		const {container} = render(<PopoverBase.Body>Body</PopoverBase.Body>);

		expect(container).toMatchSnapshot();
	});
});

describe('PopoverBase.Footer', () => {
	afterEach(cleanup);

	it('renders', () => {
		const {container} = render(
			<PopoverBase.Footer>Footer</PopoverBase.Footer>
		);

		expect(container).toMatchSnapshot();
	});
});
