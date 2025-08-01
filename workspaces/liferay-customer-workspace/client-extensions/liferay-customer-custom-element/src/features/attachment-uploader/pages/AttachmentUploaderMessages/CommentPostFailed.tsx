/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {useAppPropertiesContext} from '~/contexts/AppPropertiesContext';
import i18n from '~/utils/I18n';
import routerPath from '~/utils/routerPath';

import AttachmentMessage from '../../components/AttachmentMessage/AttachmentMessage';

interface IUploadParams {
	ticketId: string;
	uploadAccountKey: string;
}

const CommentPostFailed = ({ticketId, uploadAccountKey}: IUploadParams) => {
	const pageRoutes = routerPath();
	const {helpCenterURL} = useAppPropertiesContext();

	return (
		<AttachmentMessage
			icon="warning-full"
			subtitle="please-check-again-later"
			title="your-attachment-is-uploaded-however-we-encountered-a-problem-posting-your-comment-the-system-is-automatically-retrying-to-send-it"
		>
			{uploadAccountKey && (
				<a
					className="btn btn-secondary mr-2 uploader-secondary-button"
					href={`${pageRoutes.project(uploadAccountKey)}/attachments`}
				>
					{i18n.translate('return-to-attachments')}
				</a>
			)}

			{ticketId && (
				<a
					className="btn btn-primary button-rounded"
					href={`${helpCenterURL}/${ticketId}`}
				>
					{i18n.translate('return-to-ticket')}
				</a>
			)}
		</AttachmentMessage>
	);
};

export default CommentPostFailed;
