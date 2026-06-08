/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {render} from '@testing-library/react';
import React from 'react';

import Item from '../Item';

jest.unmock('react-dom');

describe('Item', () => {
	it('renders', () => {
		const {container} = render(<Item />);

		expect(container).toMatchSnapshot();
	});

	it('renders with children', () => {
		const childrenContent = 'Children';

		const {queryByText} = render(<Item>{childrenContent}</Item>);

		expect(queryByText(childrenContent)).toBeTruthy();
	});

	it('renders a header item', () => {
		const {container} = render(<Item header />);

		expect(container.querySelector('.list-group-header')).toBeTruthy();
		expect(container.querySelector('.list-group-item')).toBeFalsy();
	});

	it('renders as disabled', () => {
		const {container} = render(<Item disabled />);

		expect(
			container.querySelector('.list-group-item-disabled')
		).toBeTruthy();
	});

	it('renders as active', () => {
		const {container} = render(<Item active />);

		expect(container.querySelector('.active')).toBeTruthy();
	});
});
