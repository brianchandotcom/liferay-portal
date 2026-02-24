/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

declare module '*.svg' {
	const content: any;
	export default content;
}

declare module 'warning';

declare module '@liferay/oauth2-provider-web/client' {
	export function FromUserAgentApplication(
		userAgentApplicationId: string
	): Promise<{
		_getOrRequestToken: () => Promise<string>;
		fetch: (url: string, options?: RequestInit) => Promise<Response>;
	}>;
}
