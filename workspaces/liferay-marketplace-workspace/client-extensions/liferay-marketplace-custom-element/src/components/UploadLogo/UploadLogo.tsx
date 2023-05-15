import emptyPicture from '../../assets/icons/empty_picture_icon.svg';

import './UploadLogo.scss';

import {ClayTooltipProvider} from '@clayui/tooltip';

import {UploadedFile} from '../FileList/FileList';

interface UploadLogoProps {
	onDeleteFile: (id: string) => void;
	onUpload: (files: FileList) => void;
	tooltip?: string;
	uploadedFile?: UploadedFile;
}

export function UploadLogo({
	onDeleteFile,
	onUpload,
	tooltip,
	uploadedFile,
}: UploadLogoProps) {
	return (
		<ClayTooltipProvider>
			<div className="upload-logo-container">
				<>
					<div
						className="upload-logo-icon"
						style={{
							backgroundImage: `url(${
								uploadedFile?.preview ?? emptyPicture
							})`,
							backgroundPosition: '50% 50%',
							backgroundRepeat: 'no-repeat',
							backgroundSize: 'cover',
						}}
					/>

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
							className="upload-logo-upload-label"
							htmlFor="file"
						>
							Upload Image
						</label>
					</div>
				</>

				{uploadedFile?.uploaded && (
					<button
						className="upload-logo-delete-button"
						onClick={() => onDeleteFile(uploadedFile.id)}
					>
						<span className="upload-logo-delete-button-text">
							Delete
						</span>
					</button>
				)}
			</div>
		</ClayTooltipProvider>
	);
}
