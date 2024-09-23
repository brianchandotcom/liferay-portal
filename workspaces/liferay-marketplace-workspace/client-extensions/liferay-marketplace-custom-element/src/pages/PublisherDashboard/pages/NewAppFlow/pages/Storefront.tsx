/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {filesize} from 'filesize';

import {DropzoneUpload} from '../../../../../components/DropzoneUpload/DropzoneUpload';
import {FileList} from '../../../../../components/FileList/FileList';
import {Section} from '../../../../../components/Section/Section';
import {
	NewAppTypes,
	useNewAppContext,
} from '../../../../../context/NewAppContext';
import i18n from '../../../../../i18n';
import {swapElements} from '../../../../../utils/array';
import {getRandomID} from '../../../../../utils/string';
import {ACCEPT_FILE_TYPES} from '../../Apps/AppCreationFlow/StorefrontPage/CustomizeAppStorefrontPage';
import {MAX_IMAGE_QUANTITY, MAX_SIZE_5MBS} from '../constants';

const Storefront = () => {
	const [
		{
			storefront: {images},
		},
		dispatch,
	] = useNewAppContext();

	const handleRemoveAppPackages = (imageId: string) =>
		dispatch({
			payload: {
				images: images.filter((image) => image.id !== imageId),
			},
			type: NewAppTypes.SET_STOREFRONT,
		});

	const handleUploadAppPackages = (files: File[]) =>
		dispatch({
			payload: {
				images: [
					...images,
					...files.map((file) => ({
						error: false,
						file,
						fileName: file.name,
						id: getRandomID(),
						preview: URL.createObjectURL(file),
						progress: 0,
						readableSize: filesize(file.size),
						uploaded: false,
					})),
				] as any,
			},
			type: NewAppTypes.SET_STOREFRONT,
		});

	const handleArrowClick = (index: number, direction: string) => {
		const newIndex = direction === 'up' ? index - 1 : index + 1;

		const _images = swapElements(images, index, newIndex);

		_images[index].changed = true;
		_images[newIndex].changed = true;

		dispatch({
			payload: {
				images: _images,
			},
			type: NewAppTypes.SET_STOREFRONT,
		});
	};

	return (
		<Section label={i18n.translate('app-storefront')}>
			<span className="h5">{i18n.sub('add-up-to-x-images', '10')}</span>

			<FileList
				isProcessing={false}
				onArrowClick={handleArrowClick}
				onChangeInput={(newImagesInputs) =>
					dispatch({
						payload: {images: newImagesInputs},
						type: NewAppTypes.SET_STOREFRONT,
					})
				}
				onDelete={handleRemoveAppPackages}
				type="image"
				uploadedFiles={images}
				uploadedImages={images}
			/>

			<DropzoneUpload
				acceptFileTypes={ACCEPT_FILE_TYPES}
				buttonText={i18n.translate('select-a-file')}
				description={i18n.translate(
					'only-gif-jpg-png-are-allowed-ax-file-size-is-5mb'
				)}
				maxFiles={MAX_IMAGE_QUANTITY}
				maxSize={MAX_SIZE_5MBS}
				multiple
				onDropRejected={(fileList) => {
					if (fileList.length > MAX_IMAGE_QUANTITY) {

						// TODO
						// onOpenChange(true);

					}
				}}
				onHandleUpload={handleUploadAppPackages}
				title={i18n.translate('drag-and-drop-to-upload-or')}
			/>
		</Section>
	);
};

export default Storefront;
