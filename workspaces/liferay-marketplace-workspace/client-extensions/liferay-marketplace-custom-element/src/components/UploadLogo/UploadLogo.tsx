import emptyImage from '../../assets/icons/emptyImage.svg';

import './UploadLogo.scss';
import {UploadedFile} from '../FileList/FileList';

interface UploadLogoProps {
	onDeleteFile: (id: string) => void;
	onUpload: (files: FileList) => void;
	uploadedFile?: UploadedFile;
}

export function UploadLogo({
	onDeleteFile,
	onUpload,
	uploadedFile,
}: UploadLogoProps) {
	return (
		<div className="upload-logo-container">
			<div
				className="upload-logo-icon"
				style={{
					backgroundImage: `url(${
						uploadedFile?.preview ?? emptyImage
					})`,
					backgroundPosition: '50% 50%',
					backgroundRepeat: 'no-repeat',
					backgroundSize: 'cover',
				}}
			/>

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

			<label className="upload-logo-upload-label" htmlFor="file">
				Upload Image
			</label>

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
	);
}
