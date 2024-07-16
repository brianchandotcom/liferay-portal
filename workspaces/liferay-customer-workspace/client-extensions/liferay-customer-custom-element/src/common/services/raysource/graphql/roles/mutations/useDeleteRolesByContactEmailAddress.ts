/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {gql, useMutation} from '@apollo/client';

const DELETE_ROLES_BY_CONTACT_EMAIL_ADDRESS = gql`
	mutation deleteRolesByContactEmailAddress(
		$externalReferenceCode: String!
		$contactEmail: String!
		$contactRoleNames: String!
	) {
		deleteRolesByContactEmailAddress(
			externalReferenceCode: $externalReferenceCode
			contactEmail: $contactEmail
			contactRoleNames: $contactRoleNames
		)
			@rest(
				type: "R_ContactRole"
				path: "/accounts/{args.externalReferenceCode}/contacts/by-email-address/{args.contactEmail}/roles?{args.contactRoleNames}"
				method: "DELETE"
			) {
			NoResponse
		}
	}
`;

export function useDeleteRolesByContactEmailAddress() {
	return useMutation(DELETE_ROLES_BY_CONTACT_EMAIL_ADDRESS, {
		context: {
			displaySuccess: false,
			type: 'raysource-rest',
		},
	});
}
