/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {gql, useQuery} from '@apollo/client';

const GET_ACTIVATION_KEYS = gql`
	query getActivationKeys(
		$accountKey: String!
		$filter: String!
		$page: Number!
		$pageSize: Number!
	) {
		getActivationKeys(
			accountKey: $accountKey
			filter: $filter
			page: $page
			pageSize: $pageSize
			sort: $sort
		)
			@rest(
				type: "R_ActivationKeysPage"
				path: "/accounts/{args.accountKey}/license-keys?filter={args.filter}&page={args.page}&pageSize={args.pageSize}&sort={args.sort}"
				method: "GET"
			) {
			items {
				active
				complimentary
				description
				expirationDate
				hostName
				id
				ipAddresses
				licenseEntryType
				macAddresses
				maxClusterNodes
				name
				productName
				productVersion
				sizing
				startDate
			}
		}
	}
`;

export function useGetActivationKeys(
	accountKey,
	filter,
	page,
	pageSize,
	options = {
		notifyOnNetworkStatusChange: false,
		skip: false,
	}
) {
	return useQuery(GET_ACTIVATION_KEYS, {
		context: {
			type: 'raysource-rest',
		},
		fetchPolicy: 'cache-and-network',
		nextFetchPolicy: 'cache-first',
		notifyOnNetworkStatusChange: options.notifyOnNetworkStatusChange,
		skip: options.skip,
		variables: {
			accountKey,
			filter,
			page,
			pageSize,
			sort: 'startDate:desc',
		},
	});
}
