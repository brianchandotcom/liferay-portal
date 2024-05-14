/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayIcon from '@clayui/icon';
import classNames from 'classnames';

import {UploadedFile} from '../../../../../../components/FileList/FileList';
import i18n from '../../../../../../i18n';

interface ContentReviewImageInfoProps {
	className?: string;
	description?: string;
	icon?: string;
	imageFile: UploadedFile;
	title?: string;
}

export function ContentReviewImageInfo({
	className,
	description,
	icon,
	imageFile,
	title,
}: ContentReviewImageInfoProps) {
	return (
		<div className={classNames('d-flex mb-4', className)}>
			<div className="align-items-start d-flex justify-content-center mr-3">
				<img
					alt={imageFile.preview}
					className="solution-preview-header-image"
					src={imageFile.preview}
				/>
			</div>
			<div>
				{icon && (
					<ClayIcon
						className="solution-preview-header-image-icon"
						symbol={icon}
					/>
				)}
				<h5 className="m-0">{imageFile.fileName}</h5>
				<p className="mb-0">{imageFile.imageDescription}</p>

				{title && (
					<>
						<h5 className="m-0">{i18n.translate('title')}</h5>
						<p>{title}</p>
					</>
				)}
				{description && (
					<>
						<h5 className="m-0">{i18n.translate('description')}</h5>
						<p>{description}</p>
					</>
				)}
			</div>
		</div>
	);
}
