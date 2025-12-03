/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {gql, useQuery} from '@apollo/client';

export const GET_CLOUD_NATIVE_ENVIRONMENTS = gql`
	query getCloudNativeEnvironments(
		$filter: String
		$page: Int = 1
		$pageSize: Int = 20
	) {
		c {
			cloudNativeEnvironments(
				filter: $filter
				page: $page
				pageSize: $pageSize
			) {
				items {
					cloudNativeEnvironmentId
					maxClusterNodes
					nonProductionSubscriptionUuid
					productionSubscriptionUuid
				}
			}
		}
	}
`;

export function useGetCloudNativeEnvironments(
	options = {
		filter: '',
		notifyOnNetworkStatusChange: false,
		page: 1,
		pageSize: 20,
		skip: false,
	}
) {
	return useQuery(GET_CLOUD_NATIVE_ENVIRONMENTS, {
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
