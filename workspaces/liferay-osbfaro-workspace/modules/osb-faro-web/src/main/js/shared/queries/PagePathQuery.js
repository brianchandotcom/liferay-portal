/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {gql} from '@apollo/client';

export default gql`
	query PagePath(
		$canonicalUrl: String
		$channelId: String
		$rangeEnd: String
		$rangeKey: Int
		$rangeStart: String
		$segmentId: String
		$title: String!
	) {
		pagePath(
			canonicalUrl: $canonicalUrl
			channelId: $channelId
			rangeEnd: $rangeEnd
			rangeKey: $rangeKey
			rangeStart: $rangeStart
			segmentId: $segmentId
			title: $title
		) {
			canonicalUrl
			followingPagePathNodes {
				canonicalUrl
				external
				views
				title
			}
			previousPagePathNodes {
				canonicalUrl
				external
				views
				title
			}
			views
			title
		}
	}
`;
