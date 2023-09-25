/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {CardButton} from '../CardButton/CardButton';

interface PaymentMethodInfo {
	description: string;
	disabled?: boolean;
	method: PaymentMethodSelector;
	title: string;
}

export function PaymentMethodSelector({
	enableTrial,
	selectedPaymentMethod,
	setSelectedPaymentMethod,
}: {
	enableTrial: boolean;
	selectedPaymentMethod: string;
	setSelectedPaymentMethod: (value: PaymentMethodSelector) => void;
}) {
	const paymentMethods: PaymentMethodInfo[] = [
		{
			description: 'Try Now. Pay Later.',
			disabled: !enableTrial,
			method: 'trial',
			title: '30-day trial',
		},
		{
			description: 'Pay Today',
			method: 'pay',
			title: 'Pay Now',
		},
		{
			description: 'Requires a PO Number',
			method: 'order',
			title: 'Invoice',
		},
	];

	return (
		<>
			{paymentMethods.map((methodInfo) => {
				return (
					<CardButton
						description={methodInfo.description}
						disabled={methodInfo.disabled || false}
						key={methodInfo.method}
						onClick={() => {
							if (!methodInfo.disabled) {
								setSelectedPaymentMethod(methodInfo.method);
							}
						}}
						selected={methodInfo.method === selectedPaymentMethod}
						title={methodInfo.title}
					/>
				);
			})}
		</>
	);
}
