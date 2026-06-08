/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {cleanup, render} from '@testing-library/react';
import React from 'react';

import Legend from '../Legend';

jest.unmock('react-dom');

const data = [
	{
		color: '#FFFFFF',
		name: 'Legend 1',
	},
	{
		color: '#EEEEEE',
		name: 'Legend 2',
	},
	{
		color: '#C9C9C9',
		name: 'Legend 3',
	},
];

describe('Legend', () => {
	afterEach(cleanup);

	it('renders a Legend component', () => {
		const {container} = render(<Legend data={data} />);

		expect(container).toMatchSnapshot();
	});
});
