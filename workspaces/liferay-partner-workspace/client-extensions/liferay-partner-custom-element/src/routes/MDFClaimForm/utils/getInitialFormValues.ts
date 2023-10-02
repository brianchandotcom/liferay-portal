/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import MDFRequestActivityDTO from '../../../common/interfaces/dto/mdfRequestActivityDTO';
import LiferayPicklist from '../../../common/interfaces/liferayPicklist';
import MDFClaim from '../../../common/interfaces/mdfClaim';
import {Status} from '../../../common/utils/constants/status';

const getInitialFormValues = (
	mdfRequestId: number,
	currency: LiferayPicklist,
	activitiesDTO?: MDFRequestActivityDTO[],
	totalMDFRequestAmount?: number,
	mdfClaim?: MDFClaim
): MDFClaim => ({
	...mdfClaim,
	activities: activitiesDTO?.map((activity) => {
		const mdfClaimActivity = mdfClaim?.activities?.find(
			(claimActivity) =>
				claimActivity.r_actToMDFClmActs_c_activityId === activity.id
		);

		if (mdfClaimActivity) {
			return {
				...mdfClaimActivity,
				activityStatus: activity.activityStatus,
				budgets: activity?.actToBgts?.map((budget) => {
					const mdfClaimBudget = mdfClaimActivity.budgets?.find(
						(claimBudget) =>
							claimBudget.r_bgtToMDFClmBgts_c_budgetId ===
							budget.id
					);

					if (mdfClaimBudget) {
						return {
							...mdfClaimBudget,
							r_bgtToMDFClmBgts_c_budgetId: budget.id,
							requestAmount: budget.cost,
						};
					}

					return {
						expenseName: budget.expense.name,
						invoiceAmount: budget.cost,
						r_bgtToMDFClmBgts_c_budgetId: budget.id,
						requestAmount: budget.cost,
						selected: false,
					};
				}),
				claimed: activity.actToMDFClmActs
					?.map((mdfClaimActivity) => {
						return (
							mdfClaimActivity?.r_mdfClmToMDFClmActs_c_mdfClaim
								?.mdfClaimStatus.key !== 'draft' &&
							mdfClaimActivity.selected
						);
					})
					.includes(true),
				name: activity.name,
				r_actToMDFClmActs_c_activityId: activity.id,
				selected:
					mdfClaim?.mdfClaimStatus.key === Status.DRAFT.key
						? false
						: mdfClaimActivity.selected,
			};
		}

		return {
			activityStatus: activity.activityStatus,
			budgets: activity?.actToBgts?.map((budget) => {
				return {
					expenseName: budget.expense.name,
					invoiceAmount: budget.cost,
					r_bgtToMDFClmBgts_c_budgetId: budget.id,
					requestAmount: budget.cost,
					selected: false,
				};
			}),
			claimed: activity.actToMDFClmActs
				?.map((mdfClaimActivity) => {
					return (
						mdfClaimActivity?.r_mdfClmToMDFClmActs_c_mdfClaim
							?.mdfClaimStatus.key !== 'draft' &&
						mdfClaimActivity.selected
					);
				})
				.includes(true),
			currency: activity.currency,
			metrics: '',
			name: activity.name,
			r_actToMDFClmActs_c_activityId: activity.id,
			selected: false,
			totalCost: 0,
			typeActivity: activity.typeActivity,
		};
	}),
	currency: mdfClaim?.currency ? mdfClaim?.currency : currency,
	mdfClaimStatus: mdfClaim?.mdfClaimStatus
		? mdfClaim.mdfClaimStatus
		: Status.PENDING,
	r_mdfReqToMDFClms_c_mdfRequestId: mdfRequestId,
	totalMDFRequestedAmount: mdfClaim?.totalMDFRequestedAmount
		? mdfClaim.totalMDFRequestedAmount
		: totalMDFRequestAmount,
});

export default getInitialFormValues;
