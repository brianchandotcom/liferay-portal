/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {ClayRadio, ClayRadioGroup} from '@clayui/form';
import {useState} from 'react';
import ReactQuill from 'react-quill';

import Form from '../../../../../../components/MarketplaceForm';
import i18n from '../../../../../../i18n';

import 'react-quill/dist/quill.snow.css';
import ClayIcon from '@clayui/icon';

const SolutionHeader = () => {
	const [editorValue, setEditorValue] = useState('');

	const [videoURL, setVideoURL] = useState(false);

	return (
		<div className="mb-4 solution-header-container">
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

			<ClayRadioGroup
				className="d-flex flex-column mt-1"
				defaultValue="upload-images"
			>
				<ClayRadio
					label="Upload images"
					onClick={() => setVideoURL(false)}
					value="upload-images"
				/>
				<ClayRadio
					label="Embed video URL"
					onClick={() => setVideoURL(true)}
					value="embed-video-url"
				/>
			</ClayRadioGroup>

			{videoURL && (
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
						You can paste links directly from YouTube, Vimeo,
						Facebook, and Twitch.
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
		</div>
	);
};

export default SolutionHeader;
