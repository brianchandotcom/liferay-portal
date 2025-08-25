/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayButton from '@clayui/button';
import {useModal} from '@clayui/modal';

import Page from '../../../components/Page';
import {useMarketplaceContext} from '../../../context/MarketplaceContext';
import SearchBuilder from '../../../core/SearchBuilder';
import {OrderTypes, OrderWorkflowStatusCode} from '../../../enums/Order';
import {usePlacedOrders} from '../../../hooks/data/usePlacedOrder';
import useModalContext from '../../../hooks/useModalContext';
import i18n from '../../../i18n';
import {useSSADashboardOutlet} from '../SSADashboardOutlet';
import TrialListView from '../components/TrialListView/TrialListView';
import useSSAActions from '../useSSAActions';

export default function SaaSTrials() {
	const {marketplaceUserAccount, myUserAccount} = useMarketplaceContext();
	const {onOpenModal} = useModalContext();
	const {ssaAccount} = useSSADashboardOutlet();
	const actions = useSSAActions();
	const createTrialFormModal = useModal();

	const isSSAAdmin = marketplaceUserAccount.isSSAAdmin;

	const {
		data: SSATrialsInProgress = {items: [], pageSize: 1, totalCount: 0},
	} = usePlacedOrders({
		accountId: ssaAccount.id,
		filter: new SearchBuilder()
			.eq('author', myUserAccount?.name)
			.and()
			.eq('orderTypeExternalReferenceCode', OrderTypes.SSA_SAAS)
			.and()
			.lambda('orderStatus', OrderWorkflowStatusCode.IN_PROGRESS, {
				unquote: true,
			})
			.build(),
		page: 1,
		pageSize: -1,
		shouldFetch: isSSAAdmin,
	});

	const canCreateTrial = isSSAAdmin
		? true
		: SSATrialsInProgress.totalCount < 3;

	return (
		<>
			<Page
				description={
					isSSAAdmin
						? i18n.translate('manage-your-teams-trial')
						: i18n.translate('manage-your-current-trials')
				}
				pageRendererProps={{className: 'border py-2'}}
				rightButton={
					<ClayButton
						onClick={() => {
							if (canCreateTrial) {
								return createTrialFormModal.onOpenChange(true);
							}

							onOpenModal({
								body: (
									<span>
										{i18n.translate(
											'you-have-reached-the-maximum-number-of-active-trials-allowed-to-start-a-new-trial-please-end-one-of-your-existing-trials-first'
										)}
									</span>
								),
								header: i18n.translate(
									'ssa-trials-limit-reached'
								),
							});
						}}
					>
						{i18n.translate('add-new-trial')}
					</ClayButton>
				}
				title={isSSAAdmin ? 'SaaS Demos' : 'My SaaS Demos'}
			>
				<TrialListView
					actions={actions}
					createTrialFormModal={createTrialFormModal}
					isSortable
					managementToolbarProps={{
						searchVisible: true,
						visible: isSSAAdmin,
					}}
				/>
			</Page>
		</>
	);
}
