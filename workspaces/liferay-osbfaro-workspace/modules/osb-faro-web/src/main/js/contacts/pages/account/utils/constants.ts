/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

export enum LifecycleStages {
	AT_RISK = 'AT_RISK',
	AWARE = 'AWARE',
	ENGAGED = 'ENGAGED',
	ESTABLISHED = 'ESTABLISHED',
	ONBOARDING = 'ONBOARDING',
	PIPELINE = 'PIPELINE',
}

type DisplayType = 'danger' | 'info' | 'secondary' | 'success' | 'warning';

export const lifecycleStagesLabelMap: Record<
	LifecycleStages,
	{displayType: DisplayType; label: string}
> = {
	[LifecycleStages.AT_RISK]: {
		displayType: 'danger',
		label: Liferay.Language.get('at-risk'),
	},
	[LifecycleStages.AWARE]: {
		displayType: 'secondary',
		label: Liferay.Language.get('aware'),
	},
	[LifecycleStages.ENGAGED]: {
		displayType: 'warning',
		label: Liferay.Language.get('engaged'),
	},
	[LifecycleStages.ESTABLISHED]: {
		displayType: 'success',
		label: Liferay.Language.get('established'),
	},
	[LifecycleStages.ONBOARDING]: {
		displayType: 'secondary',
		label: Liferay.Language.get('onboarding'),
	},
	[LifecycleStages.PIPELINE]: {
		displayType: 'info',
		label: Liferay.Language.get('pipeline'),
	},
};
