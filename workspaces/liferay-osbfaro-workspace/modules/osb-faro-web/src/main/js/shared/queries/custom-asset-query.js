/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {gql} from '@apollo/client';

const CustomAssetQuery = function CustomAssetQuery(metric) {
	return gql(`
		query Custom(
			$assetId: String!
			$channelId: String
			$country: String
			$deviceType: String
			$rangeEnd: String
			$rangeKey: Int
			$rangeStart: String
			$title: String
			$touchpoint: String
		) {
			custom(
				assetId: $assetId
				canonicalUrl: $touchpoint
				channelId: $channelId
				country: $country
				deviceType: $deviceType
				rangeEnd: $rangeEnd
				rangeKey: $rangeKey
				rangeStart: $rangeStart
				title: $title
			) {
				${metric} {
					histogram {
						metrics {
							key
							previousValue
							previousValueKey
							value
							valueKey
							trend {
								percentage
								trendClassification
							}
						}
					}
					previousValue
					trend {
						percentage
						trendClassification
					}
					value
				}
			}
		}`);
};

export default CustomAssetQuery;
