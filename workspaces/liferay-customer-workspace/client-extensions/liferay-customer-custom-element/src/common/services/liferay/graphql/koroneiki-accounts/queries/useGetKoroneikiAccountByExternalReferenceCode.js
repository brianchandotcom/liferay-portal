/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {gql, useQuery} from '@apollo/client';
import {CORE_KORONEIKI_ACCOUNT_FIELDS} from '../fragments/coreKoroneikiAccountFields';

const GET_KORONEIKI_ACCOUNT_BY_EXTERNAL_REFERENCE_CODE = gql`
	${CORE_KORONEIKI_ACCOUNT_FIELDS}
	query getKoroneikiAccountByExternalReferenceCode(
		$externalReferenceCode: String
	) {
		koroneikiAccountByExternalReferenceCode(
			externalReferenceCode: $externalReferenceCode
		)
			@rest(
				type: "C_KoroneikiAccount"
				path: "/c/koroneikiaccounts/by-external-reference-code/{args.externalReferenceCode}"
			) {
			...CoreKoroneikiAccountFields
			r_accountEntryToKoroneikiAccount_accountEntryId
		}
	}
`;

export function useGetKoroneikiAccountByExternalReferenceCode(
	externalReferenceCode,
	options = {
		notifyOnNetworkStatusChange: false,
		skip: false,
	}
) {
	return useQuery(GET_KORONEIKI_ACCOUNT_BY_EXTERNAL_REFERENCE_CODE, {
		context: {
			type: 'liferay-rest',
		},
		fetchPolicy: 'cache-and-network',
		nextFetchPolicy: 'cache-first',
		notifyOnNetworkStatusChange: options.notifyOnNetworkStatusChange,
		skip: options.skip,
		variables: {
			externalReferenceCode,
		},
	});
}
