import {Header} from '../../components/Header/Header';
import {Input} from '../../components/Input/Input';
import {NewAppPageFooterButtons} from '../../components/NewAppPageFooterButtons/NewAppPageFooterButtons';
import {Section} from '../../components/Section/Section';
import {getCompanyId} from '../../liferay/constants';
import {useAppContext} from '../../manage-app-state/AppManageState';
import {TYPES} from '../../manage-app-state/actionTypes';
import {
	addSkuExpandoValue,
	createAppSKU,
	getOptions,
	getProductSKU,
	postOptionValue,
	postTrialOption,
	postTrialProductOption,
} from '../../utils/api';

import './ProvideVersionDetailsPage.scss';

import {useEffect} from 'react';

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
			appVersion,
			optionId,
			optionValuesId,
			productOptionId,
		},
		dispatch,
	] = useAppContext();
	useEffect(() => {
		if (!productOptionId) {
			const makeFetch = async () => {
				let newOptionId: number;
				const options = await getOptions();

				const trialOption = options.find(({key}) => key === 'trial');

				if (!optionId && !trialOption) {
					newOptionId = await postTrialOption();
				}
				else {
					newOptionId = optionId ?? trialOption!.id;
				}

				dispatch({
					payload: {value: newOptionId},
					type: TYPES.UPDATE_OPTION_ID,
				});

				const newProductOptionId = await postTrialProductOption(
					newOptionId,
					appProductId
				);

				dispatch({
					payload: {value: newProductOptionId},
					type: TYPES.UPDATE_PRODUCT_OPTION_ID,
				});

				const noOptionId = await postOptionValue(
					'no',
					'No',
					newProductOptionId,
					0
				);
				const yesOptionId = await postOptionValue(
					'yes',
					'Yes',
					newProductOptionId,
					1
				);

				dispatch({
					payload: {noOptionId, yesOptionId},
					type: TYPES.UPDATE_PRODUCT_OPTION_VALUES_ID,
				});
			};

			makeFetch();
		}
	}, []);

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
				tooltip="More info"
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
					tooltip="version"
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
					tooltip="notes"
					value={appNotes}
				/>
			</Section>

			<NewAppPageFooterButtons
				disableContinueButton={!appVersion || !appNotes}
				onClickBack={() => onClickBack()}
				onClickContinue={async () => {
					const skuResponse = await getProductSKU({appProductId});

					const versionSku = skuResponse.items.find(
						({sku}) => sku === appVersion
					);

					let id;

					if (versionSku) {
						id = versionSku.id;
					}
					else {
						const response = await createAppSKU({
							appProductId,
							body: {
								sku: `${appProductId}v${appVersion.replace(
									/[^a-zA-Z0-9 ]/g,
									''
								)}`,
								skuOptions: [
									{
										key: productOptionId,
										value: optionValuesId.noOptionId,
									},
								],
							},
						});

						id = response.id;

						dispatch({
							payload: {
								value: response.id,
							},
							type: TYPES.UPDATE_SKU_ID,
						});
					}

					addSkuExpandoValue({
						companyId: parseInt(getCompanyId()),
						notesValue: appNotes,
						skuId: id,
						versionValue: appVersion,
					});

					onClickContinue();
				}}
			/>
		</div>
	);
}
