/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {fireEvent, render} from '@testing-library/react';
import React from 'react';

import Thumbs from '../Thumbs';

const items = [
	{
		selected: true,
		svg: 'cerebro_thumb_line_chart',
		text: 'this is a thumb 1',
		value: 'line',
	},
	{
		selected: false,
		svg: 'cerebro_thumb_line_chart',
		text: 'this is a thumb 2',
		value: 'line',
	},
	{
		selected: false,
		svg: 'cerebro_thumb_line_chart',
		text: 'this is a thumb 3',
		value: 'line',
	},
];

const defaultProps = {
	items,
	onSelectThumb: jest.fn(),
};

jest.unmock('react-dom');

describe('AddReport', () => {
	it('renders', () => {
		const {container} = render(<Thumbs {...defaultProps} />);

		expect(container).toMatchSnapshot();
	});

	it('selects the third thumb when clicked', () => {
		const {getByTitle} = render(<Thumbs {...defaultProps} />);

		const listItem = getByTitle('this is a thumb 3');

		fireEvent.click(listItem.firstChild);

		expect(listItem).toHaveClass('selected');
	});
});
