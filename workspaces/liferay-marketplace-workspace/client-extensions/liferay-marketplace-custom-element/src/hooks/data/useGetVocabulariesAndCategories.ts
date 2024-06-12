/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import useSWR from 'swr';

import {ProductVocabulary} from '../../enums/ProductVocabulary';
import HeadlessAdminTaxonomyImpl from '../../services/rest/HeadlessAdminTaxonomy';

const useGetVocabulariesAndCategories = (vocabulariesName: string[]) => {
	return useSWR({key: 'vocabularies/', vocabulariesName}, async () => {
		const {items} =
			await HeadlessAdminTaxonomyImpl.getTaxonomyVocabularies();

		const _vocabularies = items.filter((vocabulary: ProductCategories) =>
			vocabulariesName.includes(vocabulary.name as ProductVocabulary)
		);

		const vocabularies: any = {};

		for (const vocabulary of _vocabularies) {
			const categories =
				await HeadlessAdminTaxonomyImpl.getTaxonomyCategories(
					vocabulary.id,
					new URLSearchParams({
						fields: 'id,name',
					})
				);

			vocabularies[vocabulary.name] = {
				...vocabulary,
				categories: categories?.items?.map(
					(category: ProductCategories) => ({
						label: category.name,
						value: category.id,
					})
				),
			};
		}

		return vocabularies;
	});
};

export {useGetVocabulariesAndCategories};
