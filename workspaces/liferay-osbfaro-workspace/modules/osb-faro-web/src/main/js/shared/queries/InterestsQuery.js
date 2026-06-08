/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {gql} from '@apollo/client';
import {COMPOSITION_FRAGMENT} from '~/shared/queries/fragments';

export default gql`
	query Interests(
		$channelId: String
		$rangeEnd: String
		$rangeKey: Int
		$rangeStart: String
		$size: Int!
		$start: Int!
	) {
		siteInterests(
			channelId: $channelId
			rangeEnd: $rangeEnd
			rangeKey: $rangeKey
			rangeStart: $rangeStart
			size: $size
			start: $start
		) {
			...compositionFragment
		}
	}

	${COMPOSITION_FRAGMENT}
`;
