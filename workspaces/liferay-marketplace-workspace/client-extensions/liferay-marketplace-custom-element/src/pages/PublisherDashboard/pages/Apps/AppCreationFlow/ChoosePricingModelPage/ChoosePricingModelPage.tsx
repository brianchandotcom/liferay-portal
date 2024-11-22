/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Header} from '../../../../../../components/Header/Header';
import {RadioCard} from '../../../../../../components/RadioCard/RadioCard';
import {Section} from '../../../../../../components/Section/Section';
import {
	createProductSpecification,
	getSpecification,
	updateProductSpecification,
} from '../../../../../../utils/api';

import './ChoosePricingModelPage.scss';
import {NewAppPageFooterButtons} from '../../../../../../components/NewAppPageFooterButtons/NewAppPageFooterButtons';
import {PRODUCT_SPECIFICATION_KEY} from '../../../../../../enums/Product';
import {useAppContext} from '../AppContext/AppManageState';
import {TYPES} from '../AppContext/actionTypes';

interface ChoosePricingModelPageProps {
	onClickBack: () => void;
	onClickContinue: () => void;
}

export function ChoosePricingModelPage({
	onClickBack,
	onClickContinue,
}: ChoosePricingModelPageProps) {
	const [{appLicense, appProductId, priceModel}, dispatch] = useAppContext();

	return (
		<div className="choose-pricing-model-page-container">
			<Header
				description="Select one of the pricing models for your app. This will define how much users will pay. To enable paid apps, you must be a business and enter payment information in your Marketplace account profile."
				title="Choose pricing model"
			/>

			<Section
				label="App Price"
				required
				tooltip="Choose Free or Paid. Apps that are free have no further payment obligations once installed."
				tooltipText="More Info"
			>
				<div className="choose-pricing-model-page-radio-container">
					<RadioCard
						description="The app is offered in the Marketplace with no charge."
						onChange={() => {
							dispatch({
								payload: {id: priceModel.id, value: 'Free'},
								type: TYPES.UPDATE_APP_PRICE_MODEL,
							});
						}}
						selected={priceModel.value === 'Free'}
						title="Free"
						tooltip="The app is offered in the Marketplace with no charge."
					/>

					<RadioCard
						description="To enable paid apps, you must be a business and enter payment information in your Marketplace account profile."
						icon="credit-card"
						onChange={() => {
							dispatch({
								payload: {id: priceModel.id, value: 'Paid'},
								type: TYPES.UPDATE_APP_PRICE_MODEL,
							});
						}}
						selected={priceModel.value === 'Paid'}
						title="Paid"
						tooltip="For paid apps, you can choose the subscription model you want to use on the next screen."
					/>
				</div>
			</Section>

			<NewAppPageFooterButtons
				onClickBack={() => onClickBack()}
				onClickContinue={() => {
					const submitPriceModel = async () => {
						if (priceModel.id) {
							updateProductSpecification({
								body: {
									specificationKey:
										PRODUCT_SPECIFICATION_KEY.APP_PRICING_MODEL,
									value:
										priceModel.value === 'Free'
											? {en_US: 'Free'}
											: {en_US: 'Paid'},
								},
								id: priceModel.id,
							});

							if (priceModel.value === 'Free') {
								dispatch({
									payload: {
										id: appLicense?.id,
										value: 'Perpetual',
									},
									type: TYPES.UPDATE_APP_LICENSE,
								});

								dispatch({
									payload: {value: 'no'},
									type: TYPES.UPDATE_APP_TRIAL_INFO,
								});

								dispatch({
									payload: {value: 0},
									type: TYPES.UPDATE_APP_LICENSE_PRICES,
								});
							}
						}
						else {
							const dataSpecification =
								await getSpecification('price-model');

							const {id} = await createProductSpecification({
								body: {
									specificationId: dataSpecification.id,
									specificationKey: dataSpecification.key,
									value:
										priceModel.value === 'Free'
											? {en_US: 'Free'}
											: {en_US: 'Paid'},
								},
								id: appProductId,
							});

							dispatch({
								payload: {id, value: priceModel.value},
								type: TYPES.UPDATE_APP_PRICE_MODEL,
							});
						}
					};
					submitPriceModel();
					onClickContinue();
				}}
			/>
		</div>
	);
}
