/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Dispatch} from 'react';
import {useLocation, useNavigate, useParams} from 'react-router-dom';

import {
	PRODUCT_TAGS,
	PRODUCT_WORKFLOW_STATUS_CODE,
} from '../../../enums/Product';
import {ProductVocabulary} from '../../../enums/ProductVocabulary';
import i18n from '../../../i18n';
import {Liferay} from '../../../liferay/liferay';
import headlessCommerceAdminCatalogImpl from '../../../services/rest/HeadlessCommerceAdminCatalog';
import {base64ToText, fileToBase64} from '../../../utils/file';
import { NewAppInitialState, NewAppTypes } from '../../../context/NewAppContext';

type ProductConfig = {
	isDraft: boolean;
};

const usePublishAppSubmission = (
	context: NewAppInitialState,
	dispatch: Dispatch<any>
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

		dispatch({payload: product, type: NewAppTypes.SET_PRODUCT});

		return product;
	};

	const onSaveApp = async (config: ProductConfig) => {
		dispatch({payload: true, type: NewAppTypes.SET_LOADING});

		let product;

		try {
			product = await syncProfile(config);

		}
		catch (error) {
			console.error(error);
		}

		dispatch({payload: false, type: NewAppTypes.SET_LOADING});

		return product;
	};

	const onSaveAsDraft = async () => {
		const product = await onSaveApp({isDraft: true});

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
		await onSaveApp({isDraft: false});

		Liferay.Util.openToast({
			message: i18n.sub('app-x-submitted', [context.profile.name]),
			title: '',
			type: 'info',
		});
	};

	return {onSave, onSaveAsDraft};
};

export default usePublishAppSubmission;
