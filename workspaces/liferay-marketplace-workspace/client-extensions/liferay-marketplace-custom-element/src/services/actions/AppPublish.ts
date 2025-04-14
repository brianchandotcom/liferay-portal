/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {NewAppInitialState} from '../../context/NewAppContext';
import {
	ProductLicense,
	ProductSpecificationKey,
	ProductTags,
	ProductType,
	ProductVocabulary,
	ProductWorkflowStatusCode,
} from '../../enums/Product';
import {base64ToText, fileToBase64} from '../../utils/file';
import HeadlessCommerceAdminCatalogImpl from '../rest/HeadlessCommerceAdminCatalog';
import BaseAppPublish from './BaseAppPublish';

type ProductConfig = {
	isDraft: boolean;
};

export default class AppPublish extends BaseAppPublish {
	constructor(private context: NewAppInitialState) {
		super();
	}

	async syncProfile(config: ProductConfig) {
		const {
			_product,
			catalogId,
			profile: {categories, description, file, name, tags},
			references: {vocabulariesAndCategories},
		} = this.context;

		const productTypeCategories = (
			vocabulariesAndCategories[ProductVocabulary.PRODUCT_TYPE]
				?.categories ?? []
		).filter(({label}: any) => label === 'App');

		const productCategories = [
			...categories,
			...productTypeCategories,
			...tags,
		].map((category) => ({
			id: category.value,
			name: category.label,
		}));

		const productStatus = config.isDraft
			? ProductWorkflowStatusCode.DRAFT
			: ProductWorkflowStatusCode.PENDING;

		if (_product) {
			if (file && (!file?.uploaded || file?.changed)) {
				await HeadlessCommerceAdminCatalogImpl.createProductImageByExternalReferenceCodeAxios(
					_product.externalReferenceCode,
					{
						attachment: base64ToText(
							(await fileToBase64(file.file)) as string
						),
						galleryEnabled: false,
						neverExpire: true,
						priority: 0,
						tags: [ProductTags.APP_ICON],
						title: {
							en_US: file.fileName,
						},
					}
				);
			}

			await HeadlessCommerceAdminCatalogImpl.updateProduct(
				_product.productId as number,
				{
					categories: productCategories,
					description: {en_US: description},
					name: {en_US: name},
					productStatus,
					workflowStatusInfo: productStatus,
				}
			);

			return _product;
		}

		const product =
			await HeadlessCommerceAdminCatalogImpl.createVirtualProduct({
				catalogId,
				categories: productCategories,
				description,
				name,
				productStatus,
				workflowStatusInfo: productStatus,
			});

		product.productSpecifications = [];

		if (file.file) {
			await HeadlessCommerceAdminCatalogImpl.createProductImageByExternalReferenceCodeAxios(
				product.externalReferenceCode,
				{
					attachment: base64ToText(
						(await fileToBase64(file.file)) as string
					),
					galleryEnabled: false,
					neverExpire: true,
					priority: 0,
					tags: [ProductTags.APP_ICON],
					title: {
						en_US: file.fileName,
					},
				}
			);
		}

		return product;
	}

	async syncStorefront(product: Product) {
		const {
			storefront: {images},
		} = this.context;

		// Process Upload Images, priority starts in 1 to not conflict with
		// the app icon defined as priority 0

		await AppPublish.addOrUpdateImages(
			images,
			ProductTags.APP_ICON,
			product,
			1
		);
	}

	async syncBuild(product: Product, config: ProductConfig) {
		const {
			build: {appType, compatibleOffering, resourceRequirements},
		} = this.context;

		const specifications = [
			{
				key: ProductSpecificationKey.APP_TYPE,
				value: appType,
			},
		];

		if (appType === ProductType.CLOUD) {
			const resourceRequirementSpecifications = [
				{
					key: ProductSpecificationKey.APP_BUILD_NUMBER_OF_CPUS,
					value: resourceRequirements.cpu as string,
				},
				{
					key: ProductSpecificationKey.APP_BUILD_RAM_IN_GBS,
					value: resourceRequirements.ram as string,
				},
			];

			specifications.push(...(resourceRequirementSpecifications as any));
		}

		await BaseAppPublish.updateSpecifications(product, specifications);

		const {
			[ProductVocabulary.LIFERAY_PLATFORM_OFFERING]:
				compatibleOfferingVocabulary,
		} = this.context.references.vocabulariesAndCategories;

		const compatibleOfferingCategories =
			compatibleOfferingVocabulary.categories ?? [];

		const compatibleOfferings = compatibleOfferingCategories.filter(
			({label}: {label: string}) => compatibleOffering.includes(label)
		);

		const productStatus = config.isDraft
			? ProductWorkflowStatusCode.DRAFT
			: ProductWorkflowStatusCode.PENDING;

		await HeadlessCommerceAdminCatalogImpl.updateProduct(
			product.productId,
			{
				categories: [...product.categories, ...compatibleOfferings],
				productStatus,
				workflowStatusInfo: productStatus,
			}
		);
	}

