/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Dispatch} from 'react';

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
import {getTemporaryProductIdForSpefication} from '../../../utils/util';

type ProductConfig = {
	isDraft: boolean;
};

const updateSpecification = async (
	product: Product,
	specificationKey: PRODUCT_SPECIFICATION_KEY,
	value: string
) => {
	const {id, productId, productSpecifications = []} = product;

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

	const _productId = getTemporaryProductIdForSpefication({
		appId: id,
		appProductId: productId,
	});

	const fn = specification
		? headlessCommerceAdminCatalogImpl.updateProductSpecification
		: headlessCommerceAdminCatalogImpl.createProductSpecification;

	const result = await fn(
		(specification ? specification.id : _productId) as number,
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

		const product = await headlessCommerceAdminCatalogImpl.createVirtualProduct(
			{
				catalogId,
				categories: productCategories,
				description,
				name,
				productStatus,
				workflowStatusInfo: productStatus,
			}
		);

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
					tags: [PRODUCT_TAGS.APP_ICON],
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

		let priority = 0;
		for (const headerImage of headerImages) {
			priority++;

			if (headerImage.uploaded) {
				continue;
			}

			await headlessCommerceAdminCatalogImpl.createProductImageByExternalReferenceCodeAxios(
				product.externalReferenceCode,
				{
					attachment: base64ToText(
						(await fileToBase64(headerImage.file)) as string
					),
					galleryEnabled: false,
					neverExpire: true,
					priority,
					tags: [PRODUCT_TAGS.SOLUTION_HEADER],
					title: {
						en_US:
							headerImage.imageDescription ||
							headerImage.file.name,
					},
				},
				(progress) => {
					headerImage.progress = progress;
					headerImage.uploaded = progress === 100;
				}
			);
		}
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

			let priority =
				context.header.contentType.type === 'upload-images'
					? context.header.contentType.content.headerImages.length
					: 0;
			for (const file of files) {
				priority++;

				if (file.uploaded) {
					continue;
				}
				await headlessCommerceAdminCatalogImpl.createProductImageByExternalReferenceCodeAxios(
					product.externalReferenceCode,
					{
						attachment: base64ToText(
							(await fileToBase64(file.file)) as string
						),
						externalReferenceCode: file.id,
						galleryEnabled: false,
						neverExpire: true,
						priority,
						tags: [PRODUCT_TAGS.SOLUTION_DETAILS],
						title: {
							en_US: file.fileName,
						},
					},
					(progress) => {
						file.progress = progress;
						file.uploaded = progress === 100;
					}
				);
			}
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
			await headlessCommerceAdminCatalogImpl.deleteAttachmentByExternalReferenceCode(
				externalReferenceCode
			);
		}
	};

	const onSaveSolution = async (config: ProductConfig) => {
		dispatch({payload: true, type: SolutionTypes.SET_LOADING});

		try {
			const product = await syncProfile(config);

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
		finally {
			dispatch({payload: false, type: SolutionTypes.SET_LOADING});
		}
	};

	const onSaveAsDraft = async () => {
		await onSaveSolution({isDraft: true});

		Liferay.Util.openToast({
			message: i18n.sub('x-saved-as-a-draft-successfully', [
				context.profile.name,
			]),
			title: '',
			type: 'info',
		});
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
