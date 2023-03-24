import cancelIcon from '../../assets/icons/cancel-icon.svg';
import pendingActionsIcon from '../../assets/icons/pending-actions-icon.svg';
import scheduleIcon from '../../assets/icons/schedule-icon.svg';
import taskCheckedIcon from '../../assets/icons/task-checked-icon.svg';
import {Header} from '../../components/Header/Header';
import {RadioCard} from '../../components/RadioCard/RadioCard';
import {Section} from '../../components/Section/Section';

import './InformLicensingTermsPage.scss';
import {NewAppPageFooterButtons} from '../../components/NewAppPageFooterButtons/NewAppPageFooterButtons';
import {useAppContext} from '../../manage-app-state/AppManageState';
import {TYPES} from '../../manage-app-state/actionTypes';
import {
	createAppLicensePrice,
	createProductSubscriptionConfiguration,
	getSKUById,
	patchSKUById,
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
		{appERC, appLicense, appProductId, dayTrial, priceModel, skuId},
		dispatch,
	] = useAppContext();

	return (
		<div className="informing-licensing-terms-page-container">
			<Header
				description="Define the licensing approach for your app. This will impact users' licensing renew experience."
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
								payload: {value: 'perpetual'},
								type: TYPES.UPDATE_APP_LICENSE,
							});
						}}
						selected={appLicense === 'perpetual'}
						title="Perpetual License"
						tooltip="More Info"
					/>

					<RadioCard
						description="License must be renewed annually."
						disabled={priceModel === 'free'}
						icon={pendingActionsIcon}
						onChange={() => {
							dispatch({
								payload: {value: 'non-perpetual'},
								type: TYPES.UPDATE_APP_LICENSE,
							});
						}}
						selected={appLicense === 'non-perpetual'}
						title="Non-perpetual license"
						tooltip="More Info"
					/>
				</div>
			</Section>

			<Section
				label="30-day Trial"
				required
				tooltip="More Info"
				tooltipText="More Info"
			>
				<div className="informing-licensing-terms-page-day-trial-container">
					<RadioCard
						description="Offer a 30-day free trial for this app"
						disabled={priceModel === 'free'}
						icon={taskCheckedIcon}
						onChange={() => {
							dispatch({
								payload: {value: 'yes'},
								type: TYPES.UPDATE_APP_TRIAL_INFO,
							});
						}}
						selected={dayTrial === 'yes'}
						title="Yes"
						tooltip="More Info"
					/>

					<RadioCard
						description="Do not offer a 30-day free trial"
						icon={cancelIcon}
						onChange={() => {
							dispatch({
								payload: {value: 'no'},
								type: TYPES.UPDATE_APP_TRIAL_INFO,
							});
						}}
						selected={dayTrial === 'no'}
						title="No"
						tooltip="More Info"
					/>
				</div>
			</Section>

			<NewAppPageFooterButtons
				onClickBack={() => onClickBack()}
				onClickContinue={() => {
					const submitLicenseTermsPage = async () => {
						if (priceModel === 'free') {
							const skuJSON = await getSKUById(skuId);

							const skuBody = {
								...skuJSON,
								neverExpire: true,
								price: 0,
								purchasable: true,
							};

							await patchSKUById(skuId, skuBody);
						}
						else {
							if (appLicense === 'non-perpetual') {
								createProductSubscriptionConfiguration({
									body: {
										length: 1,
										numberOfLength: 1,
										subscriptionType: 'yearly',
									},
									externalReferenceCode: appERC,
								});
							}

							const skuJSON = await getSKUById(skuId);

							const skuBody = {
								...skuJSON,
								neverExpire: appLicense === 'perpetual',
								price: 0,
								purchasable: true,
							};

							await patchSKUById(skuId, skuBody);
						}

						if (dayTrial === 'yes' && priceModel !== 'free') {
							createAppLicensePrice({
								appProductId,
								body: {
									neverExpire: true,
									price: 0,
									published: true,
									purchasable: true,
									sku: 'trial',
								},
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
