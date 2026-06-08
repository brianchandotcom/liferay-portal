/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import gql from 'graphql-tag';

import {Name} from './types';

const AudienceReportFragment = gql`
	fragment audienceReportFragment on Metric {
		audienceReport {
			anonymousUsersCount
			knownUsersCount
			nonsegmentedKnownUsersCount
			segmentedAnonymousUsersCount
			segmentedKnownUsersCount
		}
		segment {
			metrics {
				value
				valueKey
			}
			total
		}
	}
`;

export const PageAudienceReportQuery = function PageAudienceReportQuery({
	metricName,
	name,
}: {
	metricName: string;
	name: Name;
}) {
	return gql`
        query ${name}AudienceReportQuery(
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
            ${name}(
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
                    ...audienceReportFragment
                }
            }
        }

        ${AudienceReportFragment}
    `;
};

export const AssetAudienceReportQuery = function AssetAudienceReportQuery({
	metricName,
	name,
}: {
	metricName: string;
	name: Name;
}) {
	return gql`
        query ${name}AudienceReportQuery(
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
                assetId
                assetTitle
                urls
                ${metricName} {
                    ...audienceReportFragment
                }
            }
        }

        ${AudienceReportFragment}
    `;
};
