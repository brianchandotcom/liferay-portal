/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {useMutation} from '@apollo/client';
import {useModal} from '@clayui/core';
import {ClayCheckbox} from '@clayui/form';
import ClayIcon from '@clayui/icon';
import {useCallback, useEffect, useState} from 'react';
import useProvisioningLicenseKeys from '~/common/hooks/useProvisioningLicenseKeys';
import {assignUserAccountWithAccountAndAccountRole} from '~/common/services/liferay/graphql/queries';
import {getRolesFiltered} from '~/common/utils/getProjectRoles';
import isSupportSeatRole from '~/common/utils/isSupportSeatRole';
import {rolesHighPriorityContacts} from '~/routes/customer-portal/utils/getHighPriorityContacts';

import i18n from '../../../../../../../common/I18n';
import StatusTag from '../../../../../../../common/components/StatusTag';
import Table from '../../../../../../../common/components/Table';
import {useAppPropertiesContext} from '../../../../../../../common/contexts/AppPropertiesContext';
import {useCustomerPortal} from '../../../../../context';
import {STATUS_TAG_TYPES} from '../../../../../utils/constants/statusTag';
import {getOrRequestToken} from '../../../../../../../common/services/liferay/security/auth/getOrRequestToken';
import RemoveUserModal from './components/RemoveUserModal/RemoveUserModal';
import TeamMembersTableHeader from './components/TeamMembersTableHeader/TeamMembersTableHeader';
import NameColumn from './components/columns/NameColumn';
import OptionsColumn from './components/columns/OptionsColumn';
import RolesColumn from './components/columns/RolesColumn/RolesColumn';
import useAccountRolesByAccountExternalReferenceCode from './hooks/useAccountRolesByAccountExternalReferenceCode';
import useMyUserAccountByAccountExternalReferenceCode from './hooks/useMyUserAccountByAccountExternalReferenceCode';
import usePagination from './hooks/usePaginationTeamMembers';
import useUserAccountsByAccountExternalReferenceCode from './hooks/useUserAccountsByAccountExternalReferenceCode';
import {getColumns} from './utils/getColumns';
import getFilteredRoleBriefsByName from './utils/getFilteredRoleBriefsByName';

const MAXIMUM_SUPPORT_SEATS_DEFAULT = -1;
const UNLIMITED_SUPPORT_SEATS = 9999;

