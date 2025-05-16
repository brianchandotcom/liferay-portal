/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Button as ClayButton} from '@clayui/core';
import ClayIcon from '@clayui/icon';
import {filesize} from 'filesize';
import i18n from '~/utils/I18n';

import './FileList.css';

import CircularProgress from '~/components/CircularProgress';

import {IAttachment} from '../../AttachmentUploader';

interface IProps {
	attachment: IAttachment;
	isUploading: boolean;
	onDelete: (attachment: IAttachment) => void;
	uploadedFile: IPropsUploadedFile;
}
interface IPropsUploadedFile {
	progress: number;
}

const FileList = ({
	attachment,
	isUploading,
	onDelete,
	uploadedFile,
}: IProps) => {
	return (
		<div className="file-list-container">
			<div className="file-list-item-container">
				<div className="file-list-item-left-content">
					<div className="file-list-item-left-content-icon-container">
						{isUploading ? (
							<div className="image-file-item-loading-container">
								<CircularProgress
									height={80}
									pathColor="#ffffff"
									progress={uploadedFile.progress}
									progressColor="#0B5FFF"
									width={80}
								/>
							</div>
						) : (
							<ClayIcon
								aria-label="Document Icon"
								className="file-list-item-left-content-icon"
								symbol="document-default"
							/>
						)}
					</div>

					<div className="file-list-item-left-content-text-container">
						<span className="d-flex file-list-item-left-content-text-file-name">
							{attachment.file.name}
						</span>

						<span className="file-list-item-left-content-text-file-size">
							{filesize(attachment.file.size)}
						</span>
					</div>
				</div>

				<ClayButton
					aria-label={isUploading ? 'Cancel Upload' : 'Remove'}
					className="file-list-item-button"
					onClick={() => onDelete(attachment)}
				>
					{isUploading
						? i18n.translate('cancel-upload')
						: i18n.translate('remove')}
				</ClayButton>
			</div>
		</div>
	);
};

export default FileList;
