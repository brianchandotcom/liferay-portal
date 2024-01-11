/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayModal from '@clayui/modal';
import {Observer, Size} from '@clayui/modal/lib/types';
import {ReactNode} from 'react';

interface ModalProps {
	center?: boolean;
	children: ReactNode;
	observer: Observer;
	size?: Size;
}

const ModalDetails = ({children, observer, size, ...props}: ModalProps) => {
	return (
		<ClayModal center observer={observer} size={size} {...props}>
			{children}
		</ClayModal>
	);
};

export default ModalDetails;
