/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

interface IAnalytics {
	track: (key: string, data: unknown) => void;
}

declare global {
	interface Window {
		Analytics: IAnalytics;
	}
}

const LiferayAnalytics = window.Analytics;

export const AnalyticsKeys = {
	ACCCESS_CONSOLE_BUTTON: 'Access Console Button',
	APP_PURCHASE: 'App Purchase',
	CREATE_LICENSE_KEY: 'Create License Key',
	DEACTIVATE_LICENSE_KEY: 'Deactivate License Key',
	DOWNLOAD_APP: 'Download App',
	DOWNLOAD_LICENSE_KEY: 'Download License Key',
	ORDER_CREATION: 'Order Creation',
	TRIAL_CREATION: 'Trial Creation',
	VIRTUAL_URL_NOT_FOUND: 'Virtual URL not found',
} as const;

export class Analytics {
	public static track(key: keyof typeof AnalyticsKeys, data: unknown) {
		if (!LiferayAnalytics) {

			// eslint-disable-next-line no-console
			return console.debug(`Track event: '${key}'`, data);
		}

		LiferayAnalytics.track(AnalyticsKeys[key], data);
	}
}
