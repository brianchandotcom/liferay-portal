/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import classNames from 'classnames';

import ProductPurchase from '../../components/ProductPurchase';
import {getLicenseTagText, getProductPrice} from '../../utils/productUtils';

type ProductPurchasePriceProps = {
	product: DeliveryProduct;
};

const ProductPurchasePrice: React.FC<ProductPurchasePriceProps> = ({
	product,
}) => {
	return (
		<ProductPurchase.Price
			className={classNames('mr-1 py-2 text-nowrap')}
			price={`${getProductPrice(product)}` || '$ 0,00'}
		>
			<div className="license-tag px-2">{getLicenseTagText(product)}</div>
		</ProductPurchase.Price>
	);
};

export default ProductPurchasePrice;
