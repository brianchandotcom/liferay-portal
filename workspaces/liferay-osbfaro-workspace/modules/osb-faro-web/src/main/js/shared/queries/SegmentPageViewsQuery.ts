/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import gql from 'graphql-tag';
import {SafeRangeSelectors} from '~/shared/types';

export interface SegmentPageViewsQueryVariables extends SafeRangeSelectors {
	canonicalUrl: string;
	channelId: string;
	segmentIds: string[];
	title: string;
}

export interface SegmentPageViewsQueryData {
	segmentPageViews: {
		segmentId: string;
		views: number;
	}[];
}

export const SegmentPageViewsQuery = gql`
	query SegmentPageViewsQuery(
		$canonicalUrl: String
		$channelId: String
		$rangeEnd: String
		$rangeKey: Int
		$rangeStart: String
		$segmentIds: [String]
		$title: String!
	) {
		segmentPageViews(
			canonicalUrl: $canonicalUrl
			channelId: $channelId
			rangeEnd: $rangeEnd
			rangeKey: $rangeKey
			rangeStart: $rangeStart
			segmentIds: $segmentIds
			title: $title
		) {
			segmentId
			views
		}
	}
`;
