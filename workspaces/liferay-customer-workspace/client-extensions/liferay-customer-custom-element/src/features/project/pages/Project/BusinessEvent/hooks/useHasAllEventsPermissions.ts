/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {useMemo} from 'react';
import {hasAdminUserAccount} from '~/features/project/containers/ActivationKeysTable/utils/hasAdminUserAccount';
import useCurrentKoroneikiAccount from '~/hooks/useCurrentKoroneikiAccount';

import useMyUserAccountByAccountExternalReferenceCode from '../../TeamMembers/components/TeamMembersTable/hooks/useMyUserAccountByAccountExternalReferenceCode';

interface IOrganizationBrief {
	name: string;
}

export default function useHasAllEventsPermissions() {
	const {data, loading} = useCurrentKoroneikiAccount();
	const koroneikiAccount = data?.koroneikiAccountByExternalReferenceCode;

	const {data: myUserAccountData} =
		useMyUserAccountByAccountExternalReferenceCode(
			koroneikiAccount?.accountKey,
			loading
		);
	const loggedUserAccount = myUserAccountData?.myUserAccount;

	const isAdminUserAccount = hasAdminUserAccount(myUserAccountData);
	const hasProjectAdminOrRequesterRole =
		loggedUserAccount?.selectedAccountSummary?.hasSupportSeatRole;
	const isLiferayStaff = loggedUserAccount?.isLiferayStaff;

	const hasFLSOrganizationAssociated = useMemo<boolean>(
		() =>
			loggedUserAccount?.organizationBriefs?.some(
				(orgBrief: IOrganizationBrief) => orgBrief.name.includes('FLS')
			) ?? false,
		[loggedUserAccount?.organizationBriefs]
	);

	return (
		isAdminUserAccount ||
		hasProjectAdminOrRequesterRole ||
		isLiferayStaff ||
		hasFLSOrganizationAssociated
	);
}
