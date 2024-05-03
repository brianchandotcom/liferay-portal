/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ReactQuill from 'react-quill';

import {DropzoneUpload} from '../../../../../../components/DropzoneUpload/DropzoneUpload';
import Form from '../../../../../../components/MarketplaceForm';
import {
	SolutionTypes,
	useSolutionContext,
} from '../../../../../../context/SolutionContext';
import i18n from '../../../../../../i18n';
import {ACCEPT_FILE_TYPES} from '../../../Apps/AppCreationFlow/StorefrontPage/CustomizeAppStorefrontPage';
import {MAX_SIZE_5MBS} from '../../constants';

const TextAndImages = () => {
	const [
		{
			details: {
				textImagesBlock: {description, title},
			},
		},
		dispatch,
	] = useSolutionContext();

	const handleUpload = (_files: File[]) => null;

	console.log('aaa', description, title);

	return (
		<>
			<div className="p-4">
				<Form.Label className="mt-2" htmlFor="title" required>
					Title
				</Form.Label>

				<Form.Input
					name="title"
					onChange={(event) =>
						dispatch({
							payload: {
								textImagesBlock: {
									[event.target.name]: event.target.value,
								},
							},
							type: SolutionTypes.SET_DETAILS,
						})
					}
					placeholder="Enter title header"
					type="text"
					value={title}
				/>

				<Form.Label className="mt-5" htmlFor="description" required>
					{i18n.translate('description')}
				</Form.Label>

				<div className="rich-text-editor">
					<ReactQuill
						onChange={(event: any) => {
							dispatch({
								payload: {
									textImagesBlock: {description: event},
								},
								type: SolutionTypes.SET_DETAILS,
							});
						}}
						placeholder="Insert text here"
						value={description as any}
					/>
				</div>
			</div>

			<div className="d-flex p-4">
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
			</div>
		</>
	);
};

export default TextAndImages;
