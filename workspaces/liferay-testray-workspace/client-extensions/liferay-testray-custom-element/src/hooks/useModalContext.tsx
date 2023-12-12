/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Context} from '@clayui/modal';
import {Size} from '@clayui/modal/lib/types';
import {ReactElement, useContext} from 'react';
import {useParams} from 'react-router-dom';

import Form from '../components/Form';

interface ModalOptions {
	body: ReactElement;
	footer?: ReactElement;
	footerDefault?: boolean;
	size: Size;
	title: string;
}

const useModalContext = () => {
	const [state, dispatch] = useContext(Context);
	const params = useParams();

	const onOpenModal = ({
		body,
		footer,
		footerDefault = true,
		size,
		title,
	}: ModalOptions) => {
		dispatch({
			payload: {
				body: (
					<>
						{body}

						<span className="d-none" id="testray-modal-params">
							{JSON.stringify(params)}
						</span>
					</>
				),
				footer: footer
					? [
							undefined,
							undefined,
							footerDefault ? (
								<Form.Footer
									key={4}
									onClose={state.onClose}
									onSubmit={state.onClose}
								/>
							) : (
								footer
							),
					  ]
					: [],
				header: title,
				size,
			},
			type: 1,
		});
	};

	return {onOpenModal, state};
};

export default useModalContext;
