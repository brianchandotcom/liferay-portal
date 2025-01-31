/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {gql, useQuery} from '@apollo/client';

const GET_ACCOUNT_ROLES_BY_ACCOUNT_EXTERNAL_REFERENCE_CODE = gql`
	query getAccountRolesByAccountExternalReferenceCode(
		$externalReferenceCode: String!
		$filter: String
		$pageSize: Int
	) {
		accountAccountRolesByExternalReferenceCode(
			externalReferenceCode: $externalReferenceCode
			filter: $filter
			pageSize: $pageSize
		) {
			items {
				id
				name
			}
		}
	}
`;

export function useGetAccountRolesByAccountExternalReferenceCode(
	externalReferenceCode: string,
	options = {
		filter: '',
		notifyOnNetworkStatusChange: false,
		skip: false,
	}
) {
	return useQuery(GET_ACCOUNT_ROLES_BY_ACCOUNT_EXTERNAL_REFERENCE_CODE, {
		fetchPolicy: 'cache-and-network',
		nextFetchPolicy: 'cache-first',
		notifyOnNetworkStatusChange: options.notifyOnNetworkStatusChange,
		skip: options.skip,
		variables: {
			externalReferenceCode,
			filter: options.filter || '',
			pageSize: 9999,
		},
	});
}
