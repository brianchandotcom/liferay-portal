/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayButton from '@clayui/button';
import {useModal} from '@clayui/modal';
import {useRef, useState} from 'react';
import {useNavigate} from 'react-router-dom';
import {KeyedMutator} from 'swr';

import useModalContext from '../../../../../../../hooks/useModalContext';
import i18n from '../../../../../../../i18n';
import {Liferay} from '../../../../../../../liferay/liferay';
import consoleOAuth2 from '../../../../../../../services/oauth/Console';
import ProvisioningDetails from '../components/ProvisioningDetails';
import {InstallStatus} from '../types';
import useProvisioningData from './useProvisioningData';

type OrderItem = ReturnType<
	typeof useProvisioningData
>['provisioningTableData'][0];

type UseProvisioningActionsProp = {
	mutateOrder: KeyedMutator<{
		placedOrder: PlacedOrder;
		product: DeliveryProduct;
	}>;
	order: PlacedOrder;
	resourceRequirements: ReturnType<
		typeof useProvisioningData
	>['resourceRequirements'];
	selectedAccount: Account;
};

const useProvisioningActions = ({
	mutateOrder,
	order,
	resourceRequirements,
	selectedAccount,
}: UseProvisioningActionsProp) => {
	const {onClose, onOpenModal} = useModalContext();
	const [loading, setLoading] = useState<boolean>(false);
	const [selectedOrderItem, setSelectedOrderItem] = useState<OrderItem>();
	const navigate = useNavigate();
	const uninstallModal = useModal();

	const install = () => {
		if (!resourceRequirements.resourceRequest?.userProjects?.length) {
			return onOpenModal({
				body: (
					<p>
						{i18n.translate(
							'you-currently-do-not-have-access-to-any-cloud-projects-please-login-as-a-user-that-has-access-to-a-project-or-contact-your-project-administrator-to-add-you-to-a-project'
						)}
					</p>
				),
				center: true,
				footer: [
					<ClayButton
						className="ml-2 rounded-lg"
						displayType="unstyled"
						key="install-cancel-footer"
						onClick={() => onClose()}
						size="sm"
					>
						{i18n.translate('cancel')}
					</ClayButton>,
					undefined,
					<ClayButton
						className="ml-2 rounded-lg"
						displayType="primary"
						key="install-done-footer"
						onClick={() => onClose()}
						size="sm"
					>
						{i18n.translate('done')}
					</ClayButton>,
				],
				header: i18n.translate('no-cloud-projects-available'),
				size: 'md' as any,
			});
		}

		navigate(`/order/${order?.id}/cloud-provisioning/install`);
	};

	const uninstall = async (orderItem: OrderItem) => {
		setLoading(true);

		try {
			await consoleOAuth2.uninstallApp(order.id, {
				id: orderItem.id,
				orderItemId: orderItem.orderItem,
			});

			await mutateOrder((items: any) => items, {revalidate: true});

			Liferay.Util.openToast({
				message: i18n.translate('your-request-completed-successfully'),
				type: 'success',
			});
		}
		catch (error) {
			console.warn(error);

			Liferay.Util.openToast({
				message: i18n.translate('an-unexpected-error-occurred'),
				type: 'danger',
			});
		}

		setLoading(false);
	};

	function checkOrderItemStatus(orderItem: OrderItem) {
		return {
			inProgress: orderItem.status === InstallStatus.IN_PROGRESS,
			installationInProgress:
				orderItem.status === InstallStatus.IN_PROGRESS,
			isExpired: orderItem.status === InstallStatus.EXPIRED,
			isInstalled: orderItem.status === InstallStatus.INSTALLED,
		};
	}

	const openUninstallModal = (orderItem: OrderItem) => {
		setSelectedOrderItem(orderItem);
		uninstallModal.onOpenChange(true);
	};

	const onOpenDetailsModal = (orderItem: OrderItem) => {
		const isExpired = orderItem.status === InstallStatus.EXPIRED;
		const isInstalled = orderItem.status === InstallStatus.INSTALLED;
		const installationInProgress =
			orderItem.status === InstallStatus.IN_PROGRESS;

		onOpenModal({
			body: (
				<ProvisioningDetails
					account={selectedAccount}
					headerInfo={{
						image: order.placedOrderItems[0].thumbnail,
						licenseType: `${orderItem?.type} License for ${selectedAccount.name}`,
						name: order.placedOrderItems[0].name,
					}}
					onClose={() => onClose()}
					orderItem={orderItem}
				/>
			),
			center: true,
			footer: [
				undefined,
				undefined,
				<div key="details-footer-buttons">
					{!isInstalled && !isExpired && (
						<ClayButton
							className="border border-primary ml-2 rounded-lg text-primary"
							disabled={installationInProgress}
							displayType="secondary"
							onClick={() => {
								onClose();

								install();
							}}
							size="sm"
						>
							{i18n.translate('install')}
						</ClayButton>
					)}

					{isInstalled && (
						<ClayButton
							className="border border-danger ml-2 rounded-lg text-danger"
							displayType="secondary"
							onClick={() => {
								onClose();

								openUninstallModal(orderItem);
							}}
							size="sm"
						>
							{i18n.translate('uninstall')}
						</ClayButton>
					)}

					<ClayButton
						className="ml-2 rounded-lg"
						displayType="primary"
						onClick={() => onClose()}
						size="sm"
					>
						{i18n.translate('done')}
					</ClayButton>
				</div>,
			],
			size: 'lg',
		});
	};

	const provisioningRef = useRef([
		{
			action: () => install(),
			show: (orderItem: OrderItem) =>
				orderItem.status === InstallStatus.READY_TO_INSTALL,
			title: i18n.translate('install'),
		},
		{
			action: (orderItem: OrderItem) => onOpenDetailsModal(orderItem),
			show: () => true,
			title: i18n.translate('view-details'),
		},
		{
			action: (orderItem: OrderItem) => openUninstallModal(orderItem),
			show: (orderItem: OrderItem) => {
				const {isInstalled} = checkOrderItemStatus(orderItem);

				return isInstalled;
			},
			title: i18n.translate('uninstall'),
		},
	]);

	return {
		actions: provisioningRef.current,
		loading,
		onOpenDetailsModal,
		selectedOrderItem,
		uninstall,
		uninstallModal,
	};
};

export default useProvisioningActions;
