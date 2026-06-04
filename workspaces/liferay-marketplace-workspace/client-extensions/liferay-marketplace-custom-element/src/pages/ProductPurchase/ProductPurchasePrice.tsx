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
import useProductPurchaseCart from '../../hooks/useProductPurchaseCart';
import i18n from '../../i18n';
import {cartStore} from './store';

type ProductPurchasePriceProps = {
	product: DeliveryProduct;
	productPurchaseCart: ReturnType<typeof useProductPurchaseCart>;
	solutionTypeSpecificationValue?: SolutionTypes;
};

const ProductPurchasePrice: React.FC<ProductPurchasePriceProps> = ({
	product,
	solutionTypeSpecificationValue,
}) => {
	const cart = useSelector(cartStore, ({context}) => context.cart);

	const searchParams = new URLSearchParams(window.location.search);

	const isAIHub =
		solutionTypeSpecificationValue === SolutionTypes.AI_HUB ||
		solutionTypeSpecificationValue === SolutionTypes.AI_HUB_OPEN_BETA ||
		searchParams.has('aiHubTokens');

	const marketplaceDeliveryProduct = useMemo(() => {
		return new MarketplaceDeliveryProduct(product);
	}, [product]);

	const getFormattedPrice = () => {
		const productPrice =
			cart?.summary?.totalFormatted ||
			marketplaceDeliveryProduct.getPrice();

		if (
			marketplaceDeliveryProduct.getPriceModel() ===
			ProductPriceModel.PAID
		) {
			const vatText =
				marketplaceDeliveryProduct.getPriceModel() ===
				ProductPriceModel.PAID
					? `(${i18n.translate('excluding-vat')})`
					: '';

			return `${productPrice} ${vatText}`;
		}

		return 'Free';
	};

	return (
		<ProductPurchase.Price
			className={classNames('mr-1 pr--2 py-2 text-nowrap')}
			monthlySubscription={isAIHub}
			price={getFormattedPrice()}
		>
			<div className="license-tag px-2">
				{marketplaceDeliveryProduct.getLicenseTagText()}
			</div>
		</ProductPurchase.Price>
	);
};

export default ProductPurchasePrice;
