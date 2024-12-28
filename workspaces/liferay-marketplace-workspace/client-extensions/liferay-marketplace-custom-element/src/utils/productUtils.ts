/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import productIconFallback from '../assets/icons/purchased_app_icon.svg';
import productImageFallback from '../assets/images/app_placeholder.png';
import {
	ProductImageFallbackCategories,
	ProductSpecificationKey,
	ProductType,
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
	key: PRODUCT_SPECIFICATION_KEY,
	product: DeliveryProduct
) {
	return product?.productSpecifications?.find(
		({specificationKey}) => specificationKey === key
	);
}

export function getProductSpecificationValue<T = string>(
	key: PRODUCT_SPECIFICATION_KEY,
	product: DeliveryProduct,
	value?: T
) {
	return getProductSpecification(key, product)?.value || (value as T);
}

export function isCloudProduct(product?: DeliveryProduct) {
	return (
		product?.productSpecifications?.some(
			({specificationKey, value}) =>
				specificationKey === PRODUCT_SPECIFICATION_KEY.APP_TYPE &&
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
	skuOptionValueKey: SkuLicenseUsageTypeValue
) {
	return product.skus.find(
		({purchasable, skuOptions}) =>
			purchasable &&
			skuOptions.find(
				(skuOption) =>
					[
						SkuLicenseUsageType.CLOUD,
						SkuLicenseUsageType.DXP,
					].includes(skuOption.skuOptionKey as SkuLicenseUsageType) &&
					skuOption.skuOptionValueKey === skuOptionValueKey
			)
	);
}

export function getProductPrice(product: DeliveryProduct) {
	const {isFreeApp} = getProductPriceModel(product);

	if (isFreeApp) {
		return 'Free';
	}

	const standardSku = getSkuByOptionValueKey(
		product,
		SkuLicenseUsageTypeValue.STANDARD
	);

	const standardPrice = standardSku?.price?.priceFormatted || '';

	const trialSku = getSkuByOptionValueKey(
		product,
		SkuLicenseUsageTypeValue.TRIAL
	);

	if (trialSku) {
		return `30-day trial or ${standardPrice}`;
	}

	return standardPrice;
}

export function getProductType(product: DeliveryProduct) {
	const specification = getSpecificationByKey(
		ProductSpecificationKey.APP_TYPE,
		product
	);

	return {
		isCloud: specification?.value === ProductType.CLOUD,
		isDXP: specification?.value === ProductType.DXP,
	};
}

export function getLicenseTagText(product: DeliveryProduct) {
	const licenseTypeSpecification = getValueFromDeliverySpecifications(
		product.productSpecifications,
		PRODUCT_SPECIFICATION_KEY.APP_LICENSING_TYPE
	).toLowerCase();

	return licenseTypeSpecification === PRODUCT_LICENSE_TYPE.Perpetual
		? 'One-Time'
		: 'Annually';
}

export function getProductPriceModel(product: DeliveryProduct) {
	const priceModel = getProductSpecificationValue(
		PRODUCT_SPECIFICATION_KEY.APP_PRICING_MODEL,
		product
	);

	return {
		isFreeApp: priceModel === 'free',
		isPaidApp: priceModel === 'paid',
		priceModel,
	};
}
