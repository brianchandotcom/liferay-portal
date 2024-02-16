/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayButton from '@clayui/button';
import {Text} from '@clayui/core';
import {CircularProgressbarWithChildren} from 'react-circular-progressbar';

import arrowNorth from '../../assets/icons/arrow_north_icon.svg';
import arrowSouth from '../../assets/icons/arrow_south_icon.svg';
import {Tooltip} from '../Tooltip/Tooltip';
import {UploadedFile} from './FileList';

import './ImageFileItem.scss';

import {ClayInput} from '@clayui/form';

type ImageFileItemProps = {
	index: number;
	onArrowClick?: (index: number, direction: string) => void;
	onDelete: (id: string, versionName?: string) => void;
	position: number;
	tooltip?: string;
	uploadedFile: UploadedFile;
	versionName?: string;
};

export function ImageFileItem({
	index,
	onArrowClick,
	onDelete,
	position,
	tooltip,
	uploadedFile,
	versionName,
}: ImageFileItemProps) {
	return (
		<div className="image-file-item-container">
			<div className="image-file-item-arrow-container">
				{onArrowClick && (
					<>
						<ClayButton
							disabled={index === 0}
							displayType="unstyled"
							onClick={() => onArrowClick(index, 'up')}
						>
							<img
								alt="Arrow Up"
								className="image-file-item-arrow-icon"
								src={arrowNorth}
							/>
						</ClayButton>

						<ClayButton
							disabled={index === position - 1}
							displayType="unstyled"
							onClick={() => onArrowClick(index, 'down')}
						>
							<img
								alt="Arrow South"
								className="image-file-item-arrow-icon"
								src={arrowSouth}
							/>
						</ClayButton>
					</>
				)}
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
					<Text as="span" size={3} weight="normal">
						{uploadedFile.fileName}
					</Text>

					<ClayButton
						displayType="secondary"
						onClick={() => onDelete(uploadedFile.id, versionName)}
						size="sm"
					>
						Remove
					</ClayButton>
				</div>

				<div className="align-items-center d-flex">
					<ClayInput placeholder="Image description" />

					{tooltip && (
						<div style={{marginLeft: '-40px'}}>
							<Tooltip tooltip={tooltip} />
						</div>
					)}
				</div>
			</div>
		</div>
	);
}
