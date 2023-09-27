/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {DisplayType} from '@clayui/alert';
import ClayIcon from '@clayui/icon';

import {MemberProps} from '../../pages/PublishedAppsDashboardPage/PublishedDashboardPageUtil';

import './MemberProfile.scss';

import {useEffect, useState} from 'react';

import catalogIcon from '../../assets/icons/catalog_icon.svg';
import shieldCheckIcon from '../../assets/icons/shield_check_icon.svg';
import userIcon from '../../assets/icons/user_icon.svg';
import {Liferay} from '../../liferay/liferay';
import {useAppContext} from '../../manage-app-state/AppManageState';
import {
	getMyUserAditionalInfos,
	updateUserAdditionalInfos,
	updateUserPassword,
} from '../../utils/api';
import {createPassword} from '../../utils/createPassword';
import {Avatar} from '../Avatar/Avatar';
import {DetailedCard} from '../DetailedCard/DetailedCard';
import {
	addAdditionalInfo,
	getAccountRolesOnAPI,
	getSiteURL,
	sendRoleAccountUser,
} from '../InviteMemberModal/services';

interface MemberProfileProps {
	memberUser: MemberProps;
	renderToast: (message: string, title: string, type: DisplayType) => void;
	setSelectedMember: (value: MemberProps | undefined) => void;
	userLogged?: UserLogged;
}

const finalPathUrl = {
	'customer-dashboard': 'customer-gate',
	'publisher-dashboard': 'loading',
};

