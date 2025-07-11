/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import SearchBuilder from '../../../core/SearchBuilder';
import {OrderTypes} from '../../../enums/Order';
import {ExtendRequestStatus} from '../enums/SSATrials';

export default function getSSATrialsResourceURL(
	channelId: number,
	accountId: string
) {
	const url = `/o/headless-commerce-delivery-order/v1.0/channels/${channelId}/accounts/${accountId}/placed-orders?${new URLSearchParams(
		{
			filter: SearchBuilder.eq(
				'orderTypeExternalReferenceCode',
				OrderTypes.SSA_SAAS
			),
			nestedFields: 'placedOrderItems',
			sort: 'createDate:desc',
		}
	)}`;

	return url;
}

export function getExtensionStatusFromTrialSettings(trialSettings: string) {
	return (
		JSON.parse(trialSettings)?.ssaSettings?.extendRequestStatus ??
		ExtendRequestStatus.NOT_REQUESTED
	);
}
