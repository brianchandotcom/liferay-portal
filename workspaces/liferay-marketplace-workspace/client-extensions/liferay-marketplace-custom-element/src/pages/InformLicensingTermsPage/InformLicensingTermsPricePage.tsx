import { Header } from '../../components/Header/Header';
import { LicensePriceCard } from '../../components/LicensePriceCard/LicensePriceCard';
import { NewAppPageFooterButtons } from '../../components/NewAppPageFooterButtons/NewAppPageFooterButtons';
import { Section } from '../../components/Section/Section';
import { useAppContext } from '../../manage-app-state/AppManageState';
import './InformLicensingTermsPage.scss';
import { createAppLicensePrice } from '../../utils/api';

interface InformLicensingTermsPricePageProps {
	onClickBack: () => void;
	onClickContinue: () => void;
}

export function InformLicensingTermsPricePage({
	onClickBack,
	onClickContinue,
}: InformLicensingTermsPricePageProps) {
	const [{ appLicense, appLicensePrice, appProductId, appId }, _] =
		useAppContext();

	return (
		<div className='informing-licensing-terms-page-container'>
			<Header
				description="Define the licensing approach for your app. This will impact users' licensing renew experience."
				title='Inform licensing terms'
			/>

			<Section
				label='Standard License prices'
				required
				tooltip='More Info'
				tooltipText='More Info'
			>
				<LicensePriceCard />
			</Section>

			<NewAppPageFooterButtons
				disableContinueButton={!appLicensePrice}
				onClickBack={() => onClickBack()}
				onClickContinue={() => {
					createAppLicensePrice({
						body: {
							sku: 'default',
							price: parseFloat(appLicensePrice),
						},
						appProductId,
					});

					onClickContinue();
				}}
				showBackButton
			/>
		</div>
	);
}
