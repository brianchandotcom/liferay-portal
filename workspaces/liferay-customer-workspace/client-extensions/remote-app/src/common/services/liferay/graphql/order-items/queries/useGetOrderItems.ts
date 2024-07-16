/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {gql, useQuery} from '@apollo/client';

export const GET_ORDER_ITEMS = gql`
	query getOrderItems($filter: String, $page: Int = 1, $pageSize: Int = 20) {
		orderItems(filter: $filter, page: $page, pageSize: $pageSize) {
			items {
				externalReferenceCode
				quantity
				customFields {
					name
					customValue {
						data
					}
				}
				reducedCustomFields @client
				options
			}
			lastPage
			page
			pageSize
			totalCount
		}
	}
`;

export default function useGetOrderItems(
	options = {
		filter: '',
		notifyOnNetworkStatusChange: false,
		page: 1,
		pageSize: 20,
		skip: false,
	}
) {
	return useQuery(GET_ORDER_ITEMS, {
		fetchPolicy: 'cache-and-network',
		nextFetchPolicy: 'cache-first',
		notifyOnNetworkStatusChange: options.notifyOnNetworkStatusChange,
		skip: options.skip,
		variables: {
			filter: options.filter || '',
			page: options.page || 1,
			pageSize: options.pageSize || 20,
		},
	});
}
