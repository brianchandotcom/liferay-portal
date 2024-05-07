/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayIcon from '@clayui/icon';
import ReactQuill from 'react-quill';

import Form from '../../../../../../components/MarketplaceForm';
import i18n from '../../../../../../i18n';

const TextAndVideos: React.FC<any> = ({block, onChange}) => {
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
					value={content.title}
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
				<Form.Label htmlFor="url" info="video">
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
			</div>
		</>
	);
};

export default TextAndVideos;
