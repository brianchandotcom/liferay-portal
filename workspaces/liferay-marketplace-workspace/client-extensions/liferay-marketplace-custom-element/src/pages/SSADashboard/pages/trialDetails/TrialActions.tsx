/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import DropDown from '@clayui/drop-down';
import {ClayTooltipProvider} from '@clayui/tooltip';
import {useOutletContext} from 'react-router-dom';
import {KeyedMutator} from 'swr';

import {useMarketplaceContext} from '../../../../context/MarketplaceContext';
import {OrderCustomFields, OrderStatus} from '../../../../enums/Order';
import useModalContext from '../../../../hooks/useModalContext';
import i18n from '../../../../i18n';
import {ExtendRequestStatus} from '../../enums/SSATrials';
import ExpireSSAModal from '../ExpireSSAModal';
import ExtendRequestModal from '../ExtendRequestModal';
import ExtendSSATrialModal from '../ExtendSSATrialModal';

type TrialActionsProps = {
	mutatePlacedOrder: KeyedMutator<any>;
	placedOrder: PlacedOrder;
	ssaTrialExtendMutate: KeyedMutator<any>;
};

function TrialActions({
	mutatePlacedOrder,
	placedOrder,
	ssaTrialExtendMutate,
}: TrialActionsProps) {
	const {marketplaceUserAccount} = useMarketplaceContext();
	const modalContext = useModalContext();
	const {selectedAccountId, ssaTrialExtend} = useOutletContext<any>();

	const isUserSSAAdmin = marketplaceUserAccount.isSSAAdmin;

	const isAdmin = (order: PlacedOrder) => {
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
	};

	const disableExtend = (placedOrder: PlacedOrder) => {
		const ssaTrialsExtendRequests = ssaTrialExtend.items;
		const extendRequests = ssaTrialsExtendRequests?.filter(
			(extend: TrialExtend) => {
				return (
					extend.r_orderToTrialExtensionRequest_commerceOrderId ===
					Number(placedOrder.id)
				);
			}
		) as TrialExtend[];

		if (!extendRequests) {
			return true;
		}

		return (
			placedOrder.orderStatusInfo.label !== OrderStatus.IN_PROGRESS ||
			extendRequests[0]?.dueStatus.key === ExtendRequestStatus.PENDING
		);
	};

	return (
		<DropDown.ItemList>
			<ClayTooltipProvider>
				<DropDown.Item
					data-tooltip-align="left"
					disabled={disableExtend(placedOrder)}
					onClick={() => {
						const ssaTrialsExtendRequests = ssaTrialExtend.items;
						const extendRequests = ssaTrialsExtendRequests?.filter(
							(extend: TrialExtend) => {
								return (
									extend.r_orderToTrialExtensionRequest_commerceOrderId ===
									Number(placedOrder.id)
								);
							}
						) as TrialExtend[];

						modalContext.onOpenModal({
							body: (
								<ExtendSSATrialModal
									accountId={selectedAccountId}
									firstExtendRequest={
										extendRequests?.length === 0
									}
									mutatePlacedOrder={mutatePlacedOrder}
									onClose={modalContext.onClose}
									order={placedOrder}
									ssaTrialExtendMutate={ssaTrialExtendMutate}
								/>
							),
							header: `Extend ${placedOrder.id} Trial`,
						});
					}}
				>
					{i18n.translate('extend-trial')}
				</DropDown.Item>
			</ClayTooltipProvider>

			<ClayTooltipProvider>
				<DropDown.Item
					data-tooltip-align="left"
					disabled={false}
					hidden={isAdmin(placedOrder)}
					onClick={() => {
						const ssaTrialsExtendRequests = ssaTrialExtend.items;
						const extendRequests = ssaTrialsExtendRequests?.filter(
							(extend: TrialExtend) =>
								extend.r_orderToTrialExtensionRequest_commerceOrderId ===
								Number(placedOrder.id)
						) as TrialExtend[];

						if (!extendRequests) {
							return;
						}

						const extendRequestsCount = extendRequests?.filter(
							(extend: TrialExtend) => {
								return (
									extend.dueStatus?.key ===
										ExtendRequestStatus.APPROVED ||
									extend.dueStatus?.key ===
										ExtendRequestStatus.AUTO_APPROVED
								);
							}
						) as TrialExtend[];

						modalContext.onOpenModal({
							body: (
								<ExtendRequestModal
									mutatePlacedOrder={mutatePlacedOrder}
									onClose={modalContext.onClose}
									order={placedOrder}
									ssaTrialExtendMutate={ssaTrialExtendMutate}
									trialExtend={extendRequests[0]}
									trialExtendCount={
										extendRequestsCount?.length
									}
								/>
							),
							center: true,
						});
					}}
				>
					{i18n.translate('view-request')}
				</DropDown.Item>
			</ClayTooltipProvider>

			<ClayTooltipProvider>
				<DropDown.Item
					data-tooltip-align="left"
					disabled={
						placedOrder.orderStatusInfo.label !==
						OrderStatus.IN_PROGRESS
					}
					onClick={() =>
						modalContext.onOpenModal({
							body: (
								<ExpireSSAModal
									accountId={selectedAccountId}
									mutate={mutatePlacedOrder}
									onClose={modalContext.onClose}
									order={placedOrder}
								/>
							),
							header: `Expire ${placedOrder.id} Trial`,
							status: undefined,
						})
					}
				>
					{i18n.translate('expire-trial')}
				</DropDown.Item>
			</ClayTooltipProvider>

			<ClayTooltipProvider>
				<DropDown.Item
					data-tooltip-align="left"
					disabled={
						placedOrder.orderStatusInfo.label !==
						OrderStatus.IN_PROGRESS
					}
					onClick={() => {
						window.open(
							`https://${
								placedOrder?.customFields?.[
									OrderCustomFields.TRIAL_VIRTUAL_HOST
								]
							}`
						);
					}}
				>
					{i18n.translate('go-to-trial')}
				</DropDown.Item>
			</ClayTooltipProvider>
		</DropDown.ItemList>
	);
}

export default TrialActions;
