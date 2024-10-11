/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayButton from '@clayui/button';
import {useModal} from '@clayui/modal';
import {useRef, useState} from 'react';
import {useNavigate} from 'react-router-dom';
import {KeyedMutator} from 'swr';

import {useMarketplaceContext} from '../../../../../../../context/MarketplaceContext';
import useModalContext from '../../../../../../../hooks/useModalContext';
import i18n from '../../../../../../../i18n';
import {Liferay} from '../../../../../../../liferay/liferay';
import consoleOAuth2 from '../../../../../../../services/oauth/Console';
import {cloudConsoleURLs, openLink} from '../../../../../../../utils/link';
import ProvisioningDetails from '../components/ProvisioningDetails';
import {InstallStatus} from '../types';
import useProvisioningData, {ProvisioningRow} from './useProvisioningData';

type UseProvisioningActionsProps = {
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

const getProvisioningStatus = (provisioningRow: ProvisioningRow) => ({
	inProgress: provisioningRow.status === InstallStatus.IN_PROGRESS,
	isExpired: provisioningRow.status === InstallStatus.EXPIRED,
	isInstalled: provisioningRow.status === InstallStatus.INSTALLED,
	readyToInstall: provisioningRow.status === InstallStatus.READY_TO_INSTALL,
});

const useProvisioningActions = ({
	mutateOrder,
	order,
	resourceRequirements,
	selectedAccount,
}: UseProvisioningActionsProps) => {
	const {
		properties: {cloudConsoleURL},
	} = useMarketplaceContext();
	const {onClose, onOpenModal} = useModalContext();
	const [loading, setLoading] = useState(false);
	const [selectedProvisioningRow, setSelectedProvisioningRow] =
		useState<ProvisioningRow>();
	const navigate = useNavigate();
	const uninstallModal = useModal();

	const onClickInstall = () => {
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
						onClick={onClose}
						size="sm"
					>
						{i18n.translate('cancel')}
					</ClayButton>,
					undefined,
					<ClayButton
						className="ml-2 rounded-lg"
						displayType="primary"
						key="install-done-footer"
						onClick={onClose}
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

	const uninstall = async (provisioningRow: ProvisioningRow) => {
		setLoading(true);

		try {
			await consoleOAuth2.uninstallApp(order.id, {
				id: provisioningRow.id,
				orderItemId: provisioningRow.orderItemId,
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

	const openUninstallModal = (provisioningRow: ProvisioningRow) => {
		setSelectedProvisioningRow(provisioningRow);

		uninstallModal.onOpenChange(true);
	};

	const onOpenDetailsModal = (provisioningRow: ProvisioningRow) => {
		const {inProgress, isInstalled, readyToInstall} =
			getProvisioningStatus(provisioningRow);

		onOpenModal({
			body: (
				<ProvisioningDetails
					account={selectedAccount}
					headerInfo={{
						image: order.placedOrderItems[0].thumbnail,
						licenseType: `${provisioningRow?.type} License for ${selectedAccount.name}`,
						name: order.placedOrderItems[0].name,
					}}
					onClose={onClose}
					provisioningRow={provisioningRow}
				/>
			),
			center: true,
			footer: [
				undefined,
				undefined,
				<div key="details-footer-buttons">
					{readyToInstall && (
						<ClayButton
							className="border border-primary ml-2 rounded-lg text-primary"
							disabled={inProgress}
							displayType="secondary"
							onClick={() => {
								onClose();

								onClickInstall();
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

								openUninstallModal(provisioningRow);
							}}
							size="sm"
						>
							{i18n.translate('uninstall')}
						</ClayButton>
					)}

					<ClayButton
						className="ml-2 rounded-lg"
						displayType="primary"
						onClick={onClose}
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
			action: () => onClickInstall(),
			show: (provisioningRow: ProvisioningRow) =>
				provisioningRow.status === InstallStatus.READY_TO_INSTALL,
			title: i18n.translate('install'),
		},
		{
			action: (provisioningRow: ProvisioningRow) =>
				onOpenDetailsModal(provisioningRow),
			show: () => true,
			title: i18n.translate('view-details'),
		},
		{
			action: (provisioningRow: ProvisioningRow) =>
				openLink(
					cloudConsoleURLs.getProjectServices(
						cloudConsoleURL,
						`${provisioningRow.project}-${provisioningRow.environment}`.toLowerCase()
					)
				),
			show: (provisioningRow: ProvisioningRow) =>
				provisioningRow.status === InstallStatus.INSTALLED,
			title: i18n.translate('go-to-cloud-console'),
		},
		{
			action: (provisioningRow: ProvisioningRow) =>
				openUninstallModal(provisioningRow),
			show: (provisioningRow: ProvisioningRow) => {
				const {isInstalled} = getProvisioningStatus(provisioningRow);

				return isInstalled;
			},
			title: i18n.translate('uninstall'),
		},
	]);

	return {
		actions: provisioningRef.current,
		loading,
		onOpenDetailsModal,
		selectedProvisioningRow,
		uninstall,
		uninstallModal,
	};
};

export default useProvisioningActions;
