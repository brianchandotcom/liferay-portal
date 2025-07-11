/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayButton from '@clayui/button';
import {addDays, format} from 'date-fns';

import ListView, {ListViewProps} from '../../../components/ListView';
import {ManagementToolbarProps} from '../../../components/ListView/components/ManagementToolbar';
import Page from '../../../components/Page';
import i18n from '../../../i18n';
import {useMarketplaceContext} from '../../../context/MarketplaceContext';
import {useOutletContext} from 'react-router-dom';
import OrderStatus from '../../../components/OrderStatus';
import {OrderCustomFields, OrderTypes} from '../../../enums/Order';
import SearchBuilder from '../../../core/SearchBuilder';
import TrialStatus from '../components/TrialStatus/TrialStatus';
import ExtensionStatus from '../components/ExtensionStatus/ExtensionStatus';
import getSSATrialsResourceURL, {
	getExtensionStatusFromTrialSettings,
} from '../util';
import {useModal} from '@clayui/modal';
import Modal from '../../../components/Modal';

type SSATrialsListViewProps = {
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

export function SSATrialsListView({
	listViewProps,
	managementToolbarProps,
}: SSATrialsListViewProps) {
	const {channel} = useMarketplaceContext();
	const {selectedAccount} = useOutletContext<any>();
	const resourceUrl = getSSATrialsResourceURL(
		channel.channelId,
		selectedAccount?.id
	);

	return (
		<ListView<PlacedOrder>
			emptyStateProps={{title: i18n.translate('no-orders-yet')}}
			id="ssa-trials"
			managementToolbarProps={{
				filterSchema: 'administratorOrders',
				...managementToolbarProps,
			}}
			resource={resourceUrl}
			tableProps={{
				actions: [
					{
						name: i18n.translate('view-details'),
						onClick: () => console.log('go to trial'),
					},
					{
						name: 'Expire',
						onClick: () => console.log('Expire'),
					},
					{
						name: 'Extend',
						onClick: () => console.log('extend'),
					},
				],
				columns: [
					{
						id: 'placedOrderItems',
						name: 'Name',
						render: ([placedOrderItem]) => (
							<span className="font-weight-semi-bold ml-2">
								{placedOrderItem.name}
							</span>
						),
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
						id: 'orderTypeExternalReferenceCode',
						name: i18n.translate('type'),
						render: (orderTypeExternalReferenceCode) => {
							return (
								<span className="label label-info">
									{orderTypeExternalReferenceCode}
								</span>
							);
						},
						sortable: true,
					},
					{
						id: 'createDate',
						name: 'End Date',
						render: (createDate, {customFields}) => {

							// TODO - Create a help function to retrieve a field from the customfield object
							// const duration = customFields[
							// 	OrderCustomFields.TRIAL_SETTINGS
							// ]
							// 	? customFields[OrderCustomFields.TRIAL_SETTINGS].duration
							// 	: 7;

							const duration = 7;

							if (typeof duration === 'number') {
								return format(
									addDays(new Date(createDate), duration),
									'dd MMM, yyyy'
								).toString();
							}

							return 'DNE';
						},
						sortable: true,
					},
					{
						id: 'orderStatusInfo',
						name: 'Trial Status',
						render: (orderStatusInfo) => (
							<TrialStatus trialStatus={orderStatusInfo?.label} />
						),
						sortable: true,
					},
					{
						id: 'customFields',
						name: 'Extension Status',
						render: (customFields) => (
							<ExtensionStatus
								extensionStatus={getExtensionStatusFromTrialSettings(
									customFields[
										OrderCustomFields.TRIAL_SETTINGS
									]
								)}
							/>
						),
						sortable: true,
					},
				],
			}}
			{...listViewProps}
		/>
	);
}

export default function SSATrials() {
	const modal = useModal();
	return (
		<>
			<Page
				pageRendererProps={{className: 'border py-2'}}
				rightButton={
					<ClayButton onClick={() => modal.onOpenChange(true)}>
						Add New Trials
					</ClayButton>
				}
				description="Manage your SSA Trials"
				title="SSA Trials"
			>
				<SSATrialsListView
					isSortable
					managementToolbarProps={{
						searchVisible: false,
						visible: true,
					}}
				/>
			</Page>

			{modal.open && (
				<Modal
					last={
						<ClayButton
							className="btn"
							displayType="secondary"
							onClick={() => modal.onClose()}
						>
							{i18n.translate('cancel')}
						</ClayButton>
					}
					observer={modal.observer}
					title="SSA Trials Limit Reached"
					size={'md' as any}
					visible={modal.open}
				>
					<span>
						You've reached the maximum number of active trials
						allowed. To start a new trial, please end one of your
						existing trials first.
					</span>
				</Modal>
			)}
		</>
	);
}
