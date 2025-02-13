/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {IAccountSubscriptionGroup, IProject, IUserAccount} from '~/utils/types';

export const actionTypes = {
	CHANGE_STEP: 'CHANGE_STEP',
	UPDATE_ANALYTICS_CLOUD_ACTIVATION_SUBMITTED_STATUS:
		'UPDATE_ANALYTICS_CLOUD_ACTIVATION_SUBMITTED_STATUS',
	UPDATE_DXP_CLOUD_ACTIVATION_SUBMITTED_STATUS:
		'UPDATE_DXP_CLOUD_ACTIVATION_SUBMITTED_STATUS',
	UPDATE_LIFERAY_EXPERIENCE_CLOUD_ACTIVATION_SUBMITTED_STATUS:
		'UPDATE_LIFERAY_EXPERIENCE_CLOUD_ACTIVATION_SUBMITTED_STATUS',
	UPDATE_PROJECT: 'UPDATE_PROJECT',
	UPDATE_SUBSCRIPTION_GROUPS: 'UPDATE_SUBSCRIPTION_GROUPS',
	UPDATE_USER_ACCOUNT: 'UPDATE_USER_ACCOUNT',
};

export type ActionPayload =
	| string
	| IProject
	| IUserAccount
	| IAccountSubscriptionGroup[]
	| boolean
	| undefined;

export interface IOnboardingAction {
	payload: ActionPayload;
	type: keyof typeof actionTypes;
}

export interface IOnboardingState {
	analyticsCloudActivationSubmittedStatus: boolean | undefined;
	dxpCloudActivationSubmittedStatus: boolean | undefined;
	liferayExperienceCloudActivationSubmittedStatus: boolean | undefined;
	project: IProject | undefined;
	step: number;
	subscriptionGroups: IAccountSubscriptionGroup[] | undefined;
	userAccount: IUserAccount | undefined;
}

const reducer = (
	state: IOnboardingState,
	action: IOnboardingAction
): IOnboardingState => {
	switch (action.type) {
		case actionTypes.CHANGE_STEP:
			return {...state, step: action.payload as unknown as number};
		case actionTypes.UPDATE_PROJECT:
			return {...state, project: action.payload as IProject};
		case actionTypes.UPDATE_USER_ACCOUNT:
			return {...state, userAccount: action.payload as IUserAccount};
		case actionTypes.UPDATE_SUBSCRIPTION_GROUPS:
			return {
				...state,
				subscriptionGroups:
					action.payload as IAccountSubscriptionGroup[],
			};
		case actionTypes.UPDATE_DXP_CLOUD_ACTIVATION_SUBMITTED_STATUS:
			return {
				...state,
				dxpCloudActivationSubmittedStatus: action.payload as boolean,
			};
		case actionTypes.UPDATE_LIFERAY_EXPERIENCE_CLOUD_ACTIVATION_SUBMITTED_STATUS:
			return {
				...state,
				liferayExperienceCloudActivationSubmittedStatus:
					action.payload as boolean,
			};
		case actionTypes.UPDATE_ANALYTICS_CLOUD_ACTIVATION_SUBMITTED_STATUS:
			return {
				...state,
				analyticsCloudActivationSubmittedStatus:
					action.payload as boolean,
			};
		default:
			return state;
	}
};

export default reducer;
