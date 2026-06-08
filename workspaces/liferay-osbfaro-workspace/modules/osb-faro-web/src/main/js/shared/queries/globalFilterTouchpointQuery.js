/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {gql} from '@apollo/client';
import {
	DEVICE_FRAGMENT,
	GEOLOCATION_FRAGMENT,
} from '~/shared/queries/fragments';

const GlobalFilterTouchpointQuery = function GlobalFilterTouchpointQuery(
	queryName,
	metricName
) {
	return gql`
            query GlobalFilterTouchpointQuery(
                $channelId: String
                $touchpoint: String
                $rangeKey: Int!
                $title: String
            ) {
                ${queryName}(
                    channelId: $channelId
                    canonicalUrl: $touchpoint
                    rangeKey: $rangeKey
                    title: $title
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
 * Global Filter Touchpoint Query
 * @description Create a GraphQL query
 * @param {string} queryName
 * @param {string} metricName
 * @returns GraphQL query
 */
export default GlobalFilterTouchpointQuery;
