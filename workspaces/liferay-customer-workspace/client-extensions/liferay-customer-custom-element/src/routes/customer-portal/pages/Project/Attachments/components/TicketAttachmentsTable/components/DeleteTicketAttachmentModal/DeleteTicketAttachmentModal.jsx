/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Button} from '@clayui/core';
import ClayModal from '@clayui/modal';
import classNames from 'classnames';
import {memo} from 'react';
import i18n from '../../../../../../../../../common/I18n';

const DeleteTicketAttachmentModal = ({
	children,
	modalTitle,
	observer,
	onClose,
	onDelete,
	removing,
}) => (
	<ClayModal
		center
		className="delete-ticket-attachment-modal"
		observer={observer}
	>
		<ClayModal.Header className="h-100 p-4">
			<span className="header-modal-title mb-0 text-neutral-10">
				{modalTitle}
			</span>
		</ClayModal.Header>

		<ClayModal.Body className="px-4 py-3">{children}</ClayModal.Body>

		<ClayModal.Footer
			className="p-4"
			last={
				<div className="d-flex justify-content-end">
					<Button
						aria-label={i18n.translate('cancel')}
						displayType="secondary"
						onClick={onClose}
					>
						{i18n.translate('cancel')}
					</Button>

					<Button
						aria-label={i18n.translate('delete')}
						className={classNames('bg-danger d-flex ml-3', {
							'cp-deactivate-loading': removing,
						})}
						disabled={removing}
						onClick={onDelete}
					>
						{removing ? (
							<>
								<span className="cp-spinner mr-2 mt-1 spinner-border spinner-border-sm" />
								{i18n.translate('deleting')}
							</>
						) : (
							`${i18n.translate('delete')}`
						)}
					</Button>
				</div>
			}
		/>
	</ClayModal>
);

export default memo(DeleteTicketAttachmentModal);
