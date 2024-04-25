/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {UploadedImage} from '../../components/FileList/FileList';
import {Liferay} from '../../liferay/liferay';
import fetcher from '../fetcher';

class HeadlessCommerceAdminCatalog {
	async addOrUpdateProductImageByExternalReferenceCode(
		externalReferenceCode: string,
		image: UploadedImage
	) {
		return fetcher.post(
			`/o/headless-commerce-admin-catalog/v1.0/products/by-externalReferenceCode/${externalReferenceCode}/images`,
			image
		);
	}

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

	async createProductSpecification(
		productId: number | string,
		productSpecification: ProductSpecification
	) {
		return fetcher.post(
			`/o/headless-commerce-admin-catalog/v1.0/products/${productId}/productSpecifications`,
			productSpecification
		);
	}

	async deleteProduct(productId: string | number) {
		return fetcher.delete(
			`/o/headless-commerce-admin-catalog/v1.0/products/${productId}`
		);
	}

	async getCatalog(
		catalogId: string | number,
		searchParams = new URLSearchParams()
	) {
		return fetcher(
			`/o/headless-commerce-admin-catalog/v1.0/catalog/${catalogId}?${searchParams.toString()}`
		);
	}

	async getCatalogs(searchParams = new URLSearchParams()) {
		return fetcher<APIResponse<Catalog>>(
			`/o/headless-commerce-admin-catalog/v1.0/catalogs?${searchParams.toString()}`
		);
	}

	async getSpecifications(searchParams = new URLSearchParams()) {
		return fetcher<APIResponse>(
			`/o/headless-commerce-admin-catalog/v1.0/specifications?${searchParams}`
		);
	}

	async getProduct(
		productId: string | number,
		searchParams = new URLSearchParams()
	) {
		return fetcher(
			`/o/headless-commerce-admin-catalog/v1.0/products/${productId}?${searchParams.toString()}`
		);
	}

	async getProductByExternalReferenceCode(
		externalReferenceCode: string,
		searchParams = new URLSearchParams()
	): Promise<Product> {
		return fetcher(
			`/o/headless-commerce-admin-catalog/v1.0/products/by-externalReferenceCode/${externalReferenceCode}?${searchParams.toString()}`
		);
	}

	async getProducts(searchParams = new URLSearchParams()) {
		return fetcher(
			`/o/headless-commerce-admin-catalog/v1.0/products?${searchParams.toString()}`
		);
	}

	async getProductSkus(productId: string | number) {
		return fetcher<APIResponse<SKU>>(
			`/o/headless-commerce-admin-catalog/v1.0/products/${productId}/skus`
		);
	}

	async getProductSpecifications(productId: string | number) {
		const response = await fetcher(
			`/o/headless-commerce-admin-catalog/v1.0/products/${productId}/productSpecifications`
		);

		return (response?.items ?? []) as ProductSpecification[];
	}

	async updateProductByExternalReferenceCode(
		externalReferenceCode: string,
		body: unknown
	) {
		return fetcher.patch(
			`/o/headless-commerce-admin-catalog/v1.0/products/by-externalReferenceCode/${externalReferenceCode}`,
			body
		);
	}

	async getMarketplaceVocabularies() {
		return fetcher(
			`/o/headless-admin-taxonomy/v1.0/sites/${Liferay.ThemeDisplay.getCompanyGroupId()}/taxonomy-vocabularies`
		);
	}

	async getMarketplaceCategories(
		vocabularyId: number,
		searchParams = new URLSearchParams()
	) {
		return fetcher(
			`/o/headless-admin-taxonomy/v1.0/taxonomy-vocabularies/${vocabularyId}/taxonomy-categories?${searchParams.toString()}`
		);
	}

	async updateProductSpecification(
		id: number | string,
		productSpecification: ProductSpecification
	) {
		return fetcher.patch(
			`/o/headless-commerce-admin-catalog/v1.0/productSpecifications/${id}`,
			productSpecification
		);
	}
}

const HeadlessCommerceAdminCatalogImpl = new HeadlessCommerceAdminCatalog();

export default HeadlessCommerceAdminCatalogImpl;
