/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Button} from '@clayui/core';
import ClayModal from '@clayui/modal';
import i18n from '~/common/I18n';

type OktaSessionModalProps = {
	observer: any;
	onClick: () => void;
	onClose: () => void;
};

const OktaSessionModal: React.FC<OktaSessionModalProps> = ({
	observer,
	onClick,
	onClose,
}) => (
	<ClayModal center className="remove-user-modal" observer={observer}>
		<ClayModal.Header className="h-100 p-4">
			<h2 className="mb-0 text-neutral-10">
				{i18n.translate('session-expired')}
			</h2>
		</ClayModal.Header>

		<ClayModal.Body className="px-4 py-3">
			{i18n.translate(
				'we-have-logged-you-out-of-the-customer-portal-due-to-inactivity-to-continue-please-sign-in-again'
			)}
		</ClayModal.Body>

		<ClayModal.Footer
			className="p-4"
			first={
				<Button
					className="font-weight-bold"
					displayType="unstyled"
					onClick={onClose}
				>
					{i18n.translate('exit-customer-portal')}
				</Button>
			}
			last={
				<Button className="bg-danger d-flex ml-3" onClick={onClick}>
					{i18n.translate('return-to-sign-in')}
				</Button>
			}
		/>
	</ClayModal>
);

export default OktaSessionModal;
