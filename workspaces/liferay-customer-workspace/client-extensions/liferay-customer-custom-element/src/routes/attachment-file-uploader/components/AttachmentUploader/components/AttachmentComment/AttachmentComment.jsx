/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {ClayCheckbox, ClayInput} from '@clayui/form';
import i18n from '~/common/I18n';

const AttachmentComment = ({
	attachmentComment,
	isNoPersonalDataChecked,
	setAttachmentComment,
	setIsNoPersonalDataChecked,
}) => {
	return (
		<>
			<h5 className="text-neutral-9">
				{i18n.translate('leave-a-comment')}
			</h5>

			<div className="attach-input mb-4">
				<ClayInput
					component="textarea"
					onChange={(event) =>
						setAttachmentComment(event.target.value)
					}
					placeholder={i18n.translate(
						'add-a-description-of-the-file-related-to-this-ticket'
					)}
					type="text"
					value={attachmentComment}
				/>
			</div>

			<div className="attachment-uploader-support-text ml-2">
				<ClayCheckbox
					checked={isNoPersonalDataChecked}
					label={i18n.translate(
						'please-check-this-box-if-the-file-you-upload-does-not-contain-any-personal-data-and-therefore-can-be-uploaded-to-and-accessed-from-any-liferay-support-location-globally'
					)}
					onChange={() =>
						setIsNoPersonalDataChecked(
							(previousAcknowledgmentChecked) =>
								!previousAcknowledgmentChecked
						)
					}
				/>
			</div>
		</>
	);
};

export default AttachmentComment;
