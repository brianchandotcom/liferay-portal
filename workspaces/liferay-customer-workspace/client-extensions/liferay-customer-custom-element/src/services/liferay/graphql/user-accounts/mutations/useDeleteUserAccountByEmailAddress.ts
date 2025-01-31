/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {gql, useMutation} from '@apollo/client';

const DELETE_USER_ACCOUNT_BY_EMAIL_ADDRESS = gql`
	mutation deleteUserAccountByEmailAddress(
		$emailAddress: String!
		$externalReferenceCode: String!
	) {
		deleteAccountUserAccountByExternalReferenceCodeByEmailAddress(
			emailAddress: $emailAddress
			externalReferenceCode: $externalReferenceCode
		)
	}
`;

export function useDeleteUserAccountByEmailAddress() {
	return useMutation(DELETE_USER_ACCOUNT_BY_EMAIL_ADDRESS, {
		awaitRefetchQueries: true,
		refetchQueries: ['getUserAccountsByAccountExternalReferenceCode'],
	});
}
