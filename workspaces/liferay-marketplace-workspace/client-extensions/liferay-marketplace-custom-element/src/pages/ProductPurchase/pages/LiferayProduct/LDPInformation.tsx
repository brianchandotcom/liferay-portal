/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {useSelector} from '@xstate/store/react';
import {Navigate} from 'react-router-dom';

import ProductPurchase from '../../../../components/ProductPurchase';
import i18n from '../../../../i18n';
import {useProductPurchaseOutletContext} from '../../ProductPurchaseOutlet';
import {productPurchaseStore} from '../../store';
import BillingAddress from '../App/PaymentMethod/BillingAddress/BillingAddress';
import TaxIdDisplay from '../App/PaymentMethod/TaxIdDisplay';

const LDPInformation = () => {
	const {
		actions: {nextStep, previousStep},
	} = useProductPurchaseOutletContext();

	const salesforceProject = useSelector(
		productPurchaseStore,
		({context}) => context.salesforceProject
	);

	if (!salesforceProject) {
		return <Navigate to="/" />;
	}

	return (
		<ProductPurchase.Shell
			footerProps={{
				backButtonProps: {onClick: previousStep},
				continueButtonProps: {onClick: nextStep},
			}}
			title={i18n.translate('personal-information')}
		>
			<BillingAddress sectionName={i18n.translate('address')} />

			<TaxIdDisplay />
		</ProductPurchase.Shell>
	);
};

export default LDPInformation;
