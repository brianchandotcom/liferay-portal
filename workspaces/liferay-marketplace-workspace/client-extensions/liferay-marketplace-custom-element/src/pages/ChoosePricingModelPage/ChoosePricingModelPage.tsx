import brightnessEmptyIcon from '../../assets/icons/brightness_empty_icon.svg';
import creditCardIcon from '../../assets/icons/credit_card_icon.svg';
import {Header} from '../../components/Header/Header';
import {RadioCard} from '../../components/RadioCard/RadioCard';
import {Section} from '../../components/Section/Section';

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
	const [{priceModel}, dispatch] = useAppContext();

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
								payload: {value: 'free'},
								type: TYPES.UPDATE_APP_PRICE_MODEL,
							});
						}}
						selected={priceModel === 'free'}
						title="FREE"
						tooltip="More Info"
					/>

					<RadioCard
						description="To enable paid apps, you must be a business and enter payment information in your Marketplace account profile."
						icon={creditCardIcon}
						onChange={() => {
							dispatch({
								payload: {value: 'paid'},
								type: TYPES.UPDATE_APP_PRICE_MODEL,
							});
						}}
						selected={priceModel === 'paid'}
						title="Paid"
						tooltip="More Info"
					/>
				</div>
			</Section>

			<NewAppPageFooterButtons
				onClickBack={() => onClickBack()}
				onClickContinue={() => onClickContinue()}
			/>
		</div>
	);
}