	private async createProductSKUs(product: Product) {
		const {_product} = this.context;

		if (!_product?.productOptions) {
			const _productOptions =
				await HeadlessCommerceAdminCatalogImpl.getProductOptions(
					product.productId
				);

			(_product as any).productOptions = _productOptions.items;
		}

		const [productOption] = _product?.productOptions ?? [];

		for (const productOptionValue of productOption.productOptionValues) {
			await HeadlessCommerceAdminCatalogImpl.createProductSKU(
				{
					published: true,
					purchasable: true,
					sku: productOptionValue.name.en_US,
					skuOptions: [
						{
							key: productOption.id,
							value: productOptionValue.id,
						},
					],
				},
				product.productId
			);
		}
	}

	private async createProductOption(product: Product) {
		const {
			_product,
			build: {appType},
		} = this.context;

		if (_product?.productOptions?.length) {
			return _product?.productOptions[0];
		}

		const {items: options} =
			await HeadlessCommerceAdminCatalogImpl.getOptions();

		const optionsTypes = {
			[ProductType.CLOUD]: ProductLicense.CLOUD,
			[ProductType.DXP]: ProductLicense.DXP,
		} as const;

		const option = options.find(
			(option) =>
				option.key === (optionsTypes as any)[appType] ||
				ProductLicense.BASE
		);

		if (!option) {
			return;
		}

		(option as any).optionId = option?.id;

		delete (option as any).actions;
		delete (option as any).externalReferenceCode;

		const {
			items: [productOption],
		} = await HeadlessCommerceAdminCatalogImpl.createProductOption(
			[option],
			product.productId
		);

		if (!product.productOptions) {
			product.productOptions = [];
		}

		product?.productOptions?.push(productOption);

		return productOption;
	}

	async syncLicensing(product: Product) {
		const {
			licensing: {licenseType},
		} = this.context;

		if (!licenseType) {
			return;
		}

		await this.createProductOption(product);
		await this.createProductSKUs(product);

		await BaseAppPublish.updateSpecification(
			product,
			ProductSpecificationKey.APP_LICENSING_TYPE,
			licenseType
		);
	}

	async syncPricing(product: Product) {
		const {
			pricing: {priceModel},
		} = this.context;

		await BaseAppPublish.updateSpecification(
			product,
			ProductSpecificationKey.APP_PRICING_MODEL,
			priceModel
		);
	}

	async syncSupport(product: Product) {
		const {support} = this.context;

		await BaseAppPublish.updateSpecifications(product, [
			{
				key: ProductSpecificationKey.APP_SUPPORT_USAGE_TERMS_URL,
				value: support.appUsageTermsURL,
			},

			{
				key: ProductSpecificationKey.APP_SUPPORT_DOCUMENTATION_URL,
				value: support.documentationURL,
			},

			{
				key: ProductSpecificationKey.APP_SUPPORT_EMAIL,
				value: support.email,
			},

			{
				key: ProductSpecificationKey.APP_SUPPORT_INSTALLATION_GUIDE_URL,
				value: support.installationGuideURL,
			},

			{
				key: ProductSpecificationKey.APP_SUPPORT_PHONE,
				value: support.phone,
			},

			{
				key: ProductSpecificationKey.APP_SUPPORT_PUBLISHER_WEBSITE_URL,
				value: support.publisherWebsiteURL,
			},

			{
				key: ProductSpecificationKey.APP_SUPPORT_URL,
				value: support.url,
			},
		]);
	}

	async syncVersion(product: Product) {
		const {
			version: {notes, version},
		} = this.context;

		await BaseAppPublish.updateSpecifications(product, [
			{
				key: ProductSpecificationKey.APP_VERSION,
				value: version,
			},
			{
				key: ProductSpecificationKey.APP_VERSION_NOTES,
				value: notes,
			},
		]);
	}

	public async sync(config: ProductConfig) {
		let product;

		try {
			product = await this.syncProfile(config);

			if (!this.context._product) {
				this.context._product = product;
			}

			await AppPublish.deleteReferences(
				this.context.references.imagesToDelete
			);

			for (const sync of [
				this.syncBuild.bind(this),
				this.syncStorefront.bind(this),
				this.syncVersion.bind(this),
				this.syncPricing.bind(this),
				this.syncLicensing.bind(this),
				this.syncSupport.bind(this),
			]) {
				this.context._product = product;

				await sync(product, config);
			}
		}
		catch (error) {
			console.error(error);
		}

		return product;
	}
}
