/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayButton from '@clayui/button';

import i18n from '../i18n';
import useModalContext, {ModalOptions} from './useModalContext';

type ConfirmationModalProps = {
	onConfirm: () => void;
} & Pick<ModalOptions, 'body' | 'header' | 'status'>;

const useConfirmationModal = () => {
	const modalContext = useModalContext();

	return {
		openModal: ({onConfirm, ...modalOptions}: ConfirmationModalProps) => {
			modalContext.onOpenModal({
				center: true,
				footer: [
					null,
					null,
					[
						<ClayButton
							displayType="secondary"
							key={0}
							onClick={modalContext.onClose}
						>
							{i18n.translate('cancel')}
						</ClayButton>,

						<ClayButton
							className="ml-2"
							displayType="danger"
							key={1}
							onClick={async () => {
								await onConfirm();

								modalContext.onClose();
							}}
						>
							{i18n.translate('confirm')}
						</ClayButton>,
					],
				],
				size: 'md',
				status: 'danger',
				...modalOptions,
			});
		},
	};
};

export {useConfirmationModal};
