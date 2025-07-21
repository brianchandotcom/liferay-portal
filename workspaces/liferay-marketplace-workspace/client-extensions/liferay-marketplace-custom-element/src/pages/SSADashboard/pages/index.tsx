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
import {
	getSSASettingsOrDefaultFromCustomFields,
	getSSATrialsResourceURL,
} from '../util';
import ExtendSSATrialModal from './ExtendSSATrialModal';

export default function SaaSTrial() {
	const modalContext = useModalContext();
	const modal = useModal();
	const ssaForm = useSSAForm();
	const {channel, marketplaceUserAccount, myUserAccount} =
		useMarketplaceContext();
	const {selectedAccount} = useOutletContext<any>();
	const resourceUrl = getSSATrialsResourceURL(
		channel.channelId,
		selectedAccount?.id
	);

	const onExpireTrial = (order: Order) => {
		trialOAuth2.deleteTrial(order.id);
	};

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
				order.orderStatusInfo.label === OrderStatus.COMPLETED,
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
				const SSASettings = getSSASettingsOrDefaultFromCustomFields(
					order.customFields
				);

				if (isUserSSAAdmin) {
					return !SSASettings?.adminRequestExtend;
				}

				return true;
			},
			name: i18n.translate('view-request'),
			onClick: (order: PlacedOrder) => {
				modalContext.onOpenModal({
					body: (
						<ExtendSSATrialModal
							onClose={modalContext.onClose}
							order={order}
						/>
					),
					header: `Extend ${order.id} Trial`,
				});
			},
		},
		{
			disabled: (order: Order) => {
				const SSASettings = getSSASettingsOrDefaultFromCustomFields(
					order.customFields
				);

				return (
					order.orderStatusInfo.label === OrderStatus.APPROVED ||
					order.orderStatusInfo.label === OrderStatus.COMPLETED ||
					SSASettings.extendRequestStatus ===
						ExtendRequestStatus.PENDING ||
					SSASettings.extendRequestStatus ===
						ExtendRequestStatus.REJECTED
				);
			},
			name: 'Extend',
			onClick: (order: PlacedOrder) => {
				modalContext.onOpenModal({
					body: (
						<ExtendSSATrialModal
							onClose={modalContext.onClose}
							order={order}
						/>
					),
					header: `Extend ${order.id} Trial`,
				});
			},
		},
		{
			disabled: (order: Order) =>
				order.orderStatusInfo.label === OrderStatus.APPROVED ||
				order.orderStatusInfo.label === OrderStatus.COMPLETED,
			name: 'Expire',
			onClick: (order: Order) => {
				modalContext.onOpenModal({
					body: (
						<div>
							<ClayAlert displayType="warning" role={null}>
								This action cannot be undone.
							</ClayAlert>
							<p>
								Are you sure you want to expire this trial? This
								action imply the end of the test environemnt
								permanently.
							</p>
						</div>
					),
					footer: [
						<ClayButton
							aria-label="cancel"
							displayType="secondary"
							key={0}
							onClick={modalContext.onClose}
							size="sm"
						>
							{i18n.translate('cancel')}
						</ClayButton>,
						undefined,
						<ClayButton
							aria-label="close"
							displayType="warning"
							key={2}
							onClick={() => onExpireTrial(order)}
							size="sm"
						>
							Got it
						</ClayButton>,
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
						? "Manage your team's trial"
						: 'Manage your current trials'
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
						Add New Trial
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
					title="SSA Trials Limit Reached"
					visible={modal.open}
				>
					<span>
						{`You've reached the maximum number of active trials
						allowed. To start a new trial, please end one of your
						existing trials first.`}
					</span>
				</Modal>
			)}
		</>
	);
}
