/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {gql} from '@apollo/client';

export default gql`
	query CustomAssetsList(
		$channelId: String
		$keywords: String
		$size: Int!
		$sort: Sort!
		$start: Int!
	) {
		dashboards(
			channelId: $channelId
			keywords: $keywords
			size: $size
			sort: $sort
			start: $start
		) {
			dashboards {
				id
				assetId
				assetTitle
				createDate
				modifiedByUserName
				modifiedDate
			}
			total
		}
	}
`;
