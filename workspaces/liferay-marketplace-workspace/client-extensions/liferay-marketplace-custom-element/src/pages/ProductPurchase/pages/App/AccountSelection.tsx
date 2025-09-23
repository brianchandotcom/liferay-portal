/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {useSelector} from '@xstate/store/react';
import {useEffect} from 'react';
import {useNavigate} from 'react-router-dom';

import i18n from '../../../../i18n';
import {getProductPriceModel} from '../../../../utils/productUtils';
import {useProductPurchaseOutletContext} from '../../ProductPurchaseOutlet';
import ProductPurchaseApp from '../../services/ProductPurchaseApp';
import {productPurchaseStore} from '../../store/AppPurchaseStore';
import ProductPurchaseAccountSelection from '../AccountSelection';
import LicenseTermsCheckbox from './License/LicenseTermsCheckbox';

const AccountSelection = () => {
	const {
		accounts,
		actions: {nextStep},
		handlePurchase,
		product,
		selectedAccount,
		setSelectedAccount,
	} = useProductPurchaseOutletContext();

	const navigate = useNavigate();

	useEffect(() => {
		const {isFreeApp} = getProductPriceModel(product);

		if (isFreeApp) {
			if (accounts.length === 1 && !selectedAccount) {
				setSelectedAccount(accounts[0]);
			}

			return navigate('summary', {replace: true});
		}

		if (accounts.length === 1 && !selectedAccount) {
			setSelectedAccount(accounts[0]);

			navigate('license', {replace: true});
		}
	}, [accounts, selectedAccount, product, navigate, setSelectedAccount]);

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
