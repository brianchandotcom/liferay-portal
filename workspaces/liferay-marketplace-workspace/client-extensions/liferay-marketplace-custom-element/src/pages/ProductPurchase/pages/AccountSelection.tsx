/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {ComponentProps, ReactNode} from 'react';
import {Navigate} from 'react-router-dom';

import AccountSelection from '../../../components/Checkout/AccountSelection';
import ProductPurchase from '../../../components/ProductPurchase';
import ProductPurchaseFooter from '../../../components/ProductPurchase/Footer';
import {useMarketplaceContext} from '../../../context/MarketplaceContext';
import i18n from '../../../i18n';
import {useProductPurchaseOutletContext} from '../ProductPurchaseOutlet';
import CreateNewAccount from './CreateNewAccount';

type ProductPurchaseAccountSelectionProps = {
	children?: ReactNode;
	footerProps?: ComponentProps<typeof ProductPurchaseFooter>;
};

const ProductPurchaseAccountSelection: React.FC<
	ProductPurchaseAccountSelectionProps
> = ({children, footerProps}) => {
	const {myUserAccount} = useMarketplaceContext();

	const {
		accounts,
		actions: {nextStep},
		productTypeRoute,
		selectedAccount,
		setSelectedAccount,
	} = useProductPurchaseOutletContext();

	const [, nextRoute] = productTypeRoute.routes ?? [];

	const productTypeMetadata = productTypeRoute?.metadata;

	if (
		productTypeMetadata?.skipSingleAccountSelection &&
		nextRoute &&
		accounts.length === 1
	) {
		return <Navigate to={nextRoute.path} />;
	}

	return (
		<ProductPurchase.Shell
			footerProps={{
				backButtonProps: {className: 'd-none'},
				continueButtonProps: {
					disabled: !selectedAccount,
					onClick: nextStep,
				},
				...footerProps,
			}}
			title={i18n.translate('account-selection')}
		>
			{!!accounts.length && (
				<AccountSelection
					onSelectAccount={setSelectedAccount}
					selectedAccount={selectedAccount}
					userAccount={myUserAccount}
				/>
			)}
			<CreateNewAccount accounts={accounts} />
			{children}
		</ProductPurchase.Shell>
	);
};

export default ProductPurchaseAccountSelection;
