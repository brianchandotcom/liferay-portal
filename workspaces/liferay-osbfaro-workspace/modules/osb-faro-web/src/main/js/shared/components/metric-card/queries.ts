/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import gql from 'graphql-tag';
import {
	METRIC_HISTOGRAM_FRAGMENT,
	METRIC_TABS_FRAGMENT,
} from '~/shared/queries/fragments';

import {Metric} from './metrics';

const capitalize = (str: string): string =>
	str.charAt(0).toUpperCase() + str.slice(1);

const tabsOperationName = (name: string) =>
	`${capitalize(name)}MetricTabsQuery`;

const metricOperationName = (name: string) => `${capitalize(name)}MetricQuery`;

const buildAssetTabsBody = (metrics: Metric[]) =>
	metrics.map(({name}) => `${name} { ...TabsFragment }`).join('\n\t\t\t\t');

export const AssetTabsQuery = function AssetTabsQuery(
	metrics: Metric[],
	name: string
) {
	return gql`
        query ${tabsOperationName(name)}(
            $assetId: String!
            $channelId: String
            $devices: String
            $location: String
            $rangeEnd: String
            $rangeKey: Int
            $rangeStart: String
            $title: String
            $touchpoint: String
        ) {
            ${name}(
                assetId: $assetId
                canonicalUrl: $touchpoint
                channelId: $channelId
                country: $location
                deviceType: $devices
                rangeEnd: $rangeEnd
                rangeKey: $rangeKey
                rangeStart: $rangeStart
                title: $title
            ) {
                ${buildAssetTabsBody(metrics)}
            }
        }

        ${METRIC_TABS_FRAGMENT}
    `;
};

export const AssetMetricQuery = function AssetMetricQuery(queryName: string) {
	return (metricName: string) =>
		gql`
        query ${metricOperationName(queryName)}(
            $assetId: String!
            $channelId: String
            $devices: String
            $location: String
            $rangeEnd: String
            $rangeKey: Int
            $rangeStart: String
            $title: String
            $touchpoint: String
        ) {
            ${queryName}(
                assetId: $assetId
                canonicalUrl: $touchpoint
                channelId: $channelId
                country: $location
                deviceType: $devices
                rangeEnd: $rangeEnd
                rangeKey: $rangeKey
                rangeStart: $rangeStart
                title: $title
            ) {
                ${metricName} {
                    ...HistogramFragment
                }
            }
        }

        ${METRIC_HISTOGRAM_FRAGMENT}
    `;
};

export const SitesTabsQuery = gql`
	query SitesMetricTabsQuery(
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
			bounceRateMetric {
				...TabsFragment
			}
			sessionDurationMetric {
				...TabsFragment
			}
			sessionsPerVisitorMetric {
				...TabsFragment
			}
			visitorsMetric {
				...TabsFragment
			}
		}
	}

	${METRIC_TABS_FRAGMENT}
`;

const SitesGenericMetricQuery = (metricName: string) => gql`
	query SitesMetricQuery(
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
			${metricName} {
				...HistogramFragment
			}
		}
	}

	${METRIC_HISTOGRAM_FRAGMENT}
`;

const SitesCompositeMetricQuery = gql`
	query SitesMetricQuery(
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
			visitorsMetric {
				...HistogramFragment
			}
			anonymousVisitorsMetric {
				...HistogramFragment
			}
			knownVisitorsMetric {
				...HistogramFragment
			}
		}
	}

	${METRIC_HISTOGRAM_FRAGMENT}
`;

export const SitesMetricQuery = function SitesMetricQuery(metricName: string) {
	return metricName === 'visitorsMetric'
		? SitesCompositeMetricQuery
		: SitesGenericMetricQuery(metricName);
};

export const PageMetricQuery = function PageMetricQuery(metricName: string) {
	return gql`
        query PageMetricQuery(
            $channelId: String
            $devices: String
            $experienceId: String
            $location: String
            $rangeEnd: String
            $rangeKey: Int
            $rangeStart: String
            $title: String
            $touchpoint: String
        ) {
            page(
                channelId: $channelId
                canonicalUrl: $touchpoint
                country: $location
                deviceType: $devices
                experienceId: $experienceId
                rangeEnd: $rangeEnd
                rangeKey: $rangeKey
                rangeStart: $rangeStart
                title: $title
            ) {
                ${metricName} {
                    ...HistogramFragment
                }
            }
        }

        ${METRIC_HISTOGRAM_FRAGMENT}
    `;
};

export const PageMetricTabsQuery = gql`
	query PageMetricQuery(
		$channelId: String
		$devices: String
		$experienceId: String
		$location: String
		$rangeEnd: String
		$rangeKey: Int
		$rangeStart: String
		$title: String
		$touchpoint: String
	) {
		page(
			channelId: $channelId
			canonicalUrl: $touchpoint
			country: $location
			deviceType: $devices
			experienceId: $experienceId
			rangeEnd: $rangeEnd
			rangeKey: $rangeKey
			rangeStart: $rangeStart
			title: $title
		) {
			visitorsMetric {
				...TabsFragment
			}
			avgTimeOnPageMetric {
				...TabsFragment
			}
			bounceRateMetric {
				...TabsFragment
			}
			entrancesMetric {
				...TabsFragment
			}
			exitRateMetric {
				...TabsFragment
			}
			viewsMetric {
				...TabsFragment
			}
		}
	}

	${METRIC_TABS_FRAGMENT}
`;
