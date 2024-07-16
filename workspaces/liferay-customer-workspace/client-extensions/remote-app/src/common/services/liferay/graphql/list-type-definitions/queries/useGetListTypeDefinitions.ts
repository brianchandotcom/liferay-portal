/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {gql, useQuery} from '@apollo/client';

const GET_LIST_TYPE_DEFINITIONS = gql`
	query getListTypeDefinitions($filter: String) {
		listTypeDefinitions(filter: $filter) {
			items {
				listTypeEntries {
					key
					name
				}
			}
		}
	}
`;

export function useGetListTypeDefinitions(
	options = {
		filter: '',
	}
) {
	return useQuery(GET_LIST_TYPE_DEFINITIONS, {
		variables: {
			filter: options.filter || '',
		},
	});
}
