/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {createContext, useContext, useEffect, useReducer} from 'react';
import {useAppPropertiesContext} from '~/contexts/AppPropertiesContext';
import {Liferay} from '~/services/liferay';
import {
	addAccountFlag,
	getAccountSubscriptionGroups,
	getAnalyticsCloudWorkspace,
	getDXPCloudEnvironment,
	getKoroneikiAccounts,
	getLiferayExperienceCloudEnvironments,
	getUserAccount,
} from '~/services/liferay/graphql/queries';
import {ROLE_TYPES, ROUTE_TYPES} from '~/utils/constants';
import {getAccountKey} from '~/utils/getAccountKey';
import {isValidPage} from '~/utils/page.validation';
import {IAccountBrief, IOrganizationBrief, IProject} from '~/utils/types';

import {ONBOARDING_STEP_TYPES} from '../utils/constants';
import reducer, {
	IOnboardingAction,
	IOnboardingState,
	actionTypes,
} from './reducer';

const AppContext = createContext<
	[IOnboardingState, React.Dispatch<IOnboardingAction>]
>([
	{
		analyticsCloudActivationSubmittedStatus: undefined,
		dxpCloudActivationSubmittedStatus: undefined,
		liferayExperienceCloudActivationSubmittedStatus: undefined,
		project: undefined,
		step: 0,
		subscriptionGroups: undefined,
		userAccount: undefined,
	},
	() => {},
]);

const AppContextProvider = ({children}: {children: React.ReactNode}) => {
	const {client} = useAppPropertiesContext();
	const [state, dispatch] = useReducer<
		React.Reducer<IOnboardingState, IOnboardingAction>
	>(reducer, {
		analyticsCloudActivationSubmittedStatus: undefined,
		dxpCloudActivationSubmittedStatus: undefined,
		liferayExperienceCloudActivationSubmittedStatus: undefined,
		project: undefined,
		step: ONBOARDING_STEP_TYPES.welcome,
		subscriptionGroups: undefined,
		userAccount: undefined,
	});

	useEffect(() => {
		const getUser = async (projectExternalReferenceCode: string) => {
			const {data} = await client.query({
				query: getUserAccount,
				variables: {
					id: Liferay.ThemeDisplay.getUserId(),
				},
			});

			if (data) {
				const isAccountAdministrator = Boolean(
					data.userAccount?.accountBriefs
						?.find(
							({
								externalReferenceCode,
							}: {
								externalReferenceCode: string;
							}) =>
								externalReferenceCode ===
								projectExternalReferenceCode
						)
						?.roleBriefs?.find(
							({name}: {name: string}) =>
								name === ROLE_TYPES.admin.key
						)
				);

				const isAccountProvisioning = data.userAccount.roleBriefs?.find(
					({name}: {name: string}) =>
						name === 'Provisioning Admin' ||
						name === 'Provisioning Member'
				);

				const isOmniAdmin = Boolean(
					data.userAccount?.roleBriefs?.find(
						({name}: {name: string}) => name === 'Administrator'
					)
				);

				const isStaff = data.userAccount?.organizationBriefs?.some(
					(organization: IOrganizationBrief) =>
						organization.name === 'Liferay Staff'
				);

				const userAccount = {
					...data.userAccount,
					isAccountAdmin: isAccountAdministrator,
					isOmniAdmin,
					isProvisioning: isAccountProvisioning,
					isStaff,
				};

				const action: IOnboardingAction = {
					payload: userAccount,
					type: actionTypes.UPDATE_USER_ACCOUNT as keyof typeof actionTypes,
				};

				dispatch(action);

				return userAccount;
			}
		};

		const getProject = async (
			externalReferenceCode: string,
			accountBrief: IAccountBrief
		): Promise<IProject | undefined> => {
			const {data: projects} = await client.query({
				query: getKoroneikiAccounts,
				variables: {
					filter: `accountKey eq '${externalReferenceCode}'`,
				},
			});

			if (projects) {
				const project = {
					...projects.c?.koroneikiAccounts?.items[0],
					id: accountBrief.id,
					name: accountBrief.name,
				} as IProject;

				const action: IOnboardingAction = {
					payload: project,
					type: actionTypes.UPDATE_PROJECT as keyof typeof actionTypes,
				};

				dispatch(action);

				return project;
			}

			return undefined;
		};

		const getSubscriptionGroups = async (accountKey: string) => {
			const {data} = await client.query({
				query: getAccountSubscriptionGroups,
				variables: {
					filter: `accountKey eq '${accountKey}' and hasActivation eq true`,
				},
			});

			if (data) {
				const items = data.c?.accountSubscriptionGroups?.items;
				const action: IOnboardingAction = {
					payload: items,
					type: actionTypes.UPDATE_SUBSCRIPTION_GROUPS as keyof typeof actionTypes,
				};

				dispatch(action);
			}
		};

		const getDXPCloudActivationStatus = async (accountKey: string) => {
			const {data} = await client.query({
				query: getDXPCloudEnvironment,
				variables: {
					filter: `accountKey eq '${accountKey}'`,
					scopeKey: Liferay.ThemeDisplay.getScopeGroupId(),
				},
			});

			if (data) {
				const status = Boolean(
					data.c?.dXPCloudEnvironments?.items?.length
				);

				const action: IOnboardingAction = {
					payload: status,
					type: actionTypes.UPDATE_DXP_CLOUD_ACTIVATION_SUBMITTED_STATUS as keyof typeof actionTypes,
				};

				dispatch(action);
			}
		};

		const getAnalyticsCloudActivationStatus = async (
			accountKey: string
		) => {
			const {data} = await client.query({
				query: getAnalyticsCloudWorkspace,
				variables: {
					filter: `accountKey eq '${accountKey}'`,
					scopeKey: Liferay.ThemeDisplay.getScopeGroupId(),
				},
			});

			if (data) {
				const status = Boolean(
					data.c?.analyticsCloudWorkspaces?.items?.length
				);

				const action: IOnboardingAction = {
					payload: status,
					type: actionTypes.UPDATE_ANALYTICS_CLOUD_ACTIVATION_SUBMITTED_STATUS as keyof typeof actionTypes,
				};

				dispatch(action);
			}
		};

		const getLiferayExperienceCloudActivationStatus = async (
			accountKey: string
		) => {
			const {data} = await client.query({
				query: getLiferayExperienceCloudEnvironments,
				variables: {
					filter: `accountKey eq '${accountKey}'`,
				},
			});

			if (data) {
				const status = Boolean(
					data.c?.liferayExperienceCloudEnvironments?.items?.length
				);

				const action: IOnboardingAction = {
					payload: status,
					type: actionTypes.UPDATE_LIFERAY_EXPERIENCE_CLOUD_ACTIVATION_SUBMITTED_STATUS as keyof typeof actionTypes,
				};

				dispatch(action);
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
					(accountBrief: IAccountBrief) =>
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
