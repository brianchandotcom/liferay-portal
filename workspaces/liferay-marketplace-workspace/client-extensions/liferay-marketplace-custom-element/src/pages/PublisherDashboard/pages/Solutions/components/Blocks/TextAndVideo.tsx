/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ReactQuill from 'react-quill';

import Form from '../../../../../../components/MarketplaceForm';
import VideoThumbnail from '../../../../../../components/VideoThumbnail';
import {TextVideoBlock} from '../../../../../../context/SolutionContext';
import i18n from '../../../../../../i18n';
import {BlockTypeProps} from './BlockPropsType';

const TextAndVideos: React.FC<BlockTypeProps<TextVideoBlock>> = ({
	block: {
		content: {description = '', title = '', videoUrl = ''},
	},
	onChange,
}) => {
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
					value={title}
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
						value={description}
					/>
				</div>
			</div>

			<div className="p-4">
				<Form.Label htmlFor="url" info="video">
					Video URL
				</Form.Label>

				<Form.Input
					name="video-url"
					onChange={({target: {value}}) =>
						onChange({videoUrl: value})
					}
					placeholder="http://"
					type="text"
					value={videoUrl}
				/>

				<Form.HelpMessage>
					You can paste links directly from YouTube.
				</Form.HelpMessage>

				<div className="border d-flex flex-row mt-5 p-4 rounded">
					<VideoThumbnail videoURL={videoUrl} />

					<Form.Input
						className="ml-3"
						name="video-description"
						onChange={({target: {value}}) =>
							onChange({videoDescription: value})
						}
						placeholder="Video description"
						type="text"
					/>
				</div>
			</div>
		</>
	);
};

export default TextAndVideos;
