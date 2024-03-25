/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import classnames from 'classnames';
import Dropzone, {FileRejection} from 'react-dropzone';

import './DropzoneUpload.scss';

import ClayIcon from '@clayui/icon';

interface DropzoneUploadProps {
	acceptFileTypes: {
		[key: string]: string[];
	};
	buttonText: string;
	description: string;
	disabled?: boolean;
	maxFiles: number;
	maxSize?: number;
	multiple: boolean;
	onDropRejected?: (files: FileRejection[]) => void;
	onHandleUpload: (files: File[], versionName?: string) => void;
	showDocumentIcon?: boolean;
	title: string;
	versionName?: string;
}

export function DropzoneUpload({
	acceptFileTypes,
	buttonText,
	description,
	disabled = false,
	maxFiles,
	maxSize,
	multiple,
	onDropRejected,
	onHandleUpload,
	showDocumentIcon = true,
	title,
	versionName,
}: DropzoneUploadProps) {
	return (
		<Dropzone
			accept={acceptFileTypes}
			disabled={disabled}
			maxFiles={maxFiles}
			maxSize={maxSize}
			multiple={multiple}
			onDropAccepted={(file) => onHandleUpload(file, versionName)}
			onDropRejected={onDropRejected}
			useFsAccessApi={false}
		>
			{({getInputProps, getRootProps, isDragActive, isDragReject}) => (
				<div
					className={classnames('dropzone-upload-container', {
						'dropzone-upload-container-active': isDragActive,
						'dropzone-upload-container-disabled': disabled,
						'dropzone-upload-container-reject': isDragReject,
					})}
					{...getRootProps()}
				>
					{showDocumentIcon && (
						<div className="dropzone-upload-document-container">
							<ClayIcon
								aria-label="Document icon"
								className="dropzone-upload-document-icon"
								symbol="document-text"
							/>
						</div>
					)}

					<div className="dropzone-upload-text-container">
						<span className="dropzone-upload-text">{title}</span>

						<button className="dropzone-upload-button ml-2">
							<span className="dropzone-upload-button-text">
								{buttonText}
							</span>
						</button>
					</div>

					<span className="dropzone-upload-description">
						{description}
					</span>

					{!disabled && <input {...getInputProps()} />}
				</div>
			)}
		</Dropzone>
	);
}
