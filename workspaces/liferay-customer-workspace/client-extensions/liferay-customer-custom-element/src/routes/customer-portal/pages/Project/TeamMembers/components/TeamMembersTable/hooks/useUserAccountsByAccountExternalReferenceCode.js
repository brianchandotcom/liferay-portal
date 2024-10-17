/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {NetworkStatus} from '@apollo/client';
import {useEffect, useMemo, useState} from 'react';
import {addContactRoleNameByEmailByProject} from '~/common/services/liferay/rest/raysource/LicenseKeys';

import useSearchTerm from '../../../../../../../../common/hooks/useSearchTerm';
import {useGetUserAccountsByAccountExternalReferenceCode} from '../../../../../../../../common/services/liferay/graphql/user-accounts';
import {
	getRaysourceContactRoleName,
	getRaysourceContactRoleNameURLParameter,
} from '../utils/getRaysourceContactRoleName';
import useDeleteUserAccount from './useDeleteUserAccount';
import useSupportSeatsCount from './useSupportSeatsCount';
import useUpdateUserAccount from './useUpdateUserAccount';

const getFilter = (searchTerm) => {
	if (searchTerm) {
		return `(contains(name, '${searchTerm}') or contains(emailAddress, '${searchTerm}') or userGroupRoleNames/any(s:contains(s, '${searchTerm}')))`;
	}

	return '';
};

