/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {useNavigate, useOutletContext} from 'react-router-dom';

import AccountSelection from '../../../components/Checkout/AccountSelection';
import ProductPurchase from '../../../components/ProductPurchase';
import {useMarketplaceContext} from '../../../context/MarketplaceContext';
import i18n from '../../../i18n';
import {ProductPurchaseOutletContext} from '../ProductPurchaseOutlet';

const ProductPurchaseAccountSelection = () => {
	const {myUserAccount} = useMarketplaceContext();
	const navigate = useNavigate();
	const {selectedAccount, setSelectedAccount} =
		useOutletContext<ProductPurchaseOutletContext>();

	return (
		<ProductPurchase.Shell
			footerProps={{
				backButtonProps: {className: 'd-none'},
				continueButtonProps: {
					disabled: !selectedAccount,
					onClick: () => navigate('form'),
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
