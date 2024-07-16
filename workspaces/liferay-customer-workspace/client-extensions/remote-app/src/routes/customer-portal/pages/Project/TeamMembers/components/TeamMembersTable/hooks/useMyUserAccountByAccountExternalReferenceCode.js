/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {useMemo} from 'react';
import {useGetMyUserAccount} from '../../../../../../../../common/services/liferay/graphql/user-accounts';
import isAccountAdministrator from '../../../../../../../../common/utils/isAccountAdministrator';
import isSupportSeatRole from '../../../../../../../../common/utils/isSupportSeatRole';

export default function useMyUserAccountByAccountExternalReferenceCode(
	koroneikiAccountLoading,
	externalReferenceCode
) {
	const {data, loading} = useGetMyUserAccount({
		skip: koroneikiAccountLoading,
	});

	const selectedAccountSummary = useMemo(
		() =>
			data?.myUserAccount.accountBriefs.find(
				(accountBrief) =>
					accountBrief.externalReferenceCode === externalReferenceCode
			),
		[data?.myUserAccount.accountBriefs, externalReferenceCode]
	);

	const hasAdministratorRole = useMemo(
		() =>
			selectedAccountSummary?.roleBriefs.some(({name}) =>
				isAccountAdministrator(name)
			),
		[selectedAccountSummary?.roleBriefs]
	);

	const hasSupportSeatRole = useMemo(
		() =>
			selectedAccountSummary?.roleBriefs.some(({name}) =>
				isSupportSeatRole(name)
			),
		[selectedAccountSummary?.roleBriefs]
	);

	return {
		data: {
			myUserAccount: {
				...data?.myUserAccount,
				selectedAccountSummary: {
					hasAdministratorRole,
					hasSupportSeatRole,
					roleBriefs: selectedAccountSummary?.roleBriefs,
				},
			},
		},
		loading: koroneikiAccountLoading || loading,
	};
}
