/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayAlert from '@clayui/alert';
import ClayButton from '@clayui/button';
import {useModal} from '@clayui/modal';
import {useOutletContext} from 'react-router-dom';

import Modal from '../../../components/Modal';
import Page from '../../../components/Page';
import {useMarketplaceContext} from '../../../context/MarketplaceContext';
import SearchBuilder from '../../../core/SearchBuilder';
import {OrderStatus, OrderTypes} from '../../../enums/Order';
import useModalContext from '../../../hooks/useModalContext';
import i18n from '../../../i18n';
import trialOAuth2 from '../../../services/oauth/Trial';
import {Action} from '../../../utils/constants';
import {useSSAForm} from '../components/SSAForm';
import TrialListView from '../components/TrialListView/TrialListView';
import {ExtendRequestStatus} from '../enums/SSATrials';
import {useSSATrials} from '../useSSATrials';
import {getSSATrialsResourceURL} from '../util';
import ExtendRequestModal from './ExtendRequestModal';
import ExtendSSATrialModal from './ExtendSSATrialModal';

export default function SaaSTrials() {
	const modalContext = useModalContext();
	const modal = useModal();
	const ssaForm = useSSAForm();
	const {channel, marketplaceUserAccount, myUserAccount} =
		useMarketplaceContext();
	const {selectedAccount, ssaTrialExtend, ssaTrialExtendMutate} =
		useOutletContext<any>();
	const resourceUrl = getSSATrialsResourceURL(
		channel.channelId,
		selectedAccount?.id
	);

	const onExpireTrial = (order: Order) => trialOAuth2.expireTrial(order.id);

	const {
		data: SSATrialsInProgress = {items: [], pageSize: 1, totalCount: 0},
	} = useSSATrials({
		accountId: selectedAccount?.id,
		channelId: channel.channelId,
		filter: new SearchBuilder()
			.eq('orderTypeExternalReferenceCode', OrderTypes.SSA_SAAS)
			.and()
			.eq('author', myUserAccount?.name)
			.and()
			.eq('orderStatusInfo/code', 0, {
				unquote: true,
			})
			.build(),
		page: 1,
		pageSize: -1,
	});

	const isUserSSAAdmin = marketplaceUserAccount.isSSAAdmin;
	const canCreateTrial = isUserSSAAdmin
		? true
		: SSATrialsInProgress.totalCount <= 3;

	const actions: Action[] = [
		{
			disabled: (order: Order) =>
				order.orderStatusInfo.label === OrderStatus.APPROVED ||
				order.orderStatusInfo.label === OrderStatus.COMPLETED ||
				order.orderStatusInfo.label === OrderStatus.PROCESSING,
			name: i18n.translate('go-to-trial'),
			onClick: (order: Order) =>
				window.open(
					`https://${
						order?.customFields?.['trial-virtualhost'] as string
					}`
				),
		},
		{
			hidden: (order: Order) => {
				if (isUserSSAAdmin) {
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
			onClick: (order: PlacedOrder) => {
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
					return;
				}

				modalContext.onOpenModal({
					body: (
						<ExtendRequestModal
							onClose={modalContext.onClose}
							order={order}
							ssaTrialExtendMutate={ssaTrialExtendMutate}
							trialExtend={extendRequests[0]}
							trialExtendCount={extendRequests?.length}
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
					order.orderStatusInfo.label === OrderStatus.PROCESSING ||
					extendRequests[0]?.dueStatus.key ===
						ExtendRequestStatus.PENDING
				);
			},
			name: 'Extend',
			onClick: (order: PlacedOrder) => {
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
							accountId={selectedAccount.id}
							firstExtendRequest={extendRequests?.length === 0}
							onClose={modalContext.onClose}
							order={order}
							ssaTrialExtendMutate={ssaTrialExtendMutate}
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
				order.orderStatusInfo.label === OrderStatus.PROCESSING,
			name: 'Expire',
			onClick: (order: Order) => {
				modalContext.onOpenModal({
					body: (
						<div>
							<ClayAlert displayType="warning" role={null}>
								{i18n.translate('this-action-cannot-be-undone')}
							</ClayAlert>
							<p>
								{i18n.translate(
									'are-you-sure-you-want-to-expire-this-trial-this-action-imply-the-end-of-the-test-environment-permanently'
								)}
							</p>
						</div>
					),
					footer: [
						undefined,
						undefined,
						<div key="footer-buttons">
							<ClayButton
								aria-label="cancel"
								displayType="secondary"
								key={0}
								onClick={modalContext.onClose}
							>
								{i18n.translate('cancel')}
							</ClayButton>

							<ClayButton
								aria-label="close"
								className="ml-4"
								displayType="warning"
								key={2}
								onClick={() => {
									onExpireTrial(order);
									modalContext.onClose;
								}}
							>
								Got it
							</ClayButton>
						</div>,
					],
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
					isUserSSAAdmin
						? i18n.translate('manage-your-teams-trial')
						: i18n.translate('manage-your-current-trials')
				}
				pageRendererProps={{className: 'border py-2'}}
				rightButton={
					<ClayButton
						onClick={() =>
							canCreateTrial
								? ssaForm.openModal()
								: modal.onOpenChange(true)
						}
					>
						{i18n.translate('add-new-trial')}
					</ClayButton>
				}
				title={isUserSSAAdmin ? 'SaaS Demos' : 'My SaaS Demos'}
			>
				<TrialListView
					actions={actions}
					isSortable
					managementToolbarProps={{
						searchVisible: true,
						visible: isUserSSAAdmin ? true : false,
					}}
					resourceUrl={resourceUrl}
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
			)}
		</>
	);
}
