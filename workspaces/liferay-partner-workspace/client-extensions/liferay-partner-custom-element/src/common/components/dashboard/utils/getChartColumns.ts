/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

export default function getChartColumns(
	displayCurrency: any,
	mdfRequests: any,
	setColumnsMDFChart: any,
	setTitleChart: any,
	setValueChart: any
) {
	const chartColumns: any[] = [];

	const totalMDFActivitiesAmount = totalMDFRequested(
		displayCurrency,
		mdfRequests,
		chartColumns
	);

	totalMDFApprovedRequests(displayCurrency, mdfRequests, chartColumns);

	totalRequestedMDFToClaims(displayCurrency, mdfRequests, chartColumns);

	totalApprovedMDFToClaims(displayCurrency, mdfRequests, chartColumns);

	totalPaidMDFToClaims(displayCurrency, mdfRequests, chartColumns);

	expiringSoonTotalActivities(displayCurrency, mdfRequests, chartColumns);

	expiredTotalActivites(displayCurrency, mdfRequests, chartColumns);
	setValueChart(totalMDFActivitiesAmount);
	setTitleChart('Total MDF ');
	setColumnsMDFChart(chartColumns);
}

const expiredDate = 30;

function expiredTotalActivites(
	displayCurrency: any,
	mdfRequests: any,
	chartColumns: any
) {
	const expiredActivities = mdfRequests?.items
		?.map((activity: any) =>
			activity?.mdfReqToActs?.filter(
				(activity: any) => activity.activityStatus.key === 'expired'
			)
		)
		.flat();
	const totalExpiredActivities = expiredActivities?.reduce(
		(acc: any, value: any) =>
			acc +
			parseFloat(
				displayCurrency === 'USD'
					? value.convertedMDFRequestAmount
					: value.mdfRequestAmount
			),
		0
	);

	const numberOfExpiredActivities = expiredActivities.length;

	chartColumns.push([
		'Expired',
		totalExpiredActivities,
		numberOfExpiredActivities,
	]);
}

function expiringSoonTotalActivities(
	displayCurrency: any,
	mdfRequests: any,
	chartColumns: any
) {
	const expiringSoonActivitiesDate = mdfRequests?.items
		?.map((activity: any) =>
			activity.mdfReqToActs.filter(
				(activity: any) =>
					new Date(activity.endDate).setTime(expiredDate) <
						new Date().getTime() &&
					activity.activityStatus.key !== 'expired'
			)
		)
		.flat();

	const totalExpiringSoonActivites = expiringSoonActivitiesDate?.reduce(
		(acc: any, value: any) =>
			acc +
			parseFloat(
				displayCurrency === 'USD'
					? value.convertedMDFRequestAmount
					: value.mdfRequestAmount
			),
		0
	);

	const numberOfExpiringSoonActivites = expiringSoonActivitiesDate.length;

	chartColumns.push([
		'Expiring Soon',
		totalExpiringSoonActivites,
		numberOfExpiringSoonActivites,
	]);
}

function totalRequestedMDFToClaims(
	displayCurrency: any,
	mdfRequests: any,
	chartColumns: any
) {
	const claimesRequested = mdfRequests?.items
		?.map((claim: any) =>
			claim.mdfReqToMDFClms.filter(
				(request: any) =>
					request.mdfClaimStatus.key === 'inDirectorReview' ||
					request.mdfClaimStatus.key === 'pendingMarketingReview' ||
					request.mdfClaimStatus.key === 'approved'
			)
		)
		.flat();

	const totalClaimsRequestedAmount = claimesRequested?.reduce(
		(acc: any, value: any) =>
			acc +
				(displayCurrency === 'USD'
					? value?.convertedTotalClaimAmount
					: value?.totalClaimAmount) || 0,
		0
	);

	const totalMDFClaimActivitiesCount = claimesRequested.reduce(
		(acc: any, value: any) =>
			acc + parseFloat(value.mdfClaimActivitiesCount),
		0
	);

	chartColumns.push([
		'Claim Requested',
		totalClaimsRequestedAmount,
		totalMDFClaimActivitiesCount,
	]);
}

