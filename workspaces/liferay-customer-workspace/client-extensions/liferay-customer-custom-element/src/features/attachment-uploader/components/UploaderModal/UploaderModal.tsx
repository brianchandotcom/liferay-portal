/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Button as ClayButton} from '@clayui/core';
import ClayIcon from '@clayui/icon';
import ClayModal from '@clayui/modal';
import {Observer} from '@clayui/modal/lib/types';
import i18n from '~/utils/I18n';

import './UploaderModal.css';
interface IUploaderModalProps {
	attachmentName: string;
	handleNavigateToAttachments: () => void;
	handleNavigateToTicket: () => void;
	observer: Observer;
}

const UploaderModal = ({
	attachmentName,
	handleNavigateToAttachments,
	handleNavigateToTicket,
	observer,
}: IUploaderModalProps) => {
	return (
		<ClayModal center observer={observer}>
			<div className="uploader-modal-containter">
				<div className="d-flex justify-content-center pb-4">
					<div className="uploader-icon">
						<ClayIcon symbol="check-square" />
					</div>
				</div>

				<div className="d-flex justify-content-center pb-4 text-neutral-10">
					<h3>{i18n.translate('upload-confirmation')}</h3>
				</div>

				<div className="d-flex justify-content-center pb-5">
					<p
						className="text-center"
						dangerouslySetInnerHTML={{
							__html: i18n.sub(`x-was-uploaded-successfully`, [
								`<strong>${attachmentName}</strong> <br>`,
							]),
						}}
					/>
				</div>

				<div>
					<div className="d-flex justify-content-center">
						<ClayButton
							className="mr-2 uploader-attachments-button"
							displayType="secondary"
							onClick={handleNavigateToAttachments}
						>
							{i18n.translate('go-to-attachments')}
						</ClayButton>

						<ClayButton
							className="uploader-ticket-button"
							displayType="primary"
							onClick={handleNavigateToTicket}
						>
							{i18n.translate('return-to-ticket')}
						</ClayButton>
					</div>
				</div>
			</div>
		</ClayModal>
	);
};

export default UploaderModal;
