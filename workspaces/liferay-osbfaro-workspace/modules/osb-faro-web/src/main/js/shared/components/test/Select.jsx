/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {cleanup, render} from '@testing-library/react';
import React from 'react';

import Select from '../Select';

jest.unmock('react-dom');

describe('Select', () => {
	afterEach(cleanup);

	it('renders', () => {
		const {container} = render(<Select />);
		expect(container).toMatchSnapshot();
	});

	it('renders with blank option', () => {
		const {container} = render(<Select showBlankOption />);
		expect(container).toMatchSnapshot();
	});

	it('renders with children', () => {
		const children = [
			<Select.Item key={1}>1</Select.Item>,
			<Select.Item key={2}>2</Select.Item>,
			<Select.Item key={3}>3</Select.Item>,
		];

		const {queryByText} = render(<Select>{children}</Select>);

		expect(queryByText('1')).toBeTruthy();
		expect(queryByText('2')).toBeTruthy();
		expect(queryByText('3')).toBeTruthy();
	});
});
