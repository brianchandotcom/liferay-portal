/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayButton, {ButtonProps} from '@clayui/button';
import getCN from 'classnames';
import {noop} from 'lodash';
import React, {useState} from 'react';
import Loading, {Align} from '~/shared/components/Loading';
import Modal from '~/shared/components/modal';

interface IConfirmationModalProps extends React.HTMLAttributes<HTMLDivElement> {
	cancelMessage?: string;
	closeAfterSubmit?: boolean;
	message: string;
	modalVariant?: string;
	onClose: () => void;
	onSubmit: () => any;
	submitButtonDisplay?: ButtonProps['displayType'];
	submitMessage?: string;
	title?: string;
	titleIcon?: string;
}

const ConfirmationModal: React.FC<IConfirmationModalProps> = ({
	cancelMessage = Liferay.Language.get('cancel'),
	className,
	closeAfterSubmit = true,
	message,
	modalVariant = '',
	onClose = noop,
	onSubmit = noop,
	submitButtonDisplay = 'primary',
	submitMessage = Liferay.Language.get('continue'),
	title = Liferay.Language.get('confirm'),
	titleIcon,
	...otherProps
}) => {
	const [submitting, setSubmitting] = useState<boolean>(false);

	return (
		<Modal
			{...otherProps}
			className={getCN('confirmation-modal-root', className, {
				[modalVariant]: modalVariant,
			})}
		>
			<Modal.Header
				iconSymbol={titleIcon}
				onClose={onClose}
				title={title}
			/>

			<Modal.Body>{message}</Modal.Body>

			<Modal.Footer>
				<ClayButton
					className="button-root"
					displayType="secondary"
					onClick={onClose}
				>
					{cancelMessage}
				</ClayButton>

				<ClayButton
					className="button-root"
					displayType={submitButtonDisplay}
					onClick={() => {
						setSubmitting(true);

						const submitVal: any = onSubmit();

						if (submitVal instanceof Promise) {
							submitVal
								.then(() => {
									setSubmitting(false);

									closeAfterSubmit && onClose();
								})
								.catch(() => {
									setSubmitting(false);
								});
						}
						else {
							setSubmitting(false);

							closeAfterSubmit && onClose();
						}
					}}
				>
					{submitting && <Loading align={Align.Left} />}

					{submitMessage}
				</ClayButton>
			</Modal.Footer>
		</Modal>
	);
};

export default ConfirmationModal;
