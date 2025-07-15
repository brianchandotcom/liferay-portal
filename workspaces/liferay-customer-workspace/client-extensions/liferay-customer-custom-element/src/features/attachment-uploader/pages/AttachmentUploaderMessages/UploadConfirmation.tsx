/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {useLocation} from 'react-router-dom';
import {useAppPropertiesContext} from '~/contexts/AppPropertiesContext';
import i18n from '~/utils/I18n';
import routerPath from '~/utils/routerPath';

import AttachmentMessage from '../../components/AttachmentMessage/AttachmentMessage';

const UploadConfirmation = () => {
	const {state} = useLocation();
	const pageRoutes = routerPath();
	const {helpCenterURL} = useAppPropertiesContext();

	return (
		<AttachmentMessage
			icon="check-square"
			subtitle={i18n.sub('x-was-uploaded-successfully', [
				state?.attachmentName,
			])}
			title="upload-confirmation"
		>
			{state?.uploadAccountKey && (
				<a
					className="btn btn-secondary mr-2 uploader-secondary-button"
					href={`${pageRoutes.project(state.uploadAccountKey)}/attachments`}
				>
					{i18n.translate('go-to-attachments')}
				</a>
			)}

			{state?.ticketId && (
				<a
					className="btn btn-primary uploader-primary-button"
					href={`${helpCenterURL}/browse/${state.ticketId}`}
				>
					{i18n.translate('return-to-ticket')}
				</a>
			)}
		</AttachmentMessage>
	);
};

export default UploadConfirmation;
