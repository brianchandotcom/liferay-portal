/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import formatCurrency from './formatCurrency';

export default function getRevenueChartColumns(
	revenueCurrency: any,
	growthRevenueResponseNewProjectData: any,
	growthRevenueResponseNewBusinessData: any,
	renewalRevenueData: any,
	setTitleChart: any,
	setValueChart: any,
	setColumnsRevenueChart: any
) {
	const chartColumns = [];
	const growthRevenueData = [
		...growthRevenueResponseNewProjectData.items,
		...growthRevenueResponseNewBusinessData.items,
	];

	const totalGrowthRevenue = growthRevenueData?.reduce(
		(accumulator: number, currentValue: any) => {
			return accumulator + currentValue.growthArr;
		},
		0
	);

	chartColumns.push(['Growth Revenue', totalGrowthRevenue]);

	const totalRenewalRevenue = renewalRevenueData?.items?.reduce(
		(accumulator: number, currentValue: any) => {
			return accumulator + currentValue.renewalArr;
		},
		0
	);

	chartColumns.push(['Renewal Revenue', totalRenewalRevenue]);

	const totalRevenueAmount = totalGrowthRevenue + totalRenewalRevenue;

	setValueChart(
		formatCurrency(totalRevenueAmount, revenueCurrency, 'lessPrecision')
	);
	setTitleChart(`Total Revenue `);
	setColumnsRevenueChart(chartColumns);
}
