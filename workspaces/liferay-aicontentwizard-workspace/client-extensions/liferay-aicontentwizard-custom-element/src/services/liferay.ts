/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

export type IOAuth2ClientAgentApplication = {
	authorizeURL: string;
	clientId: string;
	encodedRedirectURL: string;
	fetch: typeof fetch;
	homePageURL: string;
	redirectURIs: string[];
	tokenURL: string;
};

export type IOAuth2Client = {
	FromUserAgentApplication: (
		agentName: string
	) => IOAuth2ClientAgentApplication;
};

type ILiferay = {
	CommerceContext: {
		account?: {
			accountId: number | string | null;
		};
		commerceChannelId: string;
	};
	MarketplaceCustomerFlow: {appId: number};
	OAuth2Client: IOAuth2Client;
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
		fetch: typeof fetch;
		navigate: (path: string) => void;
		openModal: (options?: {}) => void;
		openToast: (options?: {
			message: string;
			onClick?: ({event}: {event: any}) => void;
			title?: string;
			type?: 'danger' | 'info' | 'success';
		}) => void;
	};
	authToken: string;
	fire: (event: string, data: unknown) => null;
};

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
	fire: () => null,
	on: (
		type: keyof WindowEventMap,
		callback: EventListenerOrEventListenerObject
	) => window.addEventListener(type, callback),
};
