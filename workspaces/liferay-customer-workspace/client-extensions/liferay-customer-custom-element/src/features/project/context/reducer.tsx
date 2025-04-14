/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {
	IAccountSubscription,
	IAccountSubscriptionGroup,
	IBusinessEvent,
	IProject,
	IUserAccount,
} from '~/utils/types';

export const actionTypes = {
	UPDATE_BUSINESS_EVENTS: 'UPDATE_BUSINESS_EVENTS',
	UPDATE_PAGE: 'UPDATE_PAGE',
	UPDATE_PROJECT: 'UPDATE_PROJECT',
	UPDATE_QUICK_LINKS: 'UPDATE_QUICK_LINKS',
	UPDATE_QUICK_LINKS_EXPANDED_PANEL: 'UPDATE_QUICK_LINKS_EXPANDED_PANEL',
	UPDATE_STRUCTURED_CONTENTS: 'UPDATE_STRUCTURED_CONTENTS',
	UPDATE_SUBSCRIPTION_GROUPS: 'UPDATE_SUBSCRIPTION_GROUPS',
	UPDATE_SUBSCRIPTIONS: 'UPDATE_SUBSCRIPTIONS',
	UPDATE_USER_ACCOUNT: 'UPDATE_USER_ACCOUNT',
	UPDATE_USER_PROJECT_ACCESS: 'UPDATE_USER_PROJECT_ACCESS',
};

export type ActionPayload =
	| string
	| IAccountSubscription[]
	| IAccountSubscriptionGroup[]
	| IUserAccount
	| IProject
	| boolean
	| undefined;

export interface IAction {
	payload: ActionPayload;
	type: keyof typeof actionTypes;
}

export interface IState {
	businessEvents: IBusinessEvent | undefined;
	isQuickLinksExpanded: boolean;
	page: string | undefined;
	project: IProject | undefined;
	quickLinks: string | undefined;
	structuredContents: string | undefined;
	subscriptionGroups: IAccountSubscriptionGroup[] | undefined;
	subscriptions: IAccountSubscription[] | undefined;
	userAccount: IUserAccount | undefined;
	userProjectAccess: boolean | undefined;
}

const reducer = (state: IState, action: IAction): IState => {
	switch (action.type) {
		case actionTypes.UPDATE_USER_ACCOUNT:
			return {
				...state,
				userAccount: action.payload as IUserAccount,
			};
		case actionTypes.UPDATE_BUSINESS_EVENTS:
			return {
				...state,
				businessEvents: action.payload as IBusinessEvent,
			};
		case actionTypes.UPDATE_PROJECT:
			return {
				...state,
				project: action.payload as IProject,
			};
		case actionTypes.UPDATE_QUICK_LINKS:
			return {
				...state,
				quickLinks: action.payload as string,
			};
		case actionTypes.UPDATE_QUICK_LINKS_EXPANDED_PANEL:
			return {
				...state,
				isQuickLinksExpanded: action.payload as boolean,
			};
		case actionTypes.UPDATE_STRUCTURED_CONTENTS:
			return {
				...state,
				structuredContents: action.payload as string,
			};
		case actionTypes.UPDATE_SUBSCRIPTION_GROUPS:
			return {
				...state,
				subscriptionGroups:
					action.payload as unknown as IAccountSubscriptionGroup[],
			};
		case actionTypes.UPDATE_SUBSCRIPTIONS:
			return {
				...state,
				subscriptions:
					action.payload as unknown as IAccountSubscription[],
			};
		case actionTypes.UPDATE_PAGE:
			return {
				...state,
				page: action.payload as string,
			};
		case actionTypes.UPDATE_USER_PROJECT_ACCESS:
			return {
				...state,
				userProjectAccess: action.payload as boolean,
			};
		default:
			return state;
	}
};

export default reducer;
