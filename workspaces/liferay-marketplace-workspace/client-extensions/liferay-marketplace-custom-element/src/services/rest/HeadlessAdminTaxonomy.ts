/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Liferay} from '../../liferay/liferay';
import fetcher from '../fetcher';

export default class HeadlessAdminTaxonomy {
	static async getSiteTaxonomyVocabulariesGraphQL() {
		const response = await fetcher.post<{
			data: {
				headlessAdminTaxonomy_v1_0: {
					taxonomyVocabularies: APIResponse<TaxonomyVocabulary>;
				};
			};
		}>('/o/graphql', {
			query: `{
				headlessAdminTaxonomy_v1_0 {
					taxonomyVocabularies: siteTaxonomyVocabularies(siteKey: "${Liferay.ThemeDisplay.getScopeGroupId()}") {
						items {
							id
							name
							taxonomyCategories {
								items {
									id
									name
								}
							}
						}
					}
				}
			}`,
		});

		return response.data?.headlessAdminTaxonomy_v1_0?.taxonomyVocabularies;
	}

	static async getTaxonomyVocabulariesGraphQL() {
		const response = await fetcher.post<{
			data: {
				headlessAdminTaxonomy_v1_0: {
					taxonomyVocabularies: APIResponse<TaxonomyVocabulary>;
				};
			};
		}>('/o/graphql', {
			query: `{
				headlessAdminTaxonomy_v1_0 {
					taxonomyVocabularies(siteKey: "${Liferay.ThemeDisplay.getScopeGroupId()}") {
						items {
							id
							name
							taxonomyCategories {
								items {
									id
									name
								}
							}
						}
					}
				}
			}`,
		});

		return response.data?.headlessAdminTaxonomy_v1_0?.taxonomyVocabularies;
	}
}
