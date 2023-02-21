import Dropzone from 'react-dropzone';
import classnames from 'classnames';

import documentIcon from '../../assets/icons/document-icon.svg';

import './DropzoneUpload.scss';

interface DropzoneUploadProps {
    acceptFileTypes: {
        [key: string]: string[];
    };
    buttonText: string;
    description: string;
    maxFiles: number;
    maxSize?: number;
    multiple: boolean;
    onHandleUpload: (files: File[]) => void;
    title: string;
}

export function DropzoneUpload({
    acceptFileTypes,
    buttonText,
    description,
    maxFiles,
    maxSize,
    multiple,
    onHandleUpload,
    title
}: DropzoneUploadProps) {
    return (
        <Dropzone
            accept={acceptFileTypes}
            maxFiles={maxFiles}
            maxSize={maxSize}
            multiple={multiple}
            onDropAccepted={onHandleUpload}
        >
            {({ getRootProps, getInputProps, isDragActive, isDragReject }) => (
                <div
                    className={classnames('dropzone-upload-container', {
                        'dropzone-upload-container-active': isDragActive,
                        'dropzone-upload-container-reject': isDragReject
                    })}
                    {...getRootProps()}
                >
                    <div className='dropzone-upload-document-container'>
                        <img
                            className='dropzone-upload-document-icon'
                            src={documentIcon}
                            alt="Document icon"
                        />
                    </div>

                    <div className='dropzone-upload-text-container'>
                        <span className='dropzone-upload-text'>{title}</span>

                        <button className='dropzone-upload-button'>
                            <span className='dropzone-upload-button-text'>{buttonText}</span>
                        </button>
                    </div>

                    <span className='dropzone-upload-description'>{description}</span>
                    <input {...getInputProps()} />
                </div>
            )}
        </Dropzone>
    );
}