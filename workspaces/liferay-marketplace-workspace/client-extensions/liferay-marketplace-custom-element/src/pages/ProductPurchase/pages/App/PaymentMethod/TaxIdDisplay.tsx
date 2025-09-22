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
	const contextTaxId = context.account.taxId;

	const formInitialvalueTaxid = selectedAccount?.taxId || contextTaxId;

	const [taxId, setTaxId] = useState(formInitialvalueTaxid);

	const handleChange = useCallback(
		({target: {value}}: React.ChangeEvent<HTMLInputElement>) => {
			setTaxId(value);

			productPurchaseStore.send({
				account: {taxId: value},
				type: 'setAccountTaxId',
			});
		},
		[]
	);

	return (
		<Section label={i18n.translate('vat-id')}>
			<Input
				disabled={!!selectedAccount?.taxId}
				onChange={handleChange}
				required
				value={taxId}
			/>
		</Section>
	);
};

export default TaxIdDisplay;
