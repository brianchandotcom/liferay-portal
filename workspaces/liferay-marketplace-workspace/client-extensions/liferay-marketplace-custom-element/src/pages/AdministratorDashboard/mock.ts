/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
export const colors = {
	color1: '#2057e2',
	color2: '#46e27d',
	color3: '#ff9000',
	color4: '#f16cad',
};

export const infoCard = [
	{
		growth: 25,
		growthContext: '+10k this week',
		symbol: 'dollar-symbol',
		title: 'Income',
		value: '$58,980.00',
	},
	{
		growth: 68,
		growthContext: '+36k this week',
		symbol: 'thumbs-up-arrow',
		title: 'Conversion Rate',
		value: '249.194.46',
	},
];

export const barChart = {
	colors: {
		data1: colors.color1,
		data2: colors.color2,
		data3: colors.color3,
		data4: colors.color4,
	},
	columns: [
		['data1', 100, 20, 30],
		['data2', 20, 70, 100],
		['data3', 15, 12, 45],
		['data4', 23, 74, 90],
	],
	type: 'bar',
};

export const pieChart = {
	colors: {
		data1: colors.color1,
		data2: colors.color2,
		data3: colors.color3,
		data4: colors.color4,
	},
	columns: [
		['data1', 100, 20, 30, 80],
		['data2', 20, 70, 100, 89],
		['data3', 10, 28, 60, 10],
		['data4', 8, 70, 14, 33],
	],
	type: 'donut',
};

export const lineChart = {
	colors: {
		product1: colors.color1,
		product2: colors.color2,
		product3: colors.color3,
		product4: colors.color4,
	},
	columns: [
		[
			'x',
			'2018-01-01',
			'2018-02-01',
			'2018-03-01',
			'2018-04-01',
			'2018-05-01',
			'2018-06-01',
		],
		['product1', 130, 340, 200],
		['product2', 210, 30, 180],
		['product3', 340, 200, 130],
		['product4', 100, 180, 30],
	],
	type: 'predictive',
	types: {
		product1: 'area-line-range',
		product2: 'area-spline-range',
		product3: 'area-spline-range',
		product4: 'area-line-range',
	},
	x: 'x',
};
