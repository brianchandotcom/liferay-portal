/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayButton from '@clayui/button';
import DropDown from '@clayui/drop-down';
import ClayIcon from '@clayui/icon';
import ClayLabel from '@clayui/label';
import {useModal} from '@clayui/modal';
import {Status} from '@clayui/modal/lib/types';
import {formatDistance} from 'date-fns';
import {useState} from 'react';

import {DashboardEmptyTable} from '../../../../../components/DashboardTable/DashboardEmptyTable';
import Loading from '../../../../../components/Loading';
import Modal from '../../../../../components/Modal';
import Table from '../../../../../components/Table/Table';
import {ORDER_WORKFLOW_STATUS_CODE} from '../../../../../enums/Order';
import i18n from '../../../../../i18n';
import {Liferay} from '../../../../../liferay/liferay';
import trialOAuth2 from '../../../../../services/oauth/Trial';
import CommerceSelectAccountImpl from '../../../../../services/rest/CommerceSelectAccount';
import HeadlessCommerceAdminOrderImpl from '../../../../../services/rest/HeadlessCommerceAdminOrder';
import NewTrialModal from './NewTrialModal';

type TrialTableProps = {
	items: Order[];
	revalidate: () => void;
};

type DropDownItems = {
	id: number;
	name: string;
	onClick: (item?: Order) => void;
};

const ORDER_STATUS_LABEL = {
	completed: 'success',
	pending: 'info',
	processing: 'secondary',
};

const safeRunner = async (promise: any) => {
	try {
		await promise;
	}
	catch {}
};

