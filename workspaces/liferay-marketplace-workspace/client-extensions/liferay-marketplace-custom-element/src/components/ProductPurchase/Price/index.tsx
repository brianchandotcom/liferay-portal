/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {ReactNode} from 'react';

import i18n from '../../../i18n';

type ProductPurchasePriceProps = {
	children: ReactNode;
	price: number | string;
} & React.HTMLAttributes<HTMLDivElement>;

const ProductPurchasePrice: React.FC<ProductPurchasePriceProps> = ({
	children,
	price,
	...priceProps
}) => (
	<div className="align-items-end d-flex flex-column price-text">
		<strong className="mr-1">{i18n.translate('price')}</strong>

		<div {...priceProps}>{price}</div>

		{children}
	</div>
);

export default ProductPurchasePrice;
