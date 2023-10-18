/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {useCallback} from 'react';
import useSWR from 'swr';

import {
	AccountBriefProps,
	MemberProps,
	UserAccountProps,
	customerRoles,
	publisherRoles,
} from '../../pages/PublishedAppsDashboard/PublishedDashboardPageUtil';
import {getUserAccounts} from '../../utils/api';

type Props = {
	accountId: string | null;
	isCustomerDashboard: boolean;
	isPublisherDashboard: boolean;
	selectedAccount: Account;
};

const useMembers = ({
	accountId,
	isCustomerDashboard,
	isPublisherDashboard,
	selectedAccount,
}: Props) => {
	const getRolesList = useCallback(
		(accountBriefs: AccountBrief[]) => {
			const rolesList: string[] = [];

			const accountBrief = accountBriefs.find(
				(accountBrief) => accountBrief.id === selectedAccount.id
			);

			accountBrief?.roleBriefs.forEach((role: RoleBrief) => {
				rolesList.push(role.name);
			});

			return rolesList.join(', ');
		},
		[selectedAccount?.id]
	);

	const {data: members = []} = useSWR(`/${accountId}/members`, async () => {
		const userAccountResponse = await getUserAccounts();

		return userAccountResponse?.items
			.map((member: UserAccountProps) => {
				const _member = {
					accountBriefs: member.accountBriefs,
					dateCreated: member.dateCreated,
					email: member.emailAddress,
					image: member.image,
					isCustomerAccount: false,
					isInvitedMember: false,
					isPublisherAccount: false,
					lastLoginDate: member.lastLoginDate,
					name: member.name,
					role: getRolesList(member.accountBriefs),
					userId: member.id,
				} as MemberProps;

				const roles = _member.role.split(', ');

				_member.isCustomerAccount =
					isCustomerDashboard &&
					roles.some((role) => customerRoles.includes(role));

				_member.isInvitedMember = roles.some(
					(role) => role === 'Invited Member'
				);

				_member.isPublisherAccount =
					isPublisherDashboard &&
					roles.some((role) => publisherRoles.includes(role));
			})
			.filter(
				(member: MemberProps) =>
					member.accountBriefs.find(
						(accountBrief: AccountBriefProps) =>
							accountBrief.externalReferenceCode ===
							selectedAccount.externalReferenceCode
					) &&
					((member.isCustomerAccount && isCustomerDashboard) ||
						(member.isPublisherAccount && isPublisherDashboard) ||
						member.isInvitedMember)
			);
	});

	return members;
};

export default useMembers;
