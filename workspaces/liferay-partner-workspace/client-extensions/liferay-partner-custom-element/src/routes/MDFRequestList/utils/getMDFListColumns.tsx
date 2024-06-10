/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {KeyedMutator, mutate} from 'swr';

import Dropdown from '../../../common/components/Dropdown';
import {DropdownOption} from '../../../common/components/Dropdown/Dropdown';
import StatusLabel from '../../../common/components/StatusLabel';
import {MDFColumnKey} from '../../../common/enums/mdfColumnKey';
import {PermissionActionType} from '../../../common/enums/permissionActionType';
import {PRMPageRoute} from '../../../common/enums/prmPageRoute';
import MDFRequestDTO from '../../../common/interfaces/dto/mdfRequestDTO';
import {MDFRequestListItem} from '../../../common/interfaces/mdfRequestListItem';
import TableColumn from '../../../common/interfaces/tableColumn';
import {Liferay} from '../../../common/services/liferay';
import LiferayItems from '../../../common/services/liferay/common/interfaces/liferayItems';
import deleteObjectEntry from '../../../common/services/liferay/object/deleteObjectEntry/deleteObjectEntry';
import {ResourceName} from '../../../common/services/liferay/object/enum/resourceName';
import {Status} from '../../../common/utils/constants/status';
import patchRequestStatus from '../../MDFRequestManagerStatus/util/patchRequestStatus';

