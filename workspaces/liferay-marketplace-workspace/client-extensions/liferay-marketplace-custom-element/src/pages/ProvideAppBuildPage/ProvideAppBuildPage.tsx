import {filesize} from 'filesize';
import {uniqueId} from 'lodash';

import cancelIcon from '../../assets/icons/cancel_icon.svg';
import cloudIcon from '../../assets/icons/cloud_fill_icon.svg';
import githubIcon from '../../assets/icons/github_icon.svg';
import taskCheckedIcon from '../../assets/icons/task_checked_icon.svg';
import uploadIcon from '../../assets/icons/upload_fill_icon.svg';
import {DropzoneUpload} from '../../components/DropzoneUpload/DropzoneUpload';
import {FileList, UploadedFile} from '../../components/FileList/FileList';
import {Header} from '../../components/Header/Header';
import {NewAppPageFooterButtons} from '../../components/NewAppPageFooterButtons/NewAppPageFooterButtons';
import {RadioCard} from '../../components/RadioCard/RadioCard';
import {Section} from '../../components/Section/Section';
import {useAppContext} from '../../manage-app-state/AppManageState';
import {TYPES} from '../../manage-app-state/actionTypes';
import {
	createAttachment,
	createProductSpecification,
	createSpecification,
	updateProductSpecification,
} from '../../utils/api';
import {submitBase64EncodedFile} from '../../utils/util';

import './ProvideAppBuildPage.scss';

interface ProvideAppBuildPageProps {
	onClickBack: () => void;
	onClickContinue: () => void;
}

const acceptFileTypes = {
	'application/zip': ['.zip'],
};

export function ProvideAppBuildPage({
	onClickBack,
	onClickContinue,
}: ProvideAppBuildPageProps) {
	const [
		{appBuild, appERC, appId, appProductId, appType, buildZIPFiles},
		dispatch,
	] = useAppContext();

	const handleUpload = (files: File[]) => {
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

		if (buildZIPFiles?.length) {
			dispatch({
				payload: {
					files: [...buildZIPFiles, ...newUploadedFiles],
				},
				type: TYPES.UPLOAD_BUILD_ZIP_FILES,
			});
		}
		else {
			dispatch({
				payload: {
					files: newUploadedFiles,
				},
				type: TYPES.UPLOAD_BUILD_ZIP_FILES,
			});
		}
	};

	const handleDelete = (fileId: string) => {
		const files = buildZIPFiles.filter((file) => file.id !== fileId);

		dispatch({
			payload: {
				files,
			},
			type: TYPES.UPLOAD_BUILD_ZIP_FILES,
		});
	};

	return (
		<div className="provide-app-build-page-container">
			<Header
				description="Use one of the following methods to provide your app builds."
				title="Provide app build"
			/>

			<Section
				label="Cloud Compatible?"
				required
				tooltip="More Info"
				tooltipText="MoreInfo"
			>
				<div className="provide-app-build-page-cloud-compatible-container">
					<RadioCard
						description="Lorem ipsum dolor sit amet consectetur."
						icon={taskCheckedIcon}
						onChange={() => {
							dispatch({
								payload: {id: appType.id, value: 'cloud'},
								type: TYPES.UPDATE_APP_LXC_COMPATIBILITY,
							});
						}}
						selected={appType.value === 'cloud'}
						title="Yes"
						tooltip="More Info"
					/>

					<RadioCard
						description="Lorem ipsum dolor sit amet consectetur."
						icon={cancelIcon}
						onChange={() => {
							dispatch({
								payload: {id: appType.id, value: 'dxp'},
								type: TYPES.UPDATE_APP_LXC_COMPATIBILITY,
							});
						}}
						selected={appType.value === 'dxp'}
						title="No"
						tooltip="More Info"
					/>
				</div>
			</Section>

			<Section
				label="App Build"
				required
				tooltip="More Info"
				tooltipText="MoreInfo"
			>
				<div className="provide-app-build-page-app-build-radio-container">
					<RadioCard
						description="Use any build from any available Liferay Experience Cloud account (requires LXC account) "
						disabled
						icon={cloudIcon}
						onChange={() => {
							dispatch({
								payload: {value: 'LXC'},
								type: TYPES.UPDATE_APP_BUILD,
							});
						}}
						selected={appBuild === 'LXC'}
						title="Via Liferay Experience Cloud Integration"
						tooltip="More Info"
					/>

					<RadioCard
						description="Use any build from your computer connecting with a Github provider"
						disabled
						icon={githubIcon}
						onChange={() => {
							dispatch({
								payload: {value: 'GitHub'},
								type: TYPES.UPDATE_APP_BUILD,
							});
						}}
						selected={appBuild === 'GitHub'}
						title="Via GitHub Repo"
						tooltip="More Info"
					/>

					<RadioCard
						description="Use any local ZIP files to upload. Max file size is 500MB"
						icon={uploadIcon}
						onChange={() => {
							dispatch({
								payload: {value: 'upload'},
								type: TYPES.UPDATE_APP_BUILD,
							});
						}}
						selected={appBuild === 'upload'}
						title="Via ZIP Upload"
						tooltip="More Info"
					/>
				</div>
			</Section>

			<Section
				description="Select a local file to upload"
				label="Upload ZIP Files"
				required
				tooltip="MoreInfo"
				tooltipText="MoreInfo"
			>
				<FileList
					onDelete={handleDelete}
					type="document"
					uploadedFiles={buildZIPFiles ? buildZIPFiles : []}
				/>

				<DropzoneUpload
					acceptFileTypes={acceptFileTypes}
					buttonText="Select a file"
					description="Only ZIP files are allowed. Max file size is 500MB "
					maxFiles={1}
					maxSize={500000000}
					multiple={false}
					onHandleUpload={handleUpload}
					title="Drag and drop to upload or"
				/>
			</Section>

			<NewAppPageFooterButtons
				disableContinueButton={!buildZIPFiles?.length}
				onClickBack={() => onClickBack()}
				onClickContinue={() => {
					const submitAppBuildType = async () => {
						if (appType.id) {
							updateProductSpecification({
								body: {
									specificationKey: 'type',
									value: {en_US: appType.value},
								},
								id: appType.id,
							});
						}
						else {
							const dataSpecification = await createSpecification(
								{
									body: {
										key: 'type',
										title: {en_US: 'Type'},
									},
								}
							);

							const {id} = await createProductSpecification({
								appId,
								body: {
									productId: appProductId,
									specificationId: dataSpecification.id,
									specificationKey: dataSpecification.key,
									value: {en_US: appType.value},
								},
							});

							dispatch({
								payload: {id, value: appType.value},
								type: TYPES.UPDATE_APP_LXC_COMPATIBILITY,
							});
						}
					};

					submitAppBuildType();

					buildZIPFiles.forEach((buildZIPFile) => {
						submitBase64EncodedFile(
							appERC,
							buildZIPFile.file,
							createAttachment,
							buildZIPFile.fileName
						);
					});

					onClickContinue();
				}}
				showBackButton
			/>
		</div>
	);
}
