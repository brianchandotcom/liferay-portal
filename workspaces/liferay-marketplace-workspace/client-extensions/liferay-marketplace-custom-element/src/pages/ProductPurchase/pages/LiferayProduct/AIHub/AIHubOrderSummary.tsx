/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayBadge from '@clayui/badge';
import ClayButton from '@clayui/button';
import {ClayCheckbox} from '@clayui/form';
import ClayIcon from '@clayui/icon';
import {useSelector} from '@xstate/store/react';
import {useEffect, useMemo, useState} from 'react';
import {Navigate} from 'react-router-dom';

import ProductPurchase from '../../../../../components/ProductPurchase';
import {Section} from '../../../../../components/Section/Section';
import useAccountAddresses from '../../../../../hooks/useAccountAddresses';
import i18n from '../../../../../i18n';
import {Liferay} from '../../../../../liferay/liferay';
import zodSchema, {z} from '../../../../../schema/zod';
import {productAgreements} from '../../../../../utils/agreements';
import {formatCurrency} from '../../../../../utils/currencies';
import {useProductPurchaseOutletContext} from '../../../ProductPurchaseOutlet';
import {ProductPurchaseAIHubOpenBeta} from '../../../services/ProductPurchaseAIHubOpenBeta';
import {productPurchaseStore} from '../../../store';
import BillingAddress from '../../App/PaymentMethod/BillingAddress/BillingAddress';

import './AIHubOrderSummary.scss';

const AIHubOrderSummary = () => {
	const {
		form,
		handlePurchase,
		product,
		productPurchaseCart,
		selectedAccount,
	} = useProductPurchaseOutletContext();

	const [userAgreement, setUserAgreement] = useState(false);

	const {payment: paymentStore, salesforceProject} = useSelector(
		productPurchaseStore,
		(state) => state.context
	);

	const {data: addressResponse} = useAccountAddresses(selectedAccount?.id);

	const addresses = useMemo(
		() => addressResponse?.items ?? [],
		[addressResponse?.items]
	);

	const isBillingAddressValid = zodSchema.billingAddress.safeParse(
		paymentStore.billingAddress
	).success;

	useEffect(() => {
		if (!!addresses.length && !paymentStore.billingAddress?.name) {
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

	const summary = productPurchaseCart.cart.summary;
	const currencyCode = Liferay.CommerceContext.currency.currencyCode;

	const valueFallBack = (value: string) => {
		if (!value) {
			return formatCurrency(0, currencyCode);
		}

		return value;
	};

	const onSubmit = async (
		form: z.infer<typeof zodSchema.aiHubOpenBetaForm> & {
			salesforceProjectId: string;
		}
	) => {
		const productPurchase = new ProductPurchaseAIHubOpenBeta(
			selectedAccount,
			product
		);

		form.salesforceProjectId = String(
			salesforceProject?.externalReferenceCode
		);

		productPurchase.setForm(form);

		await handlePurchase(productPurchase, {
			...productPurchaseCart.cart,
			billingAddress: paymentStore.billingAddress,
			paymentMethod: 'money-order',
			shippingAddress: paymentStore.billingAddress,
		});
	};

	if (!salesforceProject) {
		return <Navigate replace to="/" />;
	}

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
			{addresses.length === 1 ? (
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
			) : (
				<BillingAddress
					hideNewAddressButton={addresses.length > 1}
					sectionName={i18n.translate('billing-address')}
				/>
			)}

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
					<span className="d-flex font-weight-bold ml-2">
						{valueFallBack(summary?.totalFormatted)}{' '}
						<ClayBadge
							className="font-weight-normal ml-4 monthly-badge px-2 rounded text-2"
							label="Monthly"
						/>
					</span>
				</div>
			</Section>

			<p className="liferay-ai-hub-form-aggreements-text">
				<span>Please read</span>

				<a
					className="ml-1"
					href={productAgreements.links.aiHub.agreement}
					target="_blank"
				>
					this agreement
				</a>

				<span className="ml-1">
					carefully before accessing or in any way using the AI Hub
					experience.
				</span>
			</p>

			<div className="d-flex flex-row text-justify">
				<ClayCheckbox
					checked={userAgreement}
					id="user-agreement"
					onChange={() => {
						setUserAgreement(!userAgreement);
					}}
					required
				/>

				<label
					className="font-weight-normal px-1"
					htmlFor="user-agreement"
				>
					<span>
						{i18n.translate(
							'i-agree-to-the-processing-of-my-personal-data-for-the-purpose-of-evaluating-my-beta-access-request-in-accordance-with'
						)}
						<span className="ml-1">
							{i18n.translate('liferay-s-privacy-policy')}
						</span>
					</span>
				</label>
			</div>

			<p className="liferay-ai-hub-form-aggreements-text mt-2 text-justify">
				<span>
					You can stop receiving marketing emails by clicking the
					unsubscribe link in each email or withdraw your consent at
					any time by either using opt-out functionality accessible
					through the messages you receive or via email to
				</span>

				<a className="ml-1" href="mailto:dataprotection@liferay.com">
					dataprotection@liferay.com
				</a>

				<span className="ml-1">See</span>

				<a
					className="ml-1"
					href={productAgreements.links.privacyPolicy}
					target="_blank"
				>
					privacy policy
				</a>

				<span className="ml-1">for details.</span>
			</p>

			<div className="d-flex flex-column mt-4 w-100">
				<ClayButton
					className="font-weight-bold w-100"
					disabled={!isBillingAddressValid || !userAgreement}
					displayType="primary"
					onClick={() =>
						onSubmit(
							form as z.infer<
								typeof zodSchema.aiHubOpenBetaForm
							> & {salesforceProjectId: string}
						)
					}
					size="regular"
				>
					{i18n.translate('purchase')}
				</ClayButton>
			</div>
		</ProductPurchase.Shell>
	);
};

export default AIHubOrderSummary;
