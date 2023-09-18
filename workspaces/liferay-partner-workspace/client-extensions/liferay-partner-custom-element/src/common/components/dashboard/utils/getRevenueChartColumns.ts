/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

export default function getRevenueChartColumns(
	revenueCurrency: any,
	revenueData: any,
	setTitleChart: any,
	setValueChart: any,
	setColumnsRevenueChart: any
) {
	const STAGE_CLOSEDLOST = 'Closed Lost';
	const STAGE_DISQUALIFIED = 'Disqualified';
	const STAGE_ROLLED_INTO_ANOTHER_OPPORTUNITY =
		'Rolled into another opportunity';
	const STAGE_CLOSEDWON = 'Closed Won';
	const STAGE_REJECTED = 'Rejected';
	const NEW_BUSINESS = 'New Business';
	const NEW_PROJECT_EXISTING_BUSINESS = 'New Project Existing Business';

	const chartColumns = [];

	const totalGrowthRevenue = revenueData?.items?.reduce(
		(accumulator: number, currentValue: any) => {
			if (
				currentValue.stage === STAGE_CLOSEDWON &&
				(currentValue.type === NEW_BUSINESS ||
					currentValue.type === NEW_PROJECT_EXISTING_BUSINESS)
			) {
				return accumulator + currentValue.growthArr;
			}

			return accumulator;
		},
		0
	);

	chartColumns.push(['Growth Revenue', totalGrowthRevenue]);

	const totalRenewalRevenue = revenueData?.items?.reduce(
		(accumulator: number, currentValue: any) => {
			if (
				currentValue.type !== NEW_BUSINESS &&
				currentValue.type !== NEW_PROJECT_EXISTING_BUSINESS &&
				currentValue.stage !== STAGE_REJECTED &&
				currentValue.stage !== STAGE_ROLLED_INTO_ANOTHER_OPPORTUNITY &&
				currentValue.stage !== STAGE_DISQUALIFIED &&
				currentValue.stage !== STAGE_CLOSEDLOST
			) {
				return accumulator + currentValue.renewalArr;
			}

			return accumulator;
		},
		0
	);

	chartColumns.push(['Renewal Revenue', totalRenewalRevenue]);

	const totalRevenueAmount = totalGrowthRevenue + totalRenewalRevenue;

	setValueChart(totalRevenueAmount);
	setTitleChart(` Total Revenue`);
	setColumnsRevenueChart(chartColumns);
}
