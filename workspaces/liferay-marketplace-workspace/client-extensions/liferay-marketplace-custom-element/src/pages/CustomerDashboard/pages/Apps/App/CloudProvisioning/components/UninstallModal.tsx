/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayButton from '@clayui/button';
import {useModal} from '@clayui/modal';

import Modal from '../../../../../../../components/Modal';
import i18n from '../../../../../../../i18n';
import {ProvisioningRow} from '../hooks/useProvisioningData';

type UninstallModalProps = {
	loading: boolean;
	modal: ReturnType<typeof useModal>;
	provisioningRow: ProvisioningRow;
	uninstall: (provisioningRow: ProvisioningRow) => void;
};

const UninstallModal = ({
	loading,
	modal,
	provisioningRow,
	uninstall,
}: UninstallModalProps) => (
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
					await uninstall(provisioningRow);

					modal.onClose();
				}}
				size="sm"
			>
				{i18n.translate('confirm-uninstall')}
			</ClayButton>
		}
		observer={modal.observer}
		size={'md' as any}
		title={i18n.translate('confirm-uninstall-terms')}
		visible={modal.open}
	>
		<p>
			{i18n.translate(
				'deleting-a-service-cannot-be-undone-confirm-the-deletion-before-proceeding'
			)}
		</p>

		<p>
			{i18n.translate(
				'the-app-and-all-its-client-extensions-services-will-be-deleted-and-uninstalled-from-liferay-dxp'
			)}
		</p>
	</Modal>
);

export default UninstallModal;
