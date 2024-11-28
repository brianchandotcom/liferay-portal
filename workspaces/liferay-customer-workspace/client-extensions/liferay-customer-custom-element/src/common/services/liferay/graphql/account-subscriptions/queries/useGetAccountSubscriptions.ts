/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {gql, useLazyQuery, useQuery} from '@apollo/client';

const GET_ACCOUNT_SUBSCRIPTIONS = gql`
	query getAccountSubscriptions(
		$filter: String
		$page: Int = 1
		$pageSize: Int = 20
	) {
		c {
			accountSubscriptions(
				filter: $filter
				page: $page
				pageSize: $pageSize
			) {
				items {
					accountKey
					accountSubscriptionGroupERC
					accountSubscriptionId
					endDate
					externalReferenceCode
					instanceSize
					name
					quantity
					startDate
					productKey
				}
			}
		}
	}
`;

interface IQueryOptions {
	filter?: string;
	notifyOnNetworkStatusChange?: boolean;
	page?: number;
	pageSize?: number;
	skip?: boolean;
}

export function useGetAccountSubscriptions(options: IQueryOptions) {
	return useQuery(GET_ACCOUNT_SUBSCRIPTIONS, {
		fetchPolicy: 'cache-and-network',
		nextFetchPolicy: 'cache-first',
		notifyOnNetworkStatusChange:
			options?.notifyOnNetworkStatusChange || false,
		skip: options?.skip || false,
		variables: {
			filter: options?.filter || '',
			page: options?.page || 1,
			pageSize: options?.pageSize || 20,
		},
	});
}

export function useLazyGetAccountSubscriptions(
	options = {
		notifyOnNetworkStatusChange: false,
	}
) {
	return useLazyQuery(GET_ACCOUNT_SUBSCRIPTIONS, {
		fetchPolicy: 'cache-and-network',
		nextFetchPolicy: 'cache-first',
		notifyOnNetworkStatusChange: options.notifyOnNetworkStatusChange,
	});
}
