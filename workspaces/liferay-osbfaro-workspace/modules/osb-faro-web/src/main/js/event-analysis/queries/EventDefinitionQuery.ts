/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {gql} from '@apollo/client';

import {Event} from '../utils/types';

export interface EventDefinitionData {
	eventDefinition: Event;
}

export interface EventDefinitionVariables {
	displayName?: string;
	id?: string;
}

export default gql`
	query EventDefinition($displayName: String, $id: String) {
		eventDefinition(displayName: $displayName, id: $id) {
			description
			displayName
			eventAttributeDefinitions {
				dataType
				description
				displayName
				id
				name
				sampleValue
			}
			id
			name
			type
		}
	}
`;

export interface UpdateEventDefinitionVariables {
	description: string;
	displayName?: string;
	id?: string;
}

export const UPDATE_EVENT_DEFINITION = gql`
	mutation UpdateEventDefinition(
		$description: String
		$displayName: String
		$id: String!
	) {
		updateEventDefinition(
			description: $description
			displayName: $displayName
			eventDefinitionId: $id
		) {
			description
			displayName
			eventAttributeDefinitions {
				dataType
				description
				displayName
				id
				name
				sampleValue
			}
			id
			name
			type
		}
	}
`;
