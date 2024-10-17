/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {
	createContext,
	useContext,
	useEffect,
	useReducer
} from 'react';
import {useAppPropertiesContext} from '../../../common/contexts/AppPropertiesContext';
import {Liferay} from '../../../common/services/liferay';
import {
	getAccountByExternalReferenceCode,
	getAccountSubscriptionGroups,
	getKoroneikiAccounts,
	getStructuredContentFolders,
	getUserAccount,
} from '../../../common/services/liferay/graphql/queries';
import {ROLE_TYPES, ROUTE_TYPES} from '../../../common/utils/constants';
import {getAccountKey} from '../../../common/utils/getAccountKey';
import {isValidPage} from '../../../common/utils/page.validation';
import routerPath from '../../../common/utils/routerPath';
import reducer, {actionTypes} from './reducer';

const AppContext = createContext();

const AppContextProvider = ({children}) => {
	const {client} = useAppPropertiesContext();
	const [state, dispatch] = useReducer(reducer, {
		isQuickLinksExpanded: true,
		project: undefined,
		quickLinks: undefined,
		structuredContents: undefined,
		subscriptionGroups: undefined,
		userAccount: undefined,
		userProjectAccess: undefined
	});

	const pageRoutes = routerPath();

	useEffect(() => {
		const getUser = async (projectExternalReferenceCode) => {
			const {data} = await client.query({
				query: getUserAccount,
				variables: {
					id: Liferay.ThemeDisplay.getUserId(),
				}
			});

			if (data) {
				const isAccountAdministrator = Boolean(data.userAccount?.accountBriefs
					?.find(
						({externalReferenceCode}) =>
							externalReferenceCode ===
							projectExternalReferenceCode
					)
					?.roleBriefs?.find(
						({name}) => name === ROLE_TYPES.admin.key
					));

				const isAccountProvisioning = Boolean(data.userAccount?.accountBriefs
					?.find(
						({externalReferenceCode}) =>
							externalReferenceCode ===
							projectExternalReferenceCode
					)
					?.roleBriefs?.find(({name}) => name === 'Provisioning'));

				const isOmniAdmin = Boolean(data.userAccount?.roleBriefs?.find(
					({name}) => name === 'Administrator'
				));

				const isStaff = data.userAccount?.organizationBriefs?.some(
					(organization) => organization.name === 'Liferay Staff'
				);

				const userAccount = {
					...data.userAccount,
					isAccountAdmin: isAccountAdministrator,
					isOmniAdmin,
					isProvisioning: isAccountProvisioning,
					isStaff
				};

				dispatch({
					payload: userAccount,
					type: actionTypes.UPDATE_USER_ACCOUNT,
				});

				return userAccount;
			}
		};

		const getUserProjectAccess = async (userAccount, projectExternalReferenceCode) => {
			let userProjectAccess = Boolean(userAccount.accountBriefs?.find(
				(accountBrief) =>
					accountBrief.externalReferenceCode ===
					projectExternalReferenceCode
			));

			let denyAccess = false;

			if (!userProjectAccess) {
				try {
					const {data} = await client.query({
						query: getAccountByExternalReferenceCode,
						variables: {
							externalReferenceCode: projectExternalReferenceCode
						}
					});

					userProjectAccess = Boolean(data);
				}
				catch (error) {
					denyAccess = true;
				}
			}

			const currentUserProjectAccess = {
				hasProjectAccess: userAccount.isOmniAdmin || userProjectAccess,
				denyAccess
			};

			dispatch({
				payload: currentUserProjectAccess,
				type: actionTypes.UPDATE_USER_PROJECT_ACCESS
			});

			return currentUserProjectAccess;
		}

		const getProject = async (externalReferenceCode, accountBrief) => {
			const {data: projects} = await client.query({
				fetchPolicy: 'network-only',
				query: getKoroneikiAccounts,
				variables: {
					filter: `accountKey eq '${externalReferenceCode}'`,
				},
			});

			if (projects) {
				const currentProject = {
					...projects.c.koroneikiAccounts.items[0],
					id: accountBrief.id,
					name: accountBrief.name,
				};

				dispatch({
					payload: currentProject,
					type: actionTypes.UPDATE_PROJECT,
				});
			}
		};

		const getSubscriptionGroups = async (accountKey) => {
			const {data: dataSubscriptionGroups} = await client.query({
				query: getAccountSubscriptionGroups,
				variables: {
					filter: `accountKey eq '${accountKey}'`,
				},
			});

			if (dataSubscriptionGroups) {
				const items =
					dataSubscriptionGroups?.c?.accountSubscriptionGroups?.items;
				dispatch({
					payload: items,
					type: actionTypes.UPDATE_SUBSCRIPTION_GROUPS,
				});
			}
		};

		const getStructuredContents = async () => {
			const {data} = await client.query({
				query: getStructuredContentFolders,
				variables: {
					filter: `name eq 'actions'`,
					siteKey: Liferay.ThemeDisplay.getScopeGroupId(),
				},
			});

			if (data) {
				dispatch({
					payload:
						data.structuredContentFolders?.items[0]
							?.structuredContents?.items,
					type: actionTypes.UPDATE_STRUCTURED_CONTENTS,
				});
			}
		};

		const fetchData = async () => {
			const projectExternalReferenceCode = getAccountKey();

			if (!projectExternalReferenceCode) {
				Liferay.Util.navigate(pageRoutes.home());
			}

			const user = await getUser(projectExternalReferenceCode);

			if (user) {
				const userProjectAccess = await getUserProjectAccess(
					user,
					projectExternalReferenceCode
				);
	
				if (userProjectAccess.hasProjectAccess) {
					const isValid = await isValidPage(
						client,
						user,
						projectExternalReferenceCode,
						ROUTE_TYPES.project
					);

					if (isValid) {
						let accountBrief = user.accountBriefs?.find(
							(accountBrief) =>
								accountBrief.externalReferenceCode ===
								projectExternalReferenceCode
						);

						if (!accountBrief && !userProjectAccess.denyAccess) {
							const {data: dataAccount} = await client.query({
								query: getAccountByExternalReferenceCode,
								variables: {
									externalReferenceCode: projectExternalReferenceCode,
								}
							});

							if (dataAccount) {
								accountBrief =
									dataAccount?.accountByExternalReferenceCode;
							}
						}

						if (accountBrief) {
							getProject(projectExternalReferenceCode, accountBrief);
							getSubscriptionGroups(projectExternalReferenceCode);
						}

						getStructuredContents();
					}
				}
			}
		};

		fetchData();
		// eslint-disable-next-line react-hooks/exhaustive-deps
	}, []);

	return (
		<AppContext.Provider value={[state, dispatch]}>
			{children}
		</AppContext.Provider>
	);
};

const useCustomerPortal = () => useContext(AppContext);

export {AppContext, AppContextProvider, useCustomerPortal};
