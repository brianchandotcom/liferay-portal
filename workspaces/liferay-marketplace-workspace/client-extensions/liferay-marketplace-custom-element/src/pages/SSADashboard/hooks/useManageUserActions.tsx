/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {useMemo} from 'react';

import {useMarketplaceContext} from '../../../context/MarketplaceContext';
import useModalContext from '../../../hooks/useModalContext';
import i18n from '../../../i18n';
import {Action} from '../../../utils/constants';
import ManageUserModal from '../modals/ManageUserRolesModal';
import Button from '@clayui/button';
import HeaderWithTooltip from '../components/HeaderWithTooltip';
import HeadlessAdminUser from '../../../services/rest/HeadlessAdminUser';
import {Liferay} from '../../../liferay/liferay';
import marketplaceOAuth2 from '../../../services/oauth/Marketplace';
import {UserRoleTypes} from '../../../enums/Account';

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
							header: (
								<HeaderWithTooltip
									title={i18n.translate('manage-user-roles')}
									tooltip={i18n.translate(
										'set-the-users-role-ssa-users-can-create-trials-while-ssa-admins-can-manage-users-roles-and-trials'
									)}
								/>
							),
							footer: [
								<Button
									displayType="secondary"
									onClick={modalContext.onClose}
								>
									{i18n.translate('cancel')}
								</Button>,
								null,
								<Button type="submit" form="manage-roles">
									{i18n.translate('apply')}
								</Button>,
							],
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
							header: i18n.translate('remove-user'),
							status: 'warning',
							footer: [
								<Button
									onClick={modalContext.onClose}
									displayType="secondary"
								>
									{i18n.translate('cancel')}
								</Button>,
								null,
								<Button
									displayType="warning"
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
						});
					},
				},
			] as Action[],
		[modalContext]
	);
};

export default useManageUserActions;
