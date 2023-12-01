/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {useEffect} from 'react';

import {Header} from '../../components/Header/Header';
import {Input} from '../../components/Input/Input';
import {NewAppPageFooterButtons} from '../../components/NewAppPageFooterButtons/NewAppPageFooterButtons';
import {Section} from '../../components/Section/Section';
import {getCompanyId} from '../../liferay/constants';
import {useAppContext} from '../../manage-app-state/AppManageState';
import {TYPES} from '../../manage-app-state/actionTypes';
import {
	addExpandoValue,
	createAppSKU,
	getOptions,
	getProductSKU,
	postOption,
	postOptionValue,
	postProductOption,
} from '../../utils/api';
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
} from '../../utils/util';

import './ProvideVersionDetailsPage.scss';

interface ProvideVersionDetailsPageProps {
	onClickBack: () => void;
	onClickContinue: () => void;
}

export function ProvideVersionDetailsPage({
	onClickBack,
	onClickContinue,
}: ProvideVersionDetailsPageProps) {
	const [
		{
			appNotes,
			appProductId,
			appType,
			appVersion,
			dxpOptionValuesId,
			optionId,
			optionValuesId,
			productOptionId,
		},
		dispatch,
	] = useAppContext();

	let skuId: number;

	const isDxp = appType.value === 'dxp';

	const getSkuBody = (sku: string) => {
		let value;

		if (isDxp) {
			if (sku === 'DEVELOPER') {
				value = dxpOptionValuesId.developerOptionId;
			}
			else if (sku === 'STANDARD') {
				value = dxpOptionValuesId.standardOptionId;
			}
			else {
				value = dxpOptionValuesId.trialOptionId;
			}
		}
		else {
			value = optionValuesId.noOptionId;
		}

		return {
			appProductId,
			body: {
				published: true,
				purchasable: true,
				sku,
				skuOptions: [
					{
						key: productOptionId,
						value,
					},
				],
			},
		};
	};

	useEffect(() => {
		if (!productOptionId) {
			const makeFetch = async () => {
				let newOptionId: number;
				const options = await getOptions();

				const trialOption = options.find(({key}) => key === 'trial');

				const dxpOption = options.find(
					({key}) => key === 'dxp-license-usage-type'
				);

				const targetOption = isDxp ? dxpOption : trialOption;

				if (!optionId && !targetOption) {
					newOptionId = await postOption(
						isDxp ? getDxpOptionBody() : getTrialOptionBody()
					);
				}
				else {
					newOptionId = optionId ?? targetOption!.id;
				}

				dispatch({
					payload: {value: newOptionId},
					type: TYPES.UPDATE_OPTION_ID,
				});

				const productOption = isDxp
					? getDxpProductOptionBody(newOptionId)
					: getTrialProductOptionBody(newOptionId);

				const newProductOptionId = await postProductOption(
					appProductId,
					productOption
				);

				dispatch({
					payload: {value: newProductOptionId},
					type: TYPES.UPDATE_PRODUCT_OPTION_ID,
				});

				if (isDxp) {
					const standardOptionId = await postOptionValue(
						getOptionStandardBody(),
						newProductOptionId
					);
					const developerOptionId = await postOptionValue(
						getOptionDeveloperBody(),
						newProductOptionId
					);
					const trialOptionId = await postOptionValue(
						getOptionTrialBody(),
						newProductOptionId
					);

					dispatch({
						payload: {
							developerOptionId,
							standardOptionId,
							trialOptionId,
						},
						type: TYPES.UPDATE_DXP_PRODUCT_OPTION_VALUES_ID,
					});
				}
				else {
					const noOptionId = await postOptionValue(
						getOptionNoBody(),
						newProductOptionId
					);
					const yesOptionId = await postOptionValue(
						getOptionYesBody(),
						newProductOptionId
					);
					dispatch({
						payload: {noOptionId, yesOptionId},
						type: TYPES.UPDATE_PRODUCT_OPTION_VALUES_ID,
					});
				}
			};

			makeFetch();
		}
	}, [appProductId, dispatch, isDxp, optionId, productOptionId]);

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
					tooltip={`Specify your app's version.  This will help the user to understand the latest version of your app offered on the Marketplace.`}
					value={appVersion}
				/>

				<Input
					component="textarea"
					label="Notes"
					localized
					onChange={({target}) =>
						dispatch({
							payload: {value: target.value},
							type: TYPES.UPDATE_APP_NOTES,
						})
					}
					placeholder="Enter app description"
					required
					tooltip="Notes pertaining to the release of the project.  These will be displayed when the customer goes to purchase and/or update the app."
					value={appNotes}
				/>
			</Section>

			<NewAppPageFooterButtons
				disableContinueButton={!appVersion || !appNotes}
				onClickBack={() => onClickBack()}
				onClickContinue={async () => {
					const skuResponse = await getProductSKU({appProductId});

					const versionSku = skuResponse.items.find(
						({sku}) =>
							sku === createSkuName(appProductId, appVersion)
					);

					if (versionSku) {
						skuId = versionSku?.id;
					}
					else {
						if (isDxp) {
							for (const sku of getLicenceTypesObject()) {
								const response = await createAppSKU(
									getSkuBody(sku.name)
								);

								skuId = response?.id;
							}
						}
						else {
							const sku = getSkuBody(
								createSkuName(appProductId, appVersion)
							);
							const response = await createAppSKU(sku);

							skuId = response?.id;
						}

						dispatch({
							payload: {value: skuId},
							type: TYPES.UPDATE_SKU_VERSION_ID,
						});
					}

					addExpandoValue({
						attributeValues: {
							'Version': appVersion,
							'Version Description': appNotes,
						},
						className:
							'com.liferay.commerce.product.model.CPInstance',
						classPK: skuId,
						companyId: getCompanyId(),
						tableName: 'CUSTOM_FIELDS',
					});

					onClickContinue();
				}}
			/>
		</div>
	);
}