function totalApprovedMDFToClaims(
	displayCurrency: any,
	mdfRequests: any,
	chartColumns: any
) {
	const claimsApproved = mdfRequests?.items
		?.map((claim: any) =>
			claim.mdfReqToMDFClms.filter(
				(request: any) =>
					request.mdfClaimStatus.key === 'inFinanceReview'
			)
		)
		.flat();

	const totalClaimesApprovedAmount = claimsApproved?.reduce(
		(acc: any, value: any) =>
			acc +
				(displayCurrency === 'USD'
					? value?.convertedTotalClaimAmount
					: value?.totalClaimAmount) || 0,
		0
	);

	const totalMDFClaimActivitiesCount = claimsApproved.reduce(
		(acc: any, value: any) =>
			acc + parseFloat(value.mdfClaimActivitiesCount),
		0
	);

	chartColumns.push([
		'Claim Approved',
		totalClaimesApprovedAmount,
		totalMDFClaimActivitiesCount,
	]);
}

function totalPaidMDFToClaims(
	displayCurrency: any,
	mdfRequests: any,
	chartColumns: any
) {
	const claimsPaid = mdfRequests?.items
		?.map((claim: any) =>
			claim.mdfReqToMDFClms.filter(
				(request: any) => request.mdfClaimStatus.key === 'claimPaid'
			)
		)
		.flat();

	const totalClaimsPaidAmount = claimsPaid?.reduce(
		(acc: any, value: any) =>
			acc +
			parseFloat(
				(displayCurrency === 'USD'
					? value.convertedClaimPaid
					: value.claimPaid) || 0
			),
		0
	);

	const totalMDFClaimActivitiesCount = claimsPaid.reduce(
		(acc: any, value: any) =>
			acc + parseFloat(value.mdfClaimActivitiesCount),
		0
	);

	chartColumns.push([
		'Claim Paid',
		totalClaimsPaidAmount,
		totalMDFClaimActivitiesCount,
	]);
}

function totalMDFRequested(
	displayCurrency: any,
	mdfRequests: any,
	chartColumns: any
) {
	const totalMDFActivitiesAmount = mdfRequests?.items?.reduce(
		(prevValue: any, currValue: any) =>
			prevValue +
			(parseFloat(
				displayCurrency === 'USD'
					? currValue.convertedTotalMDFRequestAmount
					: currValue.totalMDFRequestAmount
			) || 0),
		0
	);

	const totalMDFActivitiesCount = mdfRequests?.items?.reduce(
		(acc: any, value: any) => acc + parseFloat(value.mdfActivitiesCount),
		0
	);

	chartColumns.push([
		'Requested',
		totalMDFActivitiesAmount,
		totalMDFActivitiesCount,
	]);

	return totalMDFActivitiesAmount;
}

function totalMDFApprovedRequests(
	displayCurrency: any,
	mdfRequests: any,
	chartColumns: any
) {
	const mdfApprovedRequests = mdfRequests?.items?.filter(
		(request: any) => request.mdfRequestStatus.key === 'approved'
	);
	const totalMDFApprovedRequestsAmount = mdfApprovedRequests?.reduce(
		(acc: any, value: any) =>
			acc +
			parseFloat(
				displayCurrency === 'USD'
					? value.convertedTotalMDFRequestAmount
					: value.totalMDFRequestAmount
			),
		0
	);

	const totalMDFActivitiesCount = mdfApprovedRequests?.reduce(
		(acc: any, value: any) => acc + parseFloat(value.mdfActivitiesCount),
		0
	);

	chartColumns.push([
		'Approved',
		totalMDFApprovedRequestsAmount,
		totalMDFActivitiesCount,
	]);
}
