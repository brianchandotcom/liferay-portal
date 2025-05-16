/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Button as ClayButton} from '@clayui/core';
import ClayIcon from '@clayui/icon';
import i18n from '~/utils/I18n';

import './AttachmentUploadConfirmation.css';

import {useLocation} from 'react-router-dom';
import {Liferay} from '~/services/liferay';
import routerPath from '~/utils/routerPath';

const AttachmentUploadConfirmation = () => {
	const {state} = useLocation();

	const handleNavigateToTicket = () => {
		window.location.href = `https://help.liferay.com/hc/requests/${state?.ticketId}`;
	};

	const handleNavigateToAttachments = () => {
		const pageRoutes = routerPath();

		Liferay.Util.navigate(
			`${pageRoutes.project(state?.uploadAccountKey)}/attachments`
		);
	};

	return (
		<div className="uploader-confirmation-container">
			<div className="uploader-confirmation-box-containter">
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
								`<strong>${state?.attachmentName}</strong> <br>`,
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
		</div>
	);
};

export default AttachmentUploadConfirmation;
