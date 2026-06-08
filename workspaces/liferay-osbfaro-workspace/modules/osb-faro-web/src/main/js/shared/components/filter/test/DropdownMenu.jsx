/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {render} from '@testing-library/react';
import React from 'react';

import DropdownMenu, {InputItem, OptionItem} from '../DropdownMenu';

jest.unmock('react-dom');

const MOCK_ITEMS = [
	{
		items: [
			{
				category: 'category1',
				checked: true,
				inputType: 'radio',
				label: 'label a',
				value: '1',
			},
			{
				category: 'category1',
				checked: false,
				inputType: 'radio',
				label: 'label b',
				value: '2',
			},
			{
				category: 'category1',
				checked: false,
				inputType: 'radio',
				label: 'label c',
				value: '100',
			},
			{
				category: 'category1',
				checked: false,
				inputType: 'radio',
				label: 'label d',
				value: '50',
			},
		],
		label: 'Location',
		name: 'location',
		value: '100',
	},
	{
		items: [
			{
				category: 'category2',
				checked: true,
				inputType: 'radio',
				items: [
					{
						category: 'category2',
						checked: true,
						inputType: 'radio',
						label: 'label a',
						value: '1',
					},
					{
						category: 'category2',
						checked: false,
						inputType: 'radio',
						label: 'label b',
						value: '2',
					},
					{
						category: 'category2',
						checked: false,
						inputType: 'radio',
						label: 'label c',
						value: '100',
					},
					{
						category: 'category2',
						checked: false,
						inputType: 'radio',
						label: 'label d',
						value: '50',
					},
				],
				label: 'label a',
				value: '1',
			},
			{
				category: 'category2',
				checked: false,
				inputType: 'radio',
				label: 'label b',
				value: '2',
			},
			{
				category: 'category2',
				checked: false,
				inputType: 'radio',
				label: 'label c',
				value: '100',
			},
			{
				category: 'category2',
				checked: false,
				inputType: 'radio',
				label: 'label d',
				value: '50',
			},
		],
		label: 'Devices',
		name: 'devices',
		value: '100',
	},
];

const [INDIVIDUAL_ITEM] = MOCK_ITEMS[0].items;

describe('DropdownMenu', () => {
	it('renders', () => {
		const {container} = render(<DropdownMenu />);

		expect(container).toMatchSnapshot();
	});

	it('renders w/ search', () => {
		const {container} = render(<DropdownMenu hasSearch />);

		expect(
			container.querySelector('.analytics-dropdown-menu-search-container')
		).toBeTruthy();
	});

	it('renders w/ items', () => {
		const {getByText} = render(<DropdownMenu items={MOCK_ITEMS} />);

		MOCK_ITEMS.forEach(({label}) => {
			expect(getByText(label)).toBeTruthy();
		});
	});
});

describe('InputItem', () => {
	it('renders', () => {
		const {container} = render(<InputItem item={INDIVIDUAL_ITEM} />);

		expect(container).toMatchSnapshot();
	});
});

describe('OptionItem', () => {
	it('renders', () => {
		const {container} = render(<OptionItem item={INDIVIDUAL_ITEM} />);

		expect(container).toMatchSnapshot();
	});
});
