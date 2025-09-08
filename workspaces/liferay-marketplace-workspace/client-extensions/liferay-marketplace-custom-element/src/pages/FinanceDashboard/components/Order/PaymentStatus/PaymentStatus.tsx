/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayIcon from '@clayui/icon';
import classNames from 'classnames';

import {PaymentStatus as PaymentStatusCode} from '../../../../../enums/Order';

import './index.scss';

const paymentStatusLabel = {
	[PaymentStatusCode.PAID]: 'Paid',
	[PaymentStatusCode.PENDING]: 'Unpaid',
	[PaymentStatusCode.PAYMENT_PENDING]: 'Unpaid',
	[PaymentStatusCode.FAILED]: 'Failed',
	[PaymentStatusCode.CANCELLED]: 'Canceled',
};

const PaymentStatus = ({paymentStatus}: {paymentStatus: number}) => (
	<div>
		<ClayIcon
			className={classNames('mr-2 payment-status-icon', {
				'payment-icon-warning':
					paymentStatus === PaymentStatusCode.PAYMENT_PENDING ||
					paymentStatus === PaymentStatusCode.PENDING,
				'text-danger':
					paymentStatus === PaymentStatusCode.FAILED ||
					paymentStatus === PaymentStatusCode.CANCELLED,
				'text-success': paymentStatus === PaymentStatusCode.PAID,
			})}
			symbol="circle"
		/>
		<span className="finance-dashboard-text-secondary">
			{paymentStatusLabel[paymentStatus as PaymentStatusCode]}
		</span>
	</div>
);

export default PaymentStatus;
