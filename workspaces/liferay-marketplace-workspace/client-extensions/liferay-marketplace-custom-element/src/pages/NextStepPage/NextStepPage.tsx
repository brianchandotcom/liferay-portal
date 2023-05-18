import ClayIcon from '@clayui/icon';
import classNames from 'classnames';
import {ReactNode, useState} from 'react';

import {AccountAndAppCard} from '../../components/Card/AccountAndAppCard';
import {Footer} from '../../components/Footer/Footer';
import {Header} from '../../components/Header/Header';
import {NewAppPageFooterButtons} from '../../components/NewAppPageFooterButtons/NewAppPageFooterButtons';
import {Liferay} from '../../liferay/liferay';
import {
	baseURL,
	getAccountInfoFromCommerce,
	getCart,
	getCartItems,
} from '../../utils/api';
import {showAccountImage, showAppImage} from '../../utils/util';

import './NextStepPage.scss';

interface NextStepPageProps {
	continueButtonText?: string;
	children?: ReactNode;
	header?: {
		description?: string;
		title?: string;
	};
	linkText?: string;
	onClickContinue?: () => void;
	size?: 'lg';
	showBackButton?: boolean;
	showOrderId?: boolean;
}

export function NextStepPage({
	children,
	continueButtonText,
	header,
	linkText,
	onClickContinue,
	showBackButton,
	showOrderId = true,
	size,
}: NextStepPageProps) {
	const queryString = window.location.search;

	const urlParams = new URLSearchParams(queryString);
	const orderId = urlParams.get('orderId');

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
			<div
				className={classNames('next-step-page-container', {
					'next-step-page-container-larger': size === 'lg',
				})}
			>
				<div className="next-step-page-content">
					{!children && (
						<>
							<div className="next-step-page-cards">
								<AccountAndAppCard
									category="Application"
									logo={showAppImage(
										appLogo as string
									).replace(
										(appLogo as string)?.split('/o')[0],
										baseURL
									)}
									title={appName ?? ''}
								></AccountAndAppCard>

								<ClayIcon
									className="next-step-page-icon"
									symbol="arrow-right-full"
								/>

								<AccountAndAppCard
									category="DXP Console"
									logo={showAccountImage(
										accountLogo as string
									)}
									title={accountName ?? ''}
								></AccountAndAppCard>
							</div>
						</>
					)}

					<div className="next-step-page-text">
						<Header
							description={
								header?.description ?? [
									'Congratulations on the purchase of ',
									<b>{appName}</b>,
									'. You will now need to configure the app in the Cloud Console. To access the Cloud Console, click the button below and provide your Order ID when prompted.',
								]
							}
							title={header?.title ?? 'Next steps'}
						/>

						{showOrderId && (
							<span>
								Your Order ID is: <strong>{orderId}</strong>
							</span>
						)}
					</div>

					{children}

					<NewAppPageFooterButtons
						backButtonText="Go Back to Dashboard"
						continueButtonText={
							continueButtonText ?? 'Continue Configuration'
						}
						onClickBack={() => {
							const customerDashboardCallbackURL = `${Liferay.ThemeDisplay.getCanonicalURL().replace(
								`/next-steps`,
								''
							)}/customer-dashboard`;

							window.location.href = customerDashboardCallbackURL;
						}}
						onClickContinue={
							onClickContinue ??
							(() =>
								(window.location.href =
									'https://console.marketplacedemo.liferay.sh/projects'))
						}
						showBackButton={showBackButton}
					/>

					<div className="next-step-page-link">
						<a>
							{linkText ?? 'Learn more about App configuration'}
						</a>
					</div>
				</div>

				<Footer />
			</div>
		</>
	);
}
