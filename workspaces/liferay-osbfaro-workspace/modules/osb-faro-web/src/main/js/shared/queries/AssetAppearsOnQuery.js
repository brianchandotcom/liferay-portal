/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {gql} from '@apollo/client';

export default gql`
	query AssetAppearsOnQuery(
		$assetId: String!
		$assetType: AssetType!
		$channelId: String
		$rangeEnd: String
		$rangeKey: Int
		$rangeStart: String
		$selectedMetrics: [String]
		$size: Int!
		$start: Int!
		$title: String
	) {
		assetPages(
			assetId: $assetId
			assetType: $assetType
			channelId: $channelId
			rangeEnd: $rangeEnd
			rangeKey: $rangeKey
			rangeStart: $rangeStart
			selectedMetrics: $selectedMetrics
			size: $size
			start: $start
			title: $title
		) {
			assetMetrics {
				assetTitle
				assetId
				selectedMetrics {
					name
					value
				}
			}
			total
		}
	}
`;
