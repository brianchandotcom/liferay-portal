/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

export const baseAttributes = ['account-id', 'user-id'] as const;

const KPI_DEFAULTS = {
	kpiConnectorQuartelyRelease: '100',
	kpiLowCodePublishedApps: '100',
	kpiPartnershipIntegration: '100',
	kpiProjectUsingMarketplaceApps: '100',
	kpiQuartelyReleaseApps: '100',
} as const;

const LAST_YEAR_PROJECTS_USING_MARKETPLACE_APPS_COUNT_DEFAULT = '0';

export type Properties = {
	accountId: string | null;
	kpi: typeof KPI_DEFAULTS;
	lastYearProjectsUsingMarketplaceAppsCount: string;
	userId: string | null;
};

export function getAttributes(element: HTMLElement): Properties {
	return {
		accountId: element.getAttribute('account-id'),
		kpi: KPI_DEFAULTS,
		lastYearProjectsUsingMarketplaceAppsCount:
			LAST_YEAR_PROJECTS_USING_MARKETPLACE_APPS_COUNT_DEFAULT,
		userId: element.getAttribute('user-id'),
	};
}
