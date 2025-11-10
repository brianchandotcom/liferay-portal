/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import Button from '@clayui/button';
import {useMemo} from 'react';

import {useMarketplaceContext} from '../../../context/MarketplaceContext';
import {UserRoleTypes} from '../../../enums/Account';
import useModalContext from '../../../hooks/useModalContext';
import i18n from '../../../i18n';
import {Liferay} from '../../../liferay/liferay';
import marketplaceOAuth2 from '../../../services/oauth/Marketplace';
import HeadlessAdminUser from '../../../services/rest/HeadlessAdminUser';
import {Action} from '../../../utils/constants';
import HeaderWithTooltip from '../components/HeaderWithTooltip';
import ManageUserModal from '../modals/ManageUserRolesModal';

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
									key="button-1"
									onClick={modalContext.onClose}
								>
									{i18n.translate('cancel')}
								</Button>,
								null,
								<Button
									form="manage-roles"
									key="button-2"
									type="submit"
								>
									{i18n.translate('apply')}
								</Button>,
							],
							header: (
								<HeaderWithTooltip
									title={i18n.translate('manage-user-roles')}
									tooltip={i18n.translate(
										'set-the-users-role-ssa-users-can-create-trials-while-ssa-admins-can-manage-users-roles-and-trials'
									)}
								/>
							),
						});
					},
				},
				{
					name: i18n.translate('remove-user'),
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
									key="button-3"
									onClick={modalContext.onClose}
								>
									{i18n.translate('cancel')}
								</Button>,
								null,
								<Button
									displayType="warning"
									key="button-3"
									onClick={async () => {
										const userRoles =
											await HeadlessAdminUser.getRolesPage(
												new URLSearchParams({
													pageSize: '-1',
												})
											);

										const ssaUser = userRoles.items.find(
											(userRole) =>
												userRole.name ===
												UserRoleTypes.SSA_USER
										);

										try {
											await marketplaceOAuth2.deleteAssignRoleUserAccount(
												Number(ssaUser?.id),
												user.id
											);

											await HeadlessAdminUser.deleteAccountUserAccountByEmailAddress(
												properties.accountExternalReferenceCode,
												user.emailAddress
											);
										}
										catch {
											Liferay.Util.openToast({
												message: i18n.translate(
													'unable-to-remove-user-from-account'
												),
												type: 'danger',
											});

											return;
										}

										Liferay.Util.openToast({
											message: i18n.translate(
												'removed-user-from-account'
											),
										});

										mutate({revalidate: true});

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
