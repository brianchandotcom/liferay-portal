/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayModal from '@clayui/modal';
import {Observer, Size, Status} from '@clayui/modal/lib/types';
import {ReactElement, ReactNode} from 'react';

type ModalProps = {
	children: ReactNode;
	observer: Observer;
	size: Size;
	status?: Status;
	subtitle?: string;
	title?: string;
	first?: ReactElement;
	last?: ReactElement;
	visible: boolean;
};

const Modal = ({
	children,
	observer,
	size,
	title,
	status,
	first,
	last,
	subtitle,
	visible,
}: ModalProps) => {
	if (!visible) {
		return null;
	}

	return (
		<ClayModal center observer={observer} size={size} status={status}>
			<ClayModal.Header>{title}</ClayModal.Header>

			{subtitle && (
				<ClayModal.SubtitleSection>
					<ClayModal.Subtitle className="legend-text">
						{subtitle}
					</ClayModal.Subtitle>
				</ClayModal.SubtitleSection>
			)}

			<ClayModal.Body>{children}</ClayModal.Body>

			{first || (last && <ClayModal.Footer first={first} last={last} />)}
		</ClayModal>
	);
};

export default Modal;
