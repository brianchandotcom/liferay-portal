import {CircularProgressbarWithChildren} from 'react-circular-progressbar'

import folderIcon from '../../assets/icons/folder-fill.svg';
import { UploadedFile } from './FileList';

import './DocumentFileItem.scss';

interface DocumentFileItemProps {
    onDelete: (id: string) => void;
    uploadedFile: UploadedFile;
}

export function DocumentFileItem({onDelete, uploadedFile}: DocumentFileItemProps) {
    return (
        <div
            className='document-file-list-item-container'
        >
            <div className='document-file-list-item-left-content'>
                <div className='document-file-list-item-left-content-icon-container'>
                    {uploadedFile.uploaded && !uploadedFile.error ? (
                        <img 
                            className='document-file-list-item-left-content-icon'
                            src={folderIcon} 
                            alt="Folder Icon" 
                        /> 
                    ): (
                        <CircularProgressbarWithChildren 
                            value={uploadedFile.progress}
                            styles={{
                                root: {
                                    width: 50
                                },
                                path: {stroke: '#0B5FFF'}                                
                            }}
                        >
                            <div style={{ fontSize: 10}}>
                                <strong>{uploadedFile.progress}</strong>
                            </div>
                        </CircularProgressbarWithChildren>
                    )}

                </div>

                <div className='document-file-list-item-left-content-text-container'>
                    <span 
                        className='document-file-list-item-left-content-text-file-name'
                    >
                        {uploadedFile.fileName}
                    </span>
                    
                    <span 
                        className='document-file-list-item-left-content-text-file-size'
                    >
                        {String(uploadedFile.readableSize)}
                    </span>
                </div>
            </div>
            
            <button
                onClick={() => onDelete(uploadedFile.id)} 
                className='document-file-list-item-button'
            >
                {uploadedFile.uploaded ? "Remove" : "Cancel Upload"}
            </button>
        </div>
    );
}