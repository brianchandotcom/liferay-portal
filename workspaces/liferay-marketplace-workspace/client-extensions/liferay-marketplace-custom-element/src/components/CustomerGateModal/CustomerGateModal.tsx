/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayButton from '@clayui/button';
import ClayLink from '@clayui/link';
import ClayModal, {useModal} from '@clayui/modal';

import './CustomerGateModal.scss';

interface CustomerGateModal {
	handleClose: () => void;
}

export function CustomerGateModal({handleClose}: CustomerGateModal) {
	const {observer, onClose} = useModal({
		onClose: handleClose,
	});

	return (
		<ClayModal
			center
			className="customer-gate-modal-container"
			observer={observer}
		>
			<ClayModal.Header>Liferay Cloud App</ClayModal.Header>

			<ClayModal.Body>
				<p>
					This is a Liferay Cloud app. Cloud apps are compatible with
					Liferay Experience Cloud accounts only. Trial or &nbsp;
					<ClayLink
						displayType="primary"
						href="https://www.liferay.com/products/liferay-cloud-capabilities"
						target="_blank"
					>
						Learn more about Liferay Experience Cloud.
					</ClayLink>
				</p>
			</ClayModal.Body>

			<ClayModal.Footer
				last={
					<ClayButton.Group spaced>
						<ClayButton
							displayType="secondary"
							onClick={() => onClose()}
						>
							Close
						</ClayButton>

						<ClayButton
							onClick={() => {
								window.open(
									'https://help.liferay.com',
									'_blank'
								);
							}}
						>
							Contact Support
						</ClayButton>
					</ClayButton.Group>
				}
			/>
		</ClayModal>
	);
}