export function MemberProfile({
	memberUser,
	renderToast,
	setSelectedMember,
	userLogged,
}: MemberProfileProps) {
	const [{gravatarAPI}, _] = useAppContext();

	const paths = Liferay.ThemeDisplay.getLayoutURL().split('/');

	const finalPath =
		finalPathUrl[paths[paths.length - 1] as keyof typeof finalPathUrl];

	const url = `${Liferay.ThemeDisplay.getPortalURL()}/c/login?redirect=${getSiteURL()}/${finalPath}`;

	const [checkInviteStatus, setCheckInviteStatus] = useState<boolean>(false);
	const [userAdditionalInfo, setUserAdditionalInfo] = useState<
		AdditionalInfoBody[]
	>([]);

	useEffect(() => {
		const getUserInfo = async () => {
			const myUserAdditionalInfos = await getMyUserAditionalInfos(
				memberUser.userId
			);
			setUserAdditionalInfo(myUserAdditionalInfos?.items);
			setCheckInviteStatus(
				myUserAdditionalInfos?.items?.some(
					(item: AdditionalInfoBody) => !item.acceptInviteStatus
				)
			);
		};

		getUserInfo();
	}, [memberUser.userId]);

	const canViewRestrictedContent =
		userLogged?.isAdminAccount && checkInviteStatus;

	const handleInvitationResend = async (event: React.FormEvent) => {
		event.preventDefault();

		const newPassword = createPassword();

		for (const userAdditionInfo of userAdditionalInfo) {
			const updatedUserInfos = await updateUserAdditionalInfos(
				{sendType: {key: 'canceled', name: 'Canceled'}},
				Number(userAdditionInfo.id)
			);

			if (updatedUserInfos.sendType.key === 'canceled') {
				await updateUserPassword(newPassword, memberUser.userId);

				const roles = await getAccountRolesOnAPI(
					updatedUserInfos.r_accountEntryToUserAdditionalInfo_accountEntryId
				);
				const accountRoles = roles?.find(
					(accountRole: AccountRole) =>
						accountRole.name === 'Invited Member'
				);

				await sendRoleAccountUser(
					updatedUserInfos.r_accountEntryToUserAdditionalInfo_accountEntryId,
					accountRoles.id,
					updatedUserInfos.r_userToUserAddInfo_userId
				);

				const newInvite = await addAdditionalInfo({
					acceptInviteStatus: false,
					accountName: updatedUserInfos.accountName,
					emailOfMember: updatedUserInfos.emailOfMember,
					id: updatedUserInfos.id,
					inviteURL: url,
					inviterName: updatedUserInfos.inviterName,
					mothersName: newPassword,
					r_accountEntryToUserAdditionalInfo_accountEntryId:
						updatedUserInfos.r_accountEntryToUserAdditionalInfo_accountEntryId,
					r_userToUserAddInfo_userId:
						updatedUserInfos.r_userToUserAddInfo_userId,
					roles: updatedUserInfos.roles,
					sendType: {key: 'shipping', name: 'Shipping'},
					userFirstName: updatedUserInfos.userFirstName,
				});

				const userAdditionalInfoList = [];
				const userAdditionalInfoData = await newInvite.json();

				userAdditionalInfoList.push(userAdditionalInfoData);

				setUserAdditionalInfo(userAdditionalInfoList);

				const toastMessage = newInvite.ok
					? `invited again successfully`
					: `Please contact Administrator`;
				const toastType = newInvite.ok ? 'success' : 'danger';

				renderToast(toastMessage, `${memberUser.name}`, toastType);
			}
		}
	};

	return (
		<div className="member-profile-view-container">
			<a
				className="align-items-center d-flex member-profile-back-button"
				onClick={() => setSelectedMember(undefined)}
			>
				<ClayIcon symbol="order-arrow-left" />

				<div className="member-profile-back-button-text">
					&nbsp;Back to Members
				</div>
			</a>

			<div className="d-flex member-profile-content-header">
				<div className="member-profile-image">
					<Avatar
						emailAddress={memberUser.email}
						gravatarAPI={gravatarAPI}
						initialImage={memberUser.image}
						userName={memberUser.name}
					/>
				</div>

				<div className="member-profile-heading-container">
					<h2 className="member-profile-heading">
						{memberUser.name}
					</h2>

					{memberUser.lastLoginDate ? (
						<div className="member-profile-subheading">
							<div className="member-profile-subheading-email">
								{memberUser.email},&nbsp;
							</div>

							<div className="member-profile-subheading-date">
								Last Login at {memberUser.lastLoginDate}
							</div>
						</div>
					) : (
						<div className="member-account-never-logged-in-text">
							{memberUser.email}, Never Logged In
						</div>
					)}
				</div>

				{canViewRestrictedContent && (
					<div className="member-profile-resend-invitation ml-auto">
						<button
							className="h-50 member-profile-button-resend-invitation mr-3"
							onClick={(event) => handleInvitationResend(event)}
						>
							Resend invitation
							<span className="icon-container-reload">
								<ClayIcon symbol="reload" />
							</span>
						</button>

						<button className="h-50 member-profile-button-edit-member mr-3">
							<span className="member-profile-button-edit-member-label-edit">
								Edit
							</span>

							<span className="icon-container-angle-down-small">
								<ClayIcon symbol="angle-down-small" />
							</span>
						</button>
					</div>
				)}
			</div>

			<div className="member-profile-row">
				<DetailedCard
					cardIcon={userIcon}
					cardIconAltText="Member Card Icon"
					cardTitle="Profile"
				>
					<table className="member-profile-information mt-4">
						<tr className="member-profile-name">
							<th className="member-profile-name-heading">
								Name
							</th>

							<td>{memberUser.name}</td>
						</tr>

						<tr>
							<th>Email</th>

							<td>{memberUser.email}</td>
						</tr>

						<tr>
							<th>User ID</th>

							<td>{memberUser.userId}</td>
						</tr>
					</table>
				</DetailedCard>

				<DetailedCard
					cardIcon={shieldCheckIcon}
					cardIconAltText="Member Roles Icon"
					cardTitle="Roles"
				>
					<table className="member-roles-information mt-4">
						<tr>
							<th className="member-roles-permissions-heading">
								Permissions
							</th>

							<td>{memberUser.role}</td>
						</tr>
					</table>
				</DetailedCard>
			</div>

			<div className="member-profile-row">
				<DetailedCard
					cardIcon={catalogIcon}
					cardIconAltText="Member Account Icon"
					cardTitle="Account"
				>
					<table className="member-account-information mt-4">
						<tr>
							<th className="member-account-membership-heading">
								Membership
							</th>

							<td>Invited On {memberUser.dateCreated}</td>
						</tr>

						<tr>
							<th className="member-account-last-logged-in-heading"></th>

							<td>
								<div className="d-inline-block">
									Last Login at&nbsp;
								</div>

								{memberUser.lastLoginDate ? (
									<div className="d-inline-block member-account-lasted-logged-in">
										{memberUser.lastLoginDate}
									</div>
								) : (
									<div className="d-inline-block member-account-never-logged-in-text">
										Never Logged In
									</div>
								)}
							</td>
						</tr>
					</table>
				</DetailedCard>
			</div>
		</div>
	);
}
