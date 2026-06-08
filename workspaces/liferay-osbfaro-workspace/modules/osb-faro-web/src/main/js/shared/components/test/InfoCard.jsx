/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {cleanup, render} from '@testing-library/react';
import React from 'react';

import InfoCard from '../InfoCard';

jest.unmock('react-dom');

const mockItems = [
	{name: 'name', value: 'Test Test'},
	{name: 'description', value: 'Test Test Description'},
	{
		name: 'shippingAddress',
		value: '123 DB Lane Diamond Bar, CA 91765 USA',
	},
];

describe('InfoCard', () => {
	afterEach(cleanup);

	it('renders', () => {
		const {container} = render(<InfoCard header="foo" items={mockItems} />);
		expect(container).toMatchSnapshot();
	});

	it('renders with an image', () => {
		const {container} = render(
			<InfoCard header="foo" image="image-foo.com" items={mockItems} />
		);
		expect(container.querySelector('.image-container')).toBeTruthy();
	});
});
