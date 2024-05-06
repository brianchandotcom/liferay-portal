/* eslint-disable no-console */
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {
	SolutionInitialState,
	SolutionTypes,
	useSolutionContext,
} from '../../../context/SolutionContext';
import {
	PRODUCT_SPECIFICATION_KEY,
	PRODUCT_WORKFLOW_STATUS_CODE,
} from '../../../enums/Product';
import {ProductVocabulary} from '../../../enums/ProductVocabulary';
import i18n from '../../../i18n';
import {Liferay} from '../../../liferay/liferay';
import headlessCommerceAdminCatalogImpl from '../../../services/rest/HeadlessCommerceAdminCatalog';
import {base64ToText, fileToBase64} from '../../../utils/file';

const usePublishSolutionSubmission = (
	context: SolutionInitialState,
	dispatch: ReturnType<typeof useSolutionContext>[1]
) => {
	const syncProfile = async () => {
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

		if (_product) {
			await headlessCommerceAdminCatalogImpl.updateProduct(
				_product.productId as number,
				{
					categories: productCategories,
					description: {en_US: description},
					name: {en_US: name},
					productStatus: PRODUCT_WORKFLOW_STATUS_CODE.DRAFT,
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
			}
		);

		product.productSpecifications = [];

		await headlessCommerceAdminCatalogImpl.createProductImageByExternalReferenceCodeAxios(
			product.externalReferenceCode,
			{
				attachment: base64ToText(
					(await fileToBase64(file.file)) as string
				),
				galleryEnabled: false,
				neverExpire: true,
				priority: 0,
				tags: ['app icon'],
				title: {
					en_US: file.fileName,
				},
			},
			console.info
		);

		dispatch({payload: product, type: SolutionTypes.SET_PRODUCT});

		return product;
	};

	const syncSolutionHeader = async (product: Product) => {
		const {
			header: {description, headerImages, headerVideo, radioValue, title},
		} = context;

		const headerVideoDescription = '';

		const {productId, productSpecifications = []} = product;

		const _updateSpecification = async (
			specificationKey: PRODUCT_SPECIFICATION_KEY,
			value: string
		) => {
			const specification = productSpecifications.find(
				(productSpecification) =>
					productSpecification.specificationKey === specificationKey
			);

			if (specification && specification.value.en_US === value) {

				// No need to update the specification if the value is equal.

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
			}
			else {
				productSpecifications.push(result);
			}
		};

		await _updateSpecification(
			PRODUCT_SPECIFICATION_KEY.SOLUTION_HEADER_DESCRIPTION,
			description
		);

		await _updateSpecification(
			PRODUCT_SPECIFICATION_KEY.SOLUTION_HEADER_TITLE,
			title
		);

		if (radioValue === 'embed-video-url') {
			if (headerVideoDescription) {
				await _updateSpecification(
					PRODUCT_SPECIFICATION_KEY.SOLUTION_HEADER_VIDEO_DESCRIPTION,
					headerVideoDescription
				);
			}

			await _updateSpecification(
				PRODUCT_SPECIFICATION_KEY.SOLUTION_HEADER_VIDEO_URL,
				headerVideo
			);

			return;
		}

		// Process Upload Images, priority starts in 1 to not conflict with
		// the app icon defined as priority 0

		let priority = 0;
		for (const image of headerImages) {
			priority++;

			if (image.uploaded) {
				continue;
			}

			let attachment = '';
			const base64 = await fileToBase64(image.file);

			if (base64 && typeof base64 === 'string') {
				attachment = base64.split(',').at(-1) as string;
			}

			await headlessCommerceAdminCatalogImpl.createProductImageByExternalReferenceCodeAxios(
				product.externalReferenceCode,
				{
					attachment,
					galleryEnabled: false,
					neverExpire: true,
					priority,
					tags: ['solution-header'],
					title: {
						en_US: image.imageDescription || image.file.name,
					},
				},
				console.info
			);

			image.uploaded = true;
		}
	};

	const onSave = async () => {
		console.log(context);

		const product = await syncProfile();

		await syncSolutionHeader(product);
	};

	const onSaveAsDraft = async () => {
		await onSave();

		Liferay.Util.openToast({
			message: i18n.sub('x-saved-as-a-draft-successfully', [
				context.profile.name,
			]),
			title: '',
			type: 'info',
		});
	};

	return {onSaveAsDraft};
};

export default usePublishSolutionSubmission;