const TeamMembersTable = ({
	koroneikiAccount,
	loading: koroneikiAccountLoading,
}) => {
	const {
		articleAccountSupportURL,
		articleNotifiedWhenMyActivationKeyIsAboutToExpireURL,
		gravatarAPI,
		importDate,
		provisioningServerAPI,
	} = useAppPropertiesContext();

	const [oAuthToken, setOAuthToken] = useState();
	const provisioningKeys = useProvisioningLicenseKeys();

	const [{project}] = useCustomerPortal();

	useEffect(() => {
		const fetchToken = async () => {
			const token = await getOrRequestToken();

			setOAuthToken(token);
		};

		fetchToken();
	}, []);

	const [assignUserAccountWithAccountRole] = useMutation(
		assignUserAccountWithAccountAndAccountRole,
		{
			awaitRefetchQueries: true,
			refetchQueries: ['getUserAccountsByAccountExternalReferenceCode'],
		}
	);

	const {observer, onOpenChange, open} = useModal();

	const [currentUserEditing, setCurrentUserEditing] = useState();
	const [currentUserRemoving, setCurrentUserRemoving] = useState();
	const [selectedAccountRoleItem, setSelectedAccountRoleItem] = useState();
	const [highPriorityContactsNames, setHighPriorityContactsNames] = useState(
		[]
	);
	const [checkedBoxSubscription, setCheckedBoxSubscription] = useState(false);
	const [isSingleSubscribedUser, setIsSingleSubscribedUser] = useState([]);
	const [singleSubscribedKeys, setSingleSubscribedKeys] = useState('');
	const [loadingModal, setLoadingModal] = useState(false);

	const {data: myUserAccountData, loading: myUserAccountLoading} =
		useMyUserAccountByAccountExternalReferenceCode(
			koroneikiAccountLoading,
			koroneikiAccount?.accountKey
		);

	const loggedUserAccount = myUserAccountData?.myUserAccount;

	const isUnlimitedSupportSeats =
		koroneikiAccount?.maxRequestors === MAXIMUM_SUPPORT_SEATS_DEFAULT;

	const [
		supportSeatsCount,
		{
			data: userAccountsData,
			loading: userAccountsLoading,
			remove,
			search,
			searching,
			update,
			updating,
		},
	] = useUserAccountsByAccountExternalReferenceCode(
		koroneikiAccount?.accountKey,
		koroneikiAccountLoading
	);

	const [availableSupportSeatsCount, setAvailableSupportSeatsCount] =
		useState(1);

	useEffect(() => {
		let availableSupportSeats =
			koroneikiAccount?.maxRequestors - supportSeatsCount;
		availableSupportSeats =
			availableSupportSeats < 0 ? 0 : availableSupportSeats;

		setAvailableSupportSeatsCount(
			isUnlimitedSupportSeats
				? UNLIMITED_SUPPORT_SEATS
				: availableSupportSeats
		);
	}, [koroneikiAccount, supportSeatsCount, isUnlimitedSupportSeats]);

	const userAccounts =
		userAccountsData?.accountUserAccountsByExternalReferenceCode.items;

	const totalUserAccounts =
		userAccountsData?.accountUserAccountsByExternalReferenceCode.totalCount;

	const {paginationConfig, teamMembersByStatusPaginated} =
		usePagination(userAccounts);

	const getHighPriorityContactsByFilter = useCallback(
		(filter) => {
			return userAccountsData?.accountUserAccountsByExternalReferenceCode?.items
				.filter((account) =>
					account?.selectedAccountSummary?.roleBriefs?.some(
						(role) => role?.name === filter
					)
				)
				.map((account) => ({
					email: account.emailAddress,
				}));
		},
		[userAccountsData?.accountUserAccountsByExternalReferenceCode?.items]
	);

	useEffect(() => {
		const fetchHighPriorityContacts = async () => {
			try {
				const highPriorityContactsResults = await Promise.all(
					rolesHighPriorityContacts.map((role) =>
						getHighPriorityContactsByFilter(role)
					)
				);

				const flattenedHighPriorityContacts =
					highPriorityContactsResults
						.flat()
						.filter((contact) => contact);

				const highPriorityEmails = flattenedHighPriorityContacts.map(
					(contact) => contact.email
				);

				setHighPriorityContactsNames(highPriorityEmails);
			}
			catch (error) {
				console.error('Error:', error);
			}
		};

		fetchHighPriorityContacts();
	}, [getHighPriorityContactsByFilter, userAccountsData]);

	const {data: accountRolesData, loading: accountRolesLoading} =
		useAccountRolesByAccountExternalReferenceCode(
			koroneikiAccount,
			koroneikiAccountLoading,
			!loggedUserAccount?.selectedAccountSummary.hasAdministratorRole
		);

	const availableAccountRoles = getRolesFiltered(
		accountRolesData?.accountAccountRolesByExternalReferenceCode.items,
		koroneikiAccount
	);

	const loading =
		myUserAccountLoading || userAccountsLoading || accountRolesLoading;

	const handleProvisioningKeys = useCallback(
		async (userAccount) => {
			try {
				setLoadingModal(true);

				const {items} =
					await provisioningKeys.getSingleUserSubscriptions(
						koroneikiAccount?.accountKey,
						userAccount?.emailAddress
					);

				const getLicensesKeyIds = items.map((licenseKey) => {
					return licenseKey.id;
				});

				setIsSingleSubscribedUser(items);
				setSingleSubscribedKeys(getLicensesKeyIds);
			}
			catch (error) {
				console.error('Error:', error);
			}
			setLoadingModal(false);
		},
		[koroneikiAccount?.accountKey, provisioningKeys]
	);

	useEffect(() => {
		if (!updating) {
			onOpenChange(false);

			setCurrentUserRemoving();
		}
	}, [onOpenChange, updating]);

	useEffect(() => {
		if (!updating) {
			setCurrentUserEditing();
			setSelectedAccountRoleItem();
		}
	}, [onOpenChange, updating]);

	useEffect(() => {
		if (currentUserEditing?.id) {
			setSelectedAccountRoleItem();
		}
	}, [currentUserEditing]);

	const getCurrentRoleBriefs = useCallback(
		(accountBrief) =>
			getFilteredRoleBriefsByName(accountBrief.roleBriefs, 'User'),
		[]
	);

	const checkIsValidRole = (userAccount) => {
		const isIncidentContactRole = (role) => {
			const incidentRoles = ['Security', 'Data', 'Critical'];

			return incidentRoles.some((keyword) => role.name.includes(keyword));
		};

		const roles = getCurrentRoleBriefs(userAccount.selectedAccountSummary);

		if (!roles.length) {
			return ['User'];
		}

		const memberRoles = [];

		roles.forEach((role) => {
			let roleName = role?.name;

			if (isIncidentContactRole(role)) {
				roleName = 'Incident Contact';
			}

			if (!memberRoles.includes(roleName)) {
				memberRoles.push(roleName);
			}
		});

		return memberRoles;
	};

	const handleEdit = () => {
		const currentAccountRoles =
			currentUserEditing.selectedAccountSummary.roleBriefs;

		update(
			currentUserEditing,
			currentAccountRoles,
			selectedAccountRoleItem,
			oAuthToken,
			provisioningServerAPI,
			project,
			assignUserAccountWithAccountRole,
			setCurrentUserEditing
		);
	};

	const saveSubscriptionKey = (singleSubscribedKeys) => {
		singleSubscribedKeys?.forEach(async (singleSubscribeKey) => {
			try {
				await provisioningKeys.putSubscriptionInKey(singleSubscribeKey);
			}
			catch (error) {
				console.error('Error:', error);
			}
		});
	};

	const handleSaveDisabled = () => {
		if (!selectedAccountRoleItem || updating) {
			return true;
		}

		if (isUnlimitedSupportSeats) {
			return false;
		}

		const noSupportSeatsAvailable = availableSupportSeatsCount === 0;
		const selectedSupportSeatRole = isSupportSeatRole(
			selectedAccountRoleItem?.label
		);
		const currentAccountRoles =
			currentUserEditing.selectedAccountSummary.roleBriefs;

		if (noSupportSeatsAvailable) {
			for (const role of currentAccountRoles) {
				if (isSupportSeatRole(role.name)) {
					return false;
				}
			}

			return selectedSupportSeatRole;
		}

		return availableSupportSeatsCount <= 0;
	};

	return (
		<>
			{open && currentUserRemoving !== undefined && !loadingModal && (
				<RemoveUserModal
					isSingleSubscribedUser={isSingleSubscribedUser}
					modalTitle={i18n.translate('remove-user')}
					observer={observer}
					onClose={() => onOpenChange(false)}
					onRemove={async () => {
						if (checkedBoxSubscription) {
							await saveSubscriptionKey(singleSubscribedKeys);
							await remove(currentUserRemoving);

							return;
						}

						remove(currentUserRemoving);
					}}
					removing={updating}
				>
					<p className="my-0 text-neutral-10">
						<span className="d-block font-weight-bold my-1">
							{`${i18n.translate('team-member')}: ${
								currentUserRemoving?.name
							}`}
						</span>

						{!isSingleSubscribedUser.length ? (
							<>
								{i18n.translate(
									'are-you-sure-you-want-to-remove-this-team-member-from-the-project'
								)}
							</>
						) : (
							<>
								{i18n.translate(
									'there-is-at-least-one-activation-key-for-which-this-team-member-is-the-only-one-subscribed-to-be-notified-before-the-activation-key-expires-are-you-sure-you-want-to-remove-this-team-member-and-their-notifications'
								)}
							</>
						)}
					</p>

					{!!isSingleSubscribedUser.length && (
						<div className="align-items-center d-flex pt-3">
							<ClayCheckbox
								checked={checkedBoxSubscription}
								onChange={() =>
									setCheckedBoxSubscription(
										(checkedBoxSubscription) =>
											!checkedBoxSubscription
									)
								}
							/>

							<p className="mb-0 pb-0 px-2">
								{i18n.translate(
									'i-want-to-receive-these-notifications'
								)}
							</p>

							<a
								href={
									articleNotifiedWhenMyActivationKeyIsAboutToExpireURL
								}
								rel="noreferrer noopener"
								target="_blank"
							>
								<u className="font-weight-semi-bold text-decoration-none">
									{i18n.translate('learn-more')}
								</u>

								<ClayIcon
									className="pl-1"
									symbol="order-arrow-right"
								/>
							</a>
						</div>
					)}
				</RemoveUserModal>
			)}

			<TeamMembersTableHeader
				articleAccountSupportURL={articleAccountSupportURL}
				availableSupportSeatsCount={availableSupportSeatsCount}
				count={totalUserAccounts}
				hasAdministratorRole={
					loggedUserAccount?.selectedAccountSummary
						.hasAdministratorRole
				}
				koroneikiAccount={koroneikiAccount}
				loading={loading}
				oAuthToken={oAuthToken}
				onSearch={(term) => search(term)}
				searching={searching}
			/>

			<div className="cp-team-members-table-wrapper">
				{!totalUserAccounts && !(loading || searching) && (
					<div className="d-flex justify-content-center pt-4">
						{i18n.translate('no-team-members-were-found')}
					</div>
				)}

				{!!teamMembersByStatusPaginated &&
					(totalUserAccounts || loading || searching) && (
						<Table
							className="border-0"
							columns={getColumns(
								loggedUserAccount?.selectedAccountSummary
									.hasAdministratorRole,
								articleAccountSupportURL
							)}
							hasPagination
							isLoading={loading || searching}
							paginationConfig={paginationConfig}
							rows={teamMembersByStatusPaginated?.map(
								(userAccount) => ({
									email: (
										<p className="m-0 text-truncate">
											{userAccount.emailAddress}
										</p>
									),
									name: (
										<NameColumn
											gravatarAPI={gravatarAPI}
											userAccount={userAccount}
										/>
									),
									options: (
										<OptionsColumn
											edit={
												userAccount?.id ===
												currentUserEditing?.id
											}
											highPriorityContactsNames={
												highPriorityContactsNames
											}
											onCancel={() => {
												setCurrentUserEditing();
												setSelectedAccountRoleItem();
											}}
											onEdit={() =>
												setCurrentUserEditing(
													userAccount
												)
											}
											onRemove={() => {
												setCurrentUserRemoving(
													userAccount
												);
												onOpenChange(true);
												handleProvisioningKeys(
													userAccount
												);
											}}
											onSave={() => handleEdit()}
											saveDisabled={handleSaveDisabled()}
											userAccount={userAccount}
										/>
									),
									role: (
										<RolesColumn
											accountRoles={availableAccountRoles}
											availableSupportSeatsCount={
												availableSupportSeatsCount
											}
											currentRoleBriefName={checkIsValidRole(
												userAccount
											)}
											edit={
												userAccount?.id ===
												currentUserEditing?.id
											}
											hasAccountSupportSeatRole={
												userAccount
													.selectedAccountSummary
													.hasSupportSeatRole
											}
											onClick={(
												selectedAccountRoleItem
											) =>
												setSelectedAccountRoleItem(
													selectedAccountRoleItem
												)
											}
											supportSeatsCount={
												supportSeatsCount
											}
										/>
									),
									status: (
										<StatusTag
											currentStatus={
												userAccount.lastLoginDate ||
												userAccount.dateCreated <=
													importDate
													? STATUS_TAG_TYPES.active
													: STATUS_TAG_TYPES.invited
											}
										/>
									),
									supportSeat: userAccount
										.selectedAccountSummary
										.hasSupportSeatRole &&
										!userAccount.isLiferayStaff && (
											<ClayIcon
												className="text-brand-primary-darken-2"
												symbol="check-circle-full"
											/>
										),
								})
							)}
						/>
					)}
			</div>
		</>
	);
};

export default TeamMembersTable;
