import {filesize} from 'filesize';
import {uniqueId} from 'lodash';
import {useEffect, useState} from 'react';

import {UploadedFile} from '../../components/FileList/FileList';
import {Header} from '../../components/Header/Header';
import {Input} from '../../components/Input/Input';
import {MultiSelect} from '../../components/MultiSelect/MultiSelect';
import {NewAppPageFooterButtons} from '../../components/NewAppPageFooterButtons/NewAppPageFooterButtons';
import {Section} from '../../components/Section/Section';
import {UploadLogo} from '../../components/UploadLogo/UploadLogo';
import {useAppContext} from '../../manage-app-state/AppManageState';
import {TYPES} from '../../manage-app-state/actionTypes';
import {
	createApp,
	createImage,
	getCategories,
	getVocabularies,
	updateApp,
} from '../../utils/api';
import {submitBase64EncodedFile} from '../../utils/util';

import './DefineAppProfilePage.scss';

interface DefineAppProfilePageProps {
	onClickBack: () => void;
	onClickContinue: () => void;
}

export function DefineAppProfilePage({
	onClickBack,
	onClickContinue,
}: DefineAppProfilePageProps) {
	const [
		{
			appCategories,
			appDescription,
			appERC,
			appLogo,
			appName,
			appTags,
			catalogId,
		},
		dispatch,
	] = useAppContext();

	const handleLogoUpload = (files: FileList) => {
		const file = files[0];

		const newUploadedFile: UploadedFile = {
			error: false,
			file,
			fileName: file.name,
			id: uniqueId(),
			preview: URL.createObjectURL(file),
			progress: 0,
			readableSize: filesize(file.size),
			uploaded: true,
		};

		dispatch({
			payload: {
				file: newUploadedFile,
			},
			type: TYPES.UPDATE_APP_LOGO,
		});
	};

	const handleLogoDelete = () => {
		dispatch({
			payload: {
				file: undefined,
			},
			type: TYPES.UPDATE_APP_LOGO,
		});
	};

	const [categories, setCategories] = useState([]);
	const [tags, setTags] = useState([]);

	useEffect(() => {
		const getData = async () => {
			const vocabulariesResponse = await getVocabularies();

			let categoryVocabId = 0;
			let tagVocabId = 0;

			vocabulariesResponse.items.forEach(
				(vocab: {id: number; name: string}) => {
					if (vocab.name === 'Marketplace App Category') {
						categoryVocabId = vocab.id;
					}

					if (vocab.name === 'Marketplace App Tags') {
						tagVocabId = vocab.id;
					}
				}
			);

			let categoriesList = await getCategories({
				vocabId: categoryVocabId,
			});
			let tagsList = await getCategories({vocabId: tagVocabId});

			categoriesList = categoriesList.items.map(
				(category: {
					externalReferenceCode: string;
					id: number;
					name: string;
				}) => {
					return {
						checked: false,
						externalReferenceCode: category.externalReferenceCode,
						id: category.id,
						label: category.name,
						value: category.name,
					};
				}
			);

			tagsList = tagsList.items.map(
				(tag: {
					externalReferenceCode: string;
					id: number;
					name: string;
				}) => {
					return {
						checked: false,
						externalReferenceCode: tag.externalReferenceCode,
						id: tag.id,
						label: tag.name,
						value: tag.name,
					};
				}
			);

			setCategories(categoriesList);
			setTags(tagsList);
		};
		getData();
	}, []);

	return (
		<div className="profile-page-container">
			<Header
				description="Enter your new app details. 
                                This information will be used for submission, 
                                presentation, customer support, and search capabilities."
				title="Define the app profile"
			/>

			<div className="profile-page-body-container">
				<Section
					label="App Info"
					tooltip="More Info"
					tooltipText="More info"
				>
					<UploadLogo
						onDeleteFile={handleLogoDelete}
						onUpload={handleLogoUpload}
						uploadedFile={appLogo}
					/>

					<div>
						<Input
							component="input"
							label="Name"
							onChange={({target}) =>
								dispatch({
									payload: {
										value: target.value,
									},
									type: TYPES.UPDATE_APP_NAME,
								})
							}
							placeholder="Enter app name"
							required
							tooltip="Name"
							value={appName}
						/>

						<Input
							component="textarea"
							label="Description"
							localized
							onChange={({target}) =>
								dispatch({
									payload: {
										value: target.value,
									},
									type: TYPES.UPDATE_APP_DESCRIPTION,
								})
							}
							placeholder="Enter app description"
							required
							tooltip="Description"
							value={appDescription}
						/>

						<MultiSelect
							items={categories}
							label="Categories"
							onChange={(value) =>
								dispatch({
									payload: {
										value,
									},
									type: TYPES.UPDATE_APP_CATEGORIES,
								})
							}
							placeholder="Select categories"
							required
							tooltip="Categories"
						/>

						<MultiSelect
							items={tags}
							label="Tags"
							onChange={(value) =>
								dispatch({
									payload: {
										value,
									},
									type: TYPES.UPDATE_APP_TAGS,
								})
							}
							placeholder="Select tags"
							required
							tooltip="Tags"
						/>
					</div>
				</Section>
			</div>

			<NewAppPageFooterButtons
				disableContinueButton={
					!appCategories || !appDescription || !appName || !appTags
				}
				onClickBack={() => onClickBack()}
				onClickContinue={async () => {
					let product;
					let response;

					if (appERC) {
						response = await updateApp({
							appDescription,
							appERC,
							appName,
						});
					}
					else {
						response = await createApp({
							appCategories: [...appCategories, ...appTags],
							appDescription,
							appName,
							catalogId,
						});
					}

					if (!appERC) {
						product = await response.json();

						dispatch({
							payload: {
								value: {
									appERC: product.externalReferenceCode,
									appId: product.id,
									appProductId: product.productId,
									appWorkflowStatusInfo:
										product.workflowStatusInfo,
								},
							},
							type: TYPES.SUBMIT_APP_PROFILE,
						});
					}

					if (appLogo) {
						submitBase64EncodedFile({
							appERC: product.externalReferenceCode,
							file: appLogo.file,
							requestFunction: createImage,
							title: appLogo.fileName,
						});
					}

					onClickContinue();
				}}
				showBackButton
			/>
		</div>
	);
}
