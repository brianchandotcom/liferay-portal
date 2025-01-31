/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {gql, useQuery} from '@apollo/client';

import {CORE_USER_ACCOUNT_FIELDS} from '../fragments';

const GET_USER_ACCOUNTS_BY_ACCOUNT_EXTERNAL_REFERENCE_CODE = gql`
	${CORE_USER_ACCOUNT_FIELDS}
	query getUserAccountsByAccountExternalReferenceCode(
		$externalReferenceCode: String!
		$filter: String
		$pageSize: Int
	) {
		accountUserAccountsByExternalReferenceCode(
			externalReferenceCode: $externalReferenceCode
			filter: $filter
			pageSize: $pageSize
		)
			@rest(
				type: "UserAccountPage"
				path: "/headless-admin-user/v1.0/accounts/by-external-reference-code/{args.externalReferenceCode}/user-accounts?pageSize={args.pageSize}&filter={args.filter}"
			) {
			items @type(name: "UserAccount") {
				...CoreUserAccountFields
				accountBriefs @type(name: "AccountBrief") {
					externalReferenceCode
					id
					name
					roleBriefs @type(name: "RoleBrief") {
						id
						name
					}
				}
				selectedAccountSummary @client {
					hasAdministratorRole
					hasSupportSeatRole
					roleBriefs
				}
				userAccountContactInformation {
					telephones
				}
			}

			page
			pageSize
			totalCount
			lastPage
		}
	}
`;

export function useGetUserAccountsByAccountExternalReferenceCode(
	externalReferenceCode,
	options = {
		filter: '',
		notifyOnNetworkStatusChange: false,
		skip: false,
	}
) {
	return useQuery(GET_USER_ACCOUNTS_BY_ACCOUNT_EXTERNAL_REFERENCE_CODE, {
		context: {
			type: 'liferay-rest',
		},
		fetchPolicy: 'cache-and-network',
		nextFetchPolicy: 'cache-first',
		notifyOnNetworkStatusChange: options.notifyOnNetworkStatusChange,
		skip: options.skip,
		variables: {
			externalReferenceCode,
			filter: options.filter || '',
			page: options.page || 1,
			pageSize: options.pageSize || 9999,
		},
	});
}
