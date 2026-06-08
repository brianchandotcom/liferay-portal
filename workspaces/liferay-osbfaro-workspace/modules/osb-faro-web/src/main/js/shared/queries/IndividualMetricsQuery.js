/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {gql} from '@apollo/client';

export default gql`
	query IndividualMetrics(
		$channelId: String!
		$interval: String!
		$rangeKey: Int!
	) {
		individualMetric(
			channelId: $channelId
			interval: $interval
			rangeKey: $rangeKey
		) {
			anonymousIndividualsMetric {
				...trendFragment
			}
			knownIndividualsMetric {
				...trendFragment
			}
			totalIndividualsMetric {
				...trendFragment
			}
		}
	}

	fragment trendFragment on Metric {
		value
		trend {
			percentage
			trendClassification
		}
	}
`;
