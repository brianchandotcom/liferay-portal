/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Dispatch} from 'react';
import {useLocation, useNavigate, useParams} from 'react-router-dom';

import {UploadedFile} from '../../../components/FileList/FileList';
import {
	AppActions,
	NewAppInitialState,
	NewAppTypes,
} from '../../../context/NewAppContext';
import {
	PRODUCT_SPECIFICATION_KEY,
	PRODUCT_TAGS,
	PRODUCT_WORKFLOW_STATUS_CODE,
} from '../../../enums/Product';
import {ProductType} from '../../../enums/ProductType';
import {ProductVocabulary} from '../../../enums/ProductVocabulary';
import i18n from '../../../i18n';
import {Liferay} from '../../../liferay/liferay';
import headlessCommerceAdminCatalogImpl from '../../../services/rest/HeadlessCommerceAdminCatalog';
import {base64ToText, fileToBase64} from '../../../utils/file';

type ProductConfig = {
	isDraft: boolean;
};

const addOrUpdateImages = async (
	images: UploadedFile[],
	tag: string,
	product: Product,
	priorityInitialValue: number
) => {
	let priority = priorityInitialValue;

	for (const image of images) {
		priority++;

		if (!image.changed && image.uploaded) {
			continue;
		}

		const uploadedProductImage = product?.images?.find(
			(uploadedImage) => uploadedImage.externalReferenceCode === image.id
		);

		const imageMetadata = {
			...(uploadedProductImage && {
				fileEntryId: uploadedProductImage.fileEntryId,
				id: uploadedProductImage.id,
			}),
			...(image?.file && {
				attachment: base64ToText(
					(await fileToBase64(image.file)) as string
				),
			}),
			externalReferenceCode: image.id,
			galleryEnabled: false,
			neverExpire: true,
			priority,
			tags: [tag],
			title: {
				en_US: image.imageDescription || image.file.name,
			},
		};

		await headlessCommerceAdminCatalogImpl.createProductImageByExternalReferenceCodeAxios(
			product?.externalReferenceCode,
			imageMetadata,
			(progress) => {
				image.changed = false;
				image.progress = progress;
				image.uploaded = progress === 100;
			}
		);
	}
};

const updateSpecification = async (
	product: Product,
	specificationKey: PRODUCT_SPECIFICATION_KEY,
	value: string
) => {
	const {productId, productSpecifications = []} = product;

	const specification = productSpecifications.find(
		(productSpecification) =>
			productSpecification.specificationKey === specificationKey
	);

	if (
		!value?.trim() ||
		(specification && specification.value.en_US === value)
	) {

		// No need to update the specification if the value is equal
		// the previous value or empty.

		return;
	}

	const fn = specification
		? headlessCommerceAdminCatalogImpl.updateProductSpecification
		: headlessCommerceAdminCatalogImpl.createProductSpecification;

	const result = await fn(
		(specification ? specification.id : productId) as number,
		{
			specificationKey,
			value: {en_US: value},
		}
	);

	if (specification) {
		specification.value.en_US = value;

		return;
	}

	productSpecifications.push(result);
};

