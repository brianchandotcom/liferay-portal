/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayButton from '@clayui/button';
import {filesize} from 'filesize';

import {DropzoneUpload} from '../../components/DropzoneUpload/DropzoneUpload';
import {FileList, UploadedFile} from '../../components/FileList/FileList';
import {Header} from '../../components/Header/Header';
import {NewAppPageFooterButtons} from '../../components/NewAppPageFooterButtons/NewAppPageFooterButtons';
import {Section} from '../../components/Section/Section';
import {useAppContext} from '../../manage-app-state/AppManageState';
import {TYPES} from '../../manage-app-state/actionTypes';
import {createImage} from '../../utils/api';

import './CustomizeAppStorefrontPage.scss';

import {useState} from 'react';

import i18n from '../../i18n';
import {submitBase64EncodedFile} from '../../utils/util';

const ACCEPT_FILE_TYPES = {
	'image/*': ['.png', '.svg', '.jpg', '.gif'],
};
const MAX_IMAGE_QUANTITY = 10;

type CustomizeAppStorefrontPageProps = {
	onClickBack: () => void;
	onClickContinue: () => void;
};

export function CustomizeAppStorefrontPage({
	onClickBack,
	onClickContinue,
}: CustomizeAppStorefrontPageProps) {
	const [{appERC, appStorefrontImages}, dispatch] = useAppContext();

	const [isLoading, setIsLoading] = useState<boolean>(false);

	const handleUpload = (files: File[]) => {
		if (
			files.length > MAX_IMAGE_QUANTITY ||
			appStorefrontImages?.length > MAX_IMAGE_QUANTITY
		) {
			return;
		}

		if (
			(appStorefrontImages?.length || 0) + files.length <=
			MAX_IMAGE_QUANTITY
		) {
			const newUploadedFiles: UploadedFile[] = files.map((file) => ({
				error: false,
				file,
				fileName: file.name,
				id: crypto.randomUUID(),
				preview: URL.createObjectURL(file),
				progress: 0,
				readableSize: filesize(file.size),
				uploaded: true,
			}));

			dispatch({
				payload: {
					files: appStorefrontImages?.length
						? [...appStorefrontImages, ...newUploadedFiles]
						: newUploadedFiles,
				},
				type: TYPES.UPLOAD_APP_STOREFRONT_IMAGES,
			});
		}
	};

	const handleDelete = (id: string) => {
		const files = appStorefrontImages.filter(
			(uploadedFile) => uploadedFile.id !== id
		);

		dispatch({
			payload: {
				files,
			},
			type: TYPES.UPLOAD_APP_STOREFRONT_IMAGES,
		});
	};

	return (
		<div className="storefront-page-container">
			<Header
				description="Design the storefront for your app.  This will set the information displayed on the app page in the Marketplace."
				title="Customize app storefront"
			/>

			<Section
				label="App Storefront"
				required
				tooltip={`Screenshots for your app must not exceed ${MAX_IMAGE_QUANTITY} 80 pixels in width and 678 pixels in height and must be in JPG or PNG format.  The file site of each screenshot must not exceed 384KB.  Each screenshot should preferrably be the same size, but each will be automatically scaled to match the aspect ratio of the above dimensions. It is preferrable if they are named sequentially, but you can reorder them as needed.`}
				tooltipText="More Info"
			>
				<div className="storefront-page-info-container">
					<span className="storefront-page-info-text">
						{`Add up to ${MAX_IMAGE_QUANTITY} images`}
					</span>

					{appStorefrontImages?.length > 0 && (
						<ClayButton
							className="font-weight-bold"
							displayType="link"
							onClick={() => {
								dispatch({
									payload: {
										files: [],
									},
									type: TYPES.UPLOAD_APP_STOREFRONT_IMAGES,
								});
							}}
						>
							Remove all
						</ClayButton>
					)}
				</div>

				{appStorefrontImages?.length > 0 && (
					<FileList
						onDelete={handleDelete}
						type="image"
						uploadedFiles={appStorefrontImages}
					/>
				)}

				<DropzoneUpload
					acceptFileTypes={ACCEPT_FILE_TYPES}
					buttonText="Select a file"
					description="Only gif, jpg, png are allowed. Max file size is 5MB "
					maxFiles={MAX_IMAGE_QUANTITY}
					maxSize={5000000}
					multiple={true}
					onHandleUpload={handleUpload}
					title="Drag and drop to upload or"
				/>
			</Section>

			<NewAppPageFooterButtons
				disableContinueButton={
					isLoading ||
					!appStorefrontImages ||
					!appStorefrontImages.length
				}
				isLoading={isLoading}
				loadingButtonText={i18n.translate('uploading-images')}
				onClickBack={() => onClickBack()}
				onClickContinue={async () => {
					setIsLoading(true);

					let index = 0;

					for (const image of appStorefrontImages) {
						await submitBase64EncodedFile({
							appERC,
							file: image.file,
							index,
							isAppIcon: false,
							requestFunction: createImage,
							title: image.fileName,
						});
						index++;
					}

					onClickContinue();
					setIsLoading(false);
				}}
			/>
		</div>
	);
}
