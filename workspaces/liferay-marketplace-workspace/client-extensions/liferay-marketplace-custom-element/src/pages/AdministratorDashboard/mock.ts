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
