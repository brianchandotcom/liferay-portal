/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {createContext, useContext, useEffect, useMemo, useReducer} from 'react';
import {useAppPropertiesContext} from '~/contexts/AppPropertiesContext';
import {Liferay} from '~/services/liferay';
import {
	getAccountByExternalReferenceCode,
	getAccountSubscriptionGroups,
	getAccountSubscriptions,
	getKoroneikiAccounts,
	getStructuredContentFolders,
	getUserAccount,
} from '~/services/liferay/graphql/queries';
import {ROLE_TYPES, ROUTE_TYPES} from '~/utils/constants';
import {getAccountKey} from '~/utils/getAccountKey';
import {isValidPage} from '~/utils/page.validation';
import routerPath from '~/utils/routerPath';
import {
	IAccountBrief,
	IAccountSubscription,
	IAccountSubscriptionGroup,
	IProject,
	IUserAccount,
} from '~/utils/types';

import reducer, {ActionPayload, IAction, IState, actionTypes} from './reducer';

const AppContext = createContext<[IState, React.Dispatch<IAction>]>([
	{
		isQuickLinksExpanded: true,
		page: undefined,
		project: undefined,
		quickLinks: undefined,
		structuredContents: undefined,
		subscriptionGroups: undefined,
		subscriptions: undefined,
		userAccount: undefined,
		userProjectAccess: undefined,
	},
	() => {},
]);

