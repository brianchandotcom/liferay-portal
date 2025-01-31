/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {gql, useQuery} from '@apollo/client';

export const GET_LIFERAY_EXPERIENCE_CLOUD_ENVIRONMENTS = gql`
	query getLiferayExperienceCloudEnvironments(
		$filter: String
		$page: Int = 1
		$pageSize: Int = 20
	) {
		c {
			liferayExperienceCloudEnvironments(
				filter: $filter
				page: $page
				pageSize: $pageSize
			) {
				items {
					liferayExperienceCloudEnvironmentId
					projectId
				}
			}
		}
	}
`;

export function useGetLiferayExperienceCloudEnvironments(
	options = {
		filter: '',
		notifyOnNetworkStatusChange: false,
		page: 1,
		pageSize: 20,
		skip: false,
	}
) {
	return useQuery(GET_LIFERAY_EXPERIENCE_CLOUD_ENVIRONMENTS, {
		fetchPolicy: 'network-only',
		notifyOnNetworkStatusChange: options.notifyOnNetworkStatusChange,
		skip: options.skip,
		variables: {
			filter: options.filter || '',
			page: options.page || 1,
			pageSize: options.pageSize || 20,
		},
	});
}
