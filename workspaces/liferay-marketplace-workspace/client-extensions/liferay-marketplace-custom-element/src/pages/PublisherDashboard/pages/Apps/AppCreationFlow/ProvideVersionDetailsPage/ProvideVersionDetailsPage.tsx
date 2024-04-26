/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Header} from '../../../../../../components/Header/Header';
import {Input} from '../../../../../../components/Input/Input';
import {NewAppPageFooterButtons} from '../../../../../../components/NewAppPageFooterButtons/NewAppPageFooterButtons';
import {Section} from '../../../../../../components/Section/Section';
import {Liferay} from '../../../../../../liferay/liferay';
import {
	addExpandoValue,
	createAppSKU,
	getOptions,
	postOption,
	postOptionValue,
	postProductOption,
} from '../../../../../../utils/api';
import {
	createSkuName,
	getDxpOptionBody,
	getDxpProductOptionBody,
	getLicenceTypesObject,
	getOptionDeveloperBody,
	getOptionNoBody,
	getOptionStandardBody,
	getOptionTrialBody,
	getOptionYesBody,
	getTrialOptionBody,
	getTrialProductOptionBody,
} from '../../../../../../utils/util';
import {useAppContext} from '../AppContext/AppManageState';
import {TYPES} from '../AppContext/actionTypes';

import './ProvideVersionDetailsPage.scss';

import {useState} from 'react';
import useSWR from 'swr';

type ProvideVersionDetailsPageProps = {
	onClickBack: () => void;
	onClickContinue: () => void;
};

