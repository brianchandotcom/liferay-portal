/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import productIconFallback from '../assets/icons/purchased_app_icon.svg';
import productImageFallback from '../assets/images/app_placeholder.png';
import {
	ProductImageFallbackCategories,
	ProductLicense,
	ProductLicenseType,
	ProductSpecificationKey,
	ProductType,
	SkuOptions,
} from '../enums/Product';
import i18n from '../i18n';
import {getValueFromDeliverySpecifications} from './util';

export function getProductFallback(): DeliveryProduct {
	return {
		attachments: [],
		catalogName: '',
		categories: [],
		createDate: '',
		description: i18n.translate('this-product-is-no-longer-available'),
		externalReferenceCode: '--',
		id: 0,
		images: [],
		modifiedDate: '',
		name: i18n.translate('product-unavailable'),
		productId: 0,
		productSpecifications: [],
		productType: i18n.translate('product-unavailable'),
		shortDescription: i18n.translate('this-product-is-no-longer-available'),
		skus: [],
		urlImage: '',
		urls: {en_US: ''},
	};
}

export function getProductImageFallback(type: ProductImageFallbackCategories) {
	const productImagesFallback = {
		[ProductImageFallbackCategories.PRODUCT_IMAGE]: productImageFallback,
		[ProductImageFallbackCategories.PRODUCT_ICON]: productIconFallback,
	};

	return productImagesFallback[type] || '';
}

export function getProductSpecification(
	key: ProductSpecificationKey,
	product: DeliveryProduct
) {
	return product?.productSpecifications?.find(
		({specificationKey}) => specificationKey === key
	);
}

export function getProductSpecificationValue<T = string>(
	key: ProductSpecificationKey,
	product: DeliveryProduct,
	value?: T
) {
	return getProductSpecification(key, product)?.value || (value as T);
}

export function isCloudProduct(product?: DeliveryProduct) {
	return (
		product?.productSpecifications?.some(
			({specificationKey, value}) =>
				specificationKey === ProductSpecificationKey.APP_TYPE &&
				value === ProductType.CLOUD
		) || false
	);
}

export function isTrialSKU(sku: SKU) {
	const skuName = sku.sku.toLowerCase();
	const skuOptions = getNormalizedSKUOptions(sku) || [];

	return (
		skuName.endsWith('ts') ||
		skuName === 'trial' ||
		['trial', 'yes'].some(
			(optionValue) =>
				skuOptions[0]?.value?.toLowerCase() ===
				optionValue.toLowerCase()
		)
	);
}

/**
 * @description Normalize SKU Options, Admin vs Delivery Catalog have different payloads.
 * @param sku
 */
export function getNormalizedSKUOptions(sku: SKU) {
	return sku.skuOptions.map((skuOption) => {
		if ((skuOption as unknown as DeliverySKUOption).skuOptionKey) {
			return {
				key: (skuOption as unknown as DeliverySKUOption).skuOptionKey,
				value: (skuOption as unknown as DeliverySKUOption)
					.skuOptionValueKey,
			};
		}

		return skuOption;
	});
}

export function getProductCategoriesByVocabularyName(
	categories: ProductCategories[],
	vocabulary: string
) {
	return categories
		.filter((category) =>
			vocabulary
				.toLowerCase()
				.includes(
					category.vocabulary.replaceAll(' ', '-').toLowerCase()
				)
		)
		.map(({name}) => name);
}

export function getSkuByOptionValueKey(
	product: DeliveryProduct,
	skuOptionValueKey: SkuOptions
) {
	return product.skus.find(
		({purchasable, skuOptions}) =>
			purchasable &&
			skuOptions.find(
				(skuOption) =>
					[ProductLicense.CLOUD, ProductLicense.DXP].includes(
						skuOption.skuOptionKey as ProductLicense
					) && skuOption.skuOptionValueKey === skuOptionValueKey
			)
	);
}

export function getProductPrice(product: DeliveryProduct) {
	const {isFreeApp} = getProductPriceModel(product);

	if (isFreeApp) {
		return 'Free';
	}

	const standardSku = getSkuByOptionValueKey(product, SkuOptions.STANDARD);

	const standardPrice = standardSku?.price?.priceFormatted || '';

	const trialSku = getSkuByOptionValueKey(product, SkuOptions.TRIAL);

	if (trialSku) {
		return `30-day trial or ${standardPrice}`;
	}

	return standardPrice;
}

export function getProductType(product: DeliveryProduct) {
	const specification = getProductSpecificationValue(
		ProductSpecificationKey.APP_TYPE,
		product
	);

	return {
		isCloud: specification === ProductType.CLOUD,
		isDXP: specification === ProductType.DXP,
	};
}

export function getLicenseTagText(product: DeliveryProduct) {
	const licenseTypeSpecification = getValueFromDeliverySpecifications(
		product.productSpecifications,
		ProductSpecificationKey.APP_LICENSING_TYPE
	).toLowerCase();

	return licenseTypeSpecification === ProductLicenseType.PERPETUAL
		? 'One-Time'
		: 'Annually';
}

export function getProductPriceModel(product: DeliveryProduct) {
	const priceModel = getProductSpecificationValue(
		ProductSpecificationKey.APP_PRICING_MODEL,
		product
	)?.toLowerCase();

	return {
		isFreeApp: priceModel === 'free',
		isPaidApp: priceModel === 'paid',
		priceModel,
	};
}
