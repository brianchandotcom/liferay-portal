import {filesize} from 'filesize';
import {uniqueId} from 'lodash';

import {DropzoneUpload} from '../../components/DropzoneUpload/DropzoneUpload';
import {FileList, UploadedFile} from '../../components/FileList/FileList';
import {Header} from '../../components/Header/Header';
import {NewAppPageFooterButtons} from '../../components/NewAppPageFooterButtons/NewAppPageFooterButtons';
import {Section} from '../../components/Section/Section';
import {useAppContext} from '../../manage-app-state/AppManageState';
import {TYPES} from '../../manage-app-state/actionTypes';
import {createImage} from '../../utils/api';

import './CustomizeAppStorefrontPage.scss';
import {submitBase64EncodedFile} from '../../utils/util';

const acceptFileTypes = {
	'image/*': ['.png', '.svg', '.jpg'],
};

interface CustomizeAppStorefrontPageProps {
	onClickBack: () => void;
	onClickContinue: () => void;
}

export function CustomizeAppStorefrontPage({
	onClickBack,
	onClickContinue,
}: CustomizeAppStorefrontPageProps) {
	const [{appERC, appStorefrontImages}, dispatch] = useAppContext();

	const handleUpload = (files: File[]) => {
		if (files.length > 5 || appStorefrontImages?.length > 5) {
			return;
		}

		if ((appStorefrontImages?.length || 0) + files.length < 6) {
			const newUploadedFiles: UploadedFile[] = files.map((file) => ({
				error: false,
				file,
				fileName: file.name,
				id: uniqueId(),
				preview: URL.createObjectURL(file),
				progress: 0,
				readableSize: filesize(file.size),
				uploaded: true,
			}));

			if (appStorefrontImages?.length) {
				dispatch({
					payload: {
						files: [...appStorefrontImages, ...newUploadedFiles],
					},
					type: TYPES.UPLOAD_APP_STOREFRONT_IMAGES,
				});
			}
			else {
				dispatch({
					payload: {
						files: newUploadedFiles,
					},
					type: TYPES.UPLOAD_APP_STOREFRONT_IMAGES,
				});
			}
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
				tooltip="Screenshots for your app must not exceed 1080 pixels in width and 678 pixels in height and must be in JPG or PNG format.  The file site of each screenshot must not exceed 384KB.  Each screenshot should preferrably be the same size, but each will be automatically scaled to match the aspect ratio of the above dimensions. It is preferrable if they are named sequentially, but you can reorder them as needed."
				tooltipText="More Info"
			>
				<div className="storefront-page-info-container">
					<span className="storefront-page-info-text">
						Add up to 5 images
					</span>

					{appStorefrontImages?.length > 0 && (
						<button
							className="storefront-page-info-button"
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
						</button>
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
					acceptFileTypes={acceptFileTypes}
					buttonText="Select a file"
					description="Only gif, jpg, png are allowed. Max file size is 5MB "
					maxFiles={5}
					maxSize={5000000}
					multiple={true}
					onHandleUpload={handleUpload}
					title="Drag and drop to upload or"
				/>
			</Section>

			<NewAppPageFooterButtons
				disableContinueButton={
					!appStorefrontImages || !appStorefrontImages.length
				}
				onClickBack={() => onClickBack()}
				onClickContinue={() => {
					appStorefrontImages?.forEach((image, index) => {
						submitBase64EncodedFile({
							appERC,
							file: image.file,
							index,
							requestFunction: createImage,
							title: image.fileName,
						});
					});

					onClickContinue();
				}}
			/>
		</div>
	);
}
