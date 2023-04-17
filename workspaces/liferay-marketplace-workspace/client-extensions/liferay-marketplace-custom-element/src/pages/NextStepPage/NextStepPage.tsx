import ClayIcon from '@clayui/icon';

import {AccountAndAppCard} from '../../components/Card/AccountAndAppCard';
import {Footer} from '../../components/Footer/Footer';
import {Header} from '../../components/Header/Header';
import {NewAppPageFooterButtons} from '../../components/NewAppPageFooterButtons/NewAppPageFooterButtons';

import './NextStepPage.scss';

import {useEffect, useState} from 'react';

import {
	getAccountInfoFromCommerce,
	getCart,
	getCartItems,
	getChannels,
	getDeliveryProduct,
} from '../../utils/api';

export function NextStepPage() {
	const queryString = window.location.search;

	const urlParams = new URLSearchParams(queryString);
	const orderId = urlParams.get('orderId') as string;

	const [accountLogo, setAccountLogo] = useState(urlParams.get('logoURL'));
	const [accountName, setAccountName] = useState(
		urlParams.get('accountName')
	);
	const [appLogo, setAppLogo] = useState(urlParams.get('appLogoURL'));
	const [appName, setAppName] = useState(urlParams.get('appName'));

	let cart;
	let cartItems;

	const getCartInfo = async () => {
		if (!appName) {
			cart = await getCart(Number(orderId));
			cartItems = await getCartItems(Number(orderId));

			const item = cartItems.items[0];

			setAppLogo(item.thumbnail);
			setAppName(item.name);

			const currentAccountCommerce = await getAccountInfoFromCommerce(
				cart.accountId
			);

			setAccountLogo(currentAccountCommerce.logoURL);
			setAccountName(currentAccountCommerce.name);
		}
	};

	getCartInfo();

	return (
		<>
			<div className="next-step-page-container">
				<div className="next-step-page-content">
					<div className="next-step-page-cards">
						<AccountAndAppCard
							category="Application"
							logo={appLogo ?? ''}
							title={appName ?? ''}
						></AccountAndAppCard>

						<ClayIcon
							className="next-step-page-icon"
							symbol="arrow-right-full"
						/>

						<AccountAndAppCard
							category="DXP Console"
							logo={accountLogo ?? ''}
							title={accountName ?? ''}
						></AccountAndAppCard>
					</div>

					<div className="next-step-page-text">
						<Header
							description={[
								'Congratulations on the purchase of ',
								<b>{appName}</b>,
								'. You will now need to configure the app in the Cloud Console. To access the Cloud Console, click the button below and provide your Order ID when prompted.',
							]}
							title="Next steps"
						/>

						<span>
							Your Order ID is: <strong>{orderId}</strong>
						</span>
					</div>

					<NewAppPageFooterButtons
						backButtonText="Go Back to Dashboard"
						continueButtonText="Continue Configuration"
						onClickBack={() => window.location.href = `${window.location.origin}/web/guest/publisher-dashboard`}
						onClickContinue={() => window.location.href = 'https://console.liferay.cloud/'}
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
