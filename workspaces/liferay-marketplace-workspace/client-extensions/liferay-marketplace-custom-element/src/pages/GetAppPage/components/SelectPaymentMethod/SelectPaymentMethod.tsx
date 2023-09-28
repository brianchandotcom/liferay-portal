/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {useState} from 'react';

import {Input} from '../../../../components/Input/Input';
import {paymentMethod} from '../../enums/paymentMethod';
import {BillingAddress} from './components/BillingAddress/BillingAddress';
import {PaymentMethodMode} from './components/PaymentMethodMode/PaymentMethodMode';
import {PaymentMethodSelector} from './components/PaymentMethodSelector/PaymentMethodSelector';
import {TrialTimeline} from './components/TrialTimeLine/TrialTimeline';

interface SelectPaymentMethodProps {
	addresses: BillingAddress[];
	billingAddress: BillingAddress;
	email: string;
	enableTrialMethod: boolean;
	purchaseOrderNumber: string;
	selectedPaymentMethod: PaymentMethodSelector;
	setBillingAddress: (value: BillingAddress) => void;
	setEmail: (value: string) => void;
	setEnablePurchaseButton: (value: boolean) => void;
	setPurchaseOrderNumber: (value: string) => void;
	setSelectedPaymentMethod: (value: PaymentMethodSelector) => void;
}

export function SelectPaymentMethod({
	addresses,
	billingAddress,
	email,
	enableTrialMethod,
	purchaseOrderNumber,
	selectedPaymentMethod,
	setBillingAddress,
	setEmail,
	setEnablePurchaseButton,
	setPurchaseOrderNumber,
	setSelectedPaymentMethod,
}: SelectPaymentMethodProps) {
	const [selectedAddress, setSelectedAddress] = useState<string>('');
	const [showNewAddressButton, setShowNewAddressButton] = useState<boolean>(
		true
	);

	return (
		<div className="select-payment-step">
			<div className="d-flex justify-content-between mb-6">
				<PaymentMethodSelector
					enableTrial={enableTrialMethod}
					selectedPaymentMethod={selectedPaymentMethod as string}
					setSelectedPaymentMethod={setSelectedPaymentMethod}
				/>
			</div>

			{selectedPaymentMethod === paymentMethod.TRIAL && <TrialTimeline />}

			{selectedPaymentMethod === paymentMethod.PAY && (
				<PaymentMethodMode
					selectedPaymentMethod={selectedPaymentMethod}
				/>
			)}

			{selectedPaymentMethod === paymentMethod.ORDER && (
				<>
					<Input
						label="Purchase order number"
						onChange={({target}) =>
							setPurchaseOrderNumber(target.value)
						}
						required
						value={purchaseOrderNumber}
					/>

					<Input
						label="Email Address"
						onChange={({target}) => setEmail(target.value)}
						required
						value={email}
					/>
				</>
			)}

			<BillingAddress
				addresses={addresses}
				billingAddress={billingAddress}
				selectedAddress={selectedAddress}
				setBillingAddress={setBillingAddress}
				setEnablePurchaseButton={setEnablePurchaseButton}
				setSelectedAddress={setSelectedAddress}
				setShowNewAddressButton={setShowNewAddressButton}
				showNewAddressButton={showNewAddressButton}
			/>
		</div>
	);
}
