/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayModal, {useModal} from '@clayui/modal';
import {filesize} from 'filesize';
import ReactQuill from 'react-quill';

import {DropzoneUpload} from '../../../../../../components/DropzoneUpload/DropzoneUpload';
import {
	FileList,
	UploadedFile,
} from '../../../../../../components/FileList/FileList';
import Form from '../../../../../../components/MarketplaceForm';
import {TextImageBlock} from '../../../../../../context/SolutionContext';
import i18n from '../../../../../../i18n';
import {ACCEPT_FILE_TYPES} from '../../../Apps/AppCreationFlow/StorefrontPage/CustomizeAppStorefrontPage';
import {MAX_IMAGE_QUANTITY, MAX_SIZE_5MBS} from '../../constants';
import {BlockTypeProps} from './BlockPropsType';

const TextAndImages: React.FC<BlockTypeProps<TextImageBlock>> = ({
	block: {content},
	onChange,
}) => {
	const {observer, onOpenChange, open} = useModal();

	return (
		<div className="p-4">
			<Form.Label className="mt-2" htmlFor="title" info="title" required>
				{i18n.translate('title')}
			</Form.Label>

			<Form.Input
				name="title"
				onChange={(event) => onChange({title: event.target.value})}
				placeholder="Enter title header"
				type="text"
				value={content.title || ''}
			/>

			<Form.Label
				className="mt-5"
				htmlFor="description"
				info="description"
				required
			>
				{i18n.translate('description')}
			</Form.Label>

			<div className="mb-4 rich-text-editor">
				<ReactQuill
					onChange={(text) => onChange({description: text})}
					placeholder="Insert text here"
					value={content.description || ''}
				/>
			</div>

			<Form.Label info="images">
				Add up to {MAX_IMAGE_QUANTITY} images
			</Form.Label>

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
				buttonText={i18n.translate('select-a-file')}
				description={i18n.translate(
					'only-gif-jpg-png-are-allowed-ax-file-size-is-5mb'
				)}
				disabled={content?.files?.length === MAX_IMAGE_QUANTITY}
				maxFiles={MAX_IMAGE_QUANTITY}
				maxSize={MAX_SIZE_5MBS}
				multiple={true}
				onDropRejected={(fileList) => {
					if (fileList.length > MAX_IMAGE_QUANTITY) {
						onOpenChange(true);
					}
				}}
				onHandleUpload={(files: File[]) => {
					const totalImages =
						(content.files?.length || 0) + files?.length;

					if (totalImages > MAX_IMAGE_QUANTITY) {
						return onOpenChange(true);
					}

					const newUploadedFiles: UploadedFile[] = files.map(
						(file, index) => ({
							changed: false,
							error: false,
							file,
							fileName: file.name,
							id: crypto.randomUUID(),
							index,
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

			{open && (
				<ClayModal
					center
					observer={observer}
					size={'md' as any}
					status="info"
				>
					<ClayModal.Header>
						{i18n.translate('maximum-number-of-upload-reached')}
					</ClayModal.Header>
					<ClayModal.Body className="pb-8">
						{i18n.sub(
							'you-cannot-upload-more-than-x-files',
							MAX_IMAGE_QUANTITY.toString()
						)}
					</ClayModal.Body>
				</ClayModal>
			)}
		</div>
	);
};

export default TextAndImages;