const TrialTable: React.FC<TrialTableProps> = ({items, revalidate}) => {
	const [processing, setProcessing] = useState(false);
	const [selectedTrial, setSelectedTrial] = useState<Order>();
	const newTrialModal = useModal();
	const modal = useModal();

	const onDeleteTrial = async (order: Order) => {
		setProcessing(true);

		const orderId = String(order.id);

		await safeRunner(HeadlessCommerceAdminOrderImpl.deleteOrder(orderId));
		await safeRunner(trialOAuth2.deleteTrial(orderId));

		await revalidate();

		setProcessing(false);

		modal.onClose();
	};

	const itemsDropdown = [
		{
			name: i18n.translate('go-to-trial'),
			onClick: (order: Order) =>
				window.open(
					`https://${
						order?.customFields?.['trial-virtualhost'] as string
					}`
				),
		},
		{
			name: i18n.translate('customer-dashboard'),
			onClick: (order: Order) =>
				CommerceSelectAccountImpl.selectAccount(order?.accountId).then(
					() => {
						Liferay.CommerceContext.account = {
							accountId: order?.accountId,
						};

						Liferay.Util.navigate(
							Liferay.ThemeDisplay.getLayoutURL().replace(
								'/administrator-dashboard',
								`/customer-dashboard`
							) + '#/solutions'
						);
					}
				),
		},
		{
			name: i18n.translate('delete'),
			onClick: async (order: Order) => {
				modal.onOpenChange(true);

				setSelectedTrial(order);
			},
		},
	];

	return (
		<>
			<div className="d-flex justify-content-between">
				<h1 className="mb-1">{i18n.translate('recent-trials')}</h1>

				<ClayButton
					onClick={() => newTrialModal.onOpenChange(true)}
					size="sm"
				>
					New Trial
				</ClayButton>
			</div>

			{!items.length ? (
				<div className="mt-3">
					<DashboardEmptyTable
						description1={i18n.translate(
							'purchase-and-install-new-apps-and-they-will-show-up-here'
						)}
						description2={i18n.translate(
							'click-on-add-apps-to-start'
						)}
						icon="grid"
						title={i18n.translate('no-orders-yet')}
					/>
				</div>
			) : (
				<>
					<Table
						className="mt-3"
						columns={[
							{
								key: 'id',
								render: (id) => (
									<span className="font-weight-bold">
										{id}
									</span>
								),
								title: i18n.translate('id'),
							},
							{
								key: 'orderItems',
								render: (orderItems) =>
									orderItems[0]?.name.en_US,
								title: i18n.translate('product'),
							},
							{
								key: 'account',
								render: (account) => account?.name,
								title: i18n.translate('user-account'),
							},
							{
								key: 'orderStatusInfo',
								render: (orderStatusInfo) => (
									<div className="align-items-center d-flex">
										<ClayLabel
											className="text-nowrap"
											displayType={
												ORDER_STATUS_LABEL[
													orderStatusInfo?.label as keyof typeof ORDER_STATUS_LABEL
												] as Status
											}
										>
											{orderStatusInfo?.label_i18n}
										</ClayLabel>

										{[
											ORDER_WORKFLOW_STATUS_CODE.ON_HOLD,
											ORDER_WORKFLOW_STATUS_CODE.PROCESSING,
										].includes(orderStatusInfo.code) && (
											<Loading
												displayType="primary"
												shape="circle"
												size="sm"
											/>
										)}
									</div>
								),
								title: i18n.translate('trial-status'),
							},
							{
								key: 'createDate',
								render: (createDate) => (
									<span className="ml-2 text-capitalize text-nowrap">
										{createDate &&
											formatDistance(
												new Date(createDate),
												Date.now(),
												{addSuffix: true}
											)}
									</span>
								),
								title: i18n.translate('created-at'),
							},
							{
								key: 'customFields',
								render: (customFields) => (
									<span className="ml-2 text-capitalize text-nowrap">
										{customFields['trial-start-date'] &&
											formatDistance(
												new Date(
													customFields[
														'trial-start-date'
													]
												),
												Date.now(),
												{addSuffix: true}
											)}
									</span>
								),
								title: i18n.translate('start-date'),
							},
							{
								key: 'customFields',
								render: (customFields) => (
									<span className="ml-2 text-capitalize text-nowrap">
										{customFields['trial-end-date'] &&
											formatDistance(
												new Date(
													customFields[
														'trial-end-date'
													]
												),
												Date.now(),
												{addSuffix: true}
											)}
									</span>
								),
								title: i18n.translate('expiration-date'),
							},
							{
								align: 'right',
								key: 'accountId',
								render: (_, order) => {
									if (
										order.orderStatusInfo?.code ===
										ORDER_WORKFLOW_STATUS_CODE.PROCESSING
									) {
										return null;
									}

									return (
										<DropDown
											closeOnClick
											filterKey="name"
											trigger={
												<div>
													<ClayButton
														aria-label="Action Dropdown"
														displayType="unstyled"
													>
														<ClayIcon symbol="ellipsis-v" />
													</ClayButton>
												</div>
											}
										>
											<DropDown.ItemList
												items={itemsDropdown}
											>
												{(dropDownItem: unknown) => {
													const item =
														dropDownItem as DropDownItems;

													return (
														<DropDown.Item
															key={item.name}
															onClick={() =>
																item.onClick(
																	order
																)
															}
														>
															{item?.name}
														</DropDown.Item>
													);
												}}
											</DropDown.ItemList>
										</DropDown>
									);
								},
								title: '',
							},
						]}
						rows={items}
					/>

					<Modal
						last={
							<>
								<ClayButton
									displayType="secondary"
									onClick={modal.onClose}
									size="sm"
								>
									{i18n.translate('cancel')}
								</ClayButton>

								<ClayButton
									className="ml-2"
									disabled={processing}
									displayType="danger"
									onClick={() =>
										onDeleteTrial(selectedTrial as Order)
									}
									size="sm"
								>
									{i18n.translate('delete')}
								</ClayButton>
							</>
						}
						observer={modal.observer}
						size={'md' as any}
						status="danger"
						title={`Are you sure you want to delete trial ${selectedTrial?.id}`}
						visible={modal.open}
					>
						{i18n.sub(
							'x-will-be-deleted-and-this-action-cant-be-undone-are-you-sure-you-want-to-delete-it',
							'Order'
						)}
					</Modal>
				</>
			)}

			{newTrialModal.open && (
				<NewTrialModal {...newTrialModal} revalidate={revalidate} />
			)}
		</>
	);
};

export default TrialTable;
