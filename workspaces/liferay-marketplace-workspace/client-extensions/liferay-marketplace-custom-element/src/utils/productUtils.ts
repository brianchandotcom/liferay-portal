/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import productIconFallback from '../assets/icons/purchased_app_icon.svg';
import productImageFallback from '../assets/images/app_placeholder.png';
import {PRODUCT_IMAGE_FALLBACK_CATEGORIES} from '../enums/Product';
import i18n from '../i18n';

export function getProductFallback(): DeliveryProduct {
	return {
		attachments: [],
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
	};
}

export function getProductImageFallback(
	type: PRODUCT_IMAGE_FALLBACK_CATEGORIES
) {
	const productImagesFallback = {
		[PRODUCT_IMAGE_FALLBACK_CATEGORIES.PRODUCT_IMAGE]: productImageFallback,
		[PRODUCT_IMAGE_FALLBACK_CATEGORIES.PRODUCT_ICON]: productIconFallback,
	};

	return productImagesFallback[type] || '';
}

export function getSpecificationByKey(key: string, product: DeliveryProduct) {
	return product?.productSpecifications?.find(
		({specificationKey}) => specificationKey === key
	);
}

export function isCloudProduct(product?: DeliveryProduct) {
	return (
		product?.productSpecifications?.some(
			({specificationKey, value}) =>
				specificationKey === 'type' && value === 'cloud'
		) ?? false
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
