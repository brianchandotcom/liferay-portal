/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {gql} from '@apollo/client';
import {Sort} from '~/shared/types';

import {Attribute, AttributeTypes} from '../utils/types';

export interface EventAttributeDefinitionsData {
	eventAttributeDefinitions: Attribute[];
	total: number;
}

export interface EventAttributeDefinitionsVariables {
	eventDefinitionId?: string;
	keyword?: string;
	page?: number;
	size: number;
	sort: Sort;
	type: AttributeTypes;
}

export default gql`
	query EventAttributeDefinitions(
		$eventDefinitionId: String
		$keyword: String
		$page: Int!
		$size: Int!
		$sort: Sort!
		$type: EventAttributeDefinitionType!
	) {
		eventAttributeDefinitions(
			eventDefinitionId: $eventDefinitionId
			keyword: $keyword
			page: $page
			size: $size
			sort: $sort
			type: $type
		) {
			eventAttributeDefinitions {
				dataType
				description
				displayName
				id
				name
				sampleValue
				type
			}
			total
		}
	}
`;
