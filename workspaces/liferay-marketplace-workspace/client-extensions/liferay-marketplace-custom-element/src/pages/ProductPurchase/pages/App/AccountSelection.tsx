/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {useSelector} from '@xstate/store/react';

import i18n from '../../../../i18n';
import {
	getProductPriceModel,
	isCloudProduct,
} from '../../../../utils/productUtils';
import {useProductPurchaseOutletContext} from '../../ProductPurchaseOutlet';
import ProductPurchaseApp from '../../services/ProductPurchaseApp';
import {productPurchaseStore} from '../../store/AppPurchaseStore';
import ProductPurchaseAccountSelection from '../AccountSelection';
import LicenseTermsCheckbox from './License/LicenseTermsCheckbox';

const AccountSelection = () => {
	const {
		actions: {nextStep},
		handlePurchase,
		product,
		selectedAccount,
	} = useProductPurchaseOutletContext();

	const eulaAgreement = useSelector(
		productPurchaseStore,
		(state) => state.context.payment.eulaAgreement
	);

	const {isFreeApp} = getProductPriceModel(product);


	return (
		<ProductPurchaseAccountSelection
			footerProps={{
				continueButtonProps: {
					children: i18n.translate(
						isFreeApp ? 'get-app' : 'continue'
					),
					disabled:
						!selectedAccount ||
						(isFreeApp ? !eulaAgreement : false),
					onClick: () => {
						if (isFreeApp) {
							return handlePurchase(ProductPurchaseApp);
						}

						nextStep();
					},
				},
			}}
		>
			{isFreeApp && <LicenseTermsCheckbox />}
		</ProductPurchaseAccountSelection>
	);
};

export default AccountSelection;