/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {LiferayStorage} from '../core/Storage';

export interface IOAuth2ClientAgentApplication {
	authorizeURL: string;
	clientId: string;
	encodedRedirectURL: string;
	fetch: typeof fetch;
	homePageURL: string;
	redirectURIs: string[];
	tokenURL: string;
}

export interface IOAuth2Client {
	FromUserAgentApplication: (
		agentName: string
	) => IOAuth2ClientAgentApplication;
}

interface ILiferay {
	CommerceContext: {
		account?: {
			accountId: number | string | null;
		};
		commerceChannelId: string;
	};
	MarketplaceCustomerFlow: {appId: number};
	OAuth2Client: IOAuth2Client;
	Service: Function;
	ThemeDisplay: {
		getBCP47LanguageId: () => string;
		getCanonicalURL: () => string;
		getCompanyGroupId: () => string;
		getCompanyId: () => string;
		getDefaultLanguageId: () => string;
		getLanguageId: () => string;
		getLayoutRelativeURL: () => string;
		getLayoutURL: () => string;
		getPathContext: () => string;
		getPathThemeImages: () => string;
		getPortalURL: () => string;
		getScopeGroupId: () => number;
		getUserEmailAddress: () => string;
		getUserId: () => string;
		getUserName: () => string;
		isSignedIn: () => boolean;
	};
	Util: {
		LocalStorage: LiferayStorage;
		SessionStorage: LiferayStorage;
		navigate: (path: string) => void;
		openToast: (options?: {
			message: string;
			onClick?: ({event}: {event: any}) => void;
			title?: string;
			type?: 'danger' | 'success';
		}) => void;
	};
	authToken: string;
	detach: Function;
	on: Function;
}
declare global {
	interface Window {
		Liferay: ILiferay;
	}
}

export const Liferay = window.Liferay || {
	CommerceContext: {},
	MarketplaceCustomerFlow: 0,
	Service: {},
	ThemeDisplay: {
		getCanonicalURL: () => window.location.href,
		getCompanyGroupId: () => '',
		getCompanyId: () => '',
		getDefaultLanguageId: () => 'en_US',
		getLanguageId: () => '',
		getLayoutRelativeURL: () => '',
		getLayoutURL: () => '',
		getPathContext: () => '',
		getPathThemeImages: () => '',
		getPortalURL: () => '',
		getUserId: () => '',
		isSignedIn: () => {
			return false;
		},
	},
	Util: {
		LocalStorage: localStorage,
		SessionStorage: sessionStorage,
	},
	detach: (
		type: keyof WindowEventMap,
		callback: EventListenerOrEventListenerObject
	) => window.removeEventListener(type, callback),
	on: (
		type: keyof WindowEventMap,
		callback: EventListenerOrEventListenerObject
	) => window.addEventListener(type, callback),
};
