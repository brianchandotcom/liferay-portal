/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {OrderCustomFields} from '../../../enums/Order';
import {ExtendRequestStatus, TrialSettings} from '../enums/SSATrials';
import {SSASettings} from '../types';

export function getSSASettingsOrDefaultFromCustomFields(customFields: any) {
	const ssaSettingsDefault = {
		adminRequestExtend: false,
		autoExtended: false,
		duration: 0,
		extendCount: 0,
		extendRequestStatus: ExtendRequestStatus.NOT_REQUESTED,
		projectId: undefined,
	};
	if (customFields) {
		const trialSettings = customFields[OrderCustomFields.TRIAL_SETTINGS];

		const ssaSettings =
			trialSettings === ''
				? ssaSettingsDefault
				: (JSON.parse(trialSettings)[
						TrialSettings.SSA_SETTINGS
					] as SSASettings);

		return {...ssaSettingsDefault, ...ssaSettings};
	}

	return ssaSettingsDefault;
}

export function getSSATrialsResourceURL(channelId: number, accountId: string) {
	return `/o/headless-commerce-delivery-order/v1.0/channels/${channelId}/accounts/${accountId}/placed-orders?${new URLSearchParams(
		{
			nestedFields: 'placedOrderItems',
			sort: 'createDate:desc',
		}
	)}`;
}
