/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {useEffect} from 'react';
import {useNavigate, useOutletContext} from 'react-router-dom';

import AccountSelection from '../../../components/Checkout/AccountSelection';
import ProductPurchase from '../../../components/ProductPurchase';
import {useMarketplaceContext} from '../../../context/MarketplaceContext';
import i18n from '../../../i18n';
import {scrollToTop} from '../../../utils/browser';
import {ProductPurchaseOutletContext} from '../ProductPurchaseOutlet';

const ProductPurchaseAccountSelection = () => {
	const {myUserAccount} = useMarketplaceContext();
	const navigate = useNavigate();
	const {accounts, routes, selectedAccount, setSelectedAccount} =
		useOutletContext<ProductPurchaseOutletContext>();

	const nextRoutePath = routes[1].path;

	useEffect(() => {
		if (accounts.length === 1 && nextRoutePath) {
			navigate(nextRoutePath);
		}
	}, [accounts.length, navigate, nextRoutePath]);

	return (
		<ProductPurchase.Shell
			footerProps={{
				backButtonProps: {className: 'd-none'},
				continueButtonProps: {
					disabled: !selectedAccount,
					onClick: () => {
						scrollToTop();

						navigate('form');
					},
				},
			}}
			title={i18n.translate('account-selection')}
		>
			<AccountSelection
				onSelectAccount={setSelectedAccount}
				selectedAccount={selectedAccount}
				userAccount={myUserAccount}
			/>
		</ProductPurchase.Shell>
	);
};

export default ProductPurchaseAccountSelection;
