/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import credit_card_icon from '../../../../../../assets/icons/credit_card_icon.svg';
import document_icon from '../../../../../../assets/icons/document_icon.svg';
import task_checked_icon from '../../../../../../assets/icons/task_checked_icon.svg';
import {CardButton} from '../../../../../../components/CardButton/CardButton';
import {paymentMethod} from '../../../../enums/paymentMethod';

interface PaymentMethodInfo {
	description: string;
	disabled?: boolean;
	icon: string;
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
			icon: task_checked_icon,
			method: paymentMethod.TRIAL,
			title: '30-day trial',
		},
		{
			description: 'Pay Today',
			icon: credit_card_icon,
			method: paymentMethod.PAY,
			title: 'Pay Now',
		},
		{
			description: 'Requires a PO Number',
			icon: document_icon,
			method: paymentMethod.ORDER,
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
						icon={methodInfo.icon}
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
