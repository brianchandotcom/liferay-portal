/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {gql} from '@apollo/client';

import {EntityType} from '../context/referencedObjects';

const DXPEntitiesQuery = function DXPEntitiesQuery(entityName) {
	if ([EntityType.Teams, EntityType.Groups].includes(entityName)) {
		return gql`
			query DXPEntitiesList(
				$channelId: String
				$keywords: String
				$size: Int!
				$sort: Sort!
				$start: Int!
			) {
				${entityName}(
					channelId: $channelId
					keywords: $keywords
					size: $size
					sort: $sort
					start: $start
				) {
					dxpEntities {
						dataSourceName
						id
						name
					}
					total
				}
			}
		`;
	}

	return gql`
		query DXPEntitiesList(
			$channelId: String
			$keywords: String
			$size: Int!
			$sort: Sort!
			$start: Int!
		) {
			${entityName}(
				channelId: $channelId
				keywords: $keywords
				size: $size
				sort: $sort
				start: $start
			) {
				dxpEntities {
					dataSourceName
					id
					name
				}
				total
			}
		}
	`;
};

/**
 * DXP Entity List Query
 * @description Create a GraphQL query
 * @param {string} queryName
 * @param {string} metricName
 * @returns GraphQL query
 */
export default DXPEntitiesQuery;
