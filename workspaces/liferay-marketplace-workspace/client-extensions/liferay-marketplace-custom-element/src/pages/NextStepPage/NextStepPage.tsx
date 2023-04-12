import ClayIcon from '@clayui/icon';

import {AccountAndAppCard} from '../../components/Card/AccountAndAppCard';
import {Footer} from '../../components/Footer/Footer';
import {Header} from '../../components/Header/Header';
import {NewAppPageFooterButtons} from '../../components/NewAppPageFooterButtons/NewAppPageFooterButtons';

import './NextStepPage.scss';

export function NextStepPage() {
	const queryString = window.location.search;

	const urlParams = new URLSearchParams(queryString);

	const accountLogo = urlParams.get('logoURL') as string;
	const accountName = urlParams.get('accountName') as string;
	const appCategory = urlParams.get('appCategory') as string;
	const appLogo = urlParams.get('appLogoURL') as string;
	const appName = urlParams.get('appName') as string;
	const orderId = urlParams.get('orderId') as string;

	return (
		<>
			<div className="next-step-page-container">
				<div className="next-step-page-content">
					<div className="next-step-page-cards">
						<AccountAndAppCard
							category={appCategory}
							logo={appLogo}
							title={appName}
						></AccountAndAppCard>

						<ClayIcon
							className="next-step-page-icon"
							symbol="arrow-right-full"
						/>

						<AccountAndAppCard
							category="DXP Console"
							logo={accountLogo}
							title={accountName}
						></AccountAndAppCard>
					</div>

					<div className="next-step-page-text">
						<Header
							description={`Congratulations on the purchase of ${appName}. You will now need to configure the app in the Cloud Console. To access the Cloud Console, click the button below and provide your Order ID when prompted.`}
							title="Next steps"
						/>

						<span>
							Your Order ID is: <strong>{orderId}</strong>
						</span>
					</div>

					<NewAppPageFooterButtons
						backButtonText="Go Back to Dashboard"
						continueButtonText="Continue Configuration"
						onClickBack={() => {}}
						onClickContinue={() => {}}
					/>

					<div className="next-step-page-link">
						<a>Learn more about App configuration</a>
					</div>
				</div>

				<Footer />
			</div>
		</>
	);
}
