/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
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
