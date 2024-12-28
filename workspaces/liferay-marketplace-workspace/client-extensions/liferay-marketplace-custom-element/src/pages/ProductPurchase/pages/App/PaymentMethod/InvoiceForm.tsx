/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {useSelector} from '@xstate/store/react';

import {Input} from '../../../../../components/Input/Input';
import i18n from '../../../../../i18n';
import {productPurchaseStore} from '../../../store/AppPurchaseStore';

const InvoiceForm = () => {
	const invoice = useSelector(
		productPurchaseStore,
		({context}) => context.payment.invoice
	);

	return (
		<>
			<Input
				label={i18n.translate('purchase-order-number')}
				onChange={({target: {value}}) =>
					productPurchaseStore.send({
						invoice: {
							...invoice,
							purchaseOrderNumber: value,
						},
						type: 'setInvoice',
					})
				}
				required
				value={invoice.purchaseOrderNumber}
			/>

			<Input
				label={i18n.translate('email-address')}
				onChange={({target: {value}}) =>
					productPurchaseStore.send({
						invoice: {
							...invoice,
							email: value,
						},
						type: 'setInvoice',
					})
				}
				required
				value={invoice.email}
			/>
		</>
	);
};

export default InvoiceForm;
