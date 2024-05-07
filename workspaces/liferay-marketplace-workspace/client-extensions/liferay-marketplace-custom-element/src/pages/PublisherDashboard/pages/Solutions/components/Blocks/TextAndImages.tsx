/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {filesize} from 'filesize';
import ReactQuill from 'react-quill';

import {DropzoneUpload} from '../../../../../../components/DropzoneUpload/DropzoneUpload';
import {
	FileList,
	UploadedFile,
} from '../../../../../../components/FileList/FileList';
import Form from '../../../../../../components/MarketplaceForm';
import i18n from '../../../../../../i18n';
import {ACCEPT_FILE_TYPES} from '../../../Apps/AppCreationFlow/StorefrontPage/CustomizeAppStorefrontPage';
import {MAX_IMAGE_QUANTITY, MAX_SIZE_5MBS} from '../../constants';

// import {BlockTypeProps} from './BlockPropsType';

const TextAndImages: React.FC<any> = ({block, onChange}) => {
	const {content} = block;

	return (
		<>
			<div className="p-4">
				<Form.Label
					className="mt-2"
					htmlFor="title"
					info="title"
					required
				>
					Title
				</Form.Label>

				<Form.Input
					name="title"
					onChange={(event) => onChange({title: event.target.value})}
					placeholder="Enter title header"
					type="text"
					value={content?.title}
				/>

				<Form.Label
					className="mt-5"
					htmlFor="description"
					info="description"
					required
				>
					{i18n.translate('description')}
				</Form.Label>

				<div className="rich-text-editor">
					<ReactQuill
						onChange={(text) => onChange({description: text})}
						placeholder="Insert text here"
						value={content.description}
					/>
				</div>
			</div>

			<div className="p-4">
				<Form.Label info="images">Add up to 5 images</Form.Label>

				{!!content.files?.length && (
					<FileList
						isProcessing={false}
						onChangeInput={(files) => onChange({files})}
						onDelete={(id) => {
							const files = content?.files?.filter(
								(uploadedFile: any) => uploadedFile.id !== id
							);

							onChange({files});
						}}
						type="image"
						uploadedFiles={content?.files}
						uploadedImages={content?.files}
					/>
				)}

				<DropzoneUpload
					acceptFileTypes={ACCEPT_FILE_TYPES}
					buttonText="Select a file"
					description="Only gif, jpg, png are allowed. Max file size is 5MB "
					maxFiles={5}
					maxSize={MAX_SIZE_5MBS}
					multiple={true}
					onHandleUpload={(files: File[]) => {
						const totalImages =
							(content.files?.length || 0) + files.length;

						if (totalImages > MAX_IMAGE_QUANTITY) {
							return;
						}

						const newUploadedFiles: UploadedFile[] = files.map(
							(file) => ({
								changed: false,
								error: false,
								file,
								fileName: file.name,
								id: crypto.randomUUID(),
								index: 0,
								preview: URL.createObjectURL(file),
								progress: 0,
								readableSize: filesize(file.size),
								uploaded: false,
							})
						);

						onChange({
							files: content.files
								? [...content.files, ...newUploadedFiles]
								: newUploadedFiles,
						});
					}}
					title="Drag and drop to upload or"
				/>
			</div>
		</>
	);
};

export default TextAndImages;
