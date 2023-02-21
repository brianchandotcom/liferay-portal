import { Header } from '../../components/Header/Header';
import { RadioCard } from '../../components/RadioCard/RadioCard';
import { Section } from '../../components/Section/Section';

import scheduleIcon from '../../assets/icons/schedule-icon.svg';
import pendingActionsIcon from '../../assets/icons/pending-actions-icon.svg';
import cancelIcon from '../../assets/icons/cancel-icon.svg';
import taskCheckedIcon from '../../assets/icons/task-checked-icon.svg';

import './InformLicensingTermsPage.scss';
import { NewAppPageFooterButtons } from '../../components/NewAppPageFooterButtons/NewAppPageFooterButtons';
import { useAppContext } from '../../manage-app-state/AppManageState';
import { TYPES } from '../../manage-app-state/actionTypes';
import {
	createAppLicensePrice,
	createProductSpecification,
	createProductSubscriptionConfiguration,
	createSpecification,
} from '../../utils/api';

interface InformLicensingTermsPageProps {
	onClickBack: () => void;
	onClickContinue: () => void;
}

export function InformLicensingTermsPage({
	onClickBack,
	onClickContinue,
}: InformLicensingTermsPageProps) {
	const [
		{ appERC, appProductId, appLicense, dayTrial, priceModel },
		dispatch,
	] = useAppContext();

	return (
		<div className='informing-licensing-terms-page-container'>
			<Header
				description="Define the licensing approach for your app. This will impact users' licensing renew experience."
				title='Inform licensing terms'
			/>

			<Section
				label='App License'
				required
				tooltip='More Info'
				tooltipText='More Info'
			>
				<div className='informing-licensing-terms-page-app-license-container'>
					<RadioCard
						description='The app is offered in the Marketplace with no charge.'
						selected={appLicense === 'perpetual'}
						onChange={() => {
							dispatch({
								payload: { value: 'perpetual' },
								type: TYPES.UPDATE_APP_LICENSE,
							});
						}}
						title='Perpetual License'
						tooltip='More Info'
						icon={scheduleIcon}
					/>

					<RadioCard
						description='License must be renewed annually.'
						disabled={priceModel === 'free'}
						selected={appLicense === 'non-perpetual'}
						onChange={() => {
							dispatch({
								payload: { value: 'non-perpetual' },
								type: TYPES.UPDATE_APP_LICENSE,
							});
						}}
						title='Non-perpetual license'
						tooltip='More Info'
						icon={pendingActionsIcon}
					/>
				</div>
			</Section>

			<Section
				label='30-day Trial'
				tooltip='More Info'
				tooltipText='More Info'
				required
			>
				<div className='informing-licensing-terms-page-day-trial-container'>
					<RadioCard
						description='Offer a 30-day free trial for this app'
						title='Yes'
						icon={taskCheckedIcon}
						selected={dayTrial === 'yes'}
						onChange={() => {
							dispatch({
								payload: { value: 'yes' },
								type: TYPES.UPDATE_APP_TRIAL_INFO,
							});
						}}
						tooltip='More Info'
					/>

					<RadioCard
						description='Do not offer a 30-day free trial'
						title='No'
						icon={cancelIcon}
						selected={dayTrial === 'no'}
						onChange={() => {
							dispatch({
								payload: { value: 'no' },
								type: TYPES.UPDATE_APP_TRIAL_INFO,
							});
						}}
						tooltip='More Info'
					/>
				</div>
			</Section>

			<NewAppPageFooterButtons
				onClickBack={() => onClickBack()}
				onClickContinue={() => {
					const submitLicenseTermsPage = async () => {
						if (priceModel === 'free') {
							createAppLicensePrice({
								body: {
									sku: 'default',
									price: 0,
								},
								appProductId,
							});
						}

						if (dayTrial === 'yes') {
							createAppLicensePrice({
								body: {
									sku: 'trial',
									price: 0,
								},
								appProductId,
							});
						}

						if (appLicense === 'non-perpetual') {
							createProductSubscriptionConfiguration({
								body: {
									length: 1,
									numberOfLength: 0,
									subscriptionType: 'yearly',
									subscriptionTypeSettings: {
										month: '0',
										monthDay: '1',
										yearlyMode: '0',
									},
								},
								externalReferenceCode: appERC,
							});
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
