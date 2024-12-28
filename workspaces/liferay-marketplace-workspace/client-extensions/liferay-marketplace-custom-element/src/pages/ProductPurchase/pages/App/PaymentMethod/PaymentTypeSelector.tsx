/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import CardButton from '../../../../../components/CardButton/CardButton';
import {PaymentMethodType} from '../../../types';

const getPaymentMethods = (paymentMethodType: PaymentMethodType) => [
	{
		description: 'Try Now. Pay Later.',
		disabled: paymentMethodType !== PaymentMethodType.TRIAL,
		icon: 'check-circle',
		method: PaymentMethodType.TRIAL,
		title: '30-day trial',
	},
	{
		description: 'Pay Today',
		disabled: paymentMethodType === PaymentMethodType.TRIAL,
		icon: 'credit-card',
		method: PaymentMethodType.PAY_NOW,
		title: 'Pay Now',
	},
	{
		description: 'Requires a PO Number',
		disabled: paymentMethodType === PaymentMethodType.TRIAL,
		icon: 'document-text',
		method: PaymentMethodType.INVOICE,
		title: 'Invoice',
	},
];

const PaymentTypeSelector = ({
	paymentMethodType,
	setPaymentMethodType,
}: {
	paymentMethodType: PaymentMethodType;
	setPaymentMethodType: (value: PaymentMethodType) => void;
}) => (
	<div className="d-flex justify-content-between mb-6">
		{getPaymentMethods(paymentMethodType).map((paymentMethod, index) => (
			<CardButton
				{...paymentMethod}
				disabled={paymentMethod.disabled}
				key={index}
				onClick={() => setPaymentMethodType(paymentMethod.method)}
				selected={paymentMethodType === paymentMethod.method}
			/>
		))}
	</div>
);

export {PaymentTypeSelector};
