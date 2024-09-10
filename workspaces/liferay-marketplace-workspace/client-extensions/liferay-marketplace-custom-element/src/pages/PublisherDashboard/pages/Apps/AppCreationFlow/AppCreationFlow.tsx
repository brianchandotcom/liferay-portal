/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {useEffect, useState} from 'react';

import {AppFlowList} from '../../../../../components/NewAppFlowList/AppFlowList';
import {initialFLowListItems} from './AppCreationFlowUtil';
import {ChoosePricingModelPage} from './ChoosePricingModelPage/ChoosePricingModelPage';
import {CreateNewAppPage} from './CreateNewAppPage/CreateNewAppPage';
import {InformLicensingTermsPage} from './InformLicensingTermsPage/InformLicensingTermsPage';
import {InformLicensingTermsPricePage} from './InformLicensingTermsPage/InformLicensingTermsPricePage';
import {ProvideAppBuildPage} from './ProvideAppBuildPage/ProvideAppBuildPage';
import {ProvideAppSupportAndHelpPage} from './ProvideAppSupportAndHelpPage/ProvideAppSupportAndHelpPage';
import {ProvideVersionDetailsPage} from './ProvideVersionDetailsPage/ProvideVersionDetailsPage';
import {ReviewAndSubmitAppPage} from './ReviewAndSubmitAppPage/ReviewAndSubmitAppPage';
import {CustomizeAppStorefrontPage} from './StorefrontPage/CustomizeAppStorefrontPage';

import './AppCreationFlow.scss';

import {useNavigate} from 'react-router-dom';

import AppToolbar from '../../../../../components/AppPublish/Navbar';
import Loading from '../../../../../components/Loading';
import {useAccount} from '../../../../../hooks/data/useAccounts';
import {Liferay} from '../../../../../liferay/liferay';
import {getCategories, getVocabularies} from '../../../../../utils/api';
import {useAppContext} from './AppContext/AppManageState';
import {DefineAppProfilePage} from './DefineAppProfilePage/DefineAppProfilePage';

type SetAppFlowListStateProps = {
	checkedItems?: string[];
	selectedItem: string;
};

type AppCreationFlowProps = {
	catalogId: string;
};

type VocabDropdownItem = {
	checked: boolean;
} & Categories;

