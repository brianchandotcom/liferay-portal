/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayIcon from '@clayui/icon';
import classNames from 'classnames';
import {ReactNode, useCallback, useEffect, useState} from 'react';

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
	getAccountImage,
	getThumbnailByProductAttachment,
	showAppImage,
} from '../../utils/util';

import './NextStepPage.scss';
import {PaymentStatus} from '../GetAppPage/enums/PaymentStatus';
import useProductPriceModel from '../GetAppPage/hooks/useProductPriceModel';

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

	const [product, setProduct] = useState<Product>();
	const [isTrial, setIsTrial] = useState<boolean>(false);
	const [cart, setCart] = useState<any>({});

	const paymentStatus = cart?.paymentStatusLabel;

	const getCartInfo = useCallback(async () => {
		const [cart, cartItems] = await Promise.all([
			getCart(Number(orderId)),
			getCartItems(Number(orderId)),
		]);

		const firstItem = cartItems.items[0];

		const isTrial = cartItems.items.some(
			(item: any) =>
				item.sku.endsWith('ts') ||
				item.sku.toLowerCase().includes('trial')
		);

		if (isTrial) {
			setIsTrial(true);
		}

		const product = await getProductById({
			nestedFields: 'attachments,productSpecifications',
			productId: firstItem?.productId,
		});

		const appIcon = getThumbnailByProductAttachment(product.attachments);

		const formattedIcon = showAppImage(appIcon as string).replace(
			(appIcon as string)?.split('/o')[0],
			baseURL
		);

		const currentAccountCommerce = await getAccountInfoFromCommerce(
			cart.accountId
		);

		setCart(cart);
		setAppLogo(formattedIcon);
		setAppName(firstItem.name);
		setProduct(product);
		setAccountLogo(currentAccountCommerce.logoURL);
		setAccountName(currentAccountCommerce.name);
	}, [orderId]);

	useEffect(() => {
		getCartInfo();
	}, [getCartInfo]);

	const {isPaidApp} = useProductPriceModel(product);

	const nextStepBody: TypeNextStepBody = {
		[PaymentStatus.PAID]: (
			<Header
				description={
					isPaidApp ? (
						<>
							<p>
								Congratulations on the purchase of{' '}
								<strong>{appName}</strong>. You will need to
								create a license your app before deploying to
								your DXP instance.
							</p>
							<p>
								{orderId && (
									<span>
										Your Order ID is:{' '}
										<strong>{orderId}</strong>
									</span>
								)}
							</p>
							<p>
								To license your app, you can click Continue
								Configuration below. Find your Order ID and
								choose Create License Key. To create a license,
								you must have at least one of your instance
								details available - IP address, MAC address or
								hostname.
							</p>
						</>
					) : (
						<>
							<p>
								<strong>{appName}</strong> app is ready for
								download.
							</p>
							<p>
								{orderId && (
									<span>
										Your Order ID is:{' '}
										<strong>{orderId}</strong>
									</span>
								)}
							</p>
							<p>
								To download your app, you can click Continue
								Configuration below. To find your app download,
								find your Order ID and choose Manage → Download
								App.
							</p>
						</>
					)
				}
				title="Next steps"
			/>
		),
		[PaymentStatus.PAYMENT_PENDING]: (
			<Header
				description={
					isTrial ? (
						<>
							<p>
								Congratulations on agreeing to purchase{' '}
								<strong>{appName}</strong>. Payment is required
								before licensing the app. An invoice will be
								sent to the email address listed in the order.
								Once payment is processed, you will be notified
								as to the next steps to license your app.
							</p>
							<p>
								{orderId && (
									<span>
										Your Order ID is:{' '}
										<strong>{orderId}</strong>
									</span>
								)}
							</p>
							<p>
								To license your app, you can click Continue
								Configuration below. Find your Order ID and
								choose Create License Key. To create a license,
								you must have at least one of your instance
								details available - IP address, MAC address or
								hostname.
							</p>
						</>
					) : (
						<>
							<p>
								Congratulations on agreeing to purchase{' '}
								<strong>{appName}</strong>. Payment is required
								before licensing the app. An invoice will be
								sent to the email address listed in the order.
								Once payment is processed, you will be notified
								as to the next steps to license your app.
							</p>
							<p>
								{orderId && (
									<span>
										Your Order ID is:{' '}
										<strong>{orderId}</strong>
									</span>
								)}
							</p>
						</>
					)
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
						<div className="next-step-page-cards">
							<AccountAndAppCard
								category="Application"
								logo={appLogo ? appLogo : catalogIcon}
								title={appName ?? ''}
							/>

							<div className="icon-container">
								<ClayIcon
									className="m-0 next-step-page-icon"
									symbol="arrow-right-full"
								/>
							</div>

							<AccountAndAppCard
								category="Account"
								logo={getAccountImage(accountLogo as string)}
								title={accountName ?? ''}
							></AccountAndAppCard>
						</div>
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
						onClickContinue={() => {
							if (onClickContinue) {
								window.location.href =
									'https://console.marketplacedemo.liferay.sh/projects';
							}
						}}
						showBackButton={showBackButton}
						showContinueButton={false}
					/>
					{(paymentStatus === PaymentStatus.PAID || isTrial) && (
						<div className="d-flex justify-content-end">
							<a href="#">
								<ins>Learn more about App Configuration</ins>
							</a>
						</div>
					)}
				</div>
			</div>
		</>
	);
}
