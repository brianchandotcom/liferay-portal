import {DocumentFileItem} from './DocumentFileItem';

import './FileList.scss';
import {ImageFileItem} from './ImageFileItem';

export type UploadedFile = {
	error: boolean;
	file: File;
	fileName: string;
	id: string;
	preview?: string;
	progress: number;
	readableSize:
		| string
		| number
		| any[]
		| {
				exponent: number;
				symbol: any;
				unit: string;
				value: any;
		  };
	uploaded: boolean;
};

interface FileListProps {
	onDelete: (id: string) => void;
	type: 'document' | 'image';
	uploadedFiles: UploadedFile[];
}

export function FileList({onDelete, type, uploadedFiles}: FileListProps) {
	return (
		<div className="file-list-container">
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
							tooltip="Use the image description to provide more context about the screenshot, such as what is the user trying to accomplish, what are the business requirements met by this screen or anything else you feel would be helpful to guide your potential customer.  This content will be provided in the form of a mouse over of the image."
							uploadedFile={uploadedFile}
						/>
					);
				}
			})}
		</div>
	);
}
