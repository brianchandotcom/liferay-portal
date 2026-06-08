/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {gql} from '@apollo/client';

// eslint-disable-next-line @typescript-eslint/no-empty-interface
export interface BlockCustomEventDefinitionsData {}

export interface BlockCustomEventDefinitionsVariables {
	eventDefinitionIds: string[];
}

export const BlockCustomEventDefinitions = gql`
	mutation BlockCustomEventDefinitions($eventDefinitionIds: [String]!) {
		blockCustomEventDefinitions(eventDefinitionIds: $eventDefinitionIds)
	}
`;

export const UnblockCustomEventDefinitions = gql`
	mutation UnblockCustomEventDefinitions($eventDefinitionIds: [String]!) {
		unblockCustomEventDefinitions(eventDefinitionIds: $eventDefinitionIds)
	}
`;
