/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayIcon from '@clayui/icon';
import classNames from 'classnames';
import {ReactNode, useState} from 'react';

import catalogIcon from '../../assets/icons/catalog_icon.svg';
import {AccountAndAppCard} from '../../components/Card/AccountAndAppCard';
import {Header} from '../../components/Header/Header';
import {NewAppPageFooterButtons} from '../../components/NewAppPageFooterButtons/NewAppPageFooterButtons';
import {Liferay} from '../../liferay/liferay';
import {
	baseURL,
	getAccountInfoFromCommerce,
	getCart,
	getCartItems,
	getProductById,
} from '../../utils/api';
import {
	getThumbnailByProductAttachment,
	showAccountImage,
	showAppImage,
} from '../../utils/util';

import './NextStepPage.scss';
import {TypeLicense} from '../enums/TypeLicense';

interface NextStepPageProps {
	children?: ReactNode;
	continueButtonText?: string;
	header?: {
		description?: string;
		title?: string;
	};
	linkText?: string;
	onClickContinue?: () => void;
	showBackButton?: boolean;
	showOrderId?: boolean;
	size?: 'lg';
}

type TypeNextStepBody = {
	[key in string]?: ReactNode;
};

export function NextStepPage({
	children,
	onClickContinue,
	showBackButton,
	size,
}: NextStepPageProps) {
	const queryString = window.location.search;

	const urlParams = new URLSearchParams(queryString);

	const orderId = urlParams.get('orderId');

	const [accountLogo, setAccountLogo] = useState<string>('');
	const [accountName, setAccountName] = useState<string>('');
	const [appName, setAppName] = useState<string>('');
	const [appLogo, setAppLogo] = useState<string>('');
	const [paymentStatus, setPaymentStatus] = useState<string>('');

	let cart;
	let cartItems;

	const getCartInfo = async () => {
		if (!appName) {
			cart = await getCart(Number(orderId));
			cartItems = await getCartItems(Number(orderId));

			const item = cartItems.items[0];

			setPaymentStatus(cart.paymentStatusLabel);

			const productId = item.productId;

			const product = await getProductById({
				nestedFields: 'attachments, productSpecifications',
				productId,
			});

			const appIcon = getThumbnailByProductAttachment(
				product.attachments
			);

			const formattedIcon = showAppImage(appIcon as string).replace(
				(appIcon as string)?.split('/o')[0],
				baseURL
			);

			setAppLogo(formattedIcon);
			setAppName(item.name);

			const currentAccountCommerce = await getAccountInfoFromCommerce(
				cart.accountId
			);

			setAccountLogo(currentAccountCommerce.logoURL);
			setAccountName(currentAccountCommerce.name);
		}
	};

	getCartInfo();

	const nextStepBody: TypeNextStepBody = {
		[TypeLicense.PAID]: (
			<Header
				description={
					<>
						<p>
							Congratulations on the purchase of <b>{appName}</b>.
							You will need to create a license your app before
							deploying to your DXP instance.
						</p>
						<p>
							{orderId && (
								<span>
									Your Order ID is: <strong>{orderId}</strong>
								</span>
							)}
						</p>
						<p>
							To license your app, you can click Continue
							Configuration below. Find your Order ID and choose
							Create License Key. To create a license, you must
							have at least one your instance details available -
							IP address, MAC address or hostname.
						</p>
					</>
				}
				title="Next steps"
			/>
		),
		[TypeLicense.FREE]: (
			<Header
				description={
					<>
						<p>
							Your <b>{appName}</b> app is ready for download.
						</p>
						<p>
							{orderId && (
								<span>
									Your Order ID is: <strong>{orderId}</strong>
								</span>
							)}
						</p>
						<p>
							To download your app, you can click Continue
							Configuration below. To find your app download, find
							your Order ID and choose Manage → Download LPKG.
						</p>
					</>
				}
				title="Next steps"
			/>
		),
		[TypeLicense.TRIAL]: (
			<Header
				description={
					<>
						<p>
							You will need to create a license for your app
							before deploying it to your DXP instance.
						</p>
						<p>
							{orderId && (
								<span>
									Your Order ID is: <strong>{orderId}</strong>
								</span>
							)}
						</p>
						<p>
							To license your app, you can click Continue
							Configuration below. Find your Order ID and choose
							Create License Key. To create a license, you must
							have at least one your instance details available -
							IP address, MAC address or hostname.
						</p>
					</>
				}
				title="Next steps"
			/>
		),
		[TypeLicense.PAYMENT_PENDING]: (
			<Header
				description={
					<>
						<p>
							Congratulations on agreeing to purchase{' '}
							<b>{appName}</b>. Payment is required before
							licensing the app. An invoice will be sent to the
							email address listed in the order. Once payment is
							processed, you will be notified as to the next steps
							to license your app.
						</p>
						<p>
							{orderId && (
								<span>
									Your Order ID is: <strong>{orderId}</strong>
								</span>
							)}
						</p>
					</>
				}
				title="Next steps"
			/>
		),
	};

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
									logo={appLogo ? appLogo : catalogIcon}
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
						<div className="next-step-page-text">
							{nextStepBody[String(paymentStatus) || '']}
						</div>
					</div>

					<NewAppPageFooterButtons
						backButtonText="Go to Dashboard"
						onClickBack={() => {
							const customerDashboardCallbackURL = `${Liferay.ThemeDisplay.getCanonicalURL().replace(
								`/next-steps`,
								''
							)}/customer-dashboard`;

							window.location.href = customerDashboardCallbackURL;
						}}
						onClickContinue={
							onClickContinue ??
							(() => {
								window.location.href =
									'https://console.marketplacedemo.liferay.sh/projects';
							})
						}
						showBackButton={showBackButton}
					/>
				</div>
			</div>
		</>
	);
}
