/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {ORDER_TYPES} from '../../../enums/Order';
import {ProductType} from '../../../enums/ProductType';
import {SkuLicenseUsageTypeValue} from '../../../enums/Sku';
import headlessCommerceDeliveryCart from '../../../services/rest/HeadlessCommerceDeliveryCart';
import {postEmailAppInformation} from '../../../utils/api';
import {
	getProductPriceModel,
	getSkuByOptionValueKey,
	isCloudProduct,
} from '../../../utils/productUtils';
import {getSiteURL} from '../../../utils/site';
import ProductPurchase, {ProductPurchaseCart} from './ProductPurchase';

type ProductPurchaseCartOptions = {
	isTrialSKU?: number;
};

export default class ProductPurchaseApp extends ProductPurchase {
	protected analyticsTrack(): void {
		const {isFreeApp} = getProductPriceModel(this.product);

		this.Analytics.track('APP_PURCHASE', {
			isFreeApp,
			productName: this.product.name,
		});
	}

	public async createOrder(
		cart: ProductPurchaseCart,
		cartOptions: ProductPurchaseCartOptions
	) {
		const order = await super.createOrder(
			this.getAppPurchaseCart(cart, cartOptions)
		);

		await postEmailAppInformation({
			dashboardLink: getSiteURL() + '/customer-dashboard',
			orderID: order.id,
			priceModel: 'paid',
			productName: this.product?.name as string,
			productType: isCloudProduct(this.product)
				? ProductType.CLOUD
				: ProductType.DXP,
		});

		return order;
	}

	private getAppPurchaseCart(
		cart: ProductPurchaseCart,
		cartOptions: ProductPurchaseCartOptions
	) {
		const orderTypeExternalReferenceCode =
			ProductPurchaseApp.getOrderTypeExternalReferenceCode(this.product);

		const baseCart = {
			...cart,
			orderTypeExternalReferenceCode,
		} as Partial<Cart>;

		if (cart) {

			// Only requests with cart are processed with payment

			return {...baseCart, paymentMethod: 'paypal-integration'};
		}

		const skuOptionValue = cartOptions?.isTrialSKU
			? SkuLicenseUsageTypeValue.TRIAL
			: SkuLicenseUsageTypeValue.STANDARD;

		return {
			...baseCart,
			cartItems: super.getCartItems(
				getSkuByOptionValueKey(this.product, skuOptionValue)?.id
			),
		};
	}

	public async getNextStepsLink(cart: Cart) {
		const callback = `${window.location.origin}${getSiteURL()}/next-steps?orderId=${cart.id}`;

		const url = await headlessCommerceDeliveryCart.getPaymentMethodURL(
			cart.id,
			callback
		);

		return url || callback;
	}

	static getOrderTypeExternalReferenceCode(product: DeliveryProduct) {
		if (isCloudProduct(product)) {
			return ORDER_TYPES.CLOUDAPP;
		}

		return ORDER_TYPES.DXPAPP;
	}
}
