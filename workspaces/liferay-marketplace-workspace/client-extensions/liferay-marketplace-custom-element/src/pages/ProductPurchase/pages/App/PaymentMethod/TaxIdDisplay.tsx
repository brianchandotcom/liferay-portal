/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {useCallback, useState} from 'react';

import {Input} from '../../../../../components/Input/Input';
import {Section} from '../../../../../components/Section/Section';
import i18n from '../../../../../i18n';
import {useProductPurchaseOutletContext} from '../../../ProductPurchaseOutlet';
import {productPurchaseStore} from '../../../store/AppPurchaseStore';

const TaxIdDisplay = () => {
	const {selectedAccount} = useProductPurchaseOutletContext();

	const {context} = productPurchaseStore.getSnapshot();
	const billingAddress = context.payment.billingAddress;

	const initialVat =
		selectedAccount?.taxId || billingAddress?.vatNumber || '';

	const [vatNumber, setVatNumber] = useState(initialVat);

	const handleChange = useCallback(
		({target: {value}}: React.ChangeEvent<HTMLInputElement>) => {
			setVatNumber(value);

			productPurchaseStore.send({
				billingAddress: {...billingAddress, vatNumber: value},
				type: 'setBillingAddress',
			});
		},
		[billingAddress]
	);

	return (
		<Section
			label="VAT ID"
			tooltip={i18n.translate(
				'standard-licenses-cover-the-following-dxp-environments-production-non-production-uat-and-backup-dr-for-both-standalone-and-virtual-cluster-servers'
			)}
			tooltipText={i18n.translate('more-info')}
		>
			<Input
				disabled={!!selectedAccount?.taxId}
				label={i18n.translate('purchase-order-number')}
				onChange={handleChange}
				required
				value={vatNumber}
			/>
		</Section>
	);
};

export default TaxIdDisplay;
