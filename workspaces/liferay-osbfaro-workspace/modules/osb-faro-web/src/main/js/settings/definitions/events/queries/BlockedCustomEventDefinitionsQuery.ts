/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {gql} from '@apollo/client';
import {BlockedCustomEvent} from '~/event-analysis/utils/types';

export interface BlockedCustomEventDefinitionsData {
	blockedCustomEventDefinitions: BlockedCustomEvent[];
	total: number;
}

export interface BlockedCustomEventDefinitionsVariables {
	keyword?: string;
	page?: number;
	size: number;
	sort: {
		column: string;
		type: 'ASC' | 'DESC';
	};
}

export default gql`
	query BlockedCustomEventDefinitions(
		$keyword: String
		$page: Int!
		$size: Int!
		$sort: Sort!
	) {
		blockedCustomEventDefinitions(
			keyword: $keyword
			page: $page
			size: $size
			sort: $sort
		) {
			blockedCustomEventDefinitions {
				hidden
				id
				lastSeenDate
				lastSeenURL
				name
			}
			total
		}
	}
`;

export interface HideBlockedCustomEventDefinitionsData {
	hideBlockedEventDefinitions: BlockedCustomEvent[];
}

export interface HideBlockedCustomEventDefinitionsVariables {
	eventDefinitionIds: string[];
}

export const HideBlockedCustomEventDefinitions = gql`
	mutation HideBlockedCustomEventDefinitions($eventDefinitionIds: [String]!) {
		hideBlockedEventDefinitions(
			blockedEventDefinitionIds: $eventDefinitionIds
		) {
			hidden
			id
			name
			lastSeenDate
			lastSeenURL
		}
	}
`;

export interface UnhideBlockedCustomEventDefinitionsData {
	unhideBlockedEventDefinitions: BlockedCustomEvent[];
}

export const UnhideBlockedCustomEventDefinitions = gql`
	mutation UnhideBlockedCustomEventDefinitions(
		$eventDefinitionIds: [String]!
	) {
		unhideBlockedEventDefinitions(
			blockedEventDefinitionIds: $eventDefinitionIds
		) {
			hidden
			id
			name
			lastSeenDate
			lastSeenURL
		}
	}
`;
