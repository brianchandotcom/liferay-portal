/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {AxiosError} from 'axios';

import {DocumentFileItem} from './DocumentFileItem';

import './FileList.scss';
import {ImageFileItem} from './ImageFileItem';

export type UploadedFile = {
	error: boolean | AxiosError;
	file: File;
	fileName: string;
	id: string;
	preview?: string;
	progress: number;
	readableSize:
		| string
		| number
		| any[]
		| {
				exponent: number;
				symbol: any;
				unit: string;
				value: any;
		  };
	uploaded: boolean;
	versionName?: string;
};

type FileListProps = {
	isProcessing: boolean;
	onArrowClick?: (index: number, direction: string) => void;
	onDelete: (id: string, versionName?: string) => void;
	type: 'document' | 'image';
	uploadedFiles: UploadedFile[];
	versionName?: string;
};

export function FileList({
	isProcessing,
	onArrowClick = () => {},
	onDelete,
	type,
	uploadedFiles,
	versionName,
}: FileListProps) {
	return (
		<div className="file-list-container">
			{uploadedFiles?.map((uploadedFile, index) => {
				if (type === 'document') {
					return (
						<DocumentFileItem
							isProcessing={isProcessing}
							key={uploadedFile?.id}
							onDelete={onDelete}
							uploadedFile={uploadedFile}
							versionName={versionName}
						/>
					);
				}

				if (type === 'image') {
					return (
						<ImageFileItem
							index={index}
							isProcessing={isProcessing}
							key={index}
							onArrowClick={onArrowClick}
							onDelete={onDelete}
							position={uploadedFiles.length}
							tooltip="Use the image description to provide more context about the screenshot, such as what is the user trying to accomplish, what are the business requirements met by this screen or anything else you feel would be helpful to guide your potential customer.  This content will be provided in the form of a mouse over of the image."
							uploadedFile={uploadedFile}
							versionName={versionName}
						/>
					);
				}
			})}
		</div>
	);
}
