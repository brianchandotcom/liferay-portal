/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {gql} from '@apollo/client';

export default gql`
	query FormsList(
		$channelId: String
		$keywords: String
		$rangeEnd: String
		$rangeKey: Int
		$rangeStart: String
		$size: Int!
		$sort: Sort!
		$start: Int!
	) {
		forms(
			channelId: $channelId
			keywords: $keywords
			rangeEnd: $rangeEnd
			rangeKey: $rangeKey
			rangeStart: $rangeStart
			size: $size
			sort: $sort
			start: $start
		) {
			assetMetrics {
				... on FormMetric {
					abandonmentsMetric {
						value
					}
					assetId
					assetTitle
					completionTimeMetric {
						value
					}
					submissionsMetric {
						value
					}
					urls
					viewsMetric {
						value
					}
				}
			}
			total
		}
	}
`;
