/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

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
			vocabulary.includes(category.vocabulary.replaceAll(' ', '-'))
		)
		.map(({name}) => name);
}
