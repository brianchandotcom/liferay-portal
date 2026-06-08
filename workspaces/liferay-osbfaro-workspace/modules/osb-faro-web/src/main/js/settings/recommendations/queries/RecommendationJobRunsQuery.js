/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {gql} from '@apollo/client';

export default gql`
	query RecommendationJobRuns(
		$jobId: String!
		$size: Int!
		$sort: Sort!
		$start: Int!
	) {
		jobRuns(jobId: $jobId, size: $size, sort: $sort, start: $start) {
			jobRuns {
				completedDate
				context {
					key
					value
				}
				id
				status
			}
			total
		}
	}
`;
