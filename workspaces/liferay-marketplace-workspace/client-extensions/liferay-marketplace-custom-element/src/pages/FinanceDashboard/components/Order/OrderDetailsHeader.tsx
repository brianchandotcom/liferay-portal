/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import BackLink from '../../../../components/BackLink';
import i18n from '../../../../i18n';
import {PaymentStatus as PaymentStatusCode} from '../../../../enums/Order';
import PaymentStatus from './PaymentStatus/PaymentStatus';

type OrderDetailsHeaderProps = {
	MarkAsPaid: () => JSX.Element;
	orderId: string;
	paymentStatusCode: number;
};

const OrderDetailsHeader = ({
	MarkAsPaid,
	orderId,
	paymentStatusCode,
}: OrderDetailsHeaderProps) => {
	return (
		<div className="d-flex justify-content-between align-items-center">
			<div>
				<BackLink>
					{i18n.translate('back-to-last-transaction')}
				</BackLink>

				<h2>{orderId}</h2>

				{<PaymentStatus paymentStatus={paymentStatusCode} />}
			</div>

			{(paymentStatusCode === PaymentStatusCode.PAYMENT_PENDING ||
				paymentStatusCode === PaymentStatusCode.PENDING) && (
				<MarkAsPaid />
			)}
		</div>
	);
};

export default OrderDetailsHeader;
