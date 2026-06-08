/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {gql} from '@apollo/client';
import {EventMetric} from '~/shared/queries/EventMetricQuery';
import {TREND_FRAGMENT_EVENT_METRIC} from '~/shared/queries/fragments';
import {SessionEntityTypes} from '~/shared/util/constants';

export interface AccountEventMetricsData {
	eventMetric: {
		totalEventsMetric: Metric;
		totalSessionsMetric: Metric;
	};
}

export interface AccountEventMetricsVariables {
	accountId: string;
	channelId: string;
	entityId: string;
	entityType: SessionEntityTypes;
	interval: string;
	keywords?: string;
	rangeEnd?: string | null;
	rangeKey?: number | null;
	rangeStart?: string | null;
}

interface Metric {
	histogram: {metrics: EventMetric[]; total: number};
	value: number;
}

export default gql`
	query AccountEventMetrics(
		$accountId: String!
		$channelId: String!
		$entityId: String!
		$entityType: EntityType!
		$interval: String!
		$keywords: String
		$rangeEnd: String
		$rangeKey: Int
		$rangeStart: String
	) {
		eventMetric(
			accountId: $accountId
			channelId: $channelId
			entityId: $entityId
			entityType: $entityType
			interval: $interval
			keywords: $keywords
			rangeEnd: $rangeEnd
			rangeKey: $rangeKey
			rangeStart: $rangeStart
		) {
			totalEventsMetric {
				...trendFragmentEventMetric
			}
			totalSessionsMetric {
				...trendFragmentEventMetric
			}
		}
	}

	${TREND_FRAGMENT_EVENT_METRIC}
`;
