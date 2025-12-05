/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import useSWR from 'swr';

import {useMarketplaceContext} from '../../context/MarketplaceContext';
import HeadlessAdminTaxonomy from '../../services/rest/HeadlessAdminTaxonomy';

const useGetVocabulariesAndCategories = (vocabulariesName: string[]) => {
	const {properties} = useMarketplaceContext();

	return useSWR({key: 'vocabularies', vocabulariesName}, async () => {
		const fn = properties.useSiteTaxonomyVocabularyQuery
			? HeadlessAdminTaxonomy.getSiteTaxonomyVocabulariesGraphQL
			: HeadlessAdminTaxonomy.getTaxonomyVocabulariesGraphQL;

		const response = await fn();

		const vocabularies: Record<
			string,
			{
				categories: {label: string; value: unknown}[];
				id: unknown;
				name: string;
			}
		> = {};

		for (const vocabularyName of vocabulariesName) {
			const vocabulary = response.items.find(
				({name}) => name === vocabularyName
			);

			if (!vocabulary) {
				continue;
			}

			vocabularies[vocabularyName] = {
				...vocabulary,
				categories: vocabulary.taxonomyCategories.items.map(
					(taxonomyCategory) => ({
						label: taxonomyCategory.name,
						value: taxonomyCategory.id,
					})
				),
			};
		}

		return vocabularies;
	});
};

export {useGetVocabulariesAndCategories};
