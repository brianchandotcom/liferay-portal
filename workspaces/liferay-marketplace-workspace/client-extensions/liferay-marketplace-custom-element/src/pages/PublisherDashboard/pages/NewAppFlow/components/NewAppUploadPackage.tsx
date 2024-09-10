/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {filesize} from 'filesize';

import {DropzoneUpload} from '../../../../../components/DropzoneUpload/DropzoneUpload';
import {FileList} from '../../../../../components/FileList/FileList';
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

const MAX_FILES = 10;
export const UPLOAD_MAX_SIZE = 500_000_000;

export const acceptFileTypes = {
	[ProductType.CLOUD]: {
		'application/java-archive': ['.zip'],
	},
	[ProductType.DXP]: {
		'application/java-archive': ['.jar'],
		'application/octet-stream': ['.war'],
	},
};

export function NewAppUploadAppPackagesComponent({
	isProcessing,
	versionName,
}: NewAppUploadAppPackagesComponentProps) {
	const [
		{
			build: {cloudCompatible, liferayPackages},
		},
		dispatch,
	] = useNewAppContext();

	const enableUploadFiles =
		!isProcessing &&
		(!liferayPackages?.length || liferayPackages?.length < MAX_FILES);

	const handleRemoveAppPackages = (fileId: string) => {
		const _liferayPackages = liferayPackages.map((liferayPackage) => {
			if (liferayPackage.version === versionName) {
				return {
					...liferayPackage,
					file: liferayPackage.file.filter(({id}) => id !== fileId),
				};
			}

			return liferayPackage;
		});

		dispatch({
			payload: {
				liferayPackages: _liferayPackages,
			},
			type: NewAppTypes.SET_BUILD,
		});
	};

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

		const _liferayPackages = liferayPackages.map((liferayPackage) => {
			if (liferayPackage.version === versionName) {
				return {
					...liferayPackage,
					file: liferayPackage.file.length
						? [...liferayPackage.file, ...newUploadedPackages]
						: newUploadedPackages,
				};
			}

			return liferayPackage;
		});

		dispatch({
			payload: {
				liferayPackages: _liferayPackages,
			},
			type: NewAppTypes.SET_BUILD,
		});
	};

	return (
		<>
			<FileList
				isProcessing={isProcessing}
				onDelete={handleRemoveAppPackages}
				type="document"
				uploadedFiles={
					liferayPackages.find(
						(liferayPackage) =>
							liferayPackage.version === versionName
					)?.file ?? []
				}
				versionName={versionName}
			/>

			{enableUploadFiles && (
				<DropzoneUpload
					acceptFileTypes={
						acceptFileTypes[
							cloudCompatible
								? ProductType.CLOUD
								: ProductType.DXP
						]
					}
					buttonText={i18n.translate('select-a-file')}
					description={
						cloudCompatible
							? i18n.translate(
									'only-zip-files-are-allowed-max-file-size-is-500-mb'
								)
							: i18n.translate(
									'only-jar-war-files-are-allowed-max-file-size-is-500mb'
								)
					}
					maxFiles={MAX_FILES}
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
