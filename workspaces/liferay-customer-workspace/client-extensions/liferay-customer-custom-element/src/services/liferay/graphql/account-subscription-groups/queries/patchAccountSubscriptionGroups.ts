/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {gql} from '@apollo/client';

export const patchAccountSubscriptionGroups = gql`
	mutation patchAccountSubscriptionGroups(
		$id: Long!
		$accountSubscriptionGroup: InputC_AccountSubscriptionGroup!
	) {
		patchAccountSubscriptionGroup(
			accountSubscriptionGroupId: $id
			input: $accountSubscriptionGroup
		)
			@rest(
				method: "PATCH"
				type: "C_AccountSubscriptionGroup"
				path: "/c/accountsubscriptiongroups/{args.accountSubscriptionGroupId}"
			) {
			accountSubscriptionGroupId
			accountKey
			activationStatus
			externalReferenceCode
			name
		}
	}
`;