const usePublishAppSubmission = (
	context: NewAppInitialState,
	dispatch: Dispatch<AppActions>
) => {
	const {productId} = useParams();
	const location = useLocation();
	const navigate = useNavigate();

	const updateSpecifications = (
		product: Product,
		specifications: {key: PRODUCT_SPECIFICATION_KEY; value: string}[]
	) =>
		Promise.allSettled(
			specifications.map((specification) =>
				updateSpecification(
					product,
					specification.key,
					specification.value
				)
			)
		);

	const deleteReferences = async () => {
		const imagesToDelete = context.references.imagesToDelete;

		for (const externalReferenceCode of imagesToDelete) {
			await headlessCommerceAdminCatalogImpl.deleteAttachmentByExternalReferenceCode(
				externalReferenceCode
			);
		}
	};

	const syncProfile = async (config: ProductConfig) => {
		const {
			_product,
			catalogId,
			profile: {categories, description, file, name, tags},
			references: {vocabulariesAndCategories},
		} = context;

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
			? PRODUCT_WORKFLOW_STATUS_CODE.DRAFT
			: PRODUCT_WORKFLOW_STATUS_CODE.PENDING;

		if (_product) {
			if (file && (!file?.uploaded || file?.changed)) {
				await headlessCommerceAdminCatalogImpl.createProductImageByExternalReferenceCodeAxios(
					_product.externalReferenceCode,
					{
						attachment: base64ToText(
							(await fileToBase64(file.file)) as string
						),
						galleryEnabled: false,
						neverExpire: true,
						priority: 0,
						tags: [PRODUCT_TAGS.APP_ICON],
						title: {
							en_US: file.fileName,
						},
					}
				);
			}

			await headlessCommerceAdminCatalogImpl.updateProduct(
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
			await headlessCommerceAdminCatalogImpl.createVirtualProduct({
				catalogId,
				categories: productCategories,
				description,
				name,
				productStatus,
				workflowStatusInfo: productStatus,
			});

		product.productSpecifications = [];

		if (file.file) {
			await headlessCommerceAdminCatalogImpl.createProductImageByExternalReferenceCodeAxios(
				product.externalReferenceCode,
				{
					attachment: base64ToText(
						(await fileToBase64(file.file)) as string
					),
					galleryEnabled: false,
					neverExpire: true,
					priority: 0,
					tags: [PRODUCT_TAGS.SOLUTION_PROFILE_APP_ICON],
					title: {
						en_US: file.fileName,
					},
				}
			);
		}

		dispatch({payload: product, type: NewAppTypes.SET_PRODUCT});

		return product;
	};

	const syncStorefront = async (product: Product) => {
		const {
			storefront: {images},
		} = context;

		// Process Upload Images, priority starts in 1 to not conflict with
		// the app icon defined as priority 0

		await addOrUpdateImages(images, PRODUCT_TAGS.APP_ICON, product, 1);
	};

	const syncBuild = async (product: Product) => {
		const {
			build: {cloudCompatible, compatibleOffering, resourceRequirements},
		} = context;

		const specifications = [
			{
				key: PRODUCT_SPECIFICATION_KEY.APP_BUILD_CLOUD_COMPATIBLE,
				value: cloudCompatible ? ProductType.CLOUD : ProductType.DXP,
			},
		];

		if (cloudCompatible) {
			specifications.push([
				{
					key: PRODUCT_SPECIFICATION_KEY.APP_BUILD_NUMBER_OF_CPUS,
					value: resourceRequirements.cpu as string,
				},
				{
					key: PRODUCT_SPECIFICATION_KEY.APP_BUILD_RAM_IN_GBS,
					value: resourceRequirements.ram as string,
				},
			] as any);
		}

		await updateSpecifications(product, specifications);

		const {
			[ProductVocabulary.LIFERAY_PLATFORM_OFFERING]:
				compatibleOfferingVocabulary,
		} = context.references.vocabulariesAndCategories;

		const compatibleOfferingCategories =
			compatibleOfferingVocabulary.categories ?? [];

		const compatibleOfferings = compatibleOfferingCategories.filter(
			({label}: {label: string}) => compatibleOffering.includes(label)
		);

		await headlessCommerceAdminCatalogImpl.updateProduct(
			product.productId,
			{
				categories: [...product.categories, ...compatibleOfferings],
			}
		);
	};

	const syncVersion = async (product: Product) => {
		const {
			version: {notes, version},
		} = context;

		await updateSpecifications(product, [
			{
				key: PRODUCT_SPECIFICATION_KEY.APP_VERSION,
				value: version,
			},
			{
				key: PRODUCT_SPECIFICATION_KEY.APP_VERSION_NOTES,
				value: notes,
			},
		]);
	};

	const syncPricing = async (product: Product) => {
		const {
			pricing: {priceModel},
		} = context;

		await updateSpecification(
			product,
			PRODUCT_SPECIFICATION_KEY.APP_PRICING_MODEL,
			priceModel
		);
	};

	const syncLicensing = async (product: Product) => {
		const {
			licensing: {licenseType},
		} = context;

		await updateSpecification(
			product,
			PRODUCT_SPECIFICATION_KEY.APP_LICENSING_TYPE,
			licenseType
		);
	};

	const syncSupport = async (product: Product) => {
		const {support} = context;

		await updateSpecifications(product, [
			{
				key: PRODUCT_SPECIFICATION_KEY.APP_SUPPORT_USAGE_TERMS_URL,
				value: support.appUsageTermsURL,
			},

			{
				key: PRODUCT_SPECIFICATION_KEY.APP_SUPPORT_DOCUMENTATION_URL,
				value: support.documentationURL,
			},

			{
				key: PRODUCT_SPECIFICATION_KEY.APP_SUPPORT_EMAIL,
				value: support.email,
			},

			{
				key: PRODUCT_SPECIFICATION_KEY.APP_SUPPORT_INSTALLATION_GUIDE_URL,
				value: support.installationGuideURL,
			},

			{
				key: PRODUCT_SPECIFICATION_KEY.APP_SUPPORT_PHONE,
				value: support.phone,
			},

			{
				key: PRODUCT_SPECIFICATION_KEY.APP_SUPPORT_PUBLISHER_WEBSITE_URL,
				value: support.publisherWebsiteURL,
			},

			{
				key: PRODUCT_SPECIFICATION_KEY.APP_SUPPORT_URL,
				value: support.url,
			},
		]);
	};

	const onSaveSolution = async (config: ProductConfig) => {
		dispatch({payload: true, type: NewAppTypes.SET_LOADING});

		let product;

		try {
			product = await syncProfile(config);

			for (const sync of [
				deleteReferences,
				syncBuild,
				syncStorefront,
				syncVersion,
				syncPricing,
				syncLicensing,
				syncSupport,
			]) {
				await sync(product);
			}
		}
		catch (error) {
			console.error(error);
		}

		dispatch({payload: false, type: NewAppTypes.SET_LOADING});

		return product;
	};

	const onSaveAsDraft = async () => {
		const product = await onSaveSolution({isDraft: true});

		Liferay.Util.openToast({
			message: i18n.sub('x-saved-as-a-draft-successfully', [
				context.profile.name,
			]),
			title: '',
			type: 'info',
		});

		if (!productId) {
			navigate(
				location.pathname.replace(
					'/publisher/',
					`/${product.productId}/publisher/`
				)
			);
		}
	};

	const onSave = async () => {
		await onSaveSolution({isDraft: false});

		Liferay.Util.openToast({
			message: i18n.sub('solution-x-submitted', [context.profile.name]),
			title: '',
			type: 'info',
		});
	};

	return {onSave, onSaveAsDraft};
};

export default usePublishAppSubmission;
