/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

export default function getRevenueChartColumns(
	revenueCurrency: any,
	closedWonOpportunities: any,
	setTitleChart: any,
	setValueChart: any,
	setColumnsRevenueChart: any
) {
	const chartColumns = [];

	const totalGrowthRevenue = closedWonOpportunities.items?.reduce(
		(accumulator: number, currentValue: any) => {
			return (
				(Number(accumulator) || 0) +
				(Number(currentValue.growthArr) || 0)
			);
		},
		0
	);

	chartColumns.push(['Growth Revenue', totalGrowthRevenue]);

	const totalRenewalRevenue = closedWonOpportunities?.items?.reduce(
		(accumulator: number, currentValue: any) => {
			return (
				(Number(accumulator) || 0) +
				(Number(currentValue.renewalArr) || 0)
			);
		},
		0
	);

	chartColumns.push(['Renewal Revenue', totalRenewalRevenue]);

	const totalRevenueAmount = closedWonOpportunities.items?.reduce(
		(accumulator: number, currentValue: any) => {
			return (
				(Number(accumulator) || 0) +
				(Number(currentValue.subscriptionArr) || 0)
			);
		},
		0
	);

	setValueChart(totalRevenueAmount);
	setTitleChart(`Total Revenue `);
	setColumnsRevenueChart(chartColumns);
}
