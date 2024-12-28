/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayLabel from '@clayui/label';
import {useSelector} from '@xstate/store/react';
import classNames from 'classnames';

import ProductPurchase from '../../components/ProductPurchase';
import {getLicenseTagText, getProductPrice} from '../../utils/productUtils';
import {cartStore} from './store/CartStore';

type ProductPurchasePriceProps = {
	activeStepIndex: number;
	product: DeliveryProduct;
};

const ProductPurchasePrice: React.FC<ProductPurchasePriceProps> = ({
	activeStepIndex,
	product,
}) => {
	const cart = useSelector(cartStore, ({context}) => context.cart);
	const firstStep = activeStepIndex === 0;

	return (
		<ProductPurchase.Price
			className={classNames('mr-1 py-2', {h4: !firstStep})}
			price={
				(activeStepIndex === 0
					? getProductPrice(product)
					: cart?.summary?.totalFormatted) || '$ 0,00'
			}
		>
			<ClayLabel displayType="info">
				{getLicenseTagText(product)}
			</ClayLabel>
		</ProductPurchase.Price>
	);
};

export default ProductPurchasePrice;
