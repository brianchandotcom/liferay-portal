/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
interface ILiferay {
	MarketplaceCustomerFlow: {appId: number};
	Service: Function;
	ThemeDisplay: {
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
		getUserId: () => string;
		isSignedIn: () => boolean;
	};
	Util: {
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
	detach: (
		type: keyof WindowEventMap,
		callback: EventListenerOrEventListenerObject
	) => window.removeEventListener(type, callback),
	on: (
		type: keyof WindowEventMap,
		callback: EventListenerOrEventListenerObject
	) => window.addEventListener(type, callback),
};
