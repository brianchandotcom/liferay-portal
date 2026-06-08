/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {gql} from '@apollo/client';
import {DataTypes} from '~/event-analysis/utils/types';
import {Sort} from '~/shared/types';

export type Property = {
	dataType: DataTypes;
	displayName?: string;
	id: string;
	name: string;
};

export interface EventPropertiesData {
	eventProperties: Property[];
	total: number;
}

export interface EventPropertiesVariables {
	eventId?: string;
	keyword?: string;
	page?: number;
	size: number;
	sort: Sort;
}

export default gql`
	query EventProperties(
		$eventId: String!
		$keyword: String
		$page: Int!
		$size: Int!
		$sort: Sort!
	) {
		eventProperties(
			eventId: $eventId
			keyword: $keyword
			page: $page
			size: $size
			sort: $sort
		) {
			eventProperties {
				dataType
				displayName
				id
				name
				__typename
			}
			total
			__typename
		}
	}
`;