export default function useUserAccountsByAccountExternalReferenceCode(
	externalReferenceCode,
	koroneikiAccountLoading
) {
	const [searching, setSearching] = useState(false);

	const {
		data: userAccountData,
		networkStatus,
		refetch,
	} = useGetUserAccountsByAccountExternalReferenceCode(
		externalReferenceCode,
		{
			notifyOnNetworkStatusChange: true,
			skip: koroneikiAccountLoading,
		}
	);

	const data = useMemo(() => {
		const items = (
			userAccountData?.accountUserAccountsByExternalReferenceCode
				?.items ?? []
		).filter((account) => {
			const accountBriefByExternalReferenceCode =
				account.accountBriefs.find(
					(accountBrief) =>
						accountBrief.externalReferenceCode ===
						externalReferenceCode
				);

			if (
				accountBriefByExternalReferenceCode &&
				accountBriefByExternalReferenceCode.roleBriefs.some(
					(roleBrief) => roleBrief.name === 'Provisioning'
				)
			) {
				return false;
			}

			return true;
		});

		return {
			...userAccountData,
			accountUserAccountsByExternalReferenceCode: {
				...userAccountData?.accountUserAccountsByExternalReferenceCode,
				items,
				totalCount: items.length,
			},
		};
	}, [userAccountData, externalReferenceCode]);

	const {
		deleteContactRoles,
		deleteUserAccount,
		loading: removing,
	} = useDeleteUserAccount();

	const {
		loading: updating,
		replaceAccountRole,
		updateContactRoles,
	} = useUpdateUserAccount();

	useEffect(() => {
		if (networkStatus === NetworkStatus.refetch) {
			setSearching(false);
		}
	}, [networkStatus]);

	const supportSeatsCount = useSupportSeatsCount(
		data?.accountUserAccountsByExternalReferenceCode,
		searching
	);

	const [, onSearch] = useSearchTerm((searchTerm) => {
		setSearching(true);

		refetch({
			filter: getFilter(searchTerm),
		});
	});

	const remove = (userAccount) => {
		const contactRoleNameURLParameters =
			userAccount.selectedAccountSummary.roleBriefs?.map((roleBrief) =>
				getRaysourceContactRoleNameURLParameter(roleBrief.name)
			);

		deleteContactRoles({
			onCompleted: (_, {variables}) =>
				deleteUserAccount({
					variables: {
						emailAddress: variables.contactEmail,
						externalReferenceCode: variables.externalReferenceCode,
					},
				}),
			variables: {
				contactEmail: userAccount.emailAddress,
				contactRoleNames: contactRoleNameURLParameters.join('&'),
				externalReferenceCode,
			},
		});
	};

	const update = (
		userAccount,
		currentAccountRoles,
		newAccountRoleItem,
		oAuthToken,
		provisioningServerAPI,
		project,
		assignUserAccountWithAccountRole,
		setCurrentUserEditing
	) => {
		const newContactRoleNameURLParameter =
			getRaysourceContactRoleNameURLParameter(
				newAccountRoleItem.raysourceName
			);

		const currentContactRoleNameURLParameters = currentAccountRoles.map(
			(roleBrief) =>
				getRaysourceContactRoleNameURLParameter(roleBrief.name)
		);

		if (Array.isArray(newAccountRoleItem)) {
			const hasConflictedRole = currentAccountRoles.some((currentRole) =>
				newAccountRoleItem.some(
					(newRole) => currentRole.name === newRole.label
				)
			);

			if (!hasConflictedRole) {
				newAccountRoleItem.map((accountRole) => {
					const newAccountRoleRaysourceNameURLParameter =
						getRaysourceContactRoleNameURLParameter(
							accountRole.raysourceName
						);

					updateContactRoles({
						onCompleted: () =>
							currentAccountRoles.map((currentAccountRole) => {
								deleteContactRoles({
									onCompleted: () =>
										replaceAccountRole({
											variables: {
												currentAccountRoleId:
													currentAccountRole.id,
												emailAddress:
													userAccount.emailAddress,
												externalReferenceCode,
												newAccountRoleId:
													accountRole.value,
											},
										}),
									variables: {
										contactEmail: userAccount.emailAddress,
										contactRoleNames:
											currentContactRoleNameURLParameters.join(
												'&'
											),
										externalReferenceCode,
									},
								});
							}),
						variables: {
							contactEmail: userAccount.emailAddress,
							contactRoleName:
								newAccountRoleRaysourceNameURLParameter,
							externalReferenceCode,
						},
					});
				});
			}

			if (hasConflictedRole) {
				const nonConflictingCurrentAccountRoles =
					currentAccountRoles.filter((currentRole) => {
						return !newAccountRoleItem.some(
							(newRole) => currentRole.name === newRole.label
						);
					});

				const nonConflictingNewAccountRoleItem =
					newAccountRoleItem.filter((newRole) => {
						return !currentAccountRoles.some(
							(currentRole) => newRole.label === currentRole.name
						);
					});

				const currentRaysourceContactRoleNameURLParameters =
					nonConflictingCurrentAccountRoles.map((roleBrief) =>
						getRaysourceContactRoleNameURLParameter(roleBrief.name)
					);

				if (
					nonConflictingNewAccountRoleItem.length &&
					nonConflictingCurrentAccountRoles.length
				) {
					nonConflictingNewAccountRoleItem.map((accountRole) => {
						const oldAccountRoleRaysourceNameURLParameter =
							getRaysourceContactRoleNameURLParameter(
								accountRole.raysourceName
							);

						updateContactRoles({
							onCompleted: () =>
								nonConflictingCurrentAccountRoles.map(
									(currentAccountRole) => {
										deleteContactRoles({
											onCompleted: () =>
												replaceAccountRole({
													variables: {
														currentAccountRoleId:
															currentAccountRole.id,
														emailAddress:
															userAccount.emailAddress,
														externalReferenceCode,
														newAccountRoleId:
															accountRole.value,
													},
												}),
											variables: {
												contactEmail:
													userAccount.emailAddress,
												contactRoleNames:
													currentRaysourceContactRoleNameURLParameters.join(
														'&'
													),
												externalReferenceCode,
											},
										});
									}
								),
							variables: {
								contactEmail: userAccount.emailAddress,
								contactRoleName:
									oldAccountRoleRaysourceNameURLParameter,
								externalReferenceCode,
							},
						});
					});
				}

				if (
					!nonConflictingNewAccountRoleItem.length &&
					nonConflictingCurrentAccountRoles.length
				) {
					newAccountRoleItem.map((accountRole) => {
						nonConflictingCurrentAccountRoles.map(
							(currentAccountRole) => {
								deleteContactRoles({
									onCompleted: () =>
										replaceAccountRole({
											variables: {
												currentAccountRoleId:
													currentAccountRole.id,
												emailAddress:
													userAccount.emailAddress,
												externalReferenceCode,
												newAccountRoleId:
													accountRole.value,
											},
										}),
									variables: {
										contactEmail: userAccount.emailAddress,
										contactRoleNames:
											currentRaysourceContactRoleNameURLParameters.join(
												'&'
											),
										externalReferenceCode,
									},
								});
							}
						);
					});
				}

				if (
					nonConflictingNewAccountRoleItem.length &&
					!nonConflictingCurrentAccountRoles.length
				) {
					const firstName = userAccount?.name.split(' ')[0];
					const lastName = userAccount?.name.split(' ')[1];

					nonConflictingNewAccountRoleItem?.map(
						async (accountRole) => {
							const context = {
								displayErrors: true,
								displayServerError: false,
								displaySuccess: true,
							};

							const oldAccountRoleRaysourceName =
								getRaysourceContactRoleName(
									accountRole.raysourceName
								);

							await addContactRoleNameByEmailByProject({
								accountKey: project.accountKey,
								emailURI: encodeURI(userAccount.emailAddress),
								firstName,
								lastName,
								oAuthToken,
								provisioningServerAPI,
								roleName: oldAccountRoleRaysourceName,
							});

							await assignUserAccountWithAccountRole({
								context,
								variables: {
									accountKey: project.accountKey,
									accountRoleId: accountRole.value,
									emailAddress: encodeURI(
										userAccount.emailAddress
									),
								},
							});

							if (setCurrentUserEditing) {
								setCurrentUserEditing();
							}
						}
					);
				}
			}
		}

		if (!Array.isArray(newAccountRoleItem)) {
			updateContactRoles({
				onCompleted: () =>
					currentAccountRoles.map((currentAccountRole) => {
						deleteContactRoles({
							onCompleted: () =>
								replaceAccountRole({
									variables: {
										currentAccountRoleId:
											currentAccountRole.id,
										emailAddress: userAccount.emailAddress,
										externalReferenceCode,
										newAccountRoleId:
											newAccountRoleItem.value,
									},
								}),
							variables: {
								contactEmail: userAccount.emailAddress,
								contactRoleNames:
									currentContactRoleNameURLParameters.join(
										'&'
									),
								externalReferenceCode,
							},
						});
					}),
				variables: {
					contactEmail: userAccount.emailAddress,
					contactRoleName: newContactRoleNameURLParameter,
					externalReferenceCode,
				},
			});
		}
	};

	return [
		supportSeatsCount,
		{
			data,
			loading:
				koroneikiAccountLoading ||
				networkStatus === NetworkStatus.loading,
			refetch,
			remove,
			search: onSearch,
			searching: networkStatus === NetworkStatus.setVariables,
			update,
			updating: updating || removing,
		},
	];
}
