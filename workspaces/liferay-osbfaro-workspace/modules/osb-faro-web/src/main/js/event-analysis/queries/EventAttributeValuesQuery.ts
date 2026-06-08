/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {gql} from '@apollo/client';

export interface EventAttributeValuesData {
	eventAttributeValues: string[];
	total: number;
}

export interface EventAttributeValuesVariables {
	channelId: string;
	eventAttributeDefinitionId: string;
	eventDefinitionId: string;
	keywords: string;
	size: number;
	start: number;
}

export default gql`
	query EventAttributeValues(
		$channelId: String!
		$eventAttributeDefinitionId: String!
		$eventDefinitionId: String!
		$keywords: String!
		$size: Int!
		$start: Int!
	) {
		eventAttributeValues(
			channelId: $channelId
			eventAttributeDefinitionId: $eventAttributeDefinitionId
			eventDefinitionId: $eventDefinitionId
			keywords: $keywords
			size: $size
			start: $start
		) {
			eventAttributeValues
			total
		}
	}
`;
