/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {gql, useMutation} from '@apollo/client';

export const UPDATE_ACCOUNT_SUBSCRIPTION_GROUP = gql`
	mutation updateAccountSubscriptionGroup(
		$accountSubscriptionGroupId: Long!
		$AccountSubscriptionGroup: InputC_AccountSubscriptionGroup!
	) {
		updateAccountSubscriptionGroup(
			accountSubscriptionGroupId: $accountSubscriptionGroupId
			input: $AccountSubscriptionGroup
		)
			@rest(
				method: "PUT"
				type: "C_AccountSubscriptionGroup"
				path: "/c/accountsubscriptiongroups/{args.accountSubscriptionGroupId}"
			) {
			accountSubscriptionGroupId
			accountKey
			activationStatus
			externalReferenceCode
		}
	}
`;

export function useUpdateAccountSubscriptionGroup(
	variables,
	options = {displaySuccess: false}
) {
	return useMutation(
		UPDATE_ACCOUNT_SUBSCRIPTION_GROUP,
		{
			context: {
				displaySuccess: options.displaySuccess,
				type: 'liferay-rest',
			},
		},
		variables
	);
}
