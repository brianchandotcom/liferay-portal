import emptyImage from '../../assets/icons/emptyImage.svg';

import './UploadLogo.scss';
import { UploadedFile } from '../FileList/FileList';

interface UploadLogoProps {
    uploadedFile?: UploadedFile;
    onUpload: (files: FileList) => void
    onDeleteFile: (id: string) => void;
}

export function UploadLogo({
    uploadedFile,
    onDeleteFile,
    onUpload,
}: UploadLogoProps) {
    return (
        <div className='upload-logo-container'>
            <div
                style={{
                    backgroundImage: `url(${uploadedFile?.preview ?? emptyImage})`,
                    backgroundRepeat: 'no-repeat',
                    backgroundSize: 'cover',
                    backgroundPosition: "50% 50%",
                }}
                className='upload-logo-icon'
            />

            <input
                accept="image/jpeg, image/png, image/gif"
                type="file"
                name="file"
                id="file"
                onChange={({ target: { files } }) => {
                    if (files !== null) {
                        onUpload(files)
                    }
                }} />
            <label
                className='upload-logo-upload-label'
                htmlFor="file"
            >
                Upload Image
            </label>
            {(uploadedFile?.uploaded &&
                <button className='upload-logo-delete-button' onClick={() => onDeleteFile(uploadedFile.id)}>
                    <span className='upload-logo-delete-button-text'>Delete</span>
                </button>
            )}

        </div>
    );
}