/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {gql, useMutation} from '@apollo/client';

const REPLACE_ACCOUNT_ROLE_BY_USER_ACCOUNT_EMAIL_ADDRESS = gql`
	mutation replaceAccountRoleByUserAccountEmailAddress(
		$currentAccountRoleId: Long!
		$emailAddress: String!
		$externalReferenceCode: String!
		$newAccountRoleId: Long!
	) {
		deleteAccountByExternalReferenceCodeAccountRoleUserAccountByEmailAddress(
			accountRoleId: $currentAccountRoleId
			emailAddress: $emailAddress
			externalReferenceCode: $externalReferenceCode
		)
		createAccountByExternalReferenceCodeAccountRoleUserAccountByEmailAddress(
			accountRoleId: $newAccountRoleId
			emailAddress: $emailAddress
			externalReferenceCode: $externalReferenceCode
		)
	}
`;

export function useReplaceAccountRoleByUserAccountEmailAddress() {
	return useMutation(REPLACE_ACCOUNT_ROLE_BY_USER_ACCOUNT_EMAIL_ADDRESS, {
		awaitRefetchQueries: true,
		refetchQueries: ['getUserAccountsByAccountExternalReferenceCode'],
	});
}
