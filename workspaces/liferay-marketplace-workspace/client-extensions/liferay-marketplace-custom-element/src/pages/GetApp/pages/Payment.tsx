/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayIcon from '@clayui/icon';
import {useState} from 'react';
import {useOutletContext} from 'react-router-dom';

import {Input} from '../../../components/Input/Input';
import useCommerceRegions from '../../../hooks/useCommerceRegions';
import i18n from '../../../i18n';
import {useGetAppContext} from '../GetAppContextProvider';
import {GetAppOutletContext} from '../GetAppOutlet';
import {BillingAddress} from '../components/SelectPaymentMethod/BillingAddress/BillingAddress';
import {PaymentMethodMode} from '../components/SelectPaymentMethod/PaymentMethodMode';
import {PaymentMethodSelector} from '../components/SelectPaymentMethod/PaymentMethodSelector';
import {TrialMethod} from '../components/SelectPaymentMethod/TrialMethod/TrialMethod';
import Container from '../containers/Container';
import LicenseTermsCheckbox from '../containers/LicenseTermsCheckbox';
import {PaymentMethod} from '../enums/paymentMethod';

const primaryButtonMessage = (paymentMethod: PaymentMethod, price: string) => {
	if (paymentMethod === PaymentMethod.TRIAL) {
		return i18n.translate('start-trial');
	}
	if (paymentMethod === PaymentMethod.PAY) {
		return `Pay ${price} Now`;
	}

	return `Create PO for ${price}`;
};

export default function Payment() {
	const {
		addresses,
		cartUtil,
		handleGetApp,
		loading,
		productBasePriceAndTrial: {trialSku},
		selectedPaymentMethod,
	} = useOutletContext<GetAppOutletContext>();
	const [
		{
			currentStep,
			formState: {isValid},
			payment: {billingAddress, invoice, method: paymentMethod},
			steps,
		},
		dispatch,
	] = useGetAppContext();

	const {data: regionsResponse} = useCommerceRegions();
	const [selectedAddress, setSelectedAddress] = useState('');
	const [showNewAddressButton, setShowNewAddressButton] = useState(true);

	const cartId = cartUtil?.cart?.id;
	const isTrial = selectedPaymentMethod === PaymentMethod.TRIAL;
	const stepType = steps[currentStep].id;

	return (
		<>
			<Container
				className="d-flex flex-column select-payment-step"
				footerProps={{
					primaryButtonProps: {
						children: primaryButtonMessage(
							selectedPaymentMethod,
							cartUtil?.cart?.summary?.totalFormatted ?? '0'
						),
						disabled: !isValid || loading,
						onClick: async () => {
							if (isTrial && cartId) {
								await cartUtil.removeCart(cartId);
							}

							await handleGetApp(cartId);
						},
					},
				}}
				title="Payment Method"
			>
				<div className="d-flex justify-content-between mb-6">
					<PaymentMethodSelector
						enableTrial={!!trialSku}
						selectedPaymentMethod={paymentMethod as PaymentMethod}
						setSelectedPaymentMethod={(payload: PaymentMethod) =>
							dispatch({payload, type: 'SET_PAYMENT_METHOD'})
						}
						step={stepType}
					/>
				</div>

				<BillingAddress
					addresses={addresses}
					billingAddress={billingAddress}
					regions={regionsResponse?.items ?? []}
					selectedAddress={selectedAddress}
					setBillingAddress={(billingAddress) =>
						dispatch({
							payload: billingAddress,
							type: 'SET_BILLING_ADDRESS',
						})
					}
					setSelectedAddress={setSelectedAddress}
					setShowNewAddressButton={setShowNewAddressButton}
					showNewAddressButton={showNewAddressButton}
				/>

				{isTrial && PaymentMethod.TRIAL === selectedPaymentMethod && (
					<TrialMethod />
				)}

				{paymentMethod === PaymentMethod.PAY && (
					<PaymentMethodMode selectedPaymentMethod={paymentMethod} />
				)}

				{paymentMethod === PaymentMethod.ORDER && (
					<>
						<Input
							label="Purchase order number"
							onChange={({target: {value}}) =>
								dispatch({
									payload: {
										...invoice,
										purchaseOrderNumber: value as string,
									},
									type: 'SET_INVOICE',
								})
							}
							required
							value={invoice.purchaseOrderNumber}
						/>

						<Input
							label="Email Address"
							onChange={({target: {value}}) =>
								dispatch({
									payload: {
										...invoice,
										email: value as string,
									},
									type: 'SET_INVOICE',
								})
							}
							required
							value={invoice.email}
						/>
					</>
				)}

				{PaymentMethod.PAY === selectedPaymentMethod && (
					<LicenseTermsCheckbox />
				)}
			</Container>

			{!isTrial && (
				<div className="d-flex flex-column mt-5 text-gray text-right">
					<span className="text-2">
						You will be redirected to PayPal to complete payment
					</span>

					<span className="text-2">
						<ClayIcon className="mr-2" symbol="info-panel-open" />
						Terms, privacy, returns, or contact support. All costs
						are in US Dollars
					</span>
				</div>
			)}
		</>
	);
}
