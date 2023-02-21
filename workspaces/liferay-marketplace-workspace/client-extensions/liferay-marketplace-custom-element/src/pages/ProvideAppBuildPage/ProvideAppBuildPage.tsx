import { filesize } from 'filesize';
import { uniqueId } from 'lodash';

import cancelIcon from '../../assets/icons/cancel-icon.svg';
import cloudIcon from '../../assets/icons/cloud-fill.svg';
import githubIcon from '../../assets/icons/github-icon.svg';
import taskCheckedIcon from '../../assets/icons/task-checked-icon.svg';
import uploadIcon from '../../assets/icons/upload-fill.svg';
import { DropzoneUpload } from '../../components/DropzoneUpload/DropzoneUpload';
import { FileList, UploadedFile } from '../../components/FileList/FileList';
import { Header } from '../../components/Header/Header';
import { NewAppPageFooterButtons } from '../../components/NewAppPageFooterButtons/NewAppPageFooterButtons';
import { RadioCard } from '../../components/RadioCard/RadioCard';
import { Section } from '../../components/Section/Section';
import { useAppContext } from '../../manage-app-state/AppManageState';
import { TYPES } from '../../manage-app-state/actionTypes';
import {
	createAttachment,
	createProductSpecification,
	createSpecification,
} from '../../utils/api';
import { submitBase64EncodedFile } from '../../utils/util';
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
		{ appBuild, appERC, appId, appProductId, appType, buildZIPFiles },
		dispatch,
	] = useAppContext();

	const handleUpload = (files: File[]) => {
		const newUploadedFiles: UploadedFile[] = files.map((file) => ({
			file,
			id: uniqueId(),
			fileName: file.name,
			readableSize: filesize(file.size),
			preview: URL.createObjectURL(file),
			progress: 0,
			uploaded: true,
			error: false,
		}));

		if (buildZIPFiles?.length) {
			dispatch({
				payload: {
					files: [...buildZIPFiles, ...newUploadedFiles],
				},
				type: TYPES.UPLOAD_BUILD_ZIP_FILES,
			});
		} else {
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
				files
			},
			type: TYPES.UPLOAD_BUILD_ZIP_FILES,
		});
	};

	return (
		<div className='provide-app-build-page-container'>
			<Header
				description='Use one of the following methods to provide your app builds.'
				title='Provide app build'
			/>

			<Section
				required
				label='LXC SaaS Compatible?'
				tooltip='More Info'
				tooltipText='MoreInfo'
			>
				<div className='provide-app-build-page-saas-compatible-container'>
					<RadioCard
						description='Lorem ipsum dolor sit amet consectetur.'
						title='Yes'
						icon={taskCheckedIcon}
						selected={appType === 'saas'}
						onChange={() => {
							dispatch({
								payload: { value: 'saas' },
								type: TYPES.UPDATE_APP_LXC_COMPATIBILITY,
							});
						}}
						tooltip='More Info'
					/>

					<RadioCard
						description='Lorem ipsum dolor sit amet consectetur.'
						title='No'
						icon={cancelIcon}
						selected={appType === 'osgi'}
						onChange={() => {
							dispatch({
								payload: { value: 'osgi' },
								type: TYPES.UPDATE_APP_LXC_COMPATIBILITY,
							});
						}}
						tooltip='More Info'
					/>
				</div>
			</Section>

			<Section
				required
				label='App Build'
				tooltip='More Info'
				tooltipText='MoreInfo'
			>
				<div className='provide-app-build-page-app-build-radio-container'>
					<RadioCard
						disabled
						description='Use any build from any available Liferay Experience Cloud account (requires LXC account) '
						title='Via Liferay Experience Cloud Integration'
						icon={cloudIcon}
						selected={appBuild === 'LXC'}
						onChange={() => {
							dispatch({
								payload: { value: 'LXC' },
								type: TYPES.UPDATE_APP_BUILD,
							});
						}}
						tooltip='More Info'
					/>

					<RadioCard
						disabled
						description='Use any build from your computer connecting with a Github provider'
						title='Via GitHub Repo'
						icon={githubIcon}
						selected={appBuild === 'GitHub'}
						onChange={() => {
							dispatch({
								payload: { value: 'GitHub' },
								type: TYPES.UPDATE_APP_BUILD,
							});
						}}
						tooltip='More Info'
					/>

					<RadioCard
						description='Use any local ZIP files to upload. Max file size is 500MB'
						title='Via ZIP Upload'
						icon={uploadIcon}
						selected={appBuild === 'upload'}
						onChange={() => {
							dispatch({
								payload: { value: 'upload' },
								type: TYPES.UPDATE_APP_BUILD,
							});
						}}
						tooltip='More Info'
					/>
				</div>
			</Section>

			<Section
				description='Select a local file to upload'
				label='Upload ZIP Files'
				required
				tooltip='MoreInfo'
				tooltipText='MoreInfo'
			>
				<FileList
					onDelete={handleDelete}
					type='document'
					uploadedFiles={buildZIPFiles ? buildZIPFiles : []}
				/>

				<DropzoneUpload
					buttonText='Select a file'
					title='Drag and drop to upload or'
					description='Only ZIP files are allowed. Max file size is 500MB '
					maxFiles={1}
					multiple={false}
					maxSize={500000000}
					onHandleUpload={handleUpload}
					acceptFileTypes={acceptFileTypes}
				/>
			</Section>

			<NewAppPageFooterButtons
				disableContinueButton={!buildZIPFiles?.length}
				onClickBack={() => onClickBack()}
				onClickContinue={() => {
					const submitAppBuildType = async () => {
						const dataSpecification = await createSpecification({
							body: {
								key: 'type',
								title: { en_US: 'Type' },
							},
						});

						createProductSpecification({
							body: {
								productId: appProductId,
								specificationId: dataSpecification.id,
								specificationKey: dataSpecification.key,
								value: { en_US: appType },
							},
							appId,
						});
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
