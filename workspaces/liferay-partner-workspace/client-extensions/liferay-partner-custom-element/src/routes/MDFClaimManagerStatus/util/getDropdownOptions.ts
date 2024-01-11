/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {DropdownOption} from '../../../common/components/Dropdown/Dropdown';
import {PermissionActionType} from '../../../common/enums/permissionActionType';
import LiferayPicklist from '../../../common/interfaces/liferayPicklist';
import {Status} from '../../../common/utils/constants/status';

export default function getDropdownOptions(
	actions: PermissionActionType[],
	mdfClaimStatus: LiferayPicklist,
	updateClaimStatus: (status: LiferayPicklist) => Promise<void>
) {
	return actions?.reduce<DropdownOption[]>((previousValue, currentValue) => {
		if (mdfClaimStatus?.key === Status.PENDING.key) {
			if (currentValue === PermissionActionType.REQUEST_MORE_INFO) {
				previousValue.push({
					key: Status.REQUEST_MORE_INFO.key,
					label: Status.REQUEST_MORE_INFO.name,
					onClick: () => {
						updateClaimStatus(Status.REQUEST_MORE_INFO);
					},
				});
			}

			if (currentValue === PermissionActionType.REJECT) {
				previousValue.push({
					key: Status.REJECT.key,
					label: Status.REJECT.name,
					onClick: () => {
						updateClaimStatus(Status.REJECT);
					},
				});
			}

			if (
				currentValue === PermissionActionType.IN_FINANCE_REVIEW_STATUS
			) {
				previousValue.push({
					key: Status.IN_FINANCE_REVIEW.key,
					label: Status.IN_FINANCE_REVIEW.name,
					onClick: () => {
						updateClaimStatus(Status.IN_FINANCE_REVIEW);
					},
				});
			}
		}

		if (mdfClaimStatus?.key === Status.APPROVED.key) {
			if (
				currentValue === PermissionActionType.IN_FINANCE_REVIEW_STATUS
			) {
				previousValue.push({
					key: Status.IN_FINANCE_REVIEW.key,
					label: Status.IN_FINANCE_REVIEW.name,
					onClick: () => {
						updateClaimStatus(Status.IN_FINANCE_REVIEW);
					},
				});
			}
		}

		if (mdfClaimStatus?.key === Status.IN_FINANCE_REVIEW.key) {
			if (currentValue === PermissionActionType.CLAIM_PAID_STATUS) {
				previousValue.push({
					key: Status.CLAIM_PAID.key,
					label: Status.CLAIM_PAID.name,
					onClick: () => {
						updateClaimStatus(Status.CLAIM_PAID);
					},
				});
			}

			if (currentValue === PermissionActionType.REQUEST_MORE_INFO) {
				previousValue.push({
					key: Status.REQUEST_MORE_INFO.key,
					label: Status.REQUEST_MORE_INFO.name,
					onClick: () => {
						updateClaimStatus(Status.REQUEST_MORE_INFO);
					},
				});
			}

			if (currentValue === PermissionActionType.REJECT) {
				previousValue.push({
					key: Status.REJECT.key,
					label: Status.REJECT.name,
					onClick: () => {
						updateClaimStatus(Status.REJECT);
					},
				});
			}

			if (
				currentValue === PermissionActionType.IN_DIRECTOR_REVIEW_STATUS
			) {
				previousValue.push({
					key: Status.IN_DIRECTOR_REVIEW.key,
					label: Status.IN_DIRECTOR_REVIEW.name,
					onClick: () => {
						updateClaimStatus(Status.IN_DIRECTOR_REVIEW);
					},
				});
			}
		}

		if (mdfClaimStatus?.key === Status.REQUEST_MORE_INFO.key) {
			if (
				currentValue ===
				PermissionActionType.MARKETING_REVIEW_STATUS_UPDATE
			) {
				previousValue.push({
					key: Status.PENDING.key,
					label: Status.PENDING.name,
					onClick: () => {
						updateClaimStatus(Status.PENDING);
					},
				});
			}

			if (
				currentValue === PermissionActionType.IN_FINANCE_REVIEW_STATUS
			) {
				previousValue.push({
					key: Status.IN_FINANCE_REVIEW.key,
					label: Status.IN_FINANCE_REVIEW.name,
					onClick: () => {
						updateClaimStatus(Status.IN_FINANCE_REVIEW);
					},
				});
			}

			if (currentValue === PermissionActionType.REJECT) {
				previousValue.push({
					key: Status.REJECT.key,
					label: Status.REJECT.name,
					onClick: () => {
						updateClaimStatus(Status.REJECT);
					},
				});
			}
		}

		if (mdfClaimStatus?.key === Status.REJECT.key) {
			if (
				currentValue ===
				PermissionActionType.MARKETING_REVIEW_STATUS_UPDATE
			) {
				previousValue.push({
					key: Status.PENDING.key,
					label: Status.PENDING.name,
					onClick: () => {
						updateClaimStatus(Status.PENDING);
					},
				});
			}
		}

		if (mdfClaimStatus?.key === Status.IN_DIRECTOR_REVIEW.key) {
			if (
				currentValue === PermissionActionType.IN_FINANCE_REVIEW_STATUS
			) {
				previousValue.push({
					key: Status.IN_FINANCE_REVIEW.key,
					label: Status.IN_FINANCE_REVIEW.name,
					onClick: () => {
						updateClaimStatus(Status.IN_FINANCE_REVIEW);
					},
				});
			}

			if (currentValue === PermissionActionType.REJECT) {
				previousValue.push({
					key: Status.REJECT.key,
					label: Status.REJECT.name,
					onClick: () => {
						updateClaimStatus(Status.REJECT);
					},
				});
			}

			if (currentValue === PermissionActionType.REQUEST_MORE_INFO) {
				previousValue.push({
					key: Status.REQUEST_MORE_INFO.key,
					label: Status.REQUEST_MORE_INFO.name,
					onClick: () => {
						updateClaimStatus(Status.REQUEST_MORE_INFO);
					},
				});
			}
		}

		if (mdfClaimStatus?.key === Status.CANCELED.key) {
			if (currentValue === PermissionActionType.APPROVE) {
				previousValue.push({
					key: Status.APPROVED.key,
					label: Status.APPROVED.name,
					onClick: () => {
						updateClaimStatus(Status.APPROVED);
					},
				});
			}
		}

		return previousValue;
	}, []);
}
