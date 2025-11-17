/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import Button from '@clayui/button';
import {useMemo} from 'react';

import {Tooltip} from '../../../components/Tooltip/Tooltip';
import {useMarketplaceContext} from '../../../context/MarketplaceContext';
import useModalContext from '../../../hooks/useModalContext';
import i18n from '../../../i18n';
import {Liferay} from '../../../liferay/liferay';
import HeadlessAdminUser from '../../../services/rest/HeadlessAdminUser';
import {Action} from '../../../utils/constants';
import {ssaRoles as ssaRolesValues} from '../constants';
import ManageUserModal from '../modals/ManageUserRolesModal';

function mutateUser(
	accountERC: string,
	user: UserAccount,
	usersPage: APIResponse<UserAccount>
) {
	return {
		...usersPage,
		items: usersPage.items.map((prevUser) => {
			if (prevUser.id !== user.id) {
				return prevUser;
			}

			return {
				...prevUser,
				accountBriefs: prevUser.accountBriefs.map((account) =>
					account.externalReferenceCode === accountERC
						? {
								...account,
								roleBriefs: [],
							}
						: account
				),
			};
		}),
	};
}

const useManageUserActions = () => {
	const {properties} = useMarketplaceContext();
	const modalContext = useModalContext();

	return useMemo(
		() =>
			[
				{
					name: i18n.translate('manage-roles'),
					onClick: (user: UserAccount, mutate) => {
						modalContext.onOpenModal({
							body: (
								<ManageUserModal
									accountERC={
										properties.accountExternalReferenceCode
									}
									mutate={mutate}
									onClose={modalContext.onClose}
									user={user}
								/>
							),
							footer: [
								<Button
									displayType="secondary"
									key="cancel"
									onClick={modalContext.onClose}
								>
									{i18n.translate('cancel')}
								</Button>,
								null,
								<Button
									form="manage-roles"
									key="confirm"
									type="submit"
								>
									{i18n.translate('apply')}
								</Button>,
							],
							header: (
								<div className="align-items-center d-flex">
									<span className="mr-2">
										{i18n.translate('manage-user-roles')}
									</span>
									<Tooltip
										tooltip={i18n.translate(
											'set-the-users-role-ssa-users-can-create-trials-while-ssa-admins-can-manage-users-roles-and-trials'
										)}
									/>
								</div>
							),
						});
					},
				},
				{
					name: i18n.translate('remove-all-roles'),
					onClick: (user: UserAccount, mutate) => {
						modalContext.onOpenModal({
							body: (
								<div>
									{i18n.translate(
										'you-are-about-to-remove-this-user-from-ssa-they-will-lose-access-to-their-account-and-all-associated-features-but-dont-worry-you-can-invite-them-again-later-if-needed'
									)}
								</div>
							),
							footer: [
								<Button
									displayType="secondary"
									key="cancel"
									onClick={modalContext.onClose}
								>
									{i18n.translate('cancel')}
								</Button>,
								null,
								<Button
									displayType="warning"
									key="confirm"
									onClick={async () => {
										const {items: roles} =
											await HeadlessAdminUser.getAccountRoles(
												properties.accountExternalReferenceCode
											);

										const ssaRoles = roles.filter((role) =>
											ssaRolesValues.some(
												(ssaRole) =>
													ssaRole.key === role.name
											)
										);

										const accountId =
											Liferay.CommerceContext.account
												?.accountId;

										if (!accountId) {
											return;
										}

										try {
											await Promise.all(
												ssaRoles.map((role) =>
													HeadlessAdminUser.deleteRoleAccountUser(
														accountId,
														role.id,
														user.id
													)
												)
											);
										}
										catch {
											Liferay.Util.openToast({
												message: i18n.translate(
													'unable-to-remove-roles'
												),
												title: i18n.translate('error'),
												type: 'danger',
											});
										}
										Liferay.Util.openToast({
											message: i18n.translate(
												'successfully-removed-roles'
											),
										});

										mutate(
											(
												usersPage: APIResponse<UserAccount>
											) => {
												return mutateUser(
													properties.accountExternalReferenceCode,
													user,
													usersPage
												);
											},
											{revalidate: false}
										);

										modalContext.onClose();
									}}
								>
									{i18n.translate('confirm')}
								</Button>,
							],
							header: i18n.translate('remove-user'),
							status: 'warning',
						});
					},
				},
			] as Action[],
		[modalContext, properties.accountExternalReferenceCode]
	);
};

export default useManageUserActions;
