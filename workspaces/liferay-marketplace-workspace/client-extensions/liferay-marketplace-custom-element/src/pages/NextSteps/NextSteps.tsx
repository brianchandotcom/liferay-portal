/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayIcon from '@clayui/icon';
import ClayLoadingIndicator from '@clayui/loading-indicator';

import {AccountAndAppCard} from '../../components/Card/AccountAndAppCard';
import {Header} from '../../components/Header/Header';
import {NewAppPageFooterButtons} from '../../components/NewAppPageFooterButtons/NewAppPageFooterButtons';
import {ORDER_TYPES} from '../../enums/Order';
import withProviders from '../../hoc/withProviders';
import i18n from '../../i18n';
import {Liferay} from '../../liferay/liferay';
import CommerceSelectAccountImpl from '../../services/rest/CommerceSelectAccount';
import {baseURL} from '../../utils/api';
import {
	getAccountImage,
	getThumbnailByProductAttachment,
	showAppImage,
} from '../../utils/util';
import {PaymentStatus} from '../GetApp/enums/PaymentStatus';
import getProductPriceModel from '../GetApp/utils/getProductPriceModel';
import useNextSteps from './useNextSteps';

import './NextSteps.scss';

export function NextSteps() {
	const queryString = window.location.search;
	const urlParams = new URLSearchParams(queryString);
	const orderId = urlParams.get('orderId');

	const {
		accountCommerce,
		cart,
		cartItems,
		firstCartItem,
		isLoading,
		product,
	} = useNextSteps(orderId as string);

	const {name: appName = ''} = firstCartItem ?? {};

	const isTrial = cartItems?.items?.some(
		(item: any) =>
			item.sku.endsWith('ts') || item.sku.toLowerCase().includes('trial')
	);

	const appIcon = getThumbnailByProductAttachment(product?.images);

	const appLogo = showAppImage(appIcon as string).replace(
		(appIcon as string)?.split('/o')[0],
		baseURL
	);

	const paymentStatus = cart?.paymentStatusLabel;
	const orderTypeExternalReferenceCode = cart?.orderTypeExternalReferenceCode;

	const {isPaidApp} = getProductPriceModel(product);

	const nextStepBody = {
		[PaymentStatus.PAID]: (
			<Header
				description={
					orderTypeExternalReferenceCode === ORDER_TYPES.CLOUDAPP ? (
						<span>
							<p>
								Congratulations on the purchase of{' '}
								<strong>{appName}</strong>. You will now need to
								install the app by clicking on the
								&quot;Continue to Install&quot; button below.
							</p>

							<p>
								Your Order ID is: <strong>{orderId}</strong>
							</p>
						</span>
					) : isPaidApp ? (
						<span>
							<p>
								Congratulations on the purchase of{' '}
								<strong>{appName}</strong>. You will need to
								create a license your app before deploying to
								your DXP instance.
							</p>

							<p>
								Your Order ID is: <strong>{orderId}</strong>
							</p>

							<p>
								To license your app, you can click Go to
								Dashboard below. Find your Order ID and choose
								Create License Key. To create a license, you
								must have at least one of your instance details
								available - IP address, MAC address or hostname.
							</p>
						</span>
					) : (
						<span>
							<strong>{appName}</strong> app is ready for
							download.
							<p>
								Your Order ID is: <strong>{orderId}</strong>
							</p>
							<p>
								To download your app, you can click &quot;Go to
								Dashboard&quot; button below. To find your app
								download, find your Order ID and click on
								<ClayIcon className="m-1" symbol="ellipsis-v" />
								→ Download App.
							</p>
						</span>
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
								You will need to create a license for your app
								before deploying it to your DXP instance
							</p>

							<p>
								Your Order ID is: <strong>{orderId}</strong>
							</p>

							<p>
								To license your app, you can click Go to
								Dashboard below. Find your Order ID and choose
								Create License Key. To create a license, you
								must have at least one of your instance details
								available - IP address, MAC address or hostname.
							</p>
						</>
					) : (
						<p>
							Congratulations on agreeing to purchase{' '}
							<strong>{appName}</strong>. Payment is required
							before licensing the app. An invoice will be sent to
							the email address listed in the order. Once payment
							is processed, you will be notified as to the next
							steps to license your app.
							<span className="mt-4">
								Your Order ID is: <strong>{orderId}</strong>
							</span>
						</p>
					)
				}
				title="Next steps"
			/>
		),
	};

	if (isLoading) {
		return <ClayLoadingIndicator />;
	}

	return (
		<div className="next-step-page-container">
			<div className="next-step-page-content">
				<div className="next-step-page-cards">
					<AccountAndAppCard
						category="Application"
						logo={appLogo || 'catalog'}
						title={appName}
					/>

					<ClayIcon
						className="m-0 next-step-page-icon"
						symbol="arrow-right-full"
					/>

					<AccountAndAppCard
						category="Account"
						logo={getAccountImage(
							accountCommerce?.logoURL as string
						)}
						title={accountCommerce?.name ?? ''}
					/>
				</div>

				<div className="next-step-page-text">
					<div className="next-step-page-text">
						{(nextStepBody as any)[String(paymentStatus) || '']}
					</div>
				</div>

				<NewAppPageFooterButtons
					backButtonText={i18n.translate(
						orderTypeExternalReferenceCode === ORDER_TYPES.CLOUDAPP
							? 'go-to-my-apps'
							: 'go-to-dashboard'
					)}
					continueButtonText={i18n.translate(
						orderTypeExternalReferenceCode === ORDER_TYPES.DXPAPP
							? 'download-app'
							: 'continue-to-install'
					)}
					onClickBack={() => {
						return CommerceSelectAccountImpl.selectAccount(
							cart?.accountId
						).then(() => {
							Liferay.CommerceContext.account = {
								accountId: cart?.accountId,
							};

							Liferay.Util.navigate(
								Liferay.ThemeDisplay.getLayoutURL().replace(
									'/next-steps',
									`/customer-dashboard`
								)
							);
						});
					}}
					onClickContinue={() => {
						if (
							orderTypeExternalReferenceCode ===
							ORDER_TYPES.DXPAPP
						) {
							Liferay.Util.navigate(
								Liferay.ThemeDisplay.getLayoutURL().replace(
									'/next-steps',
									`/customer-dashboard#/order/${orderId}/download`
								)
							);
						}

						if (
							orderTypeExternalReferenceCode ===
							ORDER_TYPES.CLOUDAPP
						) {
							Liferay.Util.navigate(
								Liferay.ThemeDisplay.getLayoutURL().replace(
									'/next-steps',
									`/customer-dashboard#/order/${orderId}/cloud-provisioning`
								)
							);
						}
					}}
					showContinueButton={true}
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
	);
}

export default withProviders(NextSteps);
