/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import folderIcon from '../../assets/icons/folder_fill_icon.svg';
import {UploadedFile} from './FileList';

import './DocumentFileItem.scss';

import ClayIcon from '@clayui/icon';

import CircularProgress from '../CircularProgress';

type DocumentFileItemProps = {
	isProcessing: boolean;
	onDelete: (id: string, versionName?: string) => void;
	uploadedFile: UploadedFile;
	versionName?: string;
};

export function DocumentFileItem({
	isProcessing,
	onDelete,
	uploadedFile,
	versionName,
}: DocumentFileItemProps) {
	const isLoading = isProcessing || !uploadedFile?.uploaded;

	return (
		<div className="document-file-list-item-container">
			<div className="document-file-list-item-left-content">
				<div className="document-file-list-item-left-content-icon-container">
					{isLoading ? (
						<CircularProgress
							height={50}
							pathColor="#ffffff"
							progress={uploadedFile?.progress}
							progressColor="#0B5FFF"
							width={50}
						/>
					) : (
						<img
							alt="Folder Icon"
							className="document-file-list-item-left-content-icon"
							src={folderIcon}
						/>
					)}
				</div>

				<div className="document-file-list-item-left-content-text-container">
					<span className="d-flex document-file-list-item-left-content-text-file-name">
						{uploadedFile?.fileName}
						{uploadedFile.uploaded &&
							uploadedFile.progress === 100 && (
								<ClayIcon
									className="document-file-list-item-icon-check ml-4"
									symbol="check"
								/>
							)}
					</span>

					<span className="document-file-list-item-left-content-text-file-size">
						{String(uploadedFile?.readableSize)}
					</span>
				</div>
			</div>

			{!isProcessing && (
				<button
					className="document-file-list-item-button"
					onClick={() => onDelete(uploadedFile?.id, versionName)}
				>
					Remove
				</button>
			)}
		</div>
	);
}
