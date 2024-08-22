/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {filesize} from 'filesize';

import {DropzoneUpload} from '../../../../../components/DropzoneUpload/DropzoneUpload';
import {
	NewAppTypes,
	useNewAppContext,
} from '../../../../../context/NewAppContext';
import {ProductType} from '../../../../../enums/ProductType';
import i18n from '../../../../../i18n';
import {getRandomID} from '../../../../../utils/string';

type NewAppUploadAppPackagesComponentProps = {
	isProcessing: boolean;
	versionName: string;
};

export const acceptFileTypes = {
	[ProductType.CLOUD]: {
		'application/java-archive': ['.zip'],
	},
	[ProductType.DXP]: {
		'application/java-archive': ['.jar'],
		'application/octet-stream': ['.war'],
	},
};

export const UPLOAD_MAX_SIZE = 500_000_000;

export function NewAppUploadAppPackagesComponent({
	isProcessing,
	versionName,
}: NewAppUploadAppPackagesComponentProps) {
	const [
		{
			build: {appType, liferayPackages},
		},
		dispatch,
	] = useNewAppContext();

	const enableUploadFiles =
		!isProcessing &&
		(!liferayPackages?.length || liferayPackages?.length < 10);

	const handleUploadAppPackages = (files: File[]) => {
		const newUploadedPackages = files.map((file) => ({
			error: false,
			file,
			fileName: file.name,
			id: getRandomID(),
			preview: URL.createObjectURL(file),
			progress: 0,
			readableSize: filesize(file.size),
			uploaded: false,
			versionName,
		}));

		dispatch({
			payload: {
				liferayPackages: [
					{version: versionName as string, file: newUploadedPackages},
				],
			},
			type: NewAppTypes.SET_BUILD,
		});
	};

	return (
		<>
			{enableUploadFiles && (
				<DropzoneUpload
					acceptFileTypes={
						acceptFileTypes[
							appType.value as keyof typeof acceptFileTypes
						]
					}
					buttonText={i18n.translate('select-a-file')}
					description={
						appType.value === ProductType.CLOUD
							? i18n.translate(
									'only-zip-files-are-allowed-max-file-size-is-500-mb'
								)
							: i18n.translate(
									'only-jar-war-files-are-allowed-max-file-size-is-500mb'
								)
					}
					maxFiles={1}
					maxSize={UPLOAD_MAX_SIZE}
					multiple={true}
					onHandleUpload={handleUploadAppPackages}
					title={i18n.translate('drag-and-drop-to-upload-or')}
					versionName={versionName}
				/>
			)}
		</>
	);
}

export default NewAppUploadAppPackagesComponent;
