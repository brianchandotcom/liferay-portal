/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
import ClayBadge from '@clayui/badge';
import ClayButton from '@clayui/button';
import ClayIcon from '@clayui/icon';
import ClaySticker from '@clayui/sticker';
import {useSelector} from '@xstate/store/react';
import {useEffect} from 'react';

import paypal from '../../../../../assets/images/paypal.png';

import ProductPurchase from '../../../../../components/ProductPurchase';
import {Section} from '../../../../../components/Section/Section';
import useAccountAddresses from '../../../../../hooks/useAccountAddresses';
import i18n from '../../../../../i18n';
import {formatCurrency} from '../../../../../utils/currencies';
import {getAiHubTokenSKUs} from '../../../../../utils/productUtils';
import {useProductPurchaseOutletContext} from '../../../ProductPurchaseOutlet';
import {ProductPurchaseAIHubToken} from '../../../services/ProductPurchaseAIHubToken';
import {productPurchaseStore} from '../../../store';
import LicenseTermsCheckbox from '../../App/License/LicenseTermsCheckbox';

import './AIHubOrderSummary.scss';

const AIHubTokenOrderSummary = () => {
	const {
		actions: {previousStep},
		handlePurchase,
		product,
		productPurchaseCart,
		selectedAccount,
	} = useProductPurchaseOutletContext();

	const {payment: paymentStore} = useSelector(
		productPurchaseStore,
		(state) => state.context
	);

	const {data: addressResponse} = useAccountAddresses(selectedAccount?.id);
	const addresses = addressResponse?.items;

	const selectedSkuId = productPurchaseCart.cartItems[0]?.skuId;

	useEffect(() => {
		if (!selectedAccount || !selectedAccount.id || !selectedSkuId) {
			previousStep();
		}
	}, [selectedAccount, selectedSkuId, previousStep]);

	useEffect(() => {
		if (
			addresses &&
			!!addresses.length &&
			!paymentStore.billingAddress?.name
		) {
			const postalAddress = addresses[0];
			const billingAddress = {
				city: postalAddress.city || '',
				country: postalAddress.countryISOCode || '',
				countryISOCode: postalAddress.countryISOCode || 'US',
				name: postalAddress.name || '',
				phoneNumber: postalAddress.phoneNumber || '',
				regionISOCode: postalAddress.regionISOCode || '',
				street1: postalAddress.street1 || '',
				street2: postalAddress.street2 || '',
				zip: postalAddress.zip || '',
			};

			productPurchaseStore.send({
				billingAddress,
				type: 'setBillingAddress',
			});
		}
	}, [addresses, paymentStore.billingAddress?.name]);

	if (!selectedAccount || !selectedAccount.id || !selectedSkuId) {
		return null;
	}

	const tokens = getAiHubTokenSKUs(product);

	const selectedSku = tokens.find((token) => token.id === selectedSkuId);

	const summary = productPurchaseCart.cart.summary;
	const currencyCode =
		productPurchaseCart.cartItems[0]?.price?.currency || 'USD';

	const valueFallBack = (value: string) => {
		if (!value) {
			return formatCurrency(0, currencyCode);
		}

		return value;
	};

	const onSubmit = async () => {
		const productPurchase = new ProductPurchaseAIHubToken(
			selectedAccount,
			product
		);

		await handlePurchase(productPurchase, {
			...productPurchaseCart.cart,
			billingAddress: paymentStore.billingAddress,
			cartItems: productPurchaseCart.cartItems,
			paymentMethod: 'money-order',
			shippingAddress: paymentStore.billingAddress,
		});
	};

	return (
		<ProductPurchase.Shell
			className="ai-hub-order-summary product-purchase-summary select-payment-step"
			subtitle={
				<small className="text-black-50">
					{i18n.translate(
						'please-review-the-order-summary-below-and-flag-the-checkbox-to-complete-your-purchase'
					)}
				</small>
			}
			title={i18n.translate('summary')}
		>
			{selectedSku && (
				<Section
					className="ai-hub-summary"
					label={i18n.translate('tokens')}
				>
					<div className="ai-hub-summary-infomation-card">
						<div className="align-items-center d-flex justify-content-between w-100">
							<div className="d-flex align-items-center">
								{selectedSku.customFields?.find(
									(field: any) => field.name === 'icon-url'
								)?.customValue.data && (
									<div className="mr-3">
										<ClaySticker shape="circle" size="lg">
											<ClaySticker.Image
												alt="AI Hub Token Icon"
												src={
													selectedSku.customFields?.find(
														(field: any) =>
															field.name ===
															'icon-url'
													)?.customValue.data as string
												}
											/>
										</ClaySticker>
									</div>
								)}
								<div>
									<p className="liferay-ai-hub-form-token-name mb-1">
										{
											selectedSku.skuOptions?.[0]
												?.skuOptionValueNames?.[0]
										}
									</p>
									<p className="liferay-ai-hub-form-token-description mb-0 text-black-50">
										{
											selectedSku.customFields?.find(
												(field: any) =>
													field.name === 'description'
											)?.customValue.data as string
										}
									</p>
								</div>
							</div>
							<p className="liferay-ai-hub-form-token-price mb-0">
								{selectedSku.price?.priceFormatted}
							</p>
						</div>
					</div>
				</Section>
			)}

			<Section
				className="ai-hub-summary"
				label={i18n.translate('billing-address')}
			>
				<div className="ai-hub-summary-infomation-card">
					<ClayIcon
						className="mr-3"
						color="#0B5FFF"
						fontSize={16}
						symbol="geolocation"
					/>
					<div>
						<div className="text">
							{paymentStore.billingAddress?.name}
						</div>
						<div className="sub-text">
							{`${paymentStore.billingAddress?.street1}, ${paymentStore.billingAddress?.city}, ${paymentStore.billingAddress?.regionISOCode}, ${paymentStore.billingAddress?.country}`}
						</div>
					</div>
				</div>
			</Section>

			<Section className="ai-hub-summary" label={i18n.translate('payment-method')}
			>
				<div className="ai-hub-summary-infomation-card">
					<img
						alt="paypal"
						className="mr-3"
						height={18}
						src={paypal}
						width={16}
					/>
					<div>
						<div className="text">
							{i18n.translate('pay-with-card')}
						</div>
						<p className="font-weight-normal mb-0 sub-text text-black-50">
							Online payments with <b>PayPal</b>
						</p>
					</div>
				</div>
			</Section>

			<Section className="ai-hub-summary" label={i18n.translate('order-summary')}
			>
				<div className="d-flex mx-5">
					<div className="col-1 d-flex justify-content-end m-0 p-0 text-nowrap">
						{i18n.translate('net-price')}:
					</div>
					<span className="font-weight-bold ml-2">
						{valueFallBack(summary?.subtotalFormatted)}
					</span>
				</div>

				<div className="d-flex mx-5">
					<div className="col-1 d-flex justify-content-end m-0 p-0">
						{i18n.translate('vat')}:
					</div>
					<span className="font-weight-bold ml-2">
						{valueFallBack(summary?.taxValueFormatted)}
					</span>
				</div>

				<div className="d-flex mx-5">
					<div className="col-1 d-flex justify-content-end m-0 p-0">
						{i18n.translate('total')}:
					</div>
					<span className="font-weight-bold ml-2">
						{valueFallBack(summary?.totalFormatted)}
					</span>
					<ClayBadge
							className="ml-3 px-2 text-2 rounded font-weight-normal monthly-badge"
							label="One-Time"
						/>
				</div>
			</Section>

			<LicenseTermsCheckbox />

			<div className="d-flex flex-column mt-4 w-100">
				<ClayButton
					className="font-weight-bold w-100"
					disabled={!paymentStore.eulaAgreement}
					displayType="primary"
					onClick={onSubmit}
					size="regular"
				>
					{i18n.translate('buy-liferay-tokens')}
				</ClayButton>
			</div>
		</ProductPurchase.Shell>
	);
};

export default AIHubTokenOrderSummary;
