/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {gql} from '@apollo/client';

import {
	Breakdown,
	BreakdownDataItem,
	CalculationTypes,
	Filter,
} from '../utils/types';

export interface EventAnalysisResultData {
	breakdownItems: BreakdownDataItem[];
	count: number;
	page: number;
	value: number;
}

export interface EventAnalysisResultVariables {
	analysisType: CalculationTypes;
	channelId: string;
	compareToPrevious: boolean;
	eventAnalysisBreakdowns?: Breakdown[];
	eventAnalysisFilters?: Filter[];
	eventDefinitionId: string;
	page: number;
	rangeEnd: string | null;
	rangeKey: number | null;
	rangeStart: string | null;
	size: number;
}
const BREAKDOWN_FIELDS = gql`
	fragment BreakdownFields on BreakdownItem {
		leafNode
		name
		previousValue
		value
	}
`;

const BREAKDOWN_FRAGMENT_RECURSIVE = gql`
	${BREAKDOWN_FIELDS}
	fragment BreakdownFragment on BreakdownItem {
		breakdownItems {
			...BreakdownFields
			breakdownItems {
				...BreakdownFields
				breakdownItems {
					...BreakdownFields
					breakdownItems {
						...BreakdownFields
						breakdownItems {
							...BreakdownFields
						}
					}
				}
			}
		}
	}
`;

export default gql`
	query EventAnalysisResult(
		$analysisType: AnalysisType!
		$channelId: String!
		$compareToPrevious: Boolean!
		$eventAnalysisBreakdowns: [EventAnalysisBreakdownInput]
		$eventAnalysisFilters: [EventAnalysisFilterInput]
		$eventDefinitionId: String!
		$page: Int!
		$rangeEnd: String
		$rangeKey: Int
		$rangeStart: String
		$size: Int!
	) {
		eventAnalysisResult(
			analysisType: $analysisType
			channelId: $channelId
			compareToPrevious: $compareToPrevious
			eventAnalysisBreakdowns: $eventAnalysisBreakdowns
			eventAnalysisFilters: $eventAnalysisFilters
			eventDefinitionId: $eventDefinitionId
			page: $page
			rangeEnd: $rangeEnd
			rangeKey: $rangeKey
			rangeStart: $rangeStart
			size: $size
		) {
			breakdownItems {
				...BreakdownFields
				...BreakdownFragment
			}
			count
			page
			previousValue
			value
		}
	}
	${BREAKDOWN_FRAGMENT_RECURSIVE}
`;
