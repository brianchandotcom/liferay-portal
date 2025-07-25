/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayAlert from '@clayui/alert';
import ClayButton from '@clayui/button';
import DropDown from '@clayui/drop-down';
import {ClayTooltipProvider} from '@clayui/tooltip';
import {useOutletContext} from 'react-router-dom';

import {useMarketplaceContext} from '../../../../context/MarketplaceContext';
import {OrderStatus} from '../../../../enums/Order';
import useModalContext from '../../../../hooks/useModalContext';
import i18n from '../../../../i18n';
import trialOAuth2 from '../../../../services/oauth/Trial';
import {ExtendRequestStatus} from '../../enums/SSATrials';
import ExtendRequestModal from '../ExtendRequestModal';
import ExtendSSATrialModal from '../ExtendSSATrialModal';

type TrialActionsProps = {
	placedOrder: PlacedOrder;
};

function TrialActions({placedOrder}: TrialActionsProps) {
	const {marketplaceUserAccount} = useMarketplaceContext();
	const modalContext = useModalContext();
	const {selectedAccount, ssaTrialExtend, ssaTrialExtendMutate} =
		useOutletContext<any>();

	const onExpireTrial = (order: Order) => trialOAuth2.expireTrial(order.id);

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
			placedOrder.orderStatusInfo.label === OrderStatus.APPROVED ||
			placedOrder.orderStatusInfo.label === OrderStatus.COMPLETED ||
			placedOrder.orderStatusInfo.label === OrderStatus.PENDING ||
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
									accountId={selectedAccount.id}
									firstExtendRequest={
										extendRequests?.length === 0
									}
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

						modalContext.onOpenModal({
							body: (
								<ExtendRequestModal
									onClose={modalContext.onClose}
									order={placedOrder}
									ssaTrialExtendMutate={ssaTrialExtendMutate}
									trialExtend={extendRequests[0]}
									trialExtendCount={extendRequests?.length}
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
						placedOrder.orderStatusInfo.label ===
							OrderStatus.APPROVED ||
						placedOrder.orderStatusInfo.label ===
							OrderStatus.COMPLETED ||
						placedOrder.orderStatusInfo.label ===
							OrderStatus.PENDING
					}
					onClick={() =>
						modalContext.onOpenModal({
							body: (
								<div>
									<ClayAlert
										displayType="warning"
										role={null}
									>
										{i18n.translate(
											'this-action-cannot-be-undone'
										)}
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
											onExpireTrial(
												placedOrder as unknown as Order
											);

											ssaTrialExtendMutate(
												{
													...ssaTrialExtend,
													items: ssaTrialExtend.items.map(
														(
															order: TrialExtend
														) => {
															if (
																order.r_orderToTrialExtensionRequest_commerceOrderId ===
																placedOrder.id
															) {
																return {
																	...order,
																	dueStatus: {
																		key: ExtendRequestStatus.EXTENSION_EXPIRED,
																	},
																};
															}

															return order;
														}
													),
												},
												{
													revalidate: false,
												}
											);

											modalContext.onClose();
										}}
									>
										{i18n.translate('got-it')}
									</ClayButton>
								</div>,
							],
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
						placedOrder.orderStatusInfo.label ===
							OrderStatus.APPROVED ||
						placedOrder.orderStatusInfo.label ===
							OrderStatus.COMPLETED ||
						placedOrder.orderStatusInfo.label ===
							OrderStatus.PENDING
					}
					onClick={() => {
						window.open(
							`https://${
								placedOrder?.customFields?.[
									'trial-virtualhost'
								] as string
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
