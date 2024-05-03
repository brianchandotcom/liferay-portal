/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {
	SolutionInitialState,
	SolutionTypes,
	useSolutionContext,
} from '../../../context/SolutionContext';
import i18n from '../../../i18n';
import {Liferay} from '../../../liferay/liferay';
import headlessCommerceAdminCatalogImpl from '../../../services/rest/HeadlessCommerceAdminCatalog';

const usePublishSolutionSubmission = (
	context: SolutionInitialState,
	dispatch: ReturnType<typeof useSolutionContext>[1]
) => {
	const createOrUpdateProduct = async () => {
		const {
			_product,
			catalogId,
			profile: {categories, description, name, tags},
		} = context;

		let product;

		const productCategories = [...categories, ...tags].map((category) => ({
			id: category.value,
			name: category.label,
		}));

		if (!_product) {
			product = await headlessCommerceAdminCatalogImpl.createVirtualProduct(
				{
					catalogId,
					categories: productCategories,
					description,
					name,
					productChannels: [],
				}
			);

			dispatch({payload: product, type: SolutionTypes.SET_PRODUCT});
		} else {
			await headlessCommerceAdminCatalogImpl.updateProduct(
				_product.productId as number,
				{
					categories: productCategories,
					description: {en_US: description},
					name: {en_US: name},
				}
			);
		}

		alert('Product Updated...');
	};

	const onSave = async () => {
		await createOrUpdateProduct();
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
