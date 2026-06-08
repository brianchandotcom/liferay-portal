/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayButton from '@clayui/button';
import getCN from 'classnames';
import React from 'react';
import Loading, {Align} from '~/shared/components/Loading';
import Form, {validateInputMessage} from '~/shared/components/form';
import Modal from '~/shared/components/modal';
import {sub} from '~/shared/util/lang';

interface IDeleteConfirmationModalProps
	extends React.HTMLAttributes<HTMLElement> {
	deleteButtonLabel?: string;
	deleteConfirmationText: string;
	disabled: boolean;
	onClose: () => void;
	onSubmit: () => void;
	title?: string;
}

const DeleteConfirmationModal: React.FC<IDeleteConfirmationModalProps> = ({
	children,
	className,
	deleteButtonLabel = Liferay.Language.get('delete'),
	deleteConfirmationText,
	disabled,
	onClose,
	onSubmit,
	title = Liferay.Language.get('confirm'),
}) => (
	<Modal
		className={getCN('confirmation-modal-root', 'modal-warning', className)}
	>
		<Form
			initialValues={{
				delete: '',
			}}
			onSubmit={onSubmit}
		>
			{({handleSubmit, isSubmitting, isValid}) => (
				<Form.Form onSubmit={handleSubmit}>
					<Modal.Header
						iconSymbol="warning-full"
						onClose={onClose}
						title={title}
					/>

					<Modal.Body>
						<div className="text-secondary">{children}</div>

						<div className="font-weight-bold mb-3">
							{sub(
								Liferay.Language.get('copy-the-following-x'),
								[
									<span
										className="font-weight-normal text-secondary"
										key="deleteConfirmationText"
									>
										{deleteConfirmationText}
									</span>,
								],
								false
							)}
						</div>

						<Form.Input
							autoFocus
							data-testid="delete-confirmation-input"
							disabled={disabled}
							name="delete"
							validate={validateInputMessage(
								deleteConfirmationText
							)}
						/>
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
							disabled={disabled || !isValid || isSubmitting}
							displayType="warning"
							type="submit"
						>
							{isSubmitting && <Loading align={Align.Left} />}

							{deleteButtonLabel}
						</ClayButton>
					</Modal.Footer>
				</Form.Form>
			)}
		</Form>
	</Modal>
);

export default DeleteConfirmationModal;
