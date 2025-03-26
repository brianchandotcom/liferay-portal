/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {ProductVocabulary} from '../enums/Product';

export class MarketplaceTaxonomyVocabularies {
	constructor(private taxonomyVocabularies: TaxonomyVocabulary[]) {}

	getVocabulary(vocabulary: ProductVocabulary) {
		return this.taxonomyVocabularies.find(({name}) => name === vocabulary);
	}

	getVocabularyCategories(vocabulary: ProductVocabulary) {
		return (
			this.getVocabulary(vocabulary)?.taxonomyCategories?.items ?? []
		).sort((a, b) => a.name.localeCompare(b.name));
	}

	getVocabularyCategory(vocabulary: ProductVocabulary, category: string) {
		return this.taxonomyVocabularies
			.find(({name}) => name === vocabulary)
			?.taxonomyCategories?.items?.find(({name}) => category === name);
	}
}
