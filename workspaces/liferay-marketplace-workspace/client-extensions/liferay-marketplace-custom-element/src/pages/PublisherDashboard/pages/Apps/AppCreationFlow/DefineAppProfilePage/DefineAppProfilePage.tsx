/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

/* eslint-disable react/no-unescaped-entities */

import {filesize} from 'filesize';
import {useState} from 'react';
import ReactDOMServer from 'react-dom/server';

import {UploadedFile} from '../../../../../../components/FileList/FileList';
import {Header} from '../../../../../../components/Header/Header';
import {Input} from '../../../../../../components/Input/Input';
import {NewAppPageFooterButtons} from '../../../../../../components/NewAppPageFooterButtons/NewAppPageFooterButtons';
import {Section} from '../../../../../../components/Section/Section';
import {createApp, createImage, updateApp} from '../../../../../../utils/api';
import {submitBase64EncodedFile} from '../../../../../../utils/util';
import {useAppContext} from '../AppContext/AppManageState';
import {TYPES} from '../AppContext/actionTypes';

import './DefineAppProfilePage.scss';
import MultiSelect from '../../../../../../components/MultiSelect/MultiSelect';
import UploadLogo from '../../../../../../components/UploadLogo/UploadLogo';
import {useMarketplaceContext} from '../../../../../../context/MarketplaceContext';
import {PRODUCT_SPECIFICATION_KEY} from '../../../../../../enums/Product';
import i18n from '../../../../../../i18n';
import HeadlessCommerceAdminCatalogImpl from '../../../../../../services/rest/HeadlessCommerceAdminCatalog';
import {getRandomID} from '../../../../../../utils/string';

type DefineAppProfilePageProps = {
	categories: VocabDropdownItem[];
	isLoading: boolean;
	onClickBack: () => void;
	onClickContinue: () => void;
	productType: Categories;
	setLoading: React.Dispatch<React.SetStateAction<boolean>>;
	tags: VocabDropdownItem[];
};

type VocabDropdownItem = {
	checked: boolean;
} & Categories;

export function DefineAppProfilePage({
	categories,
	isLoading,
	onClickBack,
	onClickContinue,
	productType,
	setLoading,
	tags,
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
	const {channel} = useMarketplaceContext();

	const handleLogoUpload = (files: FileList) => {
		const file = files[0];

		const newUploadedFile: UploadedFile = {
			changed: false,
			error: false,
			file,
			fileName: file.name,
			id: getRandomID(),
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

	const onContinue = async () => {
		let product;
		let response;

		setLoading(true);

		const catalog =
			await HeadlessCommerceAdminCatalogImpl.getCatalog(catalogId);

		if (appERC) {
			response = await updateApp({
				appDescription: appDescription?.replace(/\n/g, '<br>'),
				appERC,
				appName,
			});
		}
		else {
			response = await createApp({
				appCategories: [
					...appCategories,
					...appTags,
					productType as Categories,
				],
				appDescription: appDescription?.replace(/\n/g, '<br>'),
				appName,
				catalogId,
				productChannels: [
					{
						channelId: channel?.id as number,
						currencyCode: channel?.currencyCode as string,
						externalReferenceCode:
							channel?.externalReferenceCode as string,
						id: channel?.id as number,
						name: channel?.name as string,
						type: channel?.type as string,
					},
				],
				productSpecifications: [
					{
						specificationKey:
							PRODUCT_SPECIFICATION_KEY.APP_LICENSING_TYPE,
						value: {en_US: catalog?.name},
					},
				],
			});

			product = await response.json();

			dispatch({
				payload: {
					value: {
						appERC: product.externalReferenceCode,
						appId: product.id,
						appProductId: product.productId,
						appWorkflowStatusInfo: product.workflowStatusInfo,
						virtualSettingId: product.productVirtualSettings.id,
					},
				},
				type: TYPES.SUBMIT_APP_PROFILE,
			});
		}

		if (appLogo) {
			await submitBase64EncodedFile({
				appERC: appERC ?? product.externalReferenceCode,
				file: appLogo.file,
				isAppIcon: true,
				requestFunction: createImage,
				title: appLogo.fileName,
			});
		}

		setLoading(false);

		onClickContinue();
	};

	const [multiSelectText, setMultiSelectText] = useState({
		categories: '',
		tags: '',
	});

	const onChangeMultiSelect = (event: any) => {
		setMultiSelectText((prevState) => ({
			...prevState,
			[event.target.name]: event.target.value,
		}));
	};

	const getFilteredItems = (
		selectedItems: {[key: string]: string}[],
		defaultItems: VocabDropdownItem[]
	) =>
		defaultItems?.filter(
			(defaultCategory) =>
				!selectedItems?.some(
					(category) => defaultCategory.value === category.value
				)
		);

	const defaultSourceItems = {
		categories: categories ?? [],
		tags: tags ?? [],
	};

	return (
		<div className="profile-page-container">
			<Header
				description="Enter your new app details. This information will be used for submission, presentation, customer support, and search capabilities."
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
							inputName="categories"
							label={i18n.translate('categories')}
							multiselectKey={`cat-${
								getFilteredItems(
									appCategories,
									defaultSourceItems?.categories
								).length
							}`}
							onChange={(value: string) =>
								onChangeMultiSelect({
									target: {
										name: 'categories',
										value,
									},
								})
							}
							onItemsChange={(value: {[key: string]: string}[]) =>
								dispatch({
									payload: {
										value,
									},
									type: TYPES.UPDATE_APP_CATEGORIES,
								})
							}
							placeholder={i18n.translate('select-categories')}
							required
							selectedItems={appCategories}
							sourceItems={getFilteredItems(
								appCategories,
								defaultSourceItems?.categories
							)}
							tooltip="Choose the Marketplace category that most accurately describes what your app does. Users looking for specific types of apps will often browse categories by searching on a specific category name in the main Marketplace home page. Having your app listed under the appropriate category will help them find your app."
							value={multiSelectText?.categories}
						/>

						<MultiSelect
							inputName="tags"
							label={i18n.translate('tags')}
							multiselectKey={`tag-${
								getFilteredItems(
									appTags,
									defaultSourceItems?.tags
								).length
							}`}
							onChange={(value: string) =>
								onChangeMultiSelect({
									target: {
										name: 'tags',
										value,
									},
								})
							}
							onItemsChange={(value: {[key: string]: string}[]) =>
								dispatch({
									payload: {
										value,
									},
									type: TYPES.UPDATE_APP_TAGS,
								})
							}
							placeholder={i18n.translate('select-tags')}
							required
							selectedItems={appTags}
							sourceItems={getFilteredItems(
								appTags,
								defaultSourceItems?.tags
							)}
							tooltip="Tags help to describe your app in the Marketplace. Select the tags most relevant to your app. They can be changed if needed."
							value={multiSelectText?.tags}
						/>
					</div>
				</Section>
			</div>

			<NewAppPageFooterButtons
				disableContinueButton={
					isLoading ||
					!appCategories.length ||
					!appDescription ||
					!appName ||
					!appTags.length
				}
				isLoading={isLoading}
				onClickBack={() => onClickBack()}
				onClickContinue={onContinue}
				showBackButton
			/>
		</div>
	);
}
