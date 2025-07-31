/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayButton from '@clayui/button';
import { useModal } from '@clayui/modal';
import { useNavigate, useOutletContext } from 'react-router-dom';

import Modal from '../../../components/Modal';
import Page from '../../../components/Page';
import { useMarketplaceContext } from '../../../context/MarketplaceContext';
import SearchBuilder from '../../../core/SearchBuilder';
import { OrderStatus, OrderTypes } from '../../../enums/Order';
import { usePlacedOrders } from '../../../hooks/data/usePlacedOrder';
import useModalContext from '../../../hooks/useModalContext';
import i18n from '../../../i18n';
import { Action } from '../../../utils/constants';
import TrialListView from '../components/TrialListView/TrialListView';
import { ExtendRequestStatus } from '../enums/SSATrials';
import ExpireSSAModal from './ExpireSSAModal';
import ExtendRequestModal from './ExtendRequestModal';
import ExtendSSATrialModal from './ExtendSSATrialModal';

export default function SaaSTrials() {
	const { marketplaceUserAccount, myUserAccount, properties } = useMarketplaceContext();
	const modal = useModal();
	const modalContext = useModalContext();
	const navigate = useNavigate();
	const createTrialFormModal = useModal();
	const { selectedAccountId, ssaTrialExtend, ssaTrialExtendMutate } =
		useOutletContext<any>();
	const accountId = properties.accountId;
	const {
		data: SSATrialsInProgress = { items: [], pageSize: 1, totalCount: 0 },
	} = usePlacedOrders({
		accountId: Number(accountId),
		filter: new SearchBuilder()
			.eq('author', myUserAccount?.name)
			.and()
			.eq('orderTypeExternalReferenceCode', OrderTypes.SSA_SAAS)
			.and()
			.ne('orderStatusInfo/code', 0, {
				unquote: true,
			})
			.build(),
		page: 1,
		pageSize: -1,
	});

	const isSSAAdmin = marketplaceUserAccount.isSSAAdmin;

	const canCreateTrial = isSSAAdmin
		? true
		: SSATrialsInProgress.totalCount < 3;

	const actions: Action[] = [
		{
			name: i18n.translate('details'),
			onClick: (order: Order) => navigate(`details/${order.id}`),
		},
		{
			disabled: (order: Order) =>
				order.orderStatusInfo.label === OrderStatus.APPROVED ||
				order.orderStatusInfo.label === OrderStatus.COMPLETED ||
				order.orderStatusInfo.label === OrderStatus.PENDING,
			name: i18n.translate('go-to-trial'),
			onClick: (order: Order) =>
				window.open(
					`https://${order?.customFields?.['trial-virtual-host'] as string
					}`
				),
		},
		{
			hidden: (order: Order) => {
				if (isSSAAdmin) {
					const ssaTrialsExtendRequests = ssaTrialExtend.items;
					const extendRequests = ssaTrialsExtendRequests?.filter(
						(extend: TrialExtend) => {
							return (
								extend.r_orderToTrialExtensionRequest_commerceOrderId ===
								Number(order.id)
							);
						}
					) as TrialExtend[];

					if (extendRequests && extendRequests?.length > 0) {
						return (
							extendRequests[0]?.dueStatus.key !==
							ExtendRequestStatus.PENDING
						);
					}
				}

				return true;
			},
			name: i18n.translate('view-request'),
			onClick: (order: PlacedOrder, orderMutate) => {
				const ssaTrialsExtendRequests = ssaTrialExtend.items;
				const extendRequests = ssaTrialsExtendRequests?.filter(
					(extend: TrialExtend) => {

						return (
							extend.r_orderToTrialExtensionRequest_commerceOrderId ===
							Number(order.id)
						);
					}
				) as TrialExtend[];

				const extendRequestsCount = extendRequests?.filter(
					(extend: TrialExtend) => {

						return extend.dueStatus?.key ===
							ExtendRequestStatus.APPROVED ||
							extend.dueStatus?.key ===
							ExtendRequestStatus.AUTO_APPROVED;
					}
				) as TrialExtend[];

				if (!extendRequests) {
					return;
				}

				modalContext.onOpenModal({
					body: (
						<ExtendRequestModal
							onClose={modalContext.onClose}
							order={order}
							ssaTrialExtendMutate={ssaTrialExtendMutate}
							trialExtend={extendRequests[0]}
							trialExtendCount={extendRequestsCount?.length}
							orderMutate={orderMutate}
						/>
					),
					center: true,
				});
			},
		},
		{
			disabled: (order: Order) => {
				const ssaTrialsExtendRequests = ssaTrialExtend.items;
				const extendRequests = ssaTrialsExtendRequests?.filter(
					(extend: TrialExtend) => {
						return (
							extend.r_orderToTrialExtensionRequest_commerceOrderId ===
							Number(order.id)
						);
					}
				) as TrialExtend[];

				if (!extendRequests) {
					return true;
				}

				return (
					order.orderStatusInfo.label === OrderStatus.APPROVED ||
					order.orderStatusInfo.label === OrderStatus.COMPLETED ||
					order.orderStatusInfo.label === OrderStatus.PENDING ||
					extendRequests[0]?.dueStatus.key ===
					ExtendRequestStatus.PENDING
				);
			},
			name: 'Extend',
			onClick: (order: PlacedOrder, orderMutate: any) => {
				const ssaTrialsExtendRequests = ssaTrialExtend.items;
				const extendRequests = ssaTrialsExtendRequests?.filter(
					(extend: TrialExtend) => {
						return (
							extend.r_orderToTrialExtensionRequest_commerceOrderId ===
							Number(order.id)
						);
					}
				) as TrialExtend[];

				modalContext.onOpenModal({
					body: (
						<ExtendSSATrialModal
							accountId={selectedAccountId}
							firstExtendRequest={!extendRequests?.length}
							onClose={modalContext.onClose}
							order={order}
							ssaTrialExtendMutate={ssaTrialExtendMutate}
							orderMutate={orderMutate}
						/>
					),
					header: `Extend ${order.id} Trial`,
				});
			},
		},
		{
			disabled: (order: Order) =>
				order.orderStatusInfo.label === OrderStatus.APPROVED ||
				order.orderStatusInfo.label === OrderStatus.COMPLETED ||
				order.orderStatusInfo.label === OrderStatus.PENDING,
			name: 'Expire',
			onClick: (order: Order, mutate) => {
				modalContext.onOpenModal({
					body: (
						<ExpireSSAModal
							accountId={selectedAccountId}
							mutate={mutate}
							onClose={modalContext.onClose}
							order={order}
						/>
					),
					header: `Expire ${order.id} Trial`,
					status: undefined,
				});
			},
		},
	];

	return (
		<>
			<Page
				description={
					isSSAAdmin
						? i18n.translate('manage-your-teams-trial')
						: i18n.translate('manage-your-current-trials')
				}
				pageRendererProps={{ className: 'border py-2' }}
				rightButton={
					<ClayButton
						onClick={() =>
							canCreateTrial
								? createTrialFormModal.onOpenChange(true)
								: modal.onOpenChange(true)}
					>
						{i18n.translate('add-new-trial')}
					</ClayButton>
				}
				title={isSSAAdmin ? 'SaaS Demos' : 'My SaaS Demos'}
			>
				<TrialListView
					createTrialFormModal={createTrialFormModal}
					actions={actions}
					isSortable
					managementToolbarProps={{
						searchVisible: true,
						visible: isSSAAdmin ? true : false,
					}}
				/>
			</Page >

			{
				modal.open && (
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
						size={'md' as any}
						title={i18n.translate('ssa-trials-limit-reached')}
						visible={modal.open}
					>
						<span>
							{i18n.translate(
								'you-have-reached-the-maximum-number-of-active-trials-allowed-to-start-a-new-trial-please-end-one-of-your-existing-trials-first'
							)}
						</span>
					</Modal>
				)
			}
		</>
	);
}
