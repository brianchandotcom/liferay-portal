/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

import ClayButton from '@clayui/button';
import {CircularProgressbarWithChildren} from 'react-circular-progressbar';

import arrowNorth from '../../assets/icons/arrow_north_icon.svg';
import arrowSouth from '../../assets/icons/arrow_south_icon.svg';
import {Tooltip} from '../Tooltip/Tooltip';
import {UploadedFile} from './FileList';

import './ImageFileItem.scss';

interface ImageFileItemProps {
	onDelete: (id: string) => void;
	tooltip?: string;
	uploadedFile: UploadedFile;
}

export function ImageFileItem({
	onDelete,
	tooltip,
	uploadedFile,
}: ImageFileItemProps) {
	return (
		<div className="image-file-item-container">
			<div className="image-file-item-arrow-container">
				<ClayButton displayType="unstyled">
					<img
						alt="Arrow Up"
						className="image-file-item-arrow-icon"
						src={arrowNorth}
					/>
				</ClayButton>

				<ClayButton displayType="unstyled">
					<img
						alt="Arrow South"
						className="image-file-item-arrow-icon"
						src={arrowSouth}
					/>
				</ClayButton>
			</div>

			{uploadedFile.uploaded && !uploadedFile.error ? (
				<img
					className="image-file-item-uploaded-preview"
					style={{
						backgroundImage: `url(${uploadedFile?.preview})`,
					}}
				/>
			) : (
				<CircularProgressbarWithChildren
					styles={{
						path: {stroke: '#0B5FFF'},
						root: {
							marginRight: 40,
							width: 50,
						},
					}}
					value={uploadedFile.progress}
				>
					<div
						style={{
							fontSize: 10,
							marginRight: 40,
							marginTop: 75,
						}}
					>
						<strong>{uploadedFile.progress}</strong>
					</div>
				</CircularProgressbarWithChildren>
			)}

			<div className="image-file-item-info-container">
				<div className="image-file-item-info-content">
					<span className="image-file-item-info-content-text">
						{uploadedFile.fileName}
					</span>

					<button
						className="image-file-item-info-content-button"
						onClick={() => onDelete(uploadedFile.id)}
					>
						Remove
					</button>
				</div>

				<div className="image-file-item-info-input-container">
					<input
						className="image-file-item-info-input"
						placeholder="Image description"
					/>

					{tooltip && <Tooltip tooltip={tooltip} />}
				</div>
			</div>
		</div>
	);
}
