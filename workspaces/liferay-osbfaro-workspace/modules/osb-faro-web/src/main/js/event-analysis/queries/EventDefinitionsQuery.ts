/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {gql} from '@apollo/client';

import {Event, EventTypes} from '../utils/types';

export interface EventDefinitionsData {
	eventDefinitions: {
		eventDefinitions: Event[];
		total: number;
	};
}

export interface EventDefinitionsVariables {
	blocked?: boolean;
	eventType: EventTypes;
	keyword?: string;
	page?: number;
	size: number;
	sort: {
		column: string;
		type: 'ASC' | 'DESC';
	};
}

export default gql`
	query EventDefinitions(
		$blocked: Boolean
		$eventType: EventDefinitionType!
		$hidden: Boolean
		$keyword: String
		$page: Int!
		$size: Int!
		$sort: Sort!
	) {
		eventDefinitions(
			blocked: $blocked
			eventType: $eventType
			hidden: $hidden
			keyword: $keyword
			page: $page
			size: $size
			sort: $sort
		) {
			eventDefinitions {
				description
				displayName
				hidden
				id
				name
				type
			}
			total
		}
	}
`;

export interface HideEventDefinitionsData {
	hideEventDefinitions: Event[];
}

export interface HideEventDefinitionsVariables {
	eventDefinitionIds: string[];
}

export const HideEventDefinitions = gql`
	mutation HideEventDefinitions($eventDefinitionIds: [String]!) {
		hideEventDefinitions(eventDefinitionIds: $eventDefinitionIds) {
			description
			displayName
			hidden
			id
			name
			type
		}
	}
`;

export interface UnhideEventDefinitionsData {
	unhideEventDefinitions: Event[];
}

export const UnhideEventDefinitions = gql`
	mutation UnhideEventDefinitions($eventDefinitionIds: [String]!) {
		unhideEventDefinitions(eventDefinitionIds: $eventDefinitionIds) {
			description
			displayName
			hidden
			id
			name
			type
		}
	}
`;
