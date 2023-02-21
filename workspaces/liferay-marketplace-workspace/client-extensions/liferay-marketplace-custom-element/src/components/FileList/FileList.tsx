import { DocumentFileItem } from './DocumentFileItem';
import './FileList.scss';
import { ImageFileItem } from './ImageFileItem';

export type UploadedFile = {
    file: File;
    id: string;
    fileName: string;
    readableSize: string | number | any[] | {
        value: any;
        symbol: any;
        exponent: number;
        unit: string;
    };
    preview?: string;
    progress: number;
    uploaded: boolean;
    error: boolean;
}

interface FileListProps {
    uploadedFiles: UploadedFile[];
    onDelete: (id: string) => void;
    type: 'document' | 'image';
}

export function FileList({
    uploadedFiles,
    onDelete,
    type
}: FileListProps) {
    return (
        <div className='file-list-container'>
            {uploadedFiles.map((uploadedFile) => {
                if (type === 'document') {
                    return (
                        <DocumentFileItem
                            key={uploadedFile.id}
                            onDelete={onDelete}
                            uploadedFile={uploadedFile}
                        />
                    );
                }

                if (type === 'image') {
                    return (
                        <ImageFileItem
                            key={uploadedFile.id}
                            onDelete={onDelete}
                            uploadedFile={uploadedFile}
                            tooltip='More Info'
                        />
                    );
                }
            })}
        </div>
    )
}