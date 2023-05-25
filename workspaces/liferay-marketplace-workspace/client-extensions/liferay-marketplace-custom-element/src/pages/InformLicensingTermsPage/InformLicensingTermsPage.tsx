/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

import cancelIcon from '../../assets/icons/cancel_icon.svg';
import pendingActionsIcon from '../../assets/icons/pending_actions_icon.svg';
import scheduleIcon from '../../assets/icons/schedule_icon.svg';
import taskCheckedIcon from '../../assets/icons/task_checked_icon.svg';
import {Header} from '../../components/Header/Header';
import {NewAppPageFooterButtons} from '../../components/NewAppPageFooterButtons/NewAppPageFooterButtons';
import {RadioCard} from '../../components/RadioCard/RadioCard';
import {Section} from '../../components/Section/Section';
import {getCompanyId} from '../../liferay/constants';
import {useAppContext} from '../../manage-app-state/AppManageState';
import {TYPES} from '../../manage-app-state/actionTypes';
import {
	addExpandoValue,
	createAppSKU,
	createProductSpecification,
	createSpecification,
	deleteTrialSKU,
	getProductSKU,
	getSKUById,
	patchSKUById,
	updateProductSpecification,
} from '../../utils/api';
import {createSkuName} from '../../utils/util';

import './InformLicensingTermsPage.scss';

interface InformLicensingTermsPageProps {
	onClickBack: () => void;
	onClickContinue: () => void;
}

