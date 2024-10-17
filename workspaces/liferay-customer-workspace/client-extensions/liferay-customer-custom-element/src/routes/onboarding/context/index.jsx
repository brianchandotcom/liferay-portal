/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {createContext, useContext, useEffect, useReducer} from 'react';
import {useAppPropertiesContext} from '../../../common/contexts/AppPropertiesContext';
import {Liferay} from '../../../common/services/liferay';
import {
	addAccountFlag,
	getAccountSubscriptionGroups,
	getAnalyticsCloudWorkspace,
	getDXPCloudEnvironment,
	getKoroneikiAccounts,
	getLiferayExperienceCloudEnvironments,
	getUserAccount,
} from '../../../common/services/liferay/graphql/queries';
import {ROLE_TYPES, ROUTE_TYPES} from '../../../common/utils/constants';
import {getAccountKey} from '../../../common/utils/getAccountKey';
import {isValidPage} from '../../../common/utils/page.validation';
import {ONBOARDING_STEP_TYPES} from '../utils/constants';
import reducer, {actionTypes} from './reducer';

const AppContext = createContext();

const AppContextProvider = ({children}) => {
	const {client} = useAppPropertiesContext();
	const [state, dispatch] = useReducer(reducer, {
		analyticsCloudActivationSubmittedStatus: undefined,
		dxpCloudActivationSubmittedStatus: undefined,
		koroneikiAccount: {},
		liferayExperienceCloudActivationSubmittedStatus: undefined,
		project: undefined,
		step: ONBOARDING_STEP_TYPES.welcome,
		subscriptionGroups: undefined,
		userAccount: undefined,
	});

	useEffect(() => {
		const getUser = async (projectExternalReferenceCode) => {
			const {data} = await client.query({
				query: getUserAccount,
				variables: {
					id: Liferay.ThemeDisplay.getUserId(),
				},
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

		const getProject = async (externalReferenceCode, accountBrief) => {
			const {data: projects} = await client.query({
				query: getKoroneikiAccounts,
				variables: {
					filter: `accountKey eq '${externalReferenceCode}'`,
				},
			});

			if (projects) {
				dispatch({
					payload: {
						...projects.c.koroneikiAccounts.items[0],
						id: accountBrief.id,
						name: accountBrief.name,
					},
					type: actionTypes.UPDATE_PROJECT,
				});
			}
		};

		const getSubscriptionGroups = async (accountKey) => {
			const {data} = await client.query({
				query: getAccountSubscriptionGroups,
				variables: {
					filter: `accountKey eq '${accountKey}' and hasActivation eq true`,
				},
			});

			if (data) {
				const items = data.c?.accountSubscriptionGroups?.items;
				dispatch({
					payload: items,
					type: actionTypes.UPDATE_SUBSCRIPTION_GROUPS,
				});
			}
		};

		const getDXPCloudActivationStatus = async (accountKey) => {
			const {data} = await client.query({
				query: getDXPCloudEnvironment,
				variables: {
					filter: `accountKey eq '${accountKey}'`,
					scopeKey: Liferay.ThemeDisplay.getScopeGroupId(),
				},
			});

			if (data) {
				const status = Boolean(data.c?.dXPCloudEnvironments?.items?.length);

				dispatch({
					payload: status,
					type:
						actionTypes.UPDATE_DXP_CLOUD_ACTIVATION_SUBMITTED_STATUS,
				});
			}
		};

		const getAnalyticsCloudActivationStatus = async (accountKey) => {
			const {data} = await client.query({
				query: getAnalyticsCloudWorkspace,
				variables: {
					filter: `accountKey eq '${accountKey}'`,
					scopeKey: Liferay.ThemeDisplay.getScopeGroupId(),
				},
			});

			if (data) {
				const status = Boolean(data.c?.analyticsCloudWorkspaces?.items
					?.length);

				dispatch({
					payload: status,
					type:
						actionTypes.UPDATE_ANALYTICS_CLOUD_ACTIVATION_SUBMITTED_STATUS,
				});
			}
		};

		const getLiferayExperienceCloudActivationStatus = async (
			accountKey
		) => {
			const {data} = await client.query({
				query: getLiferayExperienceCloudEnvironments,
				variables: {
					filter: `accountKey eq '${accountKey}'`,
				},
			});

			if (data) {
				const status = Boolean(data.c?.liferayExperienceCloudEnvironments
					?.items?.length);

				dispatch({
					payload: status,
					type:
						actionTypes.UPDATE_LIFERAY_EXPERIENCE_CLOUD_ACTIVATION_SUBMITTED_STATUS,
				});
			}
		};

		const fetchData = async () => {
			const projectExternalReferenceCode = getAccountKey();

			const user = await getUser(projectExternalReferenceCode);

			if (!user) {
				return;
			}

			const isValid = await isValidPage(
				client,
				user,
				projectExternalReferenceCode,
				ROUTE_TYPES.onboarding
			);

			if (user && isValid) {
				const accountBrief = user.accountBriefs?.find(
					(accountBrief) =>
						accountBrief.externalReferenceCode ===
						projectExternalReferenceCode
				);

				if (accountBrief) {
					const project = await getProject(
						projectExternalReferenceCode,
						accountBrief
					);
					getSubscriptionGroups(projectExternalReferenceCode);
					getDXPCloudActivationStatus(projectExternalReferenceCode);
					getAnalyticsCloudActivationStatus(
						projectExternalReferenceCode
					);
					getLiferayExperienceCloudActivationStatus(
						projectExternalReferenceCode
					);

					client.mutate({
						context: {
							displaySuccess: false,
							type: 'liferay-rest',
						},
						mutation: addAccountFlag,
						variables: {
							accountFlag: {
								accountEntryId: project?.id,
								accountKey: projectExternalReferenceCode,
								finished: true,
								name: ROUTE_TYPES.onboarding,
								r_accountEntryToAccountFlag_accountEntryId:
									accountBrief?.id,
							},
						},
					});
				}
			}
		};

		fetchData();
	}, [client]);

	return (
		<AppContext.Provider value={[state, dispatch]}>
			{children}
		</AppContext.Provider>
	);
};

const useOnboarding = () => useContext(AppContext);

export {AppContext, AppContextProvider, useOnboarding};