const AppContextProvider = ({children}: {children: React.ReactNode}) => {
	const {client} = useAppPropertiesContext();
	const [state, dispatch] = useReducer<React.Reducer<IState, IAction>>(
		reducer,
		{
			isQuickLinksExpanded: true,
			page: undefined,
			project: undefined,
			quickLinks: undefined,
			structuredContents: undefined,
			subscriptionGroups: undefined,
			subscriptions: undefined,
			userAccount: undefined,
			userProjectAccess: undefined,
		}
	);

	const pageRoutes = useMemo(() => routerPath(), []);

	useEffect(() => {
		const getUser = async (
			projectExternalReferenceCode: string
		): Promise<IUserAccount | undefined> => {
			const {data} = await client.query<{userAccount: IUserAccount}>({
				query: getUserAccount,
				variables: {
					id: Liferay.ThemeDisplay.getUserId(),
				},
			});

			if (data?.userAccount) {
				const isAccountAdministrator = Boolean(
					data.userAccount.accountBriefs
						?.find(
							({externalReferenceCode}) =>
								externalReferenceCode ===
								projectExternalReferenceCode
						)
						?.roleBriefs?.find(
							({name}) => name === ROLE_TYPES.admin.key
						)
				);

				const isAccountProvisioning = Boolean(
					data.userAccount.roleBriefs?.some(
						(role) =>
							role.name === 'Provisioning Admin' ||
							role.name === 'Provisioning Member'
					)
				);

				const isOmniAdmin = Boolean(
					data.userAccount.roleBriefs?.find(
						({name}) => name === 'Administrator'
					)
				);

				const isStaff = data.userAccount.organizationBriefs?.some(
					(organization) => organization.name === 'Liferay Staff'
				);

				const userAccount: IUserAccount = {
					...data.userAccount,
					isAccountAdmin: isAccountAdministrator,
					isOmniAdmin,
					isProvisioning: isAccountProvisioning,
					isStaff: isStaff as boolean,
				};

				dispatch({
					payload: userAccount,
					type: actionTypes.UPDATE_USER_ACCOUNT as keyof typeof actionTypes,
				});

				return userAccount;
			}

			return undefined;
		};

		const getUserProjectAccess = async (
			userAccount: IUserAccount,
			projectExternalReferenceCode: string
		): Promise<{denyAccess: boolean; hasProjectAccess: boolean}> => {
			let userProjectAccess = Boolean(
				userAccount.accountBriefs?.find(
					(accountBrief) =>
						accountBrief.externalReferenceCode ===
						projectExternalReferenceCode
				)
			);

			let denyAccess = false;

			if (!userProjectAccess) {
				try {
					const {data} = await client.query({
						query: getAccountByExternalReferenceCode,
						variables: {
							externalReferenceCode: projectExternalReferenceCode,
						},
					});

					userProjectAccess = Boolean(data);
				}
				catch (error) {
					denyAccess = true;
				}
			}

			const currentUserProjectAccess: {
				denyAccess: boolean;
				hasProjectAccess: boolean;
			} = {
				denyAccess,
				hasProjectAccess:
					userAccount.isOmniAdmin || userProjectAccess || !denyAccess,
			};

			dispatch({
				payload: currentUserProjectAccess as unknown as ActionPayload,
				type: actionTypes.UPDATE_USER_PROJECT_ACCESS as keyof typeof actionTypes,
			});

			return currentUserProjectAccess;
		};

		const getProject = async (
			externalReferenceCode: string,
			accountBrief: IAccountBrief
		) => {
			const {data: projects} = await client.query<{
				c: {koroneikiAccounts: {items: IProject[]}};
			}>({
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
					type: actionTypes.UPDATE_PROJECT as keyof typeof actionTypes,
				});
			}
		};

		const getSubscriptions = async (accountKey: string) => {
			const {data: dataSubscriptions} = await client.query<{
				c: {
					accountSubscriptions: {
						items: IAccountSubscription[];
					};
				};
			}>({
				query: getAccountSubscriptions,
				variables: {
					filter: `accountKey eq '${accountKey}'`,
				},
			});

			if (dataSubscriptions) {
				const items = dataSubscriptions?.c?.accountSubscriptions?.items;

				dispatch({
					payload: items as unknown as IAccountSubscription[],
					type: actionTypes.UPDATE_SUBSCRIPTIONS as keyof typeof actionTypes,
				});
			}
		};

		const getSubscriptionGroups = async (accountKey: string) => {
			const {data: dataSubscriptionGroups} = await client.query<{
				c: {
					accountSubscriptionGroups: {
						items: IAccountSubscriptionGroup[];
					};
				};
			}>({
				query: getAccountSubscriptionGroups,
				variables: {
					filter: `accountKey eq '${accountKey}'`,
				},
			});

			if (dataSubscriptionGroups) {
				const items =
					dataSubscriptionGroups?.c?.accountSubscriptionGroups?.items;

				dispatch({
					payload: items as unknown as IAccountSubscriptionGroup[],
					type: actionTypes.UPDATE_SUBSCRIPTION_GROUPS as keyof typeof actionTypes,
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
					type: actionTypes.UPDATE_STRUCTURED_CONTENTS as keyof typeof actionTypes,
				});
			}
		};

		const fetchData = async () => {
			const projectExternalReferenceCode = getAccountKey();

			if (!projectExternalReferenceCode) {
				Liferay.Util.navigate(pageRoutes.home());

				return;
			}

			const user = await getUser(projectExternalReferenceCode);

			if (user) {
				const {denyAccess, hasProjectAccess} =
					await getUserProjectAccess(
						user,
						projectExternalReferenceCode
					);

				if (hasProjectAccess) {
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

						if (!accountBrief && !denyAccess) {
							const {data: dataAccount} = await client.query({
								query: getAccountByExternalReferenceCode,
								variables: {
									externalReferenceCode:
										projectExternalReferenceCode,
								},
							});

							accountBrief =
								dataAccount?.accountByExternalReferenceCode;
						}

						if (accountBrief) {
							getProject(
								projectExternalReferenceCode,
								accountBrief
							);
							getSubscriptions(
								accountBrief.externalReferenceCode
							);
							getSubscriptionGroups(projectExternalReferenceCode);
						}

						getStructuredContents();
					}
				}
			}
		};

		fetchData();
	}, [client, pageRoutes]);

	return (
		<AppContext.Provider value={[state, dispatch]}>
			{children}
		</AppContext.Provider>
	);
};

const useCustomerPortal = () => useContext(AppContext);

export {AppContext, AppContextProvider, useCustomerPortal};
