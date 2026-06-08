/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {useSelector} from '@xstate/store/react';
import classNames from 'classnames';
import {useMemo} from 'react';

import ProductPurchase from '../../components/ProductPurchase';
import {MarketplaceDeliveryProduct} from '../../entity/MarketplaceDeliveryProduct';
import {ProductPriceModel, SolutionTypes} from '../../enums/Product';
import {cartStore} from './store';

type ProductPurchasePriceProps = {
	product: DeliveryProduct;
	skuRef?: null | string;
};

const ProductPurchasePrice: React.FC<ProductPurchasePriceProps> = ({
	product,
	skuRef,
}) => {
	const cart = useSelector(cartStore, ({context}) => context.cart);

	const searchParams = new URLSearchParams(window.location.search);

	const marketplaceDeliveryProduct = useMemo(() => {
		return new MarketplaceDeliveryProduct(product);
	}, [product]);

	const getFormattedPrice = () => {
		const productPrice =
			cart?.summary?.totalFormatted ||
			marketplaceDeliveryProduct.getPrice(skuRef);

		if (
			marketplaceDeliveryProduct.getPriceModel() ===
			ProductPriceModel.PAID
		) {
			return productPrice ?? '';
		}

		return 'Free';
	};

	return (
		<ProductPurchase.Price
			className={classNames('mr-1 pr--2 py-2')}
			price={getFormattedPrice()}
		>
			<div className="license-tag px-2">
				{marketplaceDeliveryProduct.getLicenseTagText()}
			</div>
		</ProductPurchase.Price>
	);
};

export default ProductPurchasePrice;
