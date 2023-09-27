/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import infoCircleIcon from '../../assets/icons/info_circle_icon.svg';
import {Input} from '../../components/Input/Input';
import {BillingAddress} from './BillingAddress';
import {PaymentMethodMode} from './PaymentMethodMode';
import {PaymentMethodSelector} from './PaymentMethodSelector';
import {TrialTimeline} from './TrialTimeline';

export function SelectPaymentMethod({
	addresses,
	billingAddress,
	email,
	enableTrialMethod,
	purchaseOrderNumber,
	selectedAddress,
	selectedPaymentMethod,
	setBillingAddress,
	setEmail,
	setEnablePurchaseButton,
	setPurchaseOrderNumber,
	setSelectedAddress,
	setSelectedPaymentMethod,
	setShowNewAddressButton,
	showNewAddressButton,
}: {
	addresses: BillingAddress[];
	billingAddress: BillingAddress;
	email: string;
	enableTrialMethod: boolean;
	purchaseOrderNumber: string;
	selectedAddress: string;
	selectedPaymentMethod: PaymentMethodSelector;
	setBillingAddress: (value: BillingAddress) => void;
	setEmail: (value: string) => void;
	setEnablePurchaseButton: (value: boolean) => void;
	setPurchaseOrderNumber: (value: string) => void;
	setSelectedAddress: (value: string) => void;
	setSelectedPaymentMethod: (value: PaymentMethodSelector) => void;
	setShowNewAddressButton: (value: boolean) => void;
	showNewAddressButton: boolean;
}) {
	return (
		<>
			<div className="get-app-modal-payment-methods">
				<div className="get-app-modal-payment-methods-container">
					<PaymentMethodSelector
						enableTrial={enableTrialMethod}
						selectedPaymentMethod={selectedPaymentMethod as string}
						setSelectedPaymentMethod={setSelectedPaymentMethod}
					/>
				</div>
			</div>

			{selectedPaymentMethod === 'trial' && <TrialTimeline />}

			{selectedPaymentMethod === 'pay' && (
				<PaymentMethodMode
					selectedPaymentMethod={selectedPaymentMethod}
				/>
			)}

			{selectedPaymentMethod === 'order' && (
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

			<img
				alt="Account icon"
				className="get-app-modal-info-icon"
				src={infoCircleIcon}
			/>

			<span className="get-app-modal-use-terms">
				Terms, privacy, returns, or contact support. All costs are in US
				Dollars
			</span>
		</>
	);
}
