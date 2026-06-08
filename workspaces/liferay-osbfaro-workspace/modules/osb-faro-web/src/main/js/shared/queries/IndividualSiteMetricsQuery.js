/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {gql} from '@apollo/client';
import {TREND_FRAGMENT} from '~/shared/queries/fragments';

export default gql`
	query SiteMetrics(
		$channelId: String
		$interval: String!
		$rangeEnd: String
		$rangeKey: Int
		$rangeStart: String
	) {
		site(
			channelId: $channelId
			interval: $interval
			rangeEnd: $rangeEnd
			rangeKey: $rangeKey
			rangeStart: $rangeStart
		) {
			anonymousVisitorsMetric {
				...trendFragment
			}
			knownVisitorsMetric {
				...trendFragment
			}
			visitorsMetric {
				...trendFragment
			}
		}
	}

	${TREND_FRAGMENT}
`;
