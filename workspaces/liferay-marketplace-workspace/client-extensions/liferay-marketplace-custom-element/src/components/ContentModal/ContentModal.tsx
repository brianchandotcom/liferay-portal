/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayButton from '@clayui/button';
import ClayModal, {useModal} from '@clayui/modal';
import './ContentModal.scss';

type EulaKeysModalProps = ReturnType<typeof useModal>;

export const ContentModal: React.FC<EulaKeysModalProps> = ({
	observer,
	onOpenChange,
}) => {
	return (
		<ClayModal observer={observer} size="lg">
			<ClayModal.Header>End User License Agreement</ClayModal.Header>
			<ClayModal.Body>
				<p>Do you want to save your documents?</p>
			</ClayModal.Body>
			<ClayModal.Footer
				first={
					<ClayButton.Group spaced>
						<ClayButton
							displayType="secondary"
							onClick={() => onOpenChange(false)}
						>
							Cancel
						</ClayButton>
					</ClayButton.Group>
				}
			/>
		</ClayModal>
	);
};
