import {Header} from '../../components/Header/Header';
import {LicensePriceCard} from '../../components/LicensePriceCard/LicensePriceCard';
import {NewAppPageFooterButtons} from '../../components/NewAppPageFooterButtons/NewAppPageFooterButtons';
import {Section} from '../../components/Section/Section';
import {useAppContext} from '../../manage-app-state/AppManageState';

import './InformLicensingTermsPage.scss';
import {createAppLicensePrice} from '../../utils/api';

interface InformLicensingTermsPricePageProps {
	onClickBack: () => void;
	onClickContinue: () => void;
}

export function InformLicensingTermsPricePage({
	onClickBack,
	onClickContinue,
}: InformLicensingTermsPricePageProps) {
	const [{appLicensePrice, appProductId}, _] = useAppContext();

	return (
		<div className="informing-licensing-terms-page-container">
			<Header
				description="Define the licensing approach for your app. This will impact users' licensing renew experience."
				title="Inform licensing terms"
			/>

			<Section
				label="Standard License prices"
				required
				tooltip="More Info"
				tooltipText="More Info"
			>
				<LicensePriceCard />
			</Section>

			<NewAppPageFooterButtons
				disableContinueButton={!appLicensePrice}
				onClickBack={() => onClickBack()}
				onClickContinue={() => {
					createAppLicensePrice({
						appProductId,
						body: {
							neverExpire: true,
							price: parseFloat(appLicensePrice),
							published: true,
							purchasable: true,
							sku: 'default',
						},
					});

					onClickContinue();
				}}
				showBackButton
			/>
		</div>
	);
}
