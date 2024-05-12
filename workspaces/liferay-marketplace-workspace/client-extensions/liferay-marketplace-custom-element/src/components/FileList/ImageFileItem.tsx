/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayButton from '@clayui/button';
import {Text} from '@clayui/core';

import {Tooltip} from '../Tooltip/Tooltip';
import {UploadedFile} from './FileList';

import './ImageFileItem.scss';

import {ClayInput} from '@clayui/form';
import ClayIcon from '@clayui/icon';
import classNames from 'classnames';

import i18n from '../../i18n';
import CircularProgress from '../CircularProgress';

type ImageFileItemProps = {
	index: number;
	isProcessing: boolean;
	onArrowClick: (index: number, direction: string) => void;
	onChangeInput: (newImagesInputs: UploadedFile[]) => void;
	onDelete: (id: string, versionName?: string) => void;
	position: number;
	tooltip?: string;
	uploadedFile: UploadedFile;
	uploadedImages: any;
	versionName?: string;
};

export function ImageFileItem({
	index,
	isProcessing,
	onArrowClick,
	onChangeInput,
	onDelete,
	position,
	tooltip,
	uploadedFile,
	uploadedImages,
	versionName,
}: ImageFileItemProps) {
	const showProgress =
		isProcessing && !uploadedFile.uploaded && uploadedFile.progress > 0;

	return (
		<div className="image-file-item-container">
			<div className="image-file-item-arrow-container">
				<ClayButton
					aria-label={i18n.translate('move-up')}
					disabled={isProcessing || index === 0}
					displayType="unstyled"
					onClick={() => onArrowClick(index, 'up')}
				>
					<ClayIcon
						aria-label="Arrow Up"
						className="image-file-item-arrow-icon"
						symbol="order-arrow-up"
					/>
				</ClayButton>

				<ClayButton
					aria-label={i18n.translate('move-down')}
					disabled={isProcessing || index === position - 1}
					displayType="unstyled"
					onClick={() => onArrowClick(index, 'down')}
				>
					<ClayIcon
						aria-label="Arrow South"
						className="image-file-item-arrow-icon"
						symbol="order-arrow-down"
					/>
				</ClayButton>
			</div>

			<div>
				{showProgress ? (
					<div className="image-file-item-loading-container">
						<CircularProgress
							height={80}
							pathColor="#ffffff"
							progress={uploadedFile.progress}
							progressColor="#0B5FFF"
							width={80}
						/>
					</div>
				) : (
					<div className="d-flex">
						<img
							alt="image"
							className="image-file-item-uploaded-preview"
							src={uploadedFile?.preview}
						/>

						{uploadedFile.uploaded && (
							<ClayIcon
								aria-label="image"
								className={classNames(
									'image-file-item-icon-check',
									{
										'image-file-item-icon-check-animation':
											uploadedFile.uploaded,
									}
								)}
								symbol="check"
							/>
						)}
					</div>
				)}
			</div>

			<div className="image-file-item-info-container">
				<div className="image-file-item-info-content">
					<Text as="span" size={3} weight="normal">
						{uploadedFile.fileName}
					</Text>

					{!isProcessing && (
						<ClayButton
							aria-label={i18n.translate('remove')}
							displayType="secondary"
							onClick={() =>
								onDelete(uploadedFile.id, versionName)
							}
							size="sm"
						>
							{i18n.translate('remove')}
						</ClayButton>
					)}
				</div>

				<div className="align-items-center d-flex">
					<ClayInput
						onChange={({target}) => {
							uploadedImages[index].imageDescription =
								target.value;

							uploadedImages[index].changed = true;

							onChangeInput(uploadedImages);
						}}
						placeholder="Image description"
						value={uploadedImages[index].imageDescription}
					/>

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