export function ProvideVersionDetailsPage({
	onClickBack,
	onClickContinue,
}: ProvideVersionDetailsPageProps) {
	const [isProcessing, setProcessing] = useState(false);

	const [
		{
			appNotes,
			appProductId,
			appType,
			appVersion,
			optionId,
			productOptionId,
		},
		dispatch,
	] = useAppContext();

	const {data: options = []} = useSWR('/publish-product/options', () =>
		getOptions()
	);

	const isDXP = appType.value === 'dxp';

	const createExpandoValue = (skuId: number) => {
		addExpandoValue({
			attributeValues: {
				'Version': appVersion,
				'Version Description': appNotes,
			},
			className: 'com.liferay.commerce.product.model.CPInstance',
			classPK: skuId,
			companyId: Liferay.ThemeDisplay.getCompanyId(),
			tableName: 'CUSTOM_FIELDS',
		});
	};

	const createProductOptions = async () => {
		const trialOption = options.find(({key}) => key === 'trial');

		const dxpOption = options.find(
			({key}) => key === 'dxp-license-usage-type'
		);

		const targetOption = isDXP ? dxpOption : trialOption;
		let newOptionId: number;

		if (!optionId && !targetOption) {
			newOptionId = await postOption(
				isDXP ? getDxpOptionBody() : getTrialOptionBody()
			);
		}
		else {
			newOptionId = optionId ?? targetOption!.id;
		}

		const productOption = isDXP
			? getDxpProductOptionBody(newOptionId)
			: getTrialProductOptionBody(newOptionId);

		const newProductOptionId = await postProductOption(
			appProductId,
			productOption
		);

		dispatch({
			payload: {value: newOptionId},
			type: TYPES.UPDATE_OPTION_ID,
		});

		dispatch({
			payload: {value: newProductOptionId},
			type: TYPES.UPDATE_PRODUCT_OPTION_ID,
		});

		if (isDXP) {
			const [
				standardOptionId,
				developerOptionId,
				trialOptionId,
			] = await Promise.all([
				postOptionValue(getOptionStandardBody(), newProductOptionId),
				postOptionValue(getOptionDeveloperBody(), newProductOptionId),
				postOptionValue(getOptionTrialBody(), newProductOptionId),
			]);

			return {
				developerOptionId,
				newProductOptionId,
				standardOptionId,
				trialOptionId,
			};
		}

		const [noOptionId, yesOptionId] = await Promise.all([
			postOptionValue(getOptionNoBody(), newProductOptionId),
			postOptionValue(getOptionYesBody(), newProductOptionId),
		]);

		dispatch({
			payload: {
				newOptionId,
				noOptionId,
				yesOptionId,
			},
			type: TYPES.UPDATE_PRODUCT_OPTION_VALUES_ID,
		});

		return {
			newProductOptionId,
			noOptionId,
			yesOptionId,
		};
	};

	const getSkuBody = (
		sku: string,
		skuProductOptions: Awaited<ReturnType<typeof createProductOptions>>,
		skuName = sku
	) => {
		let value;

		const payload = {
			appProductId,
			body: {
				published: true,
				purchasable: true,
				sku: skuName,
				skuOptions: [
					{
						key: skuProductOptions.newProductOptionId,
						value,
					},
				],
			},
		};

		if (isDXP) {
			if (sku === 'DEVELOPER') {
				value = skuProductOptions.developerOptionId;
			}

			if (sku === 'STANDARD') {
				value = skuProductOptions.standardOptionId;
			}

			if (sku === 'TRIAL') {
				value = skuProductOptions.trialOptionId;
			}
		}
		else {
			value = skuProductOptions.noOptionId;
		}

		payload.body.skuOptions[0].value = value;

		return payload;
	};

	const createSkus = async (
		skuProductOptions: Awaited<ReturnType<typeof createProductOptions>>
	) => {
		if (isDXP) {
			for (const sku of getLicenceTypesObject()) {
				const response = await createAppSKU(
					getSkuBody(
						sku.name,
						skuProductOptions,
						createSkuName(appProductId, appVersion, sku.code)
					)
				);

				if (sku.name === 'TRIAL') {
					dispatch({
						payload: {value: response.id},
						type: TYPES.UPDATE_SKU_TRIAL_ID,
					});
				}

				createExpandoValue(response.id);
			}

			return;
		}
		const sku = getSkuBody(
			createSkuName(appProductId, appVersion),
			skuProductOptions
		);

		const response = await createAppSKU(sku);

		createExpandoValue(response.id);

		dispatch({
			payload: {value: response.id},
			type: TYPES.UPDATE_SKU_VERSION_ID,
		});
	};

	return (
		<div className="provide-version-details-page-container">
			<div className="provide-version-details-page-header">
				<Header
					description="Define version information for your app. This will inform users about this version's updates on the storefront."
					title="Provide version details"
				/>
			</div>

			<Section
				label="App Version"
				tooltip="When adding app versions, you can use your own numbering system, but be sure it is consistent and understandable by the customer."
				tooltipText="More Info"
			>
				<Input
					helpMessage="This is the first version of the app to be published"
					label="Version"
					onChange={({target}) =>
						dispatch({
							payload: {value: target.value},
							type: TYPES.UPDATE_APP_VERSION,
						})
					}
					placeholder="0.0.0"
					required
					tooltip={`Specify your app's version. This will help the user to understand the latest version of your app offered on the Marketplace.`}
					value={appVersion}
				/>

				<Input
					component="textarea"
					label="Notes"
					onChange={({target}) =>
						dispatch({
							payload: {value: target.value},
							type: TYPES.UPDATE_APP_NOTES,
						})
					}
					placeholder="Enter app description"
					required
					tooltip="Notes pertaining to the release of the project. These will be displayed when the customer goes to purchase and/or update the app."
					value={appNotes}
				/>
			</Section>

			<NewAppPageFooterButtons
				disableContinueButton={!appVersion || !appNotes || isProcessing}
				isLoading={isProcessing}
				onClickBack={() => onClickBack()}
				onClickContinue={async () => {
					if (!productOptionId) {
						setProcessing(true);

						const skuProductOptions = await createProductOptions();
						await createSkus(skuProductOptions);

						setProcessing(false);
					}

					onClickContinue();
				}}
			/>
		</div>
	);
}
