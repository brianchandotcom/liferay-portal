/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {gql} from '@apollo/client';

export default gql`
	query RecommendationPageAssets(
		$keywords: String
		$propertyFilters: [PropertyFilter]
		$size: Int!
		$sort: Sort!
		$start: Int!
	) {
		pageAssets(
			keywords: $keywords
			propertyFilters: $propertyFilters
			size: $size
			sort: $sort
			start: $start
		) {
			pageAssets {
				canonicalUrl
				description
				keywords {
					type
					value
				}
				id
				title
				url
			}
			total
		}
	}
`;
