/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {gql} from '@apollo/client';

export default gql`
	query RequestList(
		$keywords: String
		$rangeKey: Int
		$size: Int!
		$sort: Sort!
		$start: Int!
		$statuses: [DataControlTaskStatus]
		$types: [DataControlTaskType]
	) {
		dataControlTasks(
			keywords: $keywords
			rangeKey: $rangeKey
			size: $size
			sort: $sort
			start: $start
			statuses: $statuses
			types: $types
		) {
			dataControlTasks {
				batchId
				completeDate
				createDate
				emailAddresses
				id
				status
				type
			}
			total
		}
	}
`;
