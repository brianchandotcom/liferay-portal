/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {format} from 'date-fns';

import ListView, {ListViewProps} from '../../../../components/ListView';
import {ManagementToolbarProps} from '../../../../components/ListView/components/ManagementToolbar';
import {OrderCustomFields, OrderTypes} from '../../../../enums/Order';
import i18n from '../../../../i18n';
import {Action} from '../../../../utils/constants';
import {getSSASettingsOrDefaultFromCustomFields} from '../../util';
import ExtensionStatus from '../ExtensionStatus/ExtensionStatus';
import TrialStatus from '../TrialStatus/TrialStatus';
import SearchBuilder from '../../../../core/SearchBuilder';
import {EXTEND_TRIAL_STATUS_LABEL} from '../../constants';

type TrialsListViewProps = {
	actions: Action[];
	isSortable?: boolean;
	listViewProps?: Partial<ListViewProps<PlacedOrder>>;
	managementToolbarProps?: {
		visible?: boolean;
	} & Omit<
		ManagementToolbarProps,
		| 'actions'
		| 'onSelectAllRows'
		| 'rowSelectable'
		| 'tableProps'
		| 'totalItems'
	>;
	resourceUrl: string;
};

export default function TrialListView({
	actions,
	listViewProps,
	managementToolbarProps,
	resourceUrl,
}: TrialsListViewProps) {
	return (
		<ListView<PlacedOrder>
			defaultFilters={{
				filter: SearchBuilder.eq(
					'orderTypeExternalReferenceCode',
					OrderTypes.SSA_SAAS
				),
			}}
			emptyStateProps={{title: i18n.translate('no-orders-yet')}}
			id="ssa-trials"
			managementToolbarProps={{
				filterSchema: 'administratorSSATrials',
				...managementToolbarProps,
			}}
			resource={resourceUrl}
			tableProps={{
				actions,
				columns: [
					{
						id: 'placedOrderItems',
						name: 'Project ID',
						render: (_, {customFields, id}) => {
							const SSASettings =
								getSSASettingsOrDefaultFromCustomFields(
									customFields
								);

							return (
								<span className="font-weight-semi-bold ml-2">
									{SSASettings?.projectId ?? id}
								</span>
							);
						},
					},
					{
						id: 'author',
						name: 'Created By',
						render: (author, {createDate}) => {
							return (
								<div className="d-flex flex-column">
									<span className="dashboard-table-row-text">
										{author}
									</span>

									<span className="dashboard-table-row-purchased-date">
										{new Date(
											createDate
										).toLocaleDateString('en-US', {
											day: 'numeric',
											month: 'short',
											year: 'numeric',
										})}
									</span>
								</div>
							);
						},
						sortable: true,
					},
					{
						id: 'id',
						name: 'Order ID',
						sortable: true,
					},
					{
						id: 'createDate',
						name: 'End Date',
						render: (_, {customFields}) => {
							return customFields[OrderCustomFields.END_DATE]
								? format(
										new Date(
											customFields[
												OrderCustomFields.END_DATE
											]
										),
										'dd MMM, yyyy'
									).toString()
								: 'DNE';
						},
						sortable: true,
					},
					{
						id: 'orderStatusInfo',
						name: 'Trial Status',
						render: (orderStatusInfo) => (
							<TrialStatus trialStatus={orderStatusInfo?.label} />
						),
					},
					{
						id: 'customFields',
						name: 'Extension Status',
						render: (customFields) => {
							const SSASettings =
								getSSASettingsOrDefaultFromCustomFields(
									customFields
								);
							console.log(SSASettings);
							return (
								<ExtensionStatus
									extensionStatus={
										SSASettings.extendRequestStatus as keyof typeof EXTEND_TRIAL_STATUS_LABEL
									}
								/>
							);
						},
					},
				],
			}}
			{...listViewProps}
		/>
	);
}
