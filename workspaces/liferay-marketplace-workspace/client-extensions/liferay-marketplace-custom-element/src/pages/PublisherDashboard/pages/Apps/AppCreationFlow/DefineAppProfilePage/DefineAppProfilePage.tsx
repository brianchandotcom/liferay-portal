/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {filesize} from 'filesize';
import {useState} from 'react';
import ReactDOMServer from 'react-dom/server';

import {UploadedFile} from '../../../../../../components/FileList/FileList';
import {Header} from '../../../../../../components/Header/Header';
import {Input} from '../../../../../../components/Input/Input';
import {NewAppPageFooterButtons} from '../../../../../../components/NewAppPageFooterButtons/NewAppPageFooterButtons';
import {Section} from '../../../../../../components/Section/Section';
import {createImage} from '../../../../../../utils/api';
import {submitBase64EncodedFile} from '../../../../../../utils/util';
import {useAppContext} from '../AppContext/AppManageState';
import {ActionTypes} from '../AppContext/actionTypes';

import './DefineAppProfilePage.scss';
import MultiSelect from '../../../../../../components/MultiSelect/MultiSelect';
import Select from '../../../../../../components/Select/Select';
import UploadLogo from '../../../../../../components/UploadLogo/UploadLogo';
import {
	ProductSpecificationKey,
	ProductWorkflowStatusCode,
} from '../../../../../../enums/Product';
import i18n from '../../../../../../i18n';
import HeadlessCommerceAdminCatalog from '../../../../../../services/rest/HeadlessCommerceAdminCatalog';
import {getRandomID} from '../../../../../../utils/string';

type DefineAppProfilePageProps = {
	areas: Categories[];
	categories: TaxonomyCategory[];
	isLoading: boolean;
	onClickBack: () => void;
	onClickContinue: () => void;
	productType: Categories;
	setLoading: React.Dispatch<React.SetStateAction<boolean>>;
	tags: Categories[];
};

export function DefineAppProfilePage({
	areas = [],
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
			appAreas,
			appCategory,
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
			type: ActionTypes.UPDATE_APP_LOGO,
		});
	};

	const handleLogoDelete = () => {
		dispatch({
			payload: {
				file: undefined,
			},
			type: ActionTypes.UPDATE_APP_LOGO,
		});
	};

	const onContinue = async () => {
		let product;

		setLoading(true);

		const catalog =
			await HeadlessCommerceAdminCatalog.getCatalog(catalogId);

		const _category = categories.find(({name}) => name === appCategory);

		const _categories = [
			...appAreas,
			...appTags,
			productType as Categories,
			_category as unknown as Categories,
		];

		if (appERC) {
			product =
				await HeadlessCommerceAdminCatalog.updateProductByExternalReferenceCode(
					appERC,
					{
						catalogId,
						categories: _categories,
						description: {en_US: appDescription},
						name: {en_US: appName},
						productStatus: ProductWorkflowStatusCode.DRAFT,
					}
				);
		}
		else {
			product = await HeadlessCommerceAdminCatalog.createVirtualProduct({
				catalogId,
				categories: _categories,
				description: appDescription,
				name: appName,
				productSpecifications: [
					{
						specificationKey:
							ProductSpecificationKey.APP_DEVELOPER_NAME,
						value: {en_US: catalog?.name},
					},
				],
				productStatus: ProductWorkflowStatusCode.DRAFT,
				workflowStatusInfo: ProductWorkflowStatusCode.DRAFT,
			});

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
				type: ActionTypes.SUBMIT_APP_PROFILE,
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
		areas: '',
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
		defaultItems: Categories[]
	) =>
		defaultItems?.filter(
			(defaultCategory) =>
				!selectedItems?.some(
					(category) => defaultCategory.value === category.value
				)
		);

	const defaultSourceItems = {
		areas: areas ?? [],
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

					<Input
						component="input"
						label="Name"
						onChange={({target}) =>
							dispatch({
								payload: {
									value: target.value,
								},
								type: ActionTypes.UPDATE_APP_NAME,
							})
						}
						placeholder="Enter app name"
						required
						tooltip={ReactDOMServer.renderToString(
							<span>
								Customers of the marketplace will see this as
								the name of the app. Please use a title of no
								longer than 50 characters. Titles longer than 18
								characters may be truncated. The App title may
								contain the word &quot;Liferay&quot; to describe
								its use or intent as long as the name does not
								imply official certification or validation from
								Liferay, Inc. An example of permissible names
								would be &quot;Exchange Connector for
								Liferay&quot; or &quot;Integration Connector Kit
								for Liferay&quot; while &quot;Liferay Mail
								App&quot; or &quot;Liferay Management
								Console&quot; would not be permitted without
								explicit approval. Please refer to our{' '}
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
								type: ActionTypes.UPDATE_APP_DESCRIPTION,
							})
						}
						placeholder="Enter app description"
						required
						tooltip="You can put anything you want here, but a good guideline is no more than 4-5 paragraphs. This field does not allow any markup tags - it’s just text. Please do not use misleading names, information, or icons. Descriptions should be as concise as possible. Ensure your icons, images, descriptions, and tags are free of profanity or other offensive material."
						value={appDescription}
					/>

					<Select
						boldLabel
						label="Category"
						name="category"
						onChange={({target: {value}}) =>
							dispatch({
								payload: {
									value,
								},
								type: ActionTypes.UPDATE_APP_CATEGORIES,
							})
						}
						options={categories.map(({name}) => ({
							key: name,
							name,
						}))}
						required
						value={appCategory}
					/>

					<MultiSelect
						inputName="areas"
						label={i18n.translate('area')}
						multiselectKey={`area-${
							getFilteredItems(
								appAreas,
								defaultSourceItems?.areas
							).length
						}`}
						onChange={(value: string) =>
							onChangeMultiSelect({
								target: {
									name: 'areas',
									value,
								},
							})
						}
						onItemsChange={(value: {[key: string]: string}[]) =>
							dispatch({
								payload: {
									value,
								},
								type: ActionTypes.UPDATE_APP_AREAS,
							})
						}
						placeholder={i18n.translate('select-areas')}
						required
						selectedItems={appAreas}
						sourceItems={getFilteredItems(
							appAreas,
							defaultSourceItems?.areas
						)}
						value={multiSelectText?.areas}
					/>

					<MultiSelect
						inputName="tags"
						label={i18n.translate('tags')}
						multiselectKey={`tag-${
							getFilteredItems(appTags, defaultSourceItems?.tags)
								.length
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
								type: ActionTypes.UPDATE_APP_TAGS,
							})
						}
						placeholder={i18n.translate('select-tags')}
						required
						selectedItems={appTags}
						sourceItems={getFilteredItems(
							appTags,
							defaultSourceItems?.tags
						)}
						value={multiSelectText?.tags}
					/>
				</Section>
			</div>

			<NewAppPageFooterButtons
				disableContinueButton={
					isLoading ||
					!appCategory ||
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
