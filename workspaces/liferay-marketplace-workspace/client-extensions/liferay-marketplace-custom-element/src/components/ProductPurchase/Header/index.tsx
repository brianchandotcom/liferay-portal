/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {ComponentProps} from 'react';

import i18n from '../../../i18n';
import AccountEmailInfo from '../../../pages/CustomerDashboard/pages/Apps/App/Licenses/CreateLicense/AccountInfo';
import {ProductCardRevamp} from '../../../pages/GetApp/components/ProductCard/ProductCard';

type ProductPurchaseHeaderProps = {
	account: Account;
	productCardProps: ComponentProps<typeof ProductCardRevamp>;
};

const ProductPurchaseHeaderAccount: React.FC<
	Pick<ProductPurchaseHeaderProps, 'account'>
> = ({account}) => {
	if (!account) {
		return null;
	}

	return (
		<>
			<hr />

			<div className="d-flex flex-row justify-content-between">
				<strong className="account-banner-title-text align-self-center">
					{i18n.translate('account-selected')}
				</strong>

				<AccountEmailInfo
					userAccount={{
						...account,
						image: account.logoURL,
					}}
				/>
			</div>
		</>
	);
};

const ProductPurchaseHeader: React.FC<ProductPurchaseHeaderProps> = ({
	account,
	productCardProps,
}) => (
	<ProductCardRevamp {...productCardProps}>
		<ProductPurchaseHeaderAccount account={account} />
	</ProductCardRevamp>
);

export default ProductPurchaseHeader;
