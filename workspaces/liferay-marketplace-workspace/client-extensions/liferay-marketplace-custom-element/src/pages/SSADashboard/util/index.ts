/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

export function getSSATrialsResourceURL(channelId: number, accountId: string) {
	return `/o/headless-commerce-delivery-order/v1.0/channels/${channelId}/accounts/${accountId}/placed-orders?${new URLSearchParams(
		{
			nestedFields: 'placedOrderItems',
			sort: 'createDate:desc',
		}
	)}`;
}
