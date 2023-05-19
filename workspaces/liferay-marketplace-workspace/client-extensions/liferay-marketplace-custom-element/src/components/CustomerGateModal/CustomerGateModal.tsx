/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
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
