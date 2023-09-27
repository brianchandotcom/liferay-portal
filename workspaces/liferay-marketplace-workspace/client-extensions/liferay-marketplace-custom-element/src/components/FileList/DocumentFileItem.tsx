/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {CircularProgressbarWithChildren} from 'react-circular-progressbar';

import folderIcon from '../../assets/icons/folder_fill_icon.svg';
import {UploadedFile} from './FileList';

import './DocumentFileItem.scss';

interface DocumentFileItemProps {
	onDelete: (id: string) => void;
	uploadedFile: UploadedFile;
}

export function DocumentFileItem({
	onDelete,
	uploadedFile,
}: DocumentFileItemProps) {
	return (
		<div className="document-file-list-item-container">
			<div className="document-file-list-item-left-content">
				<div className="document-file-list-item-left-content-icon-container">
					{uploadedFile.uploaded && !uploadedFile.error ? (
						<img
							alt="Folder Icon"
							className="document-file-list-item-left-content-icon"
							src={folderIcon}
						/>
					) : (
						<CircularProgressbarWithChildren
							styles={{
								path: {stroke: '#0B5FFF'},
								root: {
									width: 50,
								},
							}}
							value={uploadedFile.progress}
						>
							<div style={{fontSize: 10}}>
								<strong>{uploadedFile.progress}</strong>
							</div>
						</CircularProgressbarWithChildren>
					)}
				</div>

				<div className="document-file-list-item-left-content-text-container">
					<span className="document-file-list-item-left-content-text-file-name">
						{uploadedFile.fileName}
					</span>

					<span className="document-file-list-item-left-content-text-file-size">
						{String(uploadedFile.readableSize)}
					</span>
				</div>
			</div>

			<button
				className="document-file-list-item-button"
				onClick={() => onDelete(uploadedFile.id)}
			>
				{uploadedFile.uploaded ? 'Remove' : 'Cancel Upload'}
			</button>
		</div>
	);
}
