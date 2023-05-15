import {filesize} from 'filesize';
import {uniqueId} from 'lodash';
import {useEffect, useState} from 'react';
import ReactDOMServer from 'react-dom/server';

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
					tooltip="The app info section helps you differentiate your app offering from others in the Marketplace. It should be clear and concise - explaining the purpose, function, and value it provides to your prospective customer.  Tooltips along the way will provide you guidance as you determine the best content for each field.  Your app will be reviewed thoroughly before listing in the Marketplace and we will ensure the best quality apps are present in the Marketplace for our customers."
					tooltipText="More Info"
				>
					<UploadLogo
						onDeleteFile={handleLogoDelete}
						onUpload={handleLogoUpload}
						tooltip={ReactDOMServer.renderToString(
							<span>
								The icon is a small image representation of the
								app. Icons must be a PNG, JPG, or GIF format and
								cannot exceed 5MB. Animated images are
								prohibited. The use of the Liferay logo,
								including any permitted alternate versions of
								the Liferay logo, is permitted only with
								Liferay's express permission. Please refer to
								our{' '}
								<a href="https://www.liferay.com/trademark">
									trademark policy
								</a>{' '}
								for details.
							</span>
						)}
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
							tooltip={ReactDOMServer.renderToString(
								<span>
									Customers of the marketplace will see this
									as the name of the app. Please use a title
									of no longer than 50 characters. Titles
									longer than 18 characters may be truncated.
									The App title may contain the word "Liferay"
									to describe its use or intent as long as the
									name does not imply official certification
									or validation from Liferay, Inc. An example
									of permissible names would be "Exchange
									Connector for Liferay" or "Integration
									Connector Kit for Liferay" while "Liferay
									Mail App" or "Liferay Management Console"
									would not be permitted without explicit
									approval. Please refer to our{' '}
									<a href="https://www.liferay.com/trademark">
										trademark policy
									</a>
									.
								</span>
							)}
							value={appName}
						/>

						<Input
							component="textarea"
							label="Description"
							localized
							localizedTooltipText="Descriptions can be localized for each language your app supports.  Please choose the appropriate language and enter description in the language selected."
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
							tooltip="You can put anything you want here, but a good guideline is no more than 4-5 paragraphs. This field does not allow any markup tags - it’s just text. Please do not use misleading names, information, or icons. Descriptions should be as concise as possible. Ensure your icons, images, descriptions, and tags are free of profanity or other offensive material."
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
							tooltip="Choose the Marketplace category that most accurately describes what your app does. Users looking for specific types of apps will often browse categories by searching on a specific category name in the main Marketplace home page. Having your app listed under the appropriate category will help them find your app."
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
							tooltip="Tags help to describe your app in the Marketplace. Select the tags most relevant to your app. They can be changed if needed."
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
