/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

import {DisplayType} from '@clayui/alert';
import ClayIcon from '@clayui/icon';

import {MemberProps} from '../../pages/PublishedAppsDashboardPage/PublishedDashboardPageUtil';

import './MemberProfile.scss';
import catalogIcon from '../../assets/icons/catalog_icon.svg';
import shieldCheckIcon from '../../assets/icons/shield_check_icon.svg';
import userIcon from '../../assets/icons/user_icon.svg';
import {Liferay} from '../../liferay/liferay';
import {useAppContext} from '../../manage-app-state/AppManageState';
import {
	getMyUserAditionalInfos,
	updateUserAdditionalinfos,
	updateUserPassword,
} from '../../utils/api';
import {createPassword} from '../../utils/createPassword';
import {Avatar} from '../Avatar/Avatar';
import {DetailedCard} from '../DetailedCard/DetailedCard';
import {
	addAdditionalInfo,
	callRolesApi,
	getAccountRolesOnAPI,
	getSiteURL,
} from '../InviteMemberModal/services';

interface MemberProfileProps {
	member: MemberProps;
	renderToast: (message: string, title: string, type: DisplayType) => void;
	setSelectedMember: (value: MemberProps | undefined) => void;
	userLogged?: UserLogged;
}

export function MemberProfile({
	member,
	renderToast,
	setSelectedMember,
	userLogged,
}: MemberProfileProps) {
	const [{gravatarAPI}, _] = useAppContext();

	const handlePut = async (event: React.FormEvent) => {
		event.preventDefault();

		const myUserAdditionalInfos = await getMyUserAditionalInfos(
			member.userId
		);
		const newPassword = createPassword();

		for (const userAdditionInfo of myUserAdditionalInfos.items || []) {
			const response = await updateUserAdditionalinfos(
				{sendType: {key: 'canceled', name: 'Canceled'}},
				userAdditionInfo.id
			);

			if (response.sendType.key === 'canceled') {
				await updateUserPassword(newPassword, member.userId);

				const roles = await getAccountRolesOnAPI(
					response.r_accountEntryToUserAdditionalInfo_accountEntryId
				);
				const accountsRole = roles?.find(
					(accountRole: AccountRole) =>
						accountRole.name === 'Invited Member'
				);

				await callRolesApi(
					response.r_accountEntryToUserAdditionalInfo_accountEntryId,
					accountsRole.id,
					response.r_userToUserAddInfo_userId
				);

				const newInvite = await addAdditionalInfo({
					acceptInviteStatus: false,
					accountGroupERC: response.accountGroupERC,
					accountName: response.accountName,
					emailOfMember: response.emailOfMember,
					inviteURL:
						Liferay.ThemeDisplay.getPortalURL() +
						'/c/login?redirect=' +
						getSiteURL() +
						'/loading',
					inviterName: response.inviterName,
					mothersName: newPassword,
					r_accountEntryToUserAdditionalInfo_accountEntryId:
						response.r_accountEntryToUserAdditionalInfo_accountEntryId,
					r_userToUserAddInfo_userId:
						response.r_userToUserAddInfo_userId,
					roles: response.roles,
					sendType: {key: 'shipping', name: 'Shipping'},
					userFirstName: response.userFirstName,
				});

				const toastMessage = newInvite.ok
					? `invited again successfully`
					: `Please contact Administrator`;
				const toastType = newInvite.ok ? 'success' : 'danger';

				renderToast(toastMessage, `${member.name}`, toastType);
			}
		}
	};

	return (
		<div className="member-profile-view-container">
			<a
				className="member-profile-back-button"
				onClick={() => setSelectedMember(undefined)}
			>
				<ClayIcon symbol="order-arrow-left" />

				<div className="member-profile-back-button-text">
					&nbsp;Back to Members
				</div>
			</a>

			<div className="member-profile-content-header">
				<div className="member-profile-image">
					<Avatar
						emailAddress={member.email}
						gravatarAPI={gravatarAPI}
						initialImage={member.image}
						userName={member.name}
					/>
				</div>

				<div className="member-profile-heading-container">
					<h2 className="member-profile-heading">{member.name}</h2>

					{member.lastLoginDate ? (
						<div className="member-profile-subheading">
							<div className="member-profile-subheading-email">
								{member.email},&nbsp;
							</div>

							<div className="member-profile-subheading-date">
								Last Login at {member.lastLoginDate}
							</div>
						</div>
					) : (
						<div className="member-account-never-logged-in-text">
							{member.email}, Never Logged In
						</div>
					)}
				</div>

				{userLogged?.isAdminAccount ? (
					<div className="member-profile-resend-invitation">
						<button
							className="member-profile-button-resend-invitation"
							onClick={(event) => handlePut(event)}
						>
							Resend invitation
							<span className="icon-container-reload">
								<ClayIcon symbol="reload" />
							</span>
						</button>

						<button className="member-profile-button-edit-member">
							<span className="member-profile-button-edit-member-label-edit">
								Edit
							</span>

							<span className="icon-container-angle-down-small">
								<ClayIcon symbol="angle-down-small" />
							</span>
						</button>
					</div>
				) : (
					<div></div>
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

							<td>{member.name}</td>
						</tr>

						<tr>
							<th>Email</th>

							<td>{member.email}</td>
						</tr>

						<tr>
							<th>User ID</th>

							<td>{member.userId}</td>
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

							<td>{member.role}</td>
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

							<td>Invited On {member.dateCreated}</td>
						</tr>

						<tr>
							<th className="member-account-last-logged-in-heading"></th>

							<td>
								<div className="d-inline-block">
									Last Login at&nbsp;
								</div>

								{member.lastLoginDate ? (
									<div className="d-inline-block member-account-lasted-logged-in">
										{member.lastLoginDate}
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
