/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayAlert, {DisplayType} from '@clayui/alert';
import ClayLoadingIndicator from '@clayui/loading-indicator';
import {useCallback, useEffect, useState} from 'react';

import {DashboardMemberTableRow} from '../../components/DashboardTable/DashboardMemberTableRow';
import {
	DashboardTable,
	TableHeaders,
} from '../../components/DashboardTable/DashboardTable';
import {InviteMemberModal} from '../../components/InviteMemberModal/InviteMemberModal';
import {MemberProfile} from '../../components/MemberProfile/MemberProfile';
import {getMyUserAccount, getUserAccounts} from '../../utils/api';
import {
	DashboardListItems,
	DashboardPage,
} from '../DashBoardPage/DashboardPage';
import {
	AccountBriefProps,
	MemberProps,
	UserAccountProps,
	adminRoles,
	customerRoles,
	publisherRoles,
} from '../PublishedAppsDashboardPage/PublishedDashboardPageUtil';

interface MembersPageProps {
	dashboardNavigationItems: DashboardListItems[];
	icon: string;
	isCustomerDashboard: boolean;
	isPublisherDashboard: boolean;
	listOfRoles: string[];
	rolesPermissionDescription: {
		appPermissions: PermissionDescription[];
		dashboardPermissions: PermissionDescription[];
	};
	selectedAccount: Account;
	setShowDashboardNavigation?: (value: boolean) => void;
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
	dashboardNavigationItems,
	icon,
	isCustomerDashboard,
	isPublisherDashboard,
	listOfRoles,
	rolesPermissionDescription,
	selectedAccount,
}: MembersPageProps) {
	const [visible, setVisible] = useState<boolean>(false);
	const [loading] = useState<boolean>(false);
	const [members, setMembers] = useState<MemberProps[]>(Array<MemberProps>());
	const [selectedMember, setSelectedMember] = useState<MemberProps>();
	const [isCurrentUserAdmin, setIsCurrentUserAdmin] = useState<boolean>(
		false
	);
	const [toastItems, setToastItems] = useState<
		{message: string; title?: string; type: DisplayType}[]
	>([]);

	const renderToast = (message: string, title: string, type: DisplayType) => {
		setToastItems([...toastItems, {message, title, type}]);
	};
	const [userLogged, setUserLogged] = useState<UserLogged>();

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
		[selectedAccount.id]
	);

	useEffect(() => {
		(async () => {
			const currentUserAccountResponse = await getMyUserAccount();

			const currentUserAccount = {
				accountBriefs: currentUserAccountResponse.accountBriefs,
				isAdminAccount: false,
				isCustomerAccount: false,
				isPublisherAccount: false,
			};

			const currentUserAccountBriefs = currentUserAccount.accountBriefs.find(
				(accountBrief: {id: number}) =>
					accountBrief.id === selectedAccount.id
			);

			if (currentUserAccountBriefs) {
				customerRoles.forEach((customerRole) => {
					if (
						currentUserAccountBriefs.roleBriefs.find(
							(role: {name: string}) => role.name === customerRole
						)
					) {
						currentUserAccount.isCustomerAccount = true;
					}
				});

				publisherRoles.forEach((publisherRole) => {
					if (
						currentUserAccountBriefs.roleBriefs.find(
							(role: {name: string}) =>
								role.name === publisherRole
						)
					) {
						currentUserAccount.isPublisherAccount = true;
					}
				});

				adminRoles.forEach((adminRole) => {
					if (
						currentUserAccountBriefs.roleBriefs.find(
							(role: {name: string}) => role.name === adminRole
						)
					) {
						currentUserAccount.isAdminAccount = true;
						setIsCurrentUserAdmin(true);
					}
				});
			}

			const accountsListResponse = await getUserAccounts();

			const membersList = accountsListResponse?.items.map(
				(member: UserAccountProps) => {
					return {
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
				}
			);

			membersList.map((member: MemberProps) => {
				const rolesList = member.role.split(', ');
				if (isCustomerDashboard) {
					member.isCustomerAccount = rolesList.some((role) =>
						customerRoles.includes(role)
					);

					if (rolesList.find((role) => role === 'Invited Member')) {
						member.isInvitedMember = true;
					}
				}
				if (isPublisherDashboard) {
					member.isPublisherAccount = rolesList.some((role) =>
						publisherRoles.includes(role)
					);
					if (rolesList.find((role) => role === 'Invited Member')) {
						member.isInvitedMember = true;
					}
				}
			});
			let filteredMembersList: MemberProps[] = [];

			filteredMembersList = membersList.filter((member: MemberProps) => {
				return (
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

			setUserLogged(currentUserAccount);

			setMembers(filteredMembersList);
		})();
	}, [
		visible,
		selectedAccount,
		getRolesList,
		isCustomerDashboard,
		isPublisherDashboard,
	]);

	const dashBoardType =
		(isCustomerDashboard && 'customer-dashboard') ||
		(isPublisherDashboard && 'publisher-dashboard');

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
					buttonMessage={isCurrentUserAdmin ? '+ New Member' : ''}
					dashboardNavigationItems={dashboardNavigationItems}
					messages={memberMessages}
					onButtonClick={() => setVisible(true)}
				>
					{selectedMember ? (
						<MemberProfile
							memberUser={selectedMember}
							renderToast={renderToast}
							setSelectedMember={setSelectedMember}
							userLogged={userLogged}
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
					dashboardType={dashBoardType}
					handleClose={() => setVisible(false)}
					listOfRoles={listOfRoles}
					renderToast={renderToast}
					rolesPermissionDescription={rolesPermissionDescription}
					selectedAccount={selectedAccount}
				/>
			)}
			<ClayAlert.ToastContainer>
				{toastItems?.map((alert, index) => (
					<ClayAlert
						autoClose={5000}
						displayType={alert.type}
						key={index}
						onClose={() => {
							setToastItems((prevItems) =>
								prevItems.filter((item) => item !== alert)
							);
						}}
						title={alert.title}
					>
						{alert.message}
					</ClayAlert>
				))}
			</ClayAlert.ToastContainer>
		</>
	);
}
