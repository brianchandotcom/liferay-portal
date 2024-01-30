/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {useState} from 'react';
import {useOutletContext} from 'react-router-dom';
import useSWR from 'swr';

import {Input} from '../../../components/Input/Input';
import headlessCommerceAdminAddress from '../../../services/rest/HeadlessCommerceAdminAddress';
import {useGetAppContext} from '../GetAppContextProvider';
import {GetAppOutletContext} from '../GetAppOutlet';
import {BillingAddress} from '../components/SelectPaymentMethod/BillingAddress/BillingAddress';
import {PaymentMethodMode} from '../components/SelectPaymentMethod/PaymentMethodMode';
import {PaymentMethodSelector} from '../components/SelectPaymentMethod/PaymentMethodSelector';
import {TrialMethod} from '../components/SelectPaymentMethod/TrialMethod/TrialMethod';
import {PaymentMethod} from '../enums/paymentMethod';

export default function SelectPaymentMethod() {
	const {data: regionsResponse} = useSWR(
		'/commerce-regions',
		headlessCommerceAdminAddress.getRegions
	);
	const [selectedAddress, setSelectedAddress] = useState('');
	const [showNewAddressButton, setShowNewAddressButton] = useState(true);
	const {
		addresses,
		productBasePriceAndTrial: {trialSku},
	} = useOutletContext<GetAppOutletContext>();
	const [
		{
			currentStep,
			payment: {billingAddress, invoice, method: paymentMethod},
			steps,
		},
		dispatch,
	] = useGetAppContext();

	const stepType = steps[currentStep].id;

	return (
		<div className="select-payment-step">
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

			{paymentMethod === PaymentMethod.TRIAL && <TrialMethod />}

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
		</div>
	);
}
