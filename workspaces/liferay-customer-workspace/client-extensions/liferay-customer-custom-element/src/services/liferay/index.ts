/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

declare global {
	interface Window {
		Liferay?: any;
	}
}

export const Liferay = window.Liferay || {
	BREAKPOINTS: {
		PHONE: 0,
		TABLET: 0,
	},
	FeatureFlags: {},
	OAuth2Client: {
		FromUserAgentApplication: (
			_userAgentApplicationId: string
		): {
			_getOrRequestToken: () => Promise<string>;
			fetch: (_url: string, _options?: RequestInit) => Promise<Response>;
		} => {
			return {
				_getOrRequestToken: () => Promise.resolve(''),
				fetch: (_url, _options = {}) => Promise.resolve(new Response()),
			};
		},
	},
	ThemeDisplay: {
		getBCP47LanguageId: () => 'en-US',
		getCanonicalURL: () => window.location.href,
		getCompanyGroupId: () => 0,
		getLanguageId: () => 'en_US',
		getLayoutRelativeURL: () => '',
		getLayoutURL: () => '',
		getPathThemeImages: () => null,
		getPortalURL: () => window.location.origin,
		getScopeGroupId: () => 0,
		getSiteGroupId: () => 0,
		getUserId: () => '0',
	},
	Util: {
		SessionStorage: Object.assign(sessionStorage, {
			Types: {},
		}),
		isTablet: () => false,
		navigate: (path: string | URL) => window.location.assign(path),
		openToast: (options: {
			message: string;
			title?: string;
			type?: 'danger' | 'info' | 'success' | 'warning';
		}) => alert(options.message),
	},
	authToken: '',
	detach: (type: string, callback: EventListenerOrEventListenerObject) =>
		window.removeEventListener(type, callback),
	on: (type: string, callback: EventListenerOrEventListenerObject) =>
		window.addEventListener(type, callback),
	once: (type: string, callback: EventListener) =>
		window.addEventListener(
			type,
			function handler(this: any, event: Event) {
				this.removeEventListener(type, handler);

				callback(event);
			}
		),
	publish: (
		name: string,
		_options?: {
			[key: string]: unknown;
		}
	) => ({
		fire: (data?: CustomEventInit<unknown>) =>
			window.dispatchEvent(
				new CustomEvent(name, {
					bubbles: true,
					composed: true,
					...data,
				})
			),
	}),
};
