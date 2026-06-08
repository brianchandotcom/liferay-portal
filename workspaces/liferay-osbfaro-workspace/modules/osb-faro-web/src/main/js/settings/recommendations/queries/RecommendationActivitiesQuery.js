/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {gql} from '@apollo/client';

export default gql`
	query RecommendationActivities(
		$applicationId: String!
		$eventContextPropertyFilters: [PropertyFilter]
		$eventId: String!
		$rangeKey: Int!
		$size: Int!
		$start: Int!
	) {
		activities(
			applicationId: $applicationId
			eventContextPropertyFilters: $eventContextPropertyFilters
			eventId: $eventId
			rangeKey: $rangeKey
			size: $size
			start: $start
		) {
			activities {
				applicationId
				eventContext
				eventId
				eventProperties
				ownerId
				startTime
			}
			total
		}
	}
`;
