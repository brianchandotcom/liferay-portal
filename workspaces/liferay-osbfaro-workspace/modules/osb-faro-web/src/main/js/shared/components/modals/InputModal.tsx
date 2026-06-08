/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayButton from '@clayui/button';
import {noop} from 'lodash';
import React, {useRef} from 'react';
import Input from '~/shared/components/Input';
import Modal from '~/shared/components/modal';

interface IInputModalProps {
	className: string;
	onClose: () => void;
	onSubmit: (value: string | number) => void;
	placeholder: string;
	title: string;
}

const InputModal: React.FC<IInputModalProps> = ({
	className,
	onClose,
	onSubmit = noop,
	placeholder,
	title,
}) => {
	const _inputRef = useRef<any>();

	return (
		<Modal className={className} size="sm">
			<Modal.Header onClose={onClose} title={title} />

			<Modal.Body>
				<Input placeholder={placeholder} ref={_inputRef} />
			</Modal.Body>

			<Modal.Footer>
				<ClayButton
					className="button-root"
					displayType="secondary"
					onClick={onClose}
				>
					{Liferay.Language.get('cancel')}
				</ClayButton>

				<ClayButton
					className="button-root"
					displayType="secondary"
					onClick={() =>
						onSubmit(
							_inputRef?.current?._elementRef?.current?.value
						)
					}
				>
					{Liferay.Language.get('submit')}
				</ClayButton>
			</Modal.Footer>
		</Modal>
	);
};

export default InputModal;
