/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayIcon from '@clayui/icon';
import {ClayTooltipProvider} from '@clayui/tooltip';
import ReactDOMServer from 'react-dom/server';

import i18n from '../../i18n';
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
					title={ReactDOMServer.renderToString(
						<span>
							The icon is a small image representation of the app.
							Icons must be a PNG, JPG, or GIF format and cannot
							exceed 5MB. Animated images are prohibited. The use
							of the Liferay logo, including any permitted
							alternate versions of the Liferay logo, is permitted
							only with Liferay&apos;s express permission. Please
							refer to our{' '}
							<a
								href="https://www.liferay.com/trademark"
								target="_blank"
							>
								trademark policy
							</a>{' '}
							for details.
						</span>
					)}
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
						{i18n.translate('upload-image')}
					</label>
				</div>

				{uploadedFile?.preview && (
					<button
						className="btn btn-secondary btn-sm m-0 upload-logo-delete-button"
						onClick={() => onDeleteFile(uploadedFile.id)}
					>
						{i18n.translate('delete')}
					</button>
				)}
			</div>
		</ClayTooltipProvider>
	);
};

export default UploadLogo;
