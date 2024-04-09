/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {DropdownOption} from '../../../common/components/Dropdown/Dropdown';
import {PermissionActionType} from '../../../common/enums/permissionActionType';
import LiferayPicklist from '../../../common/interfaces/liferayPicklist';
import {Liferay} from '../../../common/services/liferay';
import {Status} from '../../../common/utils/constants/status';

export default function getDropdownOptions(
	actions: PermissionActionType[],
	mdfRequestStatus: LiferayPicklist,
	updateRequestStatus: (status: LiferayPicklist) => Promise<void>
) {
	const callConfirmActionMDFRequestModal = (
		action: string,
		status: LiferayPicklist
	) =>
		Liferay.Util.openConfirmModal({
			message: `Are you sure you want to ${action} this MDF request?`,
			onConfirm: (isConfirmed: boolean) => {
				if (isConfirmed) {
					updateRequestStatus(status);
				}
			},
		});

	return actions?.reduce<DropdownOption[]>((previousValue, currentValue) => {
		if (mdfRequestStatus?.key === Status.PENDING.key) {
			if (
				currentValue ===
				PermissionActionType.MARKETING_DIRECTOR_REVIEW_STATUS_UPDATE
			) {
				previousValue.push({
					key: Status.MARKETING_DIRECTOR_REVIEW.key,
					label: Status.MARKETING_DIRECTOR_REVIEW.name,
					onClick: () => {
						updateRequestStatus(Status.MARKETING_DIRECTOR_REVIEW);
					},
				});
			}

			if (currentValue === PermissionActionType.REQUEST_MORE_INFO) {
				previousValue.push({
					key: Status.REQUEST_MORE_INFO.key,
					label: Status.REQUEST_MORE_INFO.name,
					onClick: () => {
						updateRequestStatus(Status.REQUEST_MORE_INFO);
					},
				});
			}

			if (currentValue === PermissionActionType.REJECT) {
				previousValue.push({
					key: Status.REJECT.key,
					label: Status.REJECT.name,
					onClick: () => {
						updateRequestStatus(Status.REJECT);
					},
				});
			}
			if (currentValue === PermissionActionType.APPROVE) {
				previousValue.push({
					key: Status.APPROVED.key,
					label: Status.APPROVED.name,
					onClick: () => {
						updateRequestStatus(Status.APPROVED);
					},
				});
			}
		}

		if (mdfRequestStatus?.key === Status.MARKETING_DIRECTOR_REVIEW.key) {
			if (
				currentValue ===
				PermissionActionType.MARKETING_REVIEW_STATUS_UPDATE
			) {
				previousValue.push({
					key: Status.PENDING.key,
					label: Status.PENDING.name,
					onClick: () => {
						updateRequestStatus(Status.PENDING);
					},
				});
			}

			if (currentValue === PermissionActionType.APPROVE) {
				previousValue.push({
					key: Status.APPROVED.key,
					label: Status.APPROVED.name,
					onClick: () => {
						updateRequestStatus(Status.APPROVED);
					},
				});
			}

			if (currentValue === PermissionActionType.REQUEST_MORE_INFO) {
				previousValue.push({
					key: Status.REQUEST_MORE_INFO.key,
					label: Status.REQUEST_MORE_INFO.name,
					onClick: () => {
						updateRequestStatus(Status.REQUEST_MORE_INFO);
					},
				});
			}

			if (currentValue === PermissionActionType.REJECT) {
				previousValue.push({
					key: Status.REJECT.key,
					label: Status.REJECT.name,
					onClick: () => {
						updateRequestStatus(Status.REJECT);
					},
				});
			}
		}

		if (mdfRequestStatus?.key === Status.REQUEST_MORE_INFO.key) {
			if (
				currentValue ===
				PermissionActionType.MARKETING_DIRECTOR_REVIEW_STATUS_UPDATE
			) {
				previousValue.push({
					key: Status.MARKETING_DIRECTOR_REVIEW.key,
					label: Status.MARKETING_DIRECTOR_REVIEW.name,
					onClick: () => {
						updateRequestStatus(Status.MARKETING_DIRECTOR_REVIEW);
					},
				});
			}
		}

		if (mdfRequestStatus?.key === Status.EXPIRED.key) {
			if (
				currentValue ===
				PermissionActionType.MARKETING_DIRECTOR_REVIEW_STATUS_UPDATE
			) {
				previousValue.push({
					key: Status.MARKETING_DIRECTOR_REVIEW.key,
					label: Status.MARKETING_DIRECTOR_REVIEW.name,
					onClick: () => {
						updateRequestStatus(Status.MARKETING_DIRECTOR_REVIEW);
					},
				});
			}

			if (currentValue === PermissionActionType.REQUEST_MORE_INFO) {
				previousValue.push({
					key: Status.REQUEST_MORE_INFO.key,
					label: Status.REQUEST_MORE_INFO.name,
					onClick: () => {
						updateRequestStatus(Status.REQUEST_MORE_INFO);
					},
				});
			}
		}

		if (mdfRequestStatus?.key === Status.REJECT.key) {
			if (
				currentValue ===
				PermissionActionType.MARKETING_REVIEW_STATUS_UPDATE
			) {
				previousValue.push({
					key: Status.PENDING.key,
					label: Status.PENDING.name,
					onClick: () => {
						updateRequestStatus(Status.PENDING);
					},
				});
			}

			if (
				currentValue ===
				PermissionActionType.MARKETING_DIRECTOR_REVIEW_STATUS_UPDATE
			) {
				previousValue.push({
					key: Status.MARKETING_DIRECTOR_REVIEW.key,
					label: Status.MARKETING_DIRECTOR_REVIEW.name,
					onClick: () => {
						updateRequestStatus(Status.MARKETING_DIRECTOR_REVIEW);
					},
				});
			}

			if (currentValue === PermissionActionType.REQUEST_MORE_INFO) {
				previousValue.push({
					key: Status.REQUEST_MORE_INFO.key,
					label: Status.REQUEST_MORE_INFO.name,
					onClick: () => {
						updateRequestStatus(Status.REQUEST_MORE_INFO);
					},
				});
			}
		}

		if (
			mdfRequestStatus?.key === Status.APPROVED.key &&
			currentValue === PermissionActionType.CANCEL
		) {
			previousValue.push({
				key: Status.CANCELED.key,
				label: Status.CANCELED.name,
				onClick: () => {
					callConfirmActionMDFRequestModal('cancel', Status.CANCELED);
				},
			});
		}

		if (
			mdfRequestStatus?.key === Status.APPROVED.key &&
			currentValue === PermissionActionType.COMPLETE
		) {
			previousValue.push({
				key: Status.COMPLETED.key,
				label: Status.COMPLETED.name,
				onClick: () => {
					callConfirmActionMDFRequestModal(
						'complete',
						Status.COMPLETED
					);
				},
			});
		}

		if (mdfRequestStatus?.key === Status.CANCELED.key) {
			if (currentValue === PermissionActionType.APPROVE) {
				previousValue.push({
					key: Status.APPROVED.key,
					label: Status.APPROVED.name,
					onClick: () => {
						updateRequestStatus(Status.APPROVED);
					},
				});
			}
		}

		return previousValue;
	}, []);
}
