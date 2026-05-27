/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayIcon from '@clayui/icon';
import {useSelector} from '@xstate/store/react';
import {useEffect} from 'react';
import {Navigate, useNavigate} from 'react-router-dom';

import ProductPurchase from '../../../../../components/ProductPurchase';
import {Section} from '../../../../../components/Section/Section';
import useAccountAddresses from '../../../../../hooks/useAccountAddresses';
import i18n from '../../../../../i18n';
import {Liferay} from '../../../../../liferay/liferay';
import zodSchema, {z} from '../../../../../schema/zod';
import {formatCurrency} from '../../../../../utils/currencies';
import {useProductPurchaseOutletContext} from '../../../ProductPurchaseOutlet';
import {ProductPurchaseAIHubOpenBeta} from '../../../services/ProductPurchaseAIHubOpenBeta';
import {productPurchaseStore} from '../../../store';
import LicenseTermsCheckbox from '../../App/License/LicenseTermsCheckbox';

import './AIHubOrderSummary.scss';

const AIHubOrderSummary = () => {
	const navigate = useNavigate();

	const {
		actions: {previousStep},
		form,
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

	useEffect(() => {
		if (selectedAccount?.id) {
			const result = zodSchema.aiHubOpenBetaForm.safeParse(form);

			if (!result.success) {
				navigate('/ai-hub-open-beta-form');
			}
		}
	}, [form, navigate, selectedAccount?.id]);

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

	if (!selectedAccount || !selectedAccount.id) {
		return <Navigate to="/" />;
	}

	const summary = productPurchaseCart.cart.summary;
	const currencyCode = Liferay.CommerceContext.currency.currencyCode;

	const valueFallBack = (value: string) => {
		if (!value) {
			return formatCurrency(0, currencyCode);
		}

		return value;
	};

	const onSubmit = async (
		form: z.infer<typeof zodSchema.aiHubOpenBetaForm>
	) => {
		const productPurchase = new ProductPurchaseAIHubOpenBeta(
			selectedAccount,
			product
		);

		productPurchase.setForm(form);

		await handlePurchase(productPurchase, {
			...productPurchaseCart.cart,
			billingAddress: paymentStore.billingAddress,
			paymentMethod: 'money-order',
			shippingAddress: paymentStore.billingAddress,
		});
	};

	return (
		<ProductPurchase.Shell
			className="ai-hub-order-summary product-purchase-summary select-payment-step"
			footerProps={{
				backButtonProps: {
					onClick: previousStep,
				},
				continueButtonProps: {
					children: i18n.translate('get-started'),
					disabled: !paymentStore.eulaAgreement,
					onClick: () =>
						onSubmit(
							form as z.infer<typeof zodSchema.aiHubOpenBetaForm>
						),
				},
			}}
			subtitle={
				<small className="text-black-50">
					{i18n.translate(
						'please-review-the-order-summary-below-and-flag-the-checkbox-to-complete-your-purchase'
					)}
				</small>
			}
			title={i18n.translate('summary')}
		>
			<Section className="ai-hub-summary" label="Billing Address">
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

			<Section className="ai-hub-summary" label="Payment Method">
				<div className="ai-hub-alert-card">
					<ClayIcon
						className="mr-3"
						color="#25488A"
						fontSize={24}
						symbol="info-circle"
					/>
					<p className="ai-hub-alert-card-text m-0">
						{i18n.translate(
							'this-purchase-will-be-billed-under-your-existing-payment-agreement-the-payment-method-cannot-be-changed-and-no-online-payment-is-required'
						)}
					</p>
				</div>

				<div className="ai-hub-summary-infomation-card">
					<ClayIcon
						className="mr-3"
						color="#0B5FFF"
						fontSize={16}
						symbol="document-text"
					/>
					<div>
						<div className="text">
							{i18n.translate('pay-with-invoice')}
						</div>
						<div className="sub-text">
							{i18n.translate(
								'offline-payments-using-the-invoice'
							)}
						</div>
					</div>
				</div>
			</Section>

			<Section className="ai-hub-summary" label="Order Summary">
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
				</div>
			</Section>

			<LicenseTermsCheckbox />
		</ProductPurchase.Shell>
	);
};

export default AIHubOrderSummary;
