/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {gql, useQuery} from '@apollo/client';
import {CORE_USER_ACCOUNT_FIELDS} from '../fragments';

const GET_USER_ACCOUNT = gql`
	${CORE_USER_ACCOUNT_FIELDS}
	query getUserAccount($userAccountId: Long!) {
		userAccount(userAccountId: $userAccountId) {
			...CoreUserAccountFields
		}
	}
`;

export function useGetUserAccount(userAccountId, options = {skip: false}) {
	return useQuery(GET_USER_ACCOUNT, {
		fetchPolicy: 'cache-and-network',
		nextFetchPolicy: 'cache-first',
		skip: options.skip,
		variables: {
			userAccountId,
		},
	});
}
