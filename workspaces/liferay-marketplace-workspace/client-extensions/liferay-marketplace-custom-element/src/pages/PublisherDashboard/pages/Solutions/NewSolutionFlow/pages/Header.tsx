/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {ClayRadio, ClayRadioGroup} from '@clayui/form';
import ClayIcon from '@clayui/icon';
import {filesize} from 'filesize';
import ReactQuill from 'react-quill';

import {DropzoneUpload} from '../../../../../../components/DropzoneUpload/DropzoneUpload';
import {
	FileList,
	UploadedFile,
} from '../../../../../../components/FileList/FileList';
import Form from '../../../../../../components/MarketplaceForm';
import {
	SolutionTypes,
	useSolutionContext,
} from '../../../../../../context/SolutionContext';
import i18n from '../../../../../../i18n';
import {swapImageElements} from '../../../../constants';
import {ACCEPT_FILE_TYPES} from '../../../Apps/AppCreationFlow/StorefrontPage/CustomizeAppStorefrontPage';
import {MAX_IMAGE_QUANTITY, MAX_SIZE_5MBS} from '../../constants';

enum RadioOptions {
	EMBED_VIDEO_URL = 'embed-video-url',
	UPLOAD_IMAGES = 'upload-images',
}

const Header = () => {
	const [
		{
			header: {description, headerImages, radioValue, title},
		},
		dispatch,
	] = useSolutionContext();

	const handleUpload = (files: File[]) => {
		const totalImages = (headerImages?.length || 0) + files.length;

		if (totalImages > MAX_IMAGE_QUANTITY) {
			return;
		}

		const newUploadedFiles: UploadedFile[] = files.map((file) => ({
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
		}));

		dispatch({
			payload: {
				headerImages: headerImages
					? [...headerImages, ...newUploadedFiles]
					: newUploadedFiles,
			},
			type: SolutionTypes.SET_HEADER,
		});
	};

	const handleDelete = async (id: string) => {
		const files = headerImages.filter(
			(uploadedFile) => uploadedFile.id !== id
		);

		dispatch({
			payload: {
				headerImages: files,
			},
			type: SolutionTypes.SET_HEADER,
		});
	};

	const handleArrowClick = (index: number, direction: string) => {
		const newIndex = direction === 'up' ? index - 1 : index + 1;

		const files = swapImageElements(headerImages, index, newIndex);

		files[index].changed = true;
		files[newIndex].changed = true;

		dispatch({
			payload: {
				headerImages,
			},
			type: SolutionTypes.SET_HEADER,
		});
	};

	return (
		<div className="mb-4 solutions-form-header">
			<h3>{i18n.translate('solution-header')}</h3>

			<hr />

			<Form.Label className="mt-2" htmlFor="title" info="Title" required>
				Title
			</Form.Label>

			<Form.Input
				name="title"
				onChange={(event) =>
					dispatch({
						payload: {[event.target.name]: event.target.value},
						type: SolutionTypes.SET_HEADER,
					})
				}
				placeholder="Enter title header"
				type="text"
				value={title}
			/>

			<Form.Label
				className="mt-5"
				htmlFor="description"
				info="Description"
				required
			>
				{i18n.translate('description')}
			</Form.Label>

			<div className="rich-text-editor">
				<ReactQuill
					onChange={(event: any) => {
						dispatch({
							payload: {description: event},
							type: SolutionTypes.SET_HEADER,
						});
					}}
					placeholder="Insert text here"
					value={description as any}
				/>
			</div>

			<Form.Label className="mt-5" htmlFor="text" required>
				Content Media Type
			</Form.Label>

			<ClayRadioGroup
				className="d-flex flex-column mt-1"
				onChange={(event: any) => {
					dispatch({
						payload: {radioValue: event},
						type: SolutionTypes.SET_HEADER,
					});
				}}
				value={radioValue}
			>
				<ClayRadio label="Upload images" value="upload-images" />

				<ClayRadio label="Embed video URL" value="embed-video-url" />
			</ClayRadioGroup>

			{radioValue === RadioOptions.EMBED_VIDEO_URL && (
				<>
					<Form.Label className="mt-5" htmlFor="url" required>
						Video URL
					</Form.Label>

					<Form.Input
						name="video-url"
						placeholder="http://"
						type="text"
					/>

					<Form.HelpMessage>
						You can paste links directly from YouTube.
					</Form.HelpMessage>

					<div className="border d-flex flex-row mt-5 p-4 rounded">
						<div className="align-items-center d-flex justify-content-center rounded video-player">
							<ClayIcon symbol="video" />
						</div>

						<Form.Input
							className="ml-3"
							name="video-description"
							placeholder="Video description"
							type="text"
						/>
					</div>
				</>
			)}

			{radioValue === RadioOptions.UPLOAD_IMAGES && (
				<>
					<Form.Label className="mb-4 mt-2" htmlFor="description">
						Add up to 5 images
					</Form.Label>

					{!!headerImages?.length && (
						<FileList
							isProcessing={false}
							onArrowClick={handleArrowClick}
							onChangeInput={(newImagesInputs) =>
								dispatch({
									payload: {
										headerImages: newImagesInputs,
									},
									type: SolutionTypes.SET_HEADER,
								})
							}
							onDelete={handleDelete}
							type="image"
							uploadedFiles={headerImages}
							uploadedImages={headerImages}
						/>
					)}

					<DropzoneUpload
						acceptFileTypes={ACCEPT_FILE_TYPES}
						buttonText="Select a file"
						description="Only gif, jpg, png are allowed. Max file size is 5MB "
						maxFiles={5}
						maxSize={MAX_SIZE_5MBS}
						multiple={true}
						onHandleUpload={handleUpload}
						title="Drag and drop to upload or"
					/>
				</>
			)}
		</div>
	);
};

export default Header;
