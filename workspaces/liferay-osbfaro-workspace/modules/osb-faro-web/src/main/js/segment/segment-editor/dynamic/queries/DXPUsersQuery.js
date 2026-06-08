/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {gql} from '@apollo/client';

export default gql`
	query UsersList(
		$channelId: String
		$keywords: String
		$size: Int!
		$sort: Sort!
		$start: Int!
	) {
		users(
			channelId: $channelId
			keywords: $keywords
			size: $size
			sort: $sort
			start: $start
		) {
			dxpEntities {
				id
				name
				... on User {
					dataSourceName
					screenName
				}
			}
			total
		}
	}
`;
