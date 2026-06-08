/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {gql} from '@apollo/client';
import {
	DEVICE_FRAGMENT,
	GEOLOCATION_FRAGMENT,
} from '~/shared/queries/fragments';

const GlobalFilterAssetQuery = function GlobalFilterAssetQuery(
	queryName,
	metricName
) {
	return gql`
            query GlobalFilterAssetQuery(
                $assetId: String!
                $channelId: String
                $title: String
                $touchpoint: String
                $rangeKey: Int!
            ) {
                ${queryName}(
                    assetId: $assetId
                    canonicalUrl: $touchpoint
                    channelId: $channelId
                    title: $title
                    rangeKey: $rangeKey
                ) {
                    ${metricName} {
                        ...deviceFragment
                        ...geolocationFragment
                    }
                }
            }

            ${DEVICE_FRAGMENT}
            ${GEOLOCATION_FRAGMENT}
        `;
};

/**
 * Global Filter Asset Query
 * @description Create a GraphQL query
 * @param {string} queryName
 * @param {string} metricName
 * @returns GraphQL query
 */
export default GlobalFilterAssetQuery;
