import ClayButton from '@clayui/button';

import arrowNorth from '../../assets/icons/arrow-north.svg';
import arrowSouth from '../../assets/icons/arrow-south.svg';
import { Tooltip } from '../Tooltip/Tooltip';
import { UploadedFile } from './FileList';
import {CircularProgressbarWithChildren} from 'react-circular-progressbar'

import './ImageFileItem.scss';

interface ImageFileItemProps {
    onDelete: (id: string) => void;
    tooltip?: string
    uploadedFile: UploadedFile;
}

export function ImageFileItem({
        onDelete,
        tooltip, 
        uploadedFile
}: ImageFileItemProps) {
    return (
        <div className='image-file-item-container'>
            <div className='image-file-item-arrow-container'>
                <ClayButton
                    displayType='unstyled'
                >
                    <img
                        className='image-file-item-arrow-icon' 
                        src={arrowNorth} 
                        alt="Arrow Up" 
                    />
                </ClayButton>

                <ClayButton 
                    displayType='unstyled'
                >
                    <img 
                        className='image-file-item-arrow-icon'
                        src={arrowSouth} 
                        alt="Arrow South" 
                    />
                </ClayButton>
            </div>

            {uploadedFile.uploaded && !uploadedFile.error ? (
                <img
                    className="image-file-item-uploaded-preview"
                    style={{
                        backgroundImage: `url(${uploadedFile?.preview})`,
                    }}
                />
            ): (
                <CircularProgressbarWithChildren 
                    value={uploadedFile.progress}
                    styles={{
                        root: {
                            width: 50,
                            marginRight: 40
                        },
                        path: {stroke: '#0B5FFF'}                                
                    }}
                >
                    <div 
                        style={{
                            fontSize: 10, 
                            marginRight: 40, 
                            marginTop: 75
                        }}
                    >
                        <strong>{uploadedFile.progress}</strong>
                    </div>
                </CircularProgressbarWithChildren>
            )}

            <div className='image-file-item-info-container'>
                <div className='image-file-item-info-content'>
                    <span className='image-file-item-info-content-text'>{uploadedFile.fileName}</span>
                    <button 
                        className='image-file-item-info-content-button'
                        onClick={() => onDelete(uploadedFile.id)}
                    >
                        Remove
                    </button>
                </div>

                <div className='image-file-item-info-input-container'>
                    <input
                        className='image-file-item-info-input'
                        placeholder='Image description'
                    />

                    {tooltip && (
                        <Tooltip 
                            tooltip={tooltip}
                        />
                    )}
                </div>
            </div>
        </div>
    );
}