/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import Filter from '..';
import {fireEvent, render} from '@testing-library/react';
import {noop} from 'lodash';
import React from 'react';

jest.unmock('react-dom');

const MOCK_ITEMS = [
	{
		hasSearch: true,
		items: [
			{
				category: 'Location',
				checked: false,
				inputType: 'radio',
				label: 'Albania',
				value: '2',
			},
			{
				category: 'Location',
				checked: false,
				inputType: 'radio',
				label: 'Brazil',
				value: '2',
			},
			{
				category: 'Location',
				checked: false,
				inputType: 'radio',
				label: 'Jamaica',
				value: '2',
			},
			{
				category: 'Location',
				checked: false,
				inputType: 'radio',
				label: 'United States',
				value: '2',
			},
			{
				category: 'Location',
				checked: false,
				inputType: 'radio',
				label: 'Portugual',
				value: '2',
			},
		],
		label: 'Location',
		name: 'location',
		value: '156',
	},
	{
		hasSearch: false,
		items: [
			{
				category: 'Devices',
				checked: false,
				inputType: 'radio',
				label: 'Desktop',
				value: '9',
			},
			{
				category: 'Devices',
				checked: false,
				inputType: 'radio',
				label: 'EReader',
				value: '9',
			},
			{
				category: 'Devices',
				checked: false,
				inputType: 'radio',
				label: 'Mobile',
				value: '9',
			},
			{
				category: 'Devices',
				checked: false,
				inputType: 'radio',
				label: 'SmartPhone',
				value: '9',
			},
			{
				category: 'Devices',
				checked: false,
				inputType: 'radio',
				label: 'Tablet',
				value: '9',
			},
		],
		label: 'Devices',
		name: 'devices',
	},
];

describe('Filter', () => {
	it('renders', () => {
		const {container} = render(<Filter items={MOCK_ITEMS} />);

		expect(container).toMatchSnapshot();
	});

	it('calls onClick on handleClickApplyFilter', () => {
		const spy = jest.fn();

		const {getByText} = render(
			<Filter items={MOCK_ITEMS} onChange={spy} />
		);

		fireEvent.click(getByText('Albania'));

		expect(spy).toBeCalled();
	});

	it('renders de Clear Filter Button', () => {
		const {getByText} = render(
			<Filter items={MOCK_ITEMS} onChange={noop} />
		);

		fireEvent.click(getByText('Albania'));
		fireEvent.click(getByText('Phone'));

		expect(getByText('Clear Filter')).toBeTruthy();
	});
});
