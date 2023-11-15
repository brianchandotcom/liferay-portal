/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayIcon from '@clayui/icon';
import ClayLoadingIndicator from '@clayui/loading-indicator';
import {useMemo, useState} from 'react';

import {useMarketplaceContext} from '../../context/MarketplaceContext';
import {Liferay} from '../../liferay/liferay';
import {
	MemberProps,
	adminRoles,
	customerRoles,
	publisherRoles,
} from '../../pages/PublishedAppsDashboard/PublishedDashboardPageUtil';
import {DashboardPage} from '../DashBoardPage/DashboardPage';
import {DashboardMemberTableRow} from '../DashboardTable/DashboardMemberTableRow';
import {DashboardTable, TableHeaders} from '../DashboardTable/DashboardTable';
import {InviteMemberModal} from '../InviteMemberModal/InviteMemberModal';
import {MemberProfile} from '../MemberProfile/MemberProfile';
import useMembers from './useMembers';

interface MembersPageProps {
	icon: string;
	isCustomerDashboard: boolean;
	isPublisherDashboard: boolean;
	listOfRoles: string[];
	rolesPermissionDescription: {
		appPermissions: PermissionDescription[];
		dashboardPermissions: PermissionDescription[];
	};
	selectedAccount: Account;
}

const memberTableHeaders: TableHeaders = [
	{
		iconSymbol: 'order-arrow',
		title: 'Name',
	},
	{
		title: 'Email',
	},
	{
		title: 'Role',
	},
];

const memberMessages = {
	description: 'Manage users in your development team and invite new ones',
	emptyStateMessage: {
		description1: 'Create new members and they will show up here.',
		description2: 'Click on “New Member” to start.',
		title: 'No Members Yet',
	},
	title: 'Members',
};

export function MembersPage({
	icon,
	isCustomerDashboard,
	isPublisherDashboard,
	listOfRoles,
	rolesPermissionDescription,
	selectedAccount,
}: MembersPageProps) {
	const [visible, setVisible] = useState<boolean>(false);
	const [loading] = useState<boolean>(false);
	const [selectedMember, setSelectedMember] = useState<MemberProps>();
	const {accountId} = Liferay.CommerceContext.account || {};

	const marketplaceContext = useMarketplaceContext();

	const currentUserAccountBriefs =
		marketplaceContext.myUserAccount?.accountBriefs?.find(
			(accountBrief: {id: number}) =>
				accountBrief.id === selectedAccount?.id
		);

	const myUserAccount = useMemo(
		() => ({
			...marketplaceContext.myUserAccount,
			...(currentUserAccountBriefs && {
				isAdminAccount: currentUserAccountBriefs.roleBriefs.some(
					(role) => adminRoles.includes(role.name)
				),
				isCustomerAccount: currentUserAccountBriefs.roleBriefs.some(
					(role) => customerRoles.includes(role.name)
				),
				isPublisherAccount: currentUserAccountBriefs.roleBriefs.some(
					(role) => publisherRoles.includes(role.name)
				),
			}),
		}),
		[currentUserAccountBriefs, marketplaceContext.myUserAccount]
	);

	const {members, mutate: mutateMembers} = useMembers({
		accountId: accountId ?? (selectedAccount?.id as unknown as string),
		isCustomerDashboard,
		isPublisherDashboard,
		selectedAccount,
	});

	return (
		<>
			{loading ? (
				<ClayLoadingIndicator
					className="members-page-loading-indicator"
					displayType="primary"
					shape="circle"
					size="md"
				/>
			) : (
				<DashboardPage
					buttonMessage={
						myUserAccount.isAdminAccount && (
							<>
								<ClayIcon className="mr-1" symbol="plus" />
								New Member
							</>
						)
					}
					messages={memberMessages}
					onButtonClick={() => setVisible(true)}
				>
					{selectedMember ? (
						<MemberProfile
							memberUser={selectedMember}
							setSelectedMember={setSelectedMember}
							userLogged={myUserAccount}
						/>
					) : (
						<DashboardTable<MemberProps>
							emptyStateMessage={memberMessages.emptyStateMessage}
							icon={icon}
							items={members}
							tableHeaders={memberTableHeaders}
						>
							{(member) => (
								<DashboardMemberTableRow
									item={member}
									key={member.name}
									onSelectedMemberChange={setSelectedMember}
								/>
							)}
						</DashboardTable>
					)}
				</DashboardPage>
			)}

			{visible && (
				<InviteMemberModal
					dashboardType={
						isCustomerDashboard
							? 'customer-dashboard'
							: 'publisher-dashboard'
					}
					handleClose={() => setVisible(false)}
					listOfRoles={listOfRoles}
					mutateMembers={mutateMembers}
					rolesPermissionDescription={rolesPermissionDescription}
					selectedAccount={selectedAccount}
				/>
			)}
		</>
	);
}
