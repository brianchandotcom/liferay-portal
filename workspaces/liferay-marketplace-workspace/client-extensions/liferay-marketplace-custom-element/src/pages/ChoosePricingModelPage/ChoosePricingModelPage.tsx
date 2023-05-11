import brightnessEmptyIcon from '../../assets/icons/brightness_empty_icon.svg';
import creditCardIcon from '../../assets/icons/credit_card_icon.svg';
import {Header} from '../../components/Header/Header';
import {RadioCard} from '../../components/RadioCard/RadioCard';
import {Section} from '../../components/Section/Section';
import {
	createProductSpecification,
	createSpecification,
	updateProductSpecification,
} from '../../utils/api';

import './ChoosePricingModelPage.scss';
import {NewAppPageFooterButtons} from '../../components/NewAppPageFooterButtons/NewAppPageFooterButtons';
import {useAppContext} from '../../manage-app-state/AppManageState';
import {TYPES} from '../../manage-app-state/actionTypes';

interface ChoosePricingModelPageProps {
	onClickBack: () => void;
	onClickContinue: () => void;
}

export function ChoosePricingModelPage({
	onClickBack,
	onClickContinue,
}: ChoosePricingModelPageProps) {
	const [{appId, appLicense, appProductId, priceModel}, dispatch] =
		useAppContext();

	return (
		<div className="choose-pricing-model-page-container">
			<Header
				description="Select one of the pricing models for your app. This will define how much users will pay and their acquisition experience."
				title="Choose pricing model"
			/>

			<Section
				label="App Price"
				required
				tooltip="More Info"
				tooltipText="More Info"
			>
				<div className="choose-pricing-model-page-radio-container">
					<RadioCard
						description="The app is offered in the Marketplace with no charge."
						icon={brightnessEmptyIcon}
						onChange={() => {
							dispatch({
								payload: {id: priceModel.id, value: 'Free'},
								type: TYPES.UPDATE_APP_PRICE_MODEL,
							});
						}}
						selected={priceModel.value === 'Free'}
						title="FREE"
						tooltip="More Info"
					/>

					<RadioCard
						description="To enable paid apps, you must be a business and enter payment information in your Marketplace account profile."
						icon={creditCardIcon}
						onChange={() => {
							dispatch({
								payload: {id: priceModel.id, value: 'Paid'},
								type: TYPES.UPDATE_APP_PRICE_MODEL,
							});
						}}
						selected={priceModel.value === 'Paid'}
						title="Paid"
						tooltip="More Info"
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
									specificationKey: 'price-model',
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
									type: TYPES.UPDATE_APP_LICENSE_PRICE,
								});
							}
						}
						else {
							const dataSpecification = await createSpecification(
								{
									body: {
										key: 'price-model',
										title: {en_US: 'Price Model'},
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
										priceModel.value === 'Free'
											? {en_US: 'Free'}
											: {en_US: 'Paid'},
								},
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
