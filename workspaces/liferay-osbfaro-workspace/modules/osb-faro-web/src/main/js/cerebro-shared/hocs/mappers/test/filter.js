/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {getFiltersMapper} from '../filter';

const data = {
	data: {
		forms: {
			submissions: {
				device: [
					{
						metrics: [
							{
								value: 2065.0,
								valueKey: 'Windows',
							},
							{
								value: 995.0,
								valueKey: 'macOS',
							},
							{
								value: 136.0,
								valueKey: 'Linux',
							},
							{
								value: 107.0,
								valueKey: 'Mac OS X',
							},
							{
								value: 53.0,
								valueKey: 'Ubuntu',
							},
							{
								value: 3.0,
								valueKey: 'ChromeOS',
							},
							{
								value: 2.0,
								valueKey: 'Fedora',
							},
							{
								value: 1.0,
								valueKey: 'Unknown',
							},
						],
						value: 3362.0,
						valueKey: 'Desktop',
					},
					{
						metrics: [
							{
								value: 151.0,
								valueKey: 'Android',
							},
							{
								value: 61.0,
								valueKey: 'iOS',
							},
							{
								value: 9.0,
								valueKey: 'Unknown',
							},
							{
								value: 8.0,
								valueKey: 'FreeBSD',
							},
							{
								value: 1.0,
								valueKey: 'Windows Phone',
							},
						],
						value: 230.0,
						valueKey: 'SmartPhone',
					},
					{
						metrics: [
							{
								value: 67.0,
								valueKey: 'iOS',
							},
							{
								value: 12.0,
								valueKey: 'Android',
							},
						],
						value: 79.0,
						valueKey: 'Tablet',
					},
				],
				geolocation: [
					{
						metrics: [
							{
								value: 100,
								valueKey: 'Unknown',
							},
							{
								value: 100,
								valueKey: 'Pernambuco',
							},
							{
								value: 100,
								valueKey: 'Sao Paulo',
							},
							{
								value: 100,
								valueKey: 'Parana',
							},
							{
								value: 100,
								valueKey: 'Rio Grande do Sul',
							},
						],
						value: 100,
						valueKey: 'Brazil',
					},
					{
						metrics: [
							{
								value: 100,
								valueKey: 'Unknown',
							},
							{
								value: 100,
								valueKey: 'Catalonia',
							},
							{
								value: 100,
								valueKey: 'Madrid',
							},
							{
								value: 100,
								valueKey: 'Andalucia',
							},
							{
								value: 100,
								valueKey: 'Castilla y Leon',
							},
							{
								value: 100,
								valueKey: 'Others',
							},
						],
						value: 100,
						valueKey: 'Spain',
					},
					{
						metrics: [
							{
								value: 100,
								valueKey: 'California',
							},
							{
								value: 100,
								valueKey: 'Unknown',
							},
							{
								value: 100,
								valueKey: 'Georgia',
							},
							{
								value: 100,
								valueKey: 'New Jersey',
							},
							{
								value: 100,
								valueKey: 'Florida',
							},
							{
								value: 100,
								valueKey: 'Others',
							},
						],
						value: 100,
						valueKey: 'United States',
					},
				],
			},
		},
	},
};

describe('Shared HOCs Mappers - Filter', () => {
	it('maps filter information', () => {
		const mapper = getFiltersMapper((result) => result.forms.submissions);

		const result = mapper.props(data);

		expect(result).toMatchObject({
			error: null,
			items: [
				{
					hasSearch: false,
					items: [
						{
							category: 'devices',
							checked: false,
							inputType: 'radio',
							label: 'Desktop',
							value: '8',
						},
						{
							category: 'devices',
							checked: false,
							inputType: 'radio',
							label: 'SmartPhone',
							value: '5',
						},
						{
							category: 'devices',
							checked: false,
							inputType: 'radio',
							label: 'Tablet',
							value: '2',
						},
					],
					label: 'Devices',
					name: 'Devices',
					value: '3',
				},
				{
					hasSearch: false,
					items: [
						{
							category: 'location',
							checked: false,
							inputType: 'radio',
							label: 'Brazil',
							value: '5',
						},
						{
							category: 'location',
							checked: false,
							inputType: 'radio',
							label: 'Spain',
							value: '6',
						},
						{
							category: 'location',
							checked: false,
							inputType: 'radio',
							label: 'United States',
							value: '6',
						},
					],
					label: 'Location',
					name: 'Location',
					value: '3',
				},
			],
			loading: false,
		});
	});
});
