/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {ClayRadio, ClayRadioGroup} from '@clayui/form';
import ClayIcon from '@clayui/icon';
import {useState} from 'react';
import ReactQuill from 'react-quill';

import {DropzoneUpload} from '../../../../../../components/DropzoneUpload/DropzoneUpload';
import Form from '../../../../../../components/MarketplaceForm';
import i18n from '../../../../../../i18n';
import {ACCEPT_FILE_TYPES} from '../../../../../StorefrontPage/CustomizeAppStorefrontPage';
import {MAX_SIZE_5MBS} from '../../constants';

enum RadioOptions {
	EMBED_VIDEO_URL = 'embed-video-url',
	UPLOAD_IMAGES = 'upload-images',
}

const Header = () => {
	const [editorValue, setEditorValue] = useState('');
	const [radioValue, setRadioValue] = useState('');

	const handleUpload = (_files: File[]) => null;

	return (
		<div className="mb-4 solutions-form-header">
			<h3>{i18n.translate('solution-header')}</h3>

			<hr />

			<Form.Label className="mt-2" htmlFor="title" required>
				Title
			</Form.Label>

			<Form.Input
				name="title"
				placeholder="Enter title header"
				type="text"
			/>

			<Form.Label className="mt-5" htmlFor="description" required>
				{i18n.translate('description')}
			</Form.Label>

			<div className="rich-text-editor">
				<ReactQuill
					onChange={(value) => setEditorValue(value)}
					placeholder="Insert text here"
					value={editorValue}
				/>
			</div>

			<Form.Label className="mt-5" htmlFor="text" required>
				Content Media Type
			</Form.Label>

			<ClayRadioGroup className="d-flex flex-column mt-1">
				<ClayRadio
					label="Upload images"
					onClick={() => {
						setRadioValue(RadioOptions.UPLOAD_IMAGES);
					}}
					value="upload-images"
				/>

				<ClayRadio
					label="Embed video URL"
					onClick={() => setRadioValue(RadioOptions.EMBED_VIDEO_URL)}
					value="embed-video-url"
				/>
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