export function InformLicensingTermsPage({
	onClickBack,
	onClickContinue,
}: InformLicensingTermsPageProps) {
	const [
		{
			appId,
			appLicense,
			appLicensePrice,
			appNotes,
			appProductId,
			appVersion,
			dayTrial,
			optionValuesId,
			priceModel,
			productOptionId,
			skuTrialId,
			skuVersionId,
		},
		dispatch,
	] = useAppContext();

	return (
		<div className="informing-licensing-terms-page-container">
			<Header
				description="Define the licensing approach for your app. This will impact users' licensing renewal experience."
				title="Inform licensing terms"
			/>

			<Section
				label="App License"
				required
				tooltip="More Info"
				tooltipText="More Info"
			>
				<div className="informing-licensing-terms-page-app-license-container">
					<RadioCard
						description="The app is offered in the Marketplace with no charge."
						icon={scheduleIcon}
						onChange={() => {
							dispatch({
								payload: {
									id: appLicense.id,
									value: 'Perpetual',
								},
								type: TYPES.UPDATE_APP_LICENSE,
							});
						}}
						selected={appLicense.value === 'Perpetual'}
						title="Perpetual License"
						tooltip="A perpetual license requires no renewal and never expires."
					/>

					<RadioCard
						description="License must be renewed annually."
						disabled={priceModel.value === 'Free'}
						icon={pendingActionsIcon}
						onChange={() => {
							dispatch({
								payload: {
									id: appLicense.id,
									value: 'non-perpetual',
								},
								type: TYPES.UPDATE_APP_LICENSE,
							});
						}}
						selected={appLicense.value === 'non-perpetual'}
						title="Non-perpetual license"
						tooltip="A subscription license that must be renewed annually."
					/>
				</div>
			</Section>

			<Section
				label="30-day Trial"
				required
				tooltip="Trials can be offered to users for 30 days.  After this time, they will be notified of their pending trial expiration and given the opportunity to purchase the app at full price."
				tooltipText="More Info"
			>
				<div className="informing-licensing-terms-page-day-trial-container">
					<RadioCard
						description="Offer a 30-day free trial for this app."
						disabled={priceModel.value === 'Free'}
						icon={taskCheckedIcon}
						onChange={() => {
							dispatch({
								payload: {value: 'yes'},
								type: TYPES.UPDATE_APP_TRIAL_INFO,
							});
						}}
						selected={dayTrial === 'yes'}
						title="Yes"
						tooltip="Offer a 30-day free trial for this app."
					/>

					<RadioCard
						description="Do not offer a 30-day free trial."
						icon={cancelIcon}
						onChange={() => {
							dispatch({
								payload: {value: 'no'},
								type: TYPES.UPDATE_APP_TRIAL_INFO,
							});
						}}
						selected={dayTrial === 'no'}
						title="No"
						tooltip="Do not offer a 30-day trial for this app."
					/>
				</div>
			</Section>

			<NewAppPageFooterButtons
				onClickBack={() => onClickBack()}
				onClickContinue={() => {
					const submitLicenseTermsPage = async () => {
						const versionSkuJSON = await getSKUById(skuVersionId);
						if (appLicense.id) {
							updateProductSpecification({
								body: {
									specificationKey: 'license-type',
									value:
										appLicense.value === 'Perpetual'
											? {en_US: 'Perpetual'}
											: {en_US: 'Subscription'},
								},
								id: appLicense.id,
							});
						}
						else {
							const dataSpecification = await createSpecification(
								{
									body: {
										key: 'license-type',
										title: {en_US: 'License Type'},
									},
								}
							);

							const {id} = await createProductSpecification({
								appId,
								body: {
									productId: appProductId,
									specificationId: dataSpecification.id,
									specificationKey: dataSpecification.key,
									value:
										appLicense.value === 'Perpetual'
											? {en_US: 'Perpetual'}
											: {en_US: 'Subscription'},
								},
							});

							dispatch({
								payload: {id, value: appLicense.value},
								type: TYPES.UPDATE_APP_LICENSE,
							});
						}

						if (
							priceModel.value === 'Free' ||
							appLicense.value === 'Perpetual'
						) {
							const skuBody = {
								...versionSkuJSON,
								neverExpire: true,
								price:
									appLicense.value === 'Perpetual'
										? appLicensePrice
										: 0,
								published: true,
								purchasable: true,
							};

							await patchSKUById(skuVersionId, skuBody);
						}
						else if (appLicense.value === 'non-perpetual') {
							const skuBody = {
								...versionSkuJSON,
								neverExpire: false,
								skuSubscriptionConfiguration: {
									enable: true,
									length: 1,
									numberOfLength: 1,
									overrideSubscriptionInfo: true,
									subscriptionType: 'yearly',
									subscriptionTypeSettings: {yearlyMode: 0},
								},
							};

							await patchSKUById(skuVersionId, skuBody);
						}

						if (dayTrial === 'yes') {
							const skuResponse = await getProductSKU({
								appProductId,
							});

							const trialSku = skuResponse.items.find(
								({sku}) =>
									sku ===
									createSkuName(
										appProductId,
										appVersion,
										'ts'
									)
							);

							let skuTrialId;

							if (trialSku) {
								skuTrialId = trialSku.id;
							}
							else {
								const response = await createAppSKU({
									appProductId,
									body: {
										neverExpire: false,
										price: 0,
										published: true,
										purchasable: true,
										sku: createSkuName(
											appProductId,
											appVersion,
											'ts'
										),

										skuOptions: [
											{
												key: productOptionId,
												value: optionValuesId.yesOptionId,
											},
										],

										skuSubscriptionConfiguration: {
											enable: true,
											length: 30,
											numberOfLength: 1,
											overrideSubscriptionInfo: true,
											subscriptionType: 'daily',
										},
									},
								});

								skuTrialId = response.id;

								dispatch({
									payload: {
										value: response.id,
									},
									type: TYPES.UPDATE_SKU_TRIAL_ID,
								});
							}

							addExpandoValue({
								attributeValues: {
									'Version': appVersion,
									'Version Description': appNotes,
								},
								className:
									'com.liferay.commerce.product.model.CPInstance',
								classPK: skuTrialId,
								companyId: Number(getCompanyId()),
								tableName: 'CUSTOM_FIELDS',
							});
						}
						else if (skuTrialId) {
							deleteTrialSKU(skuTrialId);
						}
					};

					submitLicenseTermsPage();

					onClickContinue();
				}}
				showBackButton
			/>
		</div>
	);
}
