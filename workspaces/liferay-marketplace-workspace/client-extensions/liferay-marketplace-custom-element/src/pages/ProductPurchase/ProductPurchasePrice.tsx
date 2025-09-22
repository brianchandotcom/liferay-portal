/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import classNames from 'classnames';

import ProductPurchase from '../../components/ProductPurchase';
import {SkuOptions} from '../../enums/Product';
import useProductPurchaseCart from '../../hooks/useProductPurchaseCart';
import i18n from '../../i18n';
import {Liferay} from '../../liferay/liferay';
import {formatCurrency} from '../../utils/currencies';
import {
	getLicenseTagText,
	getProductPrice,
	getProductPriceModel,
	getSkuByOptionValueKey,
} from '../../utils/productUtils';

type ProductPurchasePriceProps = {
	product: DeliveryProduct;
	productPurchaseCart: ReturnType<typeof useProductPurchaseCart>;
};

const ProductPurchasePrice: React.FC<ProductPurchasePriceProps> = ({
	product,
	productPurchaseCart,
}) => {
	const currencyCode = Liferay.CommerceContext.currency.currencyCode;

	const getFormattedPrice = (): string => {
		const trialSku = getSkuByOptionValueKey(product, SkuOptions.TRIAL);
		const standardSku = getSkuByOptionValueKey(
			product,
			SkuOptions.STANDARD
		);

		const trialSkuId = trialSku?.id;

		const hasNoTrialLicense = productPurchaseCart?.cartItems?.some(
			(item: CartItem) => item?.skuId !== trialSkuId
		);

		if (hasNoTrialLicense && standardSku?.price?.priceFormatted) {
			return `${standardSku.price.priceFormatted} (${i18n.translate('excluding-vat')})`;
		}

		const {isFreeApp} = getProductPriceModel(product);

		if (isFreeApp) {
			return 'Free';
		}

		return `${getProductPrice(product)} (${i18n.translate('excluding-vat')})`;
	};

	return (
		<ProductPurchase.Price
			className={classNames('mr-1 py-2 text-nowrap')}
			price={getFormattedPrice() || formatCurrency(0, currencyCode)}
		>
			<div className="license-tag px-2">{getLicenseTagText(product)}</div>
		</ProductPurchase.Price>
	);
};

export default ProductPurchasePrice;
