/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
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
