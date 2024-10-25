/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Dispatch} from 'react';
import {useLocation, useNavigate, useParams} from 'react-router-dom';

import {UploadedFile} from '../../../components/FileList/FileList';
import {
	AppActions,
	SolutionInitialState,
	SolutionTypes,
} from '../../../context/SolutionContext';
import {
	PRODUCT_SPECIFICATION_KEY,
	PRODUCT_TAGS,
	PRODUCT_WORKFLOW_STATUS_CODE,
} from '../../../enums/Product';
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

const usePublishSolutionSubmission = (
	context: SolutionInitialState,
	dispatch: Dispatch<AppActions>
) => {
	const {productId} = useParams();
	const location = useLocation();
	const navigate = useNavigate();

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
		).filter(({label}: any) => label === 'Solution');

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
						tags: [PRODUCT_TAGS.SOLUTION_PROFILE_APP_ICON],
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

		dispatch({payload: product, type: SolutionTypes.SET_PRODUCT});

		return product;
	};

	const syncSolutionHeader = async (product: Product) => {
		const {
			header: {contentType, description, title},
		} = context;

		await Promise.all([
			updateSpecification(
				product,
				PRODUCT_SPECIFICATION_KEY.SOLUTION_HEADER_DESCRIPTION,
				description
			),
			updateSpecification(
				product,
				PRODUCT_SPECIFICATION_KEY.SOLUTION_HEADER_TITLE,
				title
			),
		]);

		if (contentType.type === 'embed-video-url') {
			if (contentType.content?.headerVideoDescription) {
				await updateSpecification(
					product,
					PRODUCT_SPECIFICATION_KEY.SOLUTION_HEADER_VIDEO_DESCRIPTION,
					contentType.content.headerVideoDescription
				);
			}

			await updateSpecification(
				product,
				PRODUCT_SPECIFICATION_KEY.SOLUTION_HEADER_VIDEO_URL,
				contentType.content.headerVideoUrl as string
			);

			return;
		}

		const headerImages = contentType.content?.headerImages ?? [];

		// Process Upload Images, priority starts in 1 to not conflict with
		// the app icon defined as priority 0

		await addOrUpdateImages(
			headerImages,
			PRODUCT_TAGS.SOLUTION_HEADER,
			product,
			0
		);
	};

	const syncCompanyProfileAndContactUs = async (product: Product) => {
		const {
			company: {description, email, phone, website},
			contactUs,
		} = context;

		await Promise.all(
			[
				[
					PRODUCT_SPECIFICATION_KEY.SOLUTION_COMPANY_DESCRIPTION,
					description,
				],
				[PRODUCT_SPECIFICATION_KEY.SOLUTION_COMPANY_EMAIL, email],
				[PRODUCT_SPECIFICATION_KEY.SOLUTION_COMPANY_PHONE, phone],
				[PRODUCT_SPECIFICATION_KEY.SOLUTION_COMPANY_WEBSITE, website],
				[PRODUCT_SPECIFICATION_KEY.SOLUTION_CONTACT_EMAIL, contactUs],
			].map(([specificationKey, value]) =>
				updateSpecification(
					product,
					specificationKey as PRODUCT_SPECIFICATION_KEY,
					value
				)
			)
		);
	};

	const syncBlockDetails = async (product: Product) => {
		const blocks = [...context.details];

		for (const block of blocks) {
			if (block.type !== 'text-images-block') {
				continue;
			}

			const files = block.content.files;

			await addOrUpdateImages(
				files,
				PRODUCT_TAGS.SOLUTION_DETAILS,
				product,
				context.header.contentType.type === 'upload-images'
					? context.header.contentType.content.headerImages.length
					: 0
			);
		}

		const newBlocks = blocks.map((block) => {
			if (block.type === 'text-images-block') {
				return {
					...block,
					content: {
						...block.content,
						files: block.content?.files.map(({id}) => id),
					},
				};
			}

			return block;
		});

		await updateSpecification(
			product,
			PRODUCT_SPECIFICATION_KEY.SOLUTION_DETAILS_BLOCKS,
			JSON.stringify(newBlocks)
		);
	};

	const deleteReferences = async () => {
		const imagesToDelete = context.references.imagesToDelete;

		for (const externalReferenceCode of imagesToDelete) {
			try {
				await headlessCommerceAdminCatalogImpl.deleteAttachmentByExternalReferenceCode(
					externalReferenceCode
				);
			}
			catch {}
		}
	};

	const onSaveSolution = async (config: ProductConfig) => {
		dispatch({payload: true, type: SolutionTypes.SET_LOADING});

		let product;

		try {
			product = await syncProfile(config);

			for (const sync of [
				deleteReferences,
				syncSolutionHeader,
				syncCompanyProfileAndContactUs,
				syncBlockDetails,
			]) {
				await sync(product);
			}
		}
		catch (error) {
			console.error(error);
		}

		dispatch({payload: false, type: SolutionTypes.SET_LOADING});

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

export default usePublishSolutionSubmission;