export function AppCreationFlow({catalogId}: AppCreationFlowProps) {
	const [{appERC, appLogo, appName, appProductId, priceModel}] =
		useAppContext();
	const [appFlowListItems, setAppFlowListItems] =
		useState(initialFLowListItems);
	const [currentFlow, setCurrentFlow] = useState('create');
	const [isLoading, setLoading] = useState<boolean>(false);
	const [productType, setProductType] = useState<Categories>();
	const [categories, setCategories] = useState<VocabDropdownItem[]>([]);
	const [tags, setTags] = useState<VocabDropdownItem[]>([]);

	const {data: account} = useAccount();
	const navigate = useNavigate();

	const setAppFlowListState = ({
		checkedItems,
		selectedItem,
	}: SetAppFlowListStateProps) => {
		const newAppFlowListItems = appFlowListItems.map((appItem) => {
			if (checkedItems?.includes(appItem.name)) {
				return {
					...appItem,
					checked: true,
					selected: false,
				};
			}

			if (appItem.name === selectedItem) {
				return {
					...appItem,
					checked: false,
					selected: true,
				};
			}

			return {
				...appItem,
				checked: false,
				selected: false,
			};
		});

		setAppFlowListItems(newAppFlowListItems);
	};

	useEffect(() => {
		const getData = async () => {
			setLoading(true);
			const vocabulariesResponse = await getVocabularies();

			let categoryVocabId = 0;
			let productTypeVocabId = 0;
			let tagVocabId = 0;

			vocabulariesResponse.items.forEach(
				(vocab: {id: number; name: string}) => {
					if (vocab.name === 'Marketplace App Category') {
						categoryVocabId = vocab.id;
					}

					if (vocab.name === 'Marketplace App Tags') {
						tagVocabId = vocab.id;
					}

					if (vocab.name === 'Marketplace Product Type') {
						productTypeVocabId = vocab.id;
					}
				}
			);

			const categoriesList = await getCategories({
				vocabId: categoryVocabId,
			});
			const tagsList = await getCategories({vocabId: tagVocabId});

			const productTypeList = await getCategories({
				vocabId: productTypeVocabId,
			});

			const appProductType = productTypeList.find(
				(productType) => productType.name === 'App'
			);

			if (appProductType) {
				setProductType({
					externalReferenceCode: appProductType.externalReferenceCode,
					id: appProductType.id,
					name: appProductType.name,
					vocabulary: 'Marketplace Product Type',
				});
			}

			const categoriesDropdownItems = categoriesList.map((category) => {
				return {
					checked: false,
					externalReferenceCode: category.externalReferenceCode,
					id: category.id,
					label: category.name,
					name: category.name,
					value: category.name,
					vocabulary: 'Marketplace App Category',
				};
			});

			const tagsDropdownItems = tagsList.map((tag) => {
				return {
					checked: false,
					externalReferenceCode: tag.externalReferenceCode,
					id: tag.id,
					label: tag.name,
					name: tag.name,
					value: tag.name,
					vocabulary: 'Marketplace App Tags',
				};
			});

			setCategories(categoriesDropdownItems);
			setTags(tagsDropdownItems);
			setLoading(false);
		};

		getData();
	}, []);

	return (
		<div className="app-creation-flow-container">
			<AppToolbar
				accountImage={account?.logoURL}
				accountName={account?.name as string}
				appImage={appLogo?.preview}
				appName={appName}
				exitProps={{
					onClick: () => navigate('../'),
				}}
			/>

			<div className="app-creation-flow-body">
				{isLoading ? (
					<Loading />
				) : (
					<>
						<AppFlowList appFlowListItems={appFlowListItems} />

						{currentFlow === 'create' && (
							<CreateNewAppPage
								catalogId={catalogId}
								onClickContinue={() => {
									setAppFlowListState({
										checkedItems: ['create'],
										selectedItem: 'profile',
									});

									setCurrentFlow('profile');
								}}
							/>
						)}

						{currentFlow === 'profile' && (
							<DefineAppProfilePage
								categories={categories}
								isLoading={isLoading}
								onClickBack={() => {
									setAppFlowListState({
										selectedItem: 'create',
									});
									setCurrentFlow('create');
								}}
								onClickContinue={() => {
									setAppFlowListState({
										checkedItems: ['create', 'profile'],
										selectedItem: 'build',
									});

									setCurrentFlow('build');
								}}
								productType={productType as Categories}
								setLoading={setLoading}
								tags={tags}
							/>
						)}

						{currentFlow === 'build' && (
							<ProvideAppBuildPage
								onClickBack={() => {
									setAppFlowListState({
										checkedItems: ['create'],
										selectedItem: 'profile',
									});

									setCurrentFlow('profile');
								}}
								onClickContinue={() => {
									setAppFlowListState({
										checkedItems: [
											'create',
											'profile',
											'build',
										],
										selectedItem: 'storefront',
									});

									setCurrentFlow('storefront');
								}}
							/>
						)}

						{currentFlow === 'storefront' && (
							<CustomizeAppStorefrontPage
								onClickBack={() => {
									setAppFlowListState({
										checkedItems: ['create', 'profile'],
										selectedItem: 'build',
									});

									setCurrentFlow('build');
								}}
								onClickContinue={() => {
									setAppFlowListState({
										checkedItems: [
											'create',
											'profile',
											'build',
											'storefront',
										],
										selectedItem: 'version',
									});

									setCurrentFlow('version');
								}}
							/>
						)}

						{currentFlow === 'version' && (
							<ProvideVersionDetailsPage
								onClickBack={() => {
									setAppFlowListState({
										checkedItems: [
											'create',
											'profile',
											'build',
										],
										selectedItem: 'storefront',
									});

									setCurrentFlow('storefront');
								}}
								onClickContinue={() => {
									setAppFlowListState({
										checkedItems: [
											'create',
											'profile',
											'build',
											'storefront',
											'version',
										],
										selectedItem: 'pricing',
									});

									setCurrentFlow('pricing');
								}}
							/>
						)}

						{currentFlow === 'pricing' && (
							<ChoosePricingModelPage
								onClickBack={() => {
									setAppFlowListState({
										checkedItems: [
											'create',
											'profile',
											'build',
											'storefront',
										],
										selectedItem: 'version',
									});

									setCurrentFlow('version');
								}}
								onClickContinue={() => {
									setAppFlowListState({
										checkedItems: [
											'create',
											'profile',
											'build',
											'storefront',
											'version',
											'pricing',
										],
										selectedItem: 'licensing',
									});

									setCurrentFlow('licensing');
								}}
							/>
						)}

						{currentFlow === 'licensing' && (
							<InformLicensingTermsPage
								onClickBack={() => {
									setAppFlowListState({
										checkedItems: [
											'create',
											'profile',
											'build',
											'storefront',
											'version',
										],
										selectedItem: 'pricing',
									});

									setCurrentFlow('pricing');
								}}
								onClickContinue={() => {
									if (priceModel.value !== 'Free') {
										setAppFlowListState({
											checkedItems: [
												'create',
												'profile',
												'build',
												'storefront',
												'version',
												'pricing',
											],
											selectedItem: 'licensing',
										});

										setCurrentFlow('licensingPrice');
									}
									else {
										setAppFlowListState({
											checkedItems: [
												'create',
												'profile',
												'build',
												'storefront',
												'version',
												'pricing',
												'licensing',
											],
											selectedItem: 'support',
										});

										setCurrentFlow('support');
									}
								}}
							/>
						)}

						{currentFlow === 'licensingPrice' && (
							<InformLicensingTermsPricePage
								onClickBack={() => {
									setAppFlowListState({
										checkedItems: [
											'create',
											'profile',
											'build',
											'storefront',
											'version',
											'pricing',
										],
										selectedItem: 'licensing',
									});

									setCurrentFlow('licensing');
								}}
								onClickContinue={() => {
									setAppFlowListState({
										checkedItems: [
											'create',
											'profile',
											'build',
											'storefront',
											'version',
											'pricing',
											'licensing',
										],
										selectedItem: 'support',
									});

									setCurrentFlow('support');
								}}
							/>
						)}

						{currentFlow === 'support' && (
							<ProvideAppSupportAndHelpPage
								onClickBack={() => {
									if (priceModel.value !== 'Free') {
										setAppFlowListState({
											checkedItems: [
												'create',
												'profile',
												'build',
												'storefront',
												'version',
												'pricing',
											],
											selectedItem: 'licensing',
										});

										setCurrentFlow('licensingPrice');
									}
									else {
										setAppFlowListState({
											checkedItems: [
												'create',
												'profile',
												'build',
												'storefront',
												'version',
												'pricing',
											],
											selectedItem: 'licensing',
										});

										setCurrentFlow('licensing');
									}
								}}
								onClickContinue={() => {
									setAppFlowListState({
										checkedItems: [
											'create',
											'profile',
											'build',
											'storefront',
											'version',
											'pricing',
											'licensing',
											'support',
										],
										selectedItem: 'submit',
									});

									setCurrentFlow('submit');
								}}
							/>
						)}

						{currentFlow === 'submit' && (
							<ReviewAndSubmitAppPage
								onClickBack={() => {
									setAppFlowListState({
										checkedItems: [
											'create',
											'profile',
											'build',
											'storefront',
											'version',
											'pricing',
											'licensing',
											'support',
										],
										selectedItem: 'support',
									});

									setCurrentFlow('support');
								}}
								onClickContinue={() => {
									setAppFlowListState({
										checkedItems: [
											'create',
											'profile',
											'build',
											'storefront',
											'version',
											'pricing',
											'licensing',
											'support',
											'submit',
										],
										selectedItem: '',
									});

									location.href = `${Liferay.ThemeDisplay.getLayoutURL().replace(
										'/create-new-app',
										'/publisher-dashboard'
									)}`;
								}}
								productERC={appERC}
								productId={appProductId}
							/>
						)}
					</>
				)}
			</div>
		</div>
	);
}
