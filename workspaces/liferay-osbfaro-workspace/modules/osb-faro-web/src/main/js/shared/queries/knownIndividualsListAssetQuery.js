/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {gql} from '@apollo/client';
import {INDIVIDUALS_FRAGMENT} from '~/shared/queries/fragments';

const KnownIndividualsListAssetQuery = function KnownIndividualsListAssetQuery(
	queryName,
	metricName
) {
	return gql`
            query KnownIndividualsListAssetQuery(
                $assetId: String!
                $channelId: String
                $devices: String
                $keywords: String
                $location: String
                $rangeEnd: String
                $rangeKey: Int
                $rangeStart: String
                $size: Int!
                $start: Int!
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
                        ...individualsFragment
                    }
                }
            }

            ${INDIVIDUALS_FRAGMENT}
        `;
};

/**
 * Known Individuals List Asset Query
 * @description Create a GraphQL query
 * @param {string} queryName
 * @param {string} metricName
 * @returns GraphQL query
 */
export default KnownIndividualsListAssetQuery;
