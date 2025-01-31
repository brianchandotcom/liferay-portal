/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {gql, useMutation} from '@apollo/client';

const UPDATE_ROLES_BY_CONTACT_EMAIL_ADDRESS = gql`
	mutation updateRolesByContactEmailAddress(
		$externalReferenceCode: String!
		$contactEmail: String!
		$contactRoleName: String!
	) {
		updateRolesByContactEmailAddress(
			externalReferenceCode: $externalReferenceCode
			contactEmail: $contactEmail
			contactRoleName: $contactRoleName
			input: {}
		)
			@rest(
				type: "R_ContactRole"
				path: "/accounts/{args.externalReferenceCode}/contacts/by-email-address/{args.contactEmail}/roles?{args.contactRoleName}"
				method: "PUT"
			) {
			NoResponse
		}
	}
`;

export function useUpdateRolesByContactEmailAddress() {
	return useMutation(UPDATE_ROLES_BY_CONTACT_EMAIL_ADDRESS, {
		context: {
			displayServerError: true,
			displaySuccess: false,
			type: 'raysource-rest',
		},
	});
}
