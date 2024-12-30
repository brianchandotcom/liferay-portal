/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {CardButton} from '../../../../components/CardButton/CardButton';
import {GetAppStepTypes} from '../../enums/GetAppStepTypes';
import {PaymentMethod} from '../../enums/paymentMethod';

const getPaymentMethods = (
	disablePaidMethods: boolean,
	selectedPaymentMethod: PaymentMethod
) => [
	{
		description: 'Try Now. Pay Later.',
		disabled: selectedPaymentMethod !== PaymentMethod.TRIAL,
		icon: 'check-circle',
		method: PaymentMethod.TRIAL,
		title: '30-day trial',
	},
	{
		description: 'Pay Today',
		disabled: disablePaidMethods,
		icon: 'credit-card',
		method: PaymentMethod.PAY,
		title: 'Pay Now',
	},
	{
		description: 'Requires a PO Number',
		disabled: disablePaidMethods,
		icon: 'document-text',
		method: PaymentMethod.ORDER,
		title: 'Invoice',
	},
];

export function PaymentMethodSelector({
	selectedPaymentMethod,
	setSelectedPaymentMethod,
}: {
	enableTrial: boolean;
	selectedPaymentMethod: PaymentMethod;
	setSelectedPaymentMethod: (value: PaymentMethod) => void;
	step: GetAppStepTypes;
}) {
	const disablePaidMethods =
		selectedPaymentMethod !== PaymentMethod.PAY &&
		selectedPaymentMethod !== PaymentMethod.ORDER;

	const paymentMethods = getPaymentMethods(
		disablePaidMethods,
		selectedPaymentMethod
	);

	return (
		<>
			{paymentMethods.map((paymentMethod, index) => (
				<CardButton
					{...paymentMethod}
					disabled={paymentMethod.disabled || false}
					key={index}
					onClick={() => {
						if (!paymentMethod.disabled) {
							setSelectedPaymentMethod(paymentMethod.method);
						}
					}}
					selected={paymentMethod.method === selectedPaymentMethod}
				/>
			))}
		</>
	);
}
