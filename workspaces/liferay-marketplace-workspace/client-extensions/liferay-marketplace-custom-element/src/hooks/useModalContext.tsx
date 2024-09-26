/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Context} from '@clayui/modal';
import {Size} from '@clayui/modal/lib/types';
import {ReactElement, useContext} from 'react';

interface ModalOptions {
	body: ReactElement;
	center?: boolean;
	footer?: (undefined | ReactElement)[];
	header?: string;
	size: Size;
}

const useModalContext = () => {
	const [state, dispatch] = useContext(Context);

	return {
		onClose: () => dispatch({type: 0}),
		onOpenModal: ({body, center, footer, header, size}: ModalOptions) => {
			dispatch({
				payload: {
					body,
					center,
					footer,
					header,
					size,
				},
				type: 1,
			});
		},
		state,
	};
};

export default useModalContext;
