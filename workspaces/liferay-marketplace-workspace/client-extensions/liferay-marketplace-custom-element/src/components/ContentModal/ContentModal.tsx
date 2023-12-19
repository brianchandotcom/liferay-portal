/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayButton from '@clayui/button';
import ClayModal, {useModal} from '@clayui/modal';
import DOMPurify from 'isomorphic-dompurify';

type EulaKeysModalProps = ReturnType<typeof useModal> & {
	description: string;
	header: string;
};

export function ContentModal({
	description,
	header,
	observer,
	onOpenChange,
}: EulaKeysModalProps) {
	return (
		<ClayModal observer={observer} size="lg">
			<ClayModal.Header>{header}</ClayModal.Header>
			<ClayModal.Body>
				<div
					dangerouslySetInnerHTML={{
						__html: DOMPurify.sanitize(description),
					}}
				/>
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
}
