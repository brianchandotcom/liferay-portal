/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {gql} from '@apollo/client';
import {INDIVIDUALS_FRAGMENT} from '~/shared/queries/fragments';

export default gql`
	query KnownIndividualsListAssetQuery(
		$assetId: String!
		$devices: String
		$keywords: String
		$location: String
		$rangeEnd: String
		$rangeKey: Int
		$rangeStart: String
		$size: Int!
		$start: Int!
		$touchpoint: String
	) {
		objectEntry(
			assetId: $assetId
			canonicalUrl: $touchpoint
			country: $location
			deviceType: $devices
			rangeEnd: $rangeEnd
			rangeKey: $rangeKey
			rangeStart: $rangeStart
		) {
			viewsMetric {
				...individualsFragment
			}
		}
	}

	${INDIVIDUALS_FRAGMENT}
`;
