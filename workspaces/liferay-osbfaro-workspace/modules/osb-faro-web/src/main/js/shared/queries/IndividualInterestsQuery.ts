/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {gql} from '@apollo/client';
import {COMPOSITION_FRAGMENT} from '~/shared/queries/fragments';

export interface IIndividualInterestsData {
	individualInterests: {
		compositions: [
			{
				count: number;
				name: string;
			},
		];
		maxCount: number;
		total: number;
		totalCount: number;
	};
}

export interface IIndividualInterestsVariables {
	active: boolean;
	channelId: string;
	id: string;
	size: number;
	sort: {
		column: string;
		type: string;
	};
	start: number;
}

export default gql`
	query Interests(
		$channelId: String
		$keywords: String
		$size: Int!
		$sort: Sort!
		$start: Int!
	) {
		individualInterests(
			channelId: $channelId
			keywords: $keywords
			size: $size
			sort: $sort
			start: $start
		) {
			...compositionFragment
		}
	}

	${COMPOSITION_FRAGMENT}
`;
