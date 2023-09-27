/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

export const Liferay = window.Liferay || {
	BREAKPOINTS: {
		PHONE: 0,
		TABLET: 0,
	},
	ThemeDisplay: {
		getBCP47LanguageId: () => 'en-US',
		getCanonicalURL: () => window.location.href,
		getCompanyGroupId: () => 0,
		getLanguageId: () => 'en_US',
		getLayoutRelativeURL: () => '',
		getPathThemeImages: () => null,
		getPortalURL: () => window.location.origin,
		getScopeGroupId: () => 0,
		getSiteGroupId: () => 0,
		getUserId: () => '0',
	},
	Util: {
		isTablet: () => false,
		navigate: (path) => window.location.assign(path),
		openToast: (options) => alert(options),
	},
	authToken: '',
	detach: (type: any, callback: any) =>
		window.removeEventListener(type, callback),
	on: (type: any, callback: any) => window.addEventListener(type, callback),
	once: (type: any, callback: any) =>
		window.addEventListener(type, function handler() {
			this.removeEventListener(type, handler);

			callback();
		}),
	publish: (name: any) => ({
		fire: (data: any) =>
			window.dispatchEvent(
				new CustomEvent(name, {
					bubbles: true,
					composed: true,
					...data,
				})
			),
	}),
};
