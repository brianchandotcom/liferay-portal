/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {useState} from 'react';

import {Header} from '../../../../../../components/Header/Header';
import {NewAppPageFooterButtons} from '../../../../../../components/NewAppPageFooterButtons/NewAppPageFooterButtons';
import {RadioCard} from '../../../../../../components/RadioCard/RadioCard';
import {Section} from '../../../../../../components/Section/Section';
import HeadlessCommerceAdminCatalogImpl from '../../../../../../services/rest/HeadlessCommerceAdminCatalog';
import {
	createAppSKU,
	createProductSpecification,
	deleteTrialSKU,
	getProductSKU,
	getSpecification,
	patchSKUById,
	updateProductSpecification,
} from '../../../../../../utils/api';
import {createSkuName, getSkuPrice} from '../../../../../../utils/util';
import {useAppContext} from '../AppContext/AppManageState';
import {TYPES} from '../AppContext/actionTypes';

import './InformLicensingTermsPage.scss';
import {PRODUCT_SPECIFICATION_KEY} from '../../../../../../enums/Product';

type InformLicensingTermsPageProps = {
	onClickBack: () => void;
	onClickContinue: () => void;
};

export function InformLicensingTermsPage({
	onClickBack,
	onClickContinue,
}: InformLicensingTermsPageProps) {
	const [
		{
			appLicense,
			appLicensePrice,
			appNotes,
			appProductId,
			appType,
			appVersion,
			dayTrial,
			optionValuesId,
			priceModel,
			productOptionId,
			skuTrialId,
		},
		dispatch,
	] = useAppContext();

	const [isProcessing, setProcessing] = useState(false);

	const isDXP = appType.value === 'dxp';

	const upsertProductSpecification = async () => {
		const value =
			appLicense.value === 'Perpetual'
				? {en_US: 'Perpetual'}
				: {en_US: 'Subscription'};

		if (appLicense.id) {
			return updateProductSpecification({
				body: {
					specificationKey:
						PRODUCT_SPECIFICATION_KEY.APP_LICENSING_TYPE,
					value,
				},
				id: appLicense.id,
			});
		}

		const dataSpecification = await getSpecification(
			PRODUCT_SPECIFICATION_KEY.APP_LICENSING_TYPE
		);

		const {id} = await createProductSpecification({
			body: {
				specificationId: dataSpecification.id,
				specificationKey: dataSpecification.key,
				value,
			},
			id: appProductId,
		});

		dispatch({
			payload: {id, value: appLicense.value},
			type: TYPES.UPDATE_APP_LICENSE,
		});
	};

	const submitLicenseTermsPage = async () => {
		const {items: skus} =
			await HeadlessCommerceAdminCatalogImpl.getProductSkus(appProductId);

		for (const sku of skus) {
			await patchSKUById(sku.id, {
				customFields: [
					{
						customValue: {
							data: appNotes,
						},
						dataType: 'Text',
						name: 'Version Description',
					},
					{
						customValue: {
							data: appVersion,
						},
						dataType: 'Text',
						name: 'Version',
					},
				],
				neverExpire: true,
				price:
					priceModel.value === 'Free'
						? 0
						: getSkuPrice(appLicensePrice, sku),
			});
		}

		await upsertProductSpecification();

		if (dayTrial !== 'yes' && skuTrialId) {
			return deleteTrialSKU(skuTrialId);
		}

		const skuResponse = await getProductSKU({
			appProductId,
		});

		const trialSku = skuResponse.items.find(
			({sku}) =>
				sku === createSkuName(appProductId, appVersion, 'ts') ||
				sku === 'TRIAL'
		);

		let _skuTrialId;

		if (trialSku) {
			_skuTrialId = trialSku.id;
		}
		else if (!isDXP) {
			const response = await createAppSKU({
				appProductId,
				body: {
					neverExpire: false,
					price: 0,
					published: true,
					purchasable: true,
					sku: createSkuName(appProductId, appVersion, 'ts'),
					skuOptions: [
						{
							key: productOptionId,
							value: optionValuesId.yesOptionId,
						},
					],
				},
			});

			_skuTrialId = response.id;
		}

		dispatch({
			payload: {
				value: _skuTrialId,
			},
			type: TYPES.UPDATE_SKU_TRIAL_ID,
		});
	};

	return (
		<div className="informing-licensing-terms-page-container">
			<Header
				description="Define the licensing approach for your app. This will impact users' licensing renewal experience."
				title="Select licensing terms"
			/>

			<Section
				label="App License"
				required
				tooltip="More Info"
				tooltipText="More Info"
			>
				<div className="informing-licensing-terms-page-app-license-container">
					<RadioCard
						description="The app version is offered in perpetuity."
						icon="time"
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
						description="App License must be renewed annually."
						disabled={priceModel.value === 'Free'}
						icon="document-pending"
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
						title="Subscription License"
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
						icon="check-circle"
						onChange={() =>
							dispatch({
								payload: {value: 'yes'},
								type: TYPES.UPDATE_APP_TRIAL_INFO,
							})
						}
						selected={dayTrial === 'yes'}
						title="Yes"
						tooltip="Offer a 30-day free trial for this app."
					/>

					<RadioCard
						description="Do not offer a 30-day free trial."
						icon="times-circle"
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
				disableContinueButton={isProcessing}
				isLoading={isProcessing}
				onClickBack={() => onClickBack()}
				onClickContinue={async () => {
					setProcessing(true);

					await submitLicenseTermsPage();

					setProcessing(false);

					onClickContinue();
				}}
				showBackButton
			/>
		</div>
	);
}
