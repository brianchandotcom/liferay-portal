/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {format} from 'date-fns';
import {useState} from 'react';
import {useOutletContext} from 'react-router-dom';

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

export default function TrialListView({
	actions,
	createTrialFormModal,
	listViewProps,
	managementToolbarProps,
}: TrialsListViewProps) {
	const {ssaTrialExtend} = useOutletContext<any>();
	const {marketplaceUserAccount, myUserAccount, properties} =
		useMarketplaceContext();
	const [items, setItems] = useState<PlacedOrder[]>([]);

	const handleDataLoad = ({items}: {items: PlacedOrder[]}) => {
		setItems(items);
	};

	const refresh = items.some(
		(item) => item.orderStatusInfo.label === OrderStatus.PROCESSING
	);

	const resource = `/o/headless-commerce-delivery-order/v1.0/channels/${Liferay.CommerceContext.commerceChannelId}/accounts/${properties?.accountId}/placed-orders?${new URLSearchParams(
		{
			nestedFields: 'placedOrderItems',
			sort: 'createDate:desc',
		}
	)}`;

	const defaultFilters = marketplaceUserAccount.isSSAAdmin
		? SearchBuilder.eq(
				'orderTypeExternalReferenceCode',
				OrderTypes.SSA_SAAS
			)
		: new SearchBuilder()
				.eq('author', myUserAccount?.name)
				.and()
				.eq('orderTypeExternalReferenceCode', OrderTypes.SSA_SAAS)
				.build();

	return (
		<>
			<ListView<PlacedOrder>
				defaultFilters={{filter: defaultFilters}}
				emptyStateProps={{title: i18n.translate('no-trials-yet')}}
				id="ssa-trials"
				managementToolbarProps={{
					filterSchema: 'administratorSSATrials',
					...managementToolbarProps,
				}}
				onDataLoad={handleDataLoad}
				refreshInterval={refresh ? 60 * 1000 : undefined}
				resource={resource}
				tableProps={{
					actions,
					columns: [
						{
							id: 'placedOrderItems',
							name: 'Project ID',
							render: (_, {customFields, id}) => {
								return (
									<span className="font-weight-semi-bold ml-2">
										{(customFields &&
											JSON.parse(
												customFields[
													OrderCustomFields
														.TRIAL_SETTINGS
												]
											)?.projectId) ??
											id}
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
							id: 'customFields',
							name: 'Solution Type',
							render: () => <span>Blank Site</span>,
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
						items={items}
						modal={createTrialFormModal}
						mutate={mutate}
					/>
				)}
			</ListView>
		</>
	);
}