export default function getMDFListColumns(
	hasUserAccountSameAccountEntryCurrentMDFRequest: (
		index: number
	) => boolean | undefined,
	siteURL: string,
	urlParams: URLSearchParams,
	actions?: PermissionActionType[],
	mutated?: KeyedMutator<LiferayItems<MDFRequestDTO[]>>,
	isChannel?: boolean
): TableColumn<MDFRequestListItem>[] | undefined {
	const getDropdownOptions = (row: MDFRequestListItem, index: number) => {
		const isUserAssociated =
			hasUserAccountSameAccountEntryCurrentMDFRequest(index);

		const options = actions?.reduce<DropdownOption[]>(
			(previousValue, currentValue) => {
				const currentMDFRequestHasValidStatusToEdit =
					row[MDFColumnKey.REQUEST_STATUS] === Status.DRAFT.name ||
					row[MDFColumnKey.REQUEST_STATUS] ===
						Status.REQUEST_MORE_INFO.name;

				const currentMDFRequestHasValidStatusToRemove =
					(isChannel &&
						currentValue === PermissionActionType.DELETE &&
						row.REQUEST_STATUS === 'Approved') ||
					(!isChannel &&
						currentValue === PermissionActionType.DELETE &&
						row.REQUEST_STATUS === 'Draft');

				if (currentValue === PermissionActionType.VIEW) {
					previousValue.push({
						icon: 'view',
						key: 'approve',
						label: ' View',
						onClick: () =>
							Liferay.Util.navigate(
								`${siteURL}/l/${
									row[MDFColumnKey.ID]
								}?p_l_back_url=${encodeURIComponent(
									Liferay.ThemeDisplay.getLayoutRelativeURL()
								)}&${urlParams.toString()}`
							),
					});
				}

				if (
					(currentValue === PermissionActionType.UPDATE &&
						isUserAssociated &&
						currentMDFRequestHasValidStatusToEdit) ||
					currentValue ===
						PermissionActionType.UPDATE_WO_CHANGE_STATUS
				) {
					previousValue.push({
						icon: 'pencil',
						key: 'edit',
						label: ' Edit',
						onClick: () =>
							Liferay.Util.navigate(
								`${siteURL}/${
									PRMPageRoute.EDIT_MDF_REQUEST
								}/#/${row[MDFColumnKey.ID]}`
							),
					});
				}

				if (
					currentValue === PermissionActionType.COMPLETE &&
					row[MDFColumnKey.REQUEST_STATUS] === Status.APPROVED.name
				) {
					previousValue.push({
						icon: 'check',
						key: Status.COMPLETED.key,
						label: ' Complete',
						onClick: () => {
							Liferay.Util.openConfirmModal({
								message:
									'Are you sure you want to complete the MDF request?',
								onConfirm: async (isConfirmed: boolean) => {
									if (isConfirmed) {
										const newRequestStatus =
											await patchRequestStatus(
												Status.COMPLETED,
												String(row[MDFColumnKey.ID])
											);

										if (newRequestStatus) {
											Liferay.Util.openToast({
												message:
													'MDF Request successfully completed!',
												title: 'Success',
												type: 'success',
											});
										}

										mutate(mutated);
									}
								},
							});
						},
					});
				}

				if (
					currentValue === PermissionActionType.CANCEL &&
					row[MDFColumnKey.REQUEST_STATUS] === Status.APPROVED.name
				) {
					previousValue.push({
						icon: 'block',
						key: Status.CANCELED.key,
						label: ' Cancel',
						onClick: () => {
							Liferay.Util.openConfirmModal({
								message:
									'Are you sure you want to cancel the MDF request?',
								onConfirm: async (isConfirmed: boolean) => {
									if (isConfirmed) {
										const newRequestStatus =
											await patchRequestStatus(
												Status.CANCELED,
												String(row[MDFColumnKey.ID])
											);

										if (newRequestStatus) {
											Liferay.Util.openToast({
												message:
													'MDF Request successfully canceled!',
												title: 'Success',
												type: 'success',
											});
										}

										mutate(mutated);
									}
								},
							});
						},
					});
				}

				if (currentMDFRequestHasValidStatusToRemove) {
					previousValue.push({
						icon: 'trash',
						key: 'delete',
						label: ' Delete',
						onClick: () => {
							Liferay.Util.openConfirmModal({
								message:
									'Are you sure you want to delete this MDF record?',
								onConfirm: async (isConfirmed: boolean) => {
									if (isConfirmed) {
										try {
											await deleteObjectEntry(
												ResourceName.MDF_REQUEST_DXP,
												Number(row[MDFColumnKey.ID])
											);

											Liferay.Util.openToast({
												message:
													'MDF Request successfully deleted!',
												title: 'Success',
												type: 'success',
											});

											mutate(mutated);
										}
										catch (error: unknown) {
											Liferay.Util.openToast({
												message:
													'Fail to delete MDF Request',
												title: 'Error',
												type: 'danger',
											});
										}
									}
								},
							});
						},
					});
				}

				return previousValue;
			},
			[]
		);

		return (
			<Dropdown
				closeOnClick={true}
				icon="ellipsis-v"
				options={options || []}
			></Dropdown>
		);
	};

	return [
		{
			columnKey: MDFColumnKey.ID,
			label: 'Request ID',
			render: (data, row) => (
				<a
					className="link"
					href={`${siteURL}/l/${
						row[MDFColumnKey.ID]
					}?p_l_back_url=${encodeURIComponent(
						Liferay.ThemeDisplay.getLayoutRelativeURL()
					)}&${urlParams.toString()}`}
				>
					{data}
				</a>
			),
		},
		{
			columnKey: MDFColumnKey.PARTNER,
			label: 'Partner',
			size: 'md',
		},
		{
			columnKey: MDFColumnKey.REQUEST_STATUS,
			label: 'Status',
			render: (data) => <StatusLabel status={data as string} />,
		},
		{
			columnKey: MDFColumnKey.CAMPAIGN_NAME,
			label: 'Campaign Name',
			size: 'sm',
		},
		{
			columnKey: MDFColumnKey.START_ACT_PERIOD,
			label: 'Start Act. Period',
			wrap: true,
		},
		{
			columnKey: MDFColumnKey.END_ACT_PERIOD,
			label: 'End Act. Period',
			wrap: true,
		},
		{
			columnKey: MDFColumnKey.REQUESTED,
			label: 'Requested',
		},
		{
			columnKey: MDFColumnKey.AMOUNT_CLAIMED,
			label: (
				<div>
					<p className="mb-0 mt-4 text-neutral-10">Claimed</p>
					<p className="mt-0 text-neutral-5 text-paragraph-sm">
						Paid
					</p>
				</div>
			),
			render: (_, row) => (
				<div>
					<p className="border-0 font-weight-normal mb-0">
						{row['AMOUNT_CLAIMED']}
					</p>
					<p className="mb-0 mt-0 text-neutral-7 text-paragraph-sm">
						{row['AMOUNT_PAID']}
					</p>
				</div>
			),
			size: 'md',
		},
		{
			columnKey: MDFColumnKey.DATE_SUBMITTTED,
			label: (
				<div>
					<p className="mb-0 mt-4 text-neutral-10">Submit Date</p>
				</div>
			),
			render: (_, row) => (
				<div>
					<p className="border-0 font-weight-normal mb-0">
						{row['DATE_SUBMITTED']}
					</p>
					<p className="mb-0 mt-0 text-neutral-7 text-paragraph-sm">
						{row['LAST_MODIFIED']}
					</p>
				</div>
			),
		},
		{
			columnKey: MDFColumnKey.ACTION,
			label: '',
			render: (_, row, index) => getDropdownOptions(row, index),
		},
	];
}
