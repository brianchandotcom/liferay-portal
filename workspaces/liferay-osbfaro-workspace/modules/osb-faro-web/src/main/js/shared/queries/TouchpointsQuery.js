/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {gql} from '@apollo/client';

export default gql`
	query Touchpoint(
		$channelId: String
		$keywords: String
		$rangeEnd: String
		$rangeKey: Int
		$rangeStart: String
		$size: Int!
		$sort: Sort!
		$start: Int!
		$terms: String
	) {
		pages(
			channelId: $channelId
			keywords: $keywords
			rangeEnd: $rangeEnd
			rangeKey: $rangeKey
			rangeStart: $rangeStart
			size: $size
			sort: $sort
			start: $start
			terms: $terms
		) {
			assetMetrics {
				... on PageMetric {
					assetTitle
					assetId
					dataSourceId
					avgTimeOnPageMetric {
						value
					}
					bounceRateMetric {
						value
					}
					entrancesMetric {
						value
					}
					exitRateMetric {
						value
					}
					visitorsMetric {
						value
					}
					viewsMetric {
						value
					}
				}
			}
			total
		}
	}
`;

// LRAC-6976 POC TEMP

export const TOUCHPOINTS_QUERY_TEST = gql`
	query Touchpoint(
		$channelId: String
		$keywords: String
		$rangeEnd: String
		$rangeKey: Int
		$rangeStart: String
		$size: Int!
		$sort: Sort!
		$start: Int!
		$terms: String
		$useDB: Boolean
	) {
		pages(
			channelId: $channelId
			keywords: $keywords
			rangeEnd: $rangeEnd
			rangeKey: $rangeKey
			rangeStart: $rangeStart
			size: $size
			sort: $sort
			start: $start
			terms: $terms
			useDB: $useDB
		) {
			assetMetrics {
				... on PageMetric {
					assetTitle
					assetId
					dataSourceId
					avgTimeOnPageMetric {
						value
					}
					bounceRateMetric {
						value
					}
					entrancesMetric {
						value
					}
					exitRateMetric {
						value
					}
					visitorsMetric {
						value
					}
					viewsMetric {
						value
					}
				}
			}
			total
		}
	}
`;
