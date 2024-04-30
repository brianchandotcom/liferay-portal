/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayIcon from '@clayui/icon';
import {ClayTooltipProvider} from '@clayui/tooltip';

import {UploadedFile} from '../FileList/FileList';

type UploadLogoProps = {
	onDeleteFile: (id: string) => void;
	onUpload: (files: FileList) => void;
	tooltip?: string;
	uploadedFile?: UploadedFile;
};

const UploadLogo: React.FC<UploadLogoProps> = ({
	onDeleteFile,
	onUpload,
	tooltip,
	uploadedFile,
}) => {
	return (
		<ClayTooltipProvider>
			<div className="upload-logo-container">
				{uploadedFile?.preview ? (
					<img
						alt="New App logo"
						className="upload-logo-icon"
						src={uploadedFile?.preview}
					/>
				) : (
					<div className="bg-light py-5 rounded">
						<ClayIcon
							aria-label="New App logo"
							className="text-muted upload-logo-icon"
							symbol="picture"
						/>
					</div>
				)}

				<div
					data-title-set-as-html
					data-tooltip-align="top"
					title={tooltip}
				>
					<input
						accept="image/jpeg, image/png, image/gif"
						id="file"
						name="file"
						onChange={({target: {files}}) => {
							if (files !== null) {
								onUpload(files);
							}
						}}
						type="file"
					/>

					<label
						className="btn btn-primary btn-sm m-0"
						htmlFor="file"
					>
						Upload Image
					</label>
				</div>

				{uploadedFile?.uploaded && (
					<button
						className="btn btn-secondary btn-sm m-0 upload-logo-delete-button"
						onClick={() => onDeleteFile(uploadedFile.id)}
					>
						Delete
					</button>
				)}
			</div>
		</ClayTooltipProvider>
	);
};

export default UploadLogo;
