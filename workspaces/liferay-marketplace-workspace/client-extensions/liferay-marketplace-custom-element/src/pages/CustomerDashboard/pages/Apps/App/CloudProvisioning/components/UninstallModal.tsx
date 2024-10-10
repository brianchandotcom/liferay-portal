/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayButton from '@clayui/button';
import {useModal} from '@clayui/modal';

import Modal from '../../../../../../../components/Modal';
import i18n from '../../../../../../../i18n';
import useProvisioningData from '../hooks/useProvisioningData';

type OrderItem = ReturnType<
	typeof useProvisioningData
>['provisioningTableData'][0];

type UninstallModalProps = {
	loading: boolean;
	modal: ReturnType<typeof useModal>;
	orderItem: OrderItem;
	uninstall: (orderItem: OrderItem) => void;
};

const UninstallModal = ({
	loading,
	modal,
	orderItem,
	uninstall,
}: UninstallModalProps) => {
	return (
		<Modal
			first={
				<ClayButton
					className="rounded-lg"
					displayType="secondary"
					onClick={() => modal.onClose()}
					size="sm"
				>
					{i18n.translate('cancel')}
				</ClayButton>
			}
			last={
				<ClayButton
					className="ml-2 rounded-lg"
					disabled={loading}
					displayType="danger"
					onClick={async () => {
						await uninstall(orderItem);

						modal.onClose();
					}}
					size="sm"
				>
					{i18n.translate('confirm-uninstall')}
				</ClayButton>
			}
			observer={modal.observer}
			size={'md' as any}
			title="Confirm Unstallation Terms"
			visible={modal.open}
		>
			<p>
				{i18n.translate(
					'i-certify-that-all-liferay-software-running-on-instances-activated-with-the-selected-license-has-been-shut-down-there-are-no-active-liferay-installations-or-deployments-associated-with-this-license'
				)}
			</p>

			<p>
				{i18n.translate(
					'a-request-to-uninstall-the-license-will-be-processed-and-it-will-no-longer-be-visible-in-your-account'
				)}
			</p>
		</Modal>
	);
};
export default UninstallModal;
