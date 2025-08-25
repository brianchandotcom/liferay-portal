/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {useMemo} from 'react';
import {Link} from 'react-router-dom';

import ListView, {ListViewProps} from '../../../../components/ListView';
import {ManagementToolbarProps} from '../../../../components/ListView/components/ManagementToolbar';
import {useMarketplaceContext} from '../../../../context/MarketplaceContext';
import SearchBuilder from '../../../../core/SearchBuilder';
import {
	OrderCustomFields,
	OrderStatus,
	OrderTypes,
} from '../../../../enums/Order';
import i18n from '../../../../i18n';
import {Liferay} from '../../../../liferay/liferay';
import {Action} from '../../../../utils/constants';
import {formatDate} from '../../../../utils/date';
import {safeJSONParse} from '../../../../utils/util';
import {useSSADashboardOutlet} from '../../SSADashboardOutlet';
import {EXTEND_TRIAL_STATUS_LABEL} from '../../constants';
import CreateTrialModalForm from '../../pages/CreateTrialModalform';
import ExtensionStatus from '../ExtensionStatus/ExtensionStatus';
import TrialStatus from '../TrialStatus/TrialStatus';

type TrialsListViewProps = {
	actions: Action[];
	createTrialFormModal: any;
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
};

// Refresh the table every 60 seconds

const refreshInterval = 60 * 1000;

export default function TrialListView({
	actions,
	createTrialFormModal,
	listViewProps,
	managementToolbarProps,
}: TrialsListViewProps) {
	const {ssaAccount, ssaTrialExtend} = useSSADashboardOutlet();
	const {marketplaceUserAccount, myUserAccount} = useMarketplaceContext();

	const resource = `/o/headless-commerce-delivery-order/v1.0/channels/${Liferay.CommerceContext.commerceChannelId}/accounts/${ssaAccount.id}/placed-orders?${new URLSearchParams(
		{
			nestedFields: 'placedOrderItems',
			sort: 'createDate:desc',
		}
	)}`;

	const searchBuilder = useMemo(
		() =>
			new SearchBuilder().eq(
				'orderTypeExternalReferenceCode',
				OrderTypes.SSA_SAAS
			),
		[]
	);

	if (!marketplaceUserAccount.isSSAAdmin) {
		searchBuilder.and().eq('author', myUserAccount?.name);
	}

	return (
		<>
			<ListView<PlacedOrder>
				defaultFilters={{filter: searchBuilder.build()}}
				emptyStateProps={{title: i18n.translate('no-trials-yet')}}
				id="ssa-trials"
				managementToolbarProps={{
					filterSchema: 'administratorSSATrials',
					...managementToolbarProps,
				}}
				refreshInterval={refreshInterval}
				resource={resource}
				tableProps={{
					actions,
					columns: [
						{
							id: 'placedOrderItems',
							name: 'Project ID',
							render: (_, {customFields, id}) => (
								<Link
									className="font-weight-semi-bold ml-2"
									to={`/details/${id}`}
								>
									{customFields &&
										safeJSONParse(
											customFields[
												OrderCustomFields.TRIAL_SETTINGS
											],
											{projectId: id}
										).projectId}
								</Link>
							),
						},
						{
							id: 'author',
							name: 'Created By',
							render: (author, {createDate}) => (
								<div className="d-flex flex-column">
									<span className="dashboard-table-row-text">
										{author}
									</span>

									<span className="dashboard-table-row-purchased-date">
										{formatDate(createDate)}
									</span>
								</div>
							),
							sortable: true,
						},
						{
							id: 'customFields',
							name: 'Solution Type',
							render: (customFields) =>
								safeJSONParse(
									customFields[
										OrderCustomFields.TRIAL_SETTINGS
									],
									{siteInitializerKey: 'Blank Site'}
								).siteInitializerKey,
						},
						{
							id: 'createDate',
							name: 'End Date',
							render: (_, {customFields}) =>
								formatDate(
									customFields[
										OrderCustomFields.TRIAL_END_DATE
									],
									'DNE'
								),
							sortable: true,
						},
						{
							id: 'orderStatusInfo',
							name: 'Trial Status',
							render: (orderStatusInfo) => (
								<TrialStatus
									trialStatus={orderStatusInfo?.label}
								/>
							),
						},
						{
							id: 'id',
							name: 'Extension Status',
							render: (orderId, placedOrder) => {
								const ssaTrialsExtendRequests =
									ssaTrialExtend.items;

								const extendRequests =
									ssaTrialsExtendRequests?.filter(
										(extend: TrialExtend) => {
											return (
												extend.r_orderToTrialExtensionRequest_commerceOrderId ===
												Number(orderId)
											);
										}
									) as TrialExtend[];

								if (
									!extendRequests ||
									extendRequests?.length === 0
								) {
									return (
										<ExtensionStatus extensionStatus="not-requested" />
									);
								}

								return (
									<ExtensionStatus
										extensionStatus={
											placedOrder.orderStatusInfo
												.label === OrderStatus.COMPLETED
												? 'extension-expired'
												: (extendRequests[0]?.dueStatus
														.key as keyof typeof EXTEND_TRIAL_STATUS_LABEL)
										}
									/>
								);
							},
						},
					],
				}}
				{...listViewProps}
			>
				{(_, {mutate}) => (
					<CreateTrialModalForm
						modal={createTrialFormModal}
						mutate={mutate}
					/>
				)}
			</ListView>
		</>
	);
}
