/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import fetcher from '../fetcher';

class HeadlessCommerceAdminCatalog {
	async createProduct({
		appCategories,
		appDescription,
		appName,
		catalogId,
		productChannels,
	}: {
		appCategories: Categories[];
		appDescription: string;
		appName: string;
		catalogId: number;
		productChannels?: Partial<Channel>[];
	}) {
		return fetcher.post(
			`/o/headless-commerce-admin-catalog/v1.0/products`,
			{
				active: true,
				catalogId,
				categories: appCategories,
				description: {en_US: appDescription},
				name: {en_US: appName},
				productChannels,
				productConfiguration: {
					allowBackOrder: true,
					maxOrderQuantity: 1,
				},
				productStatus: 2,
				productType: 'virtual',
			}
		);
	}

	async getProductSpecifications(productId: string | number) {
		const response = await fetcher(
			`/o/headless-commerce-admin-catalog/v1.0/products/${productId}/productSpecifications`
		);

		return (response?.items ?? []) as ProductSpecification[];
	}

	async getProduct(productId: string) {
		return fetcher(
			`/o/headless-commerce-admin-catalog/v1.0/products/${productId}`
		);
	}

	async getProducts(nestedFields?: string) {
		let url = `/o/headless-commerce-admin-catalog/v1.0/products?pageSize=-1`;

		if (nestedFields) {
			url = url + `&nestedFields=${nestedFields}`;
		}

		return fetcher(url);
	}

	async updateProductByExternalReferenceCode(
		externalReferenceCode: string,
		{
			description,
			name,
		}: {
			description: string;
			name: string;
		}
	) {
		return fetcher.patch(
			`/o/headless-commerce-admin-catalog/v1.0/products/by-externalReferenceCode/${externalReferenceCode}`,
			{
				description: {en_US: description},
				name: {en_US: name},
			}
		);
	}
}

export {HeadlessCommerceAdminCatalog};

const HeadlessCommerceAdminCatalogImpl = new HeadlessCommerceAdminCatalog();

export default HeadlessCommerceAdminCatalogImpl;
