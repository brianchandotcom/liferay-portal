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

import classnames from 'classnames';
import Dropzone from 'react-dropzone';

import documentIcon from '../../assets/icons/document_icon.svg';

import './DropzoneUpload.scss';

interface DropzoneUploadProps {
	acceptFileTypes: {
		[key: string]: string[];
	};
	buttonText: string;
	description: string;
	maxFiles: number;
	maxSize?: number;
	multiple: boolean;
	onHandleUpload: (files: File[]) => void;
	title: string;
}

export function DropzoneUpload({
	acceptFileTypes,
	buttonText,
	description,
	maxFiles,
	maxSize,
	multiple,
	onHandleUpload,
	title,
}: DropzoneUploadProps) {
	return (
		<Dropzone
			accept={acceptFileTypes}
			maxFiles={maxFiles}
			maxSize={maxSize}
			multiple={multiple}
			onDropAccepted={onHandleUpload}
		>
			{({getInputProps, getRootProps, isDragActive, isDragReject}) => (
				<div
					className={classnames('dropzone-upload-container', {
						'dropzone-upload-container-active': isDragActive,
						'dropzone-upload-container-reject': isDragReject,
					})}
					{...getRootProps()}
				>
					<div className="dropzone-upload-document-container">
						<img
							alt="Document icon"
							className="dropzone-upload-document-icon"
							src={documentIcon}
						/>
					</div>

					<div className="dropzone-upload-text-container">
						<span className="dropzone-upload-text">{title}</span>

						<button className="dropzone-upload-button">
							<span className="dropzone-upload-button-text">
								{buttonText}
							</span>
						</button>
					</div>

					<span className="dropzone-upload-description">
						{description}
					</span>

					<input {...getInputProps()} />
				</div>
			)}
		</Dropzone>
	);
}
