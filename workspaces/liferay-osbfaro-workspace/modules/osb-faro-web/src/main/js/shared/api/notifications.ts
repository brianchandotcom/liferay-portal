/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import sendRequest from '~/shared/util/request';

export const fetchNotifications = function fetchNotifications({
	groupId,
	type,
}: {
	groupId: string;
	type?: string;
}) {
	return sendRequest({
		data: {type},
		method: 'GET',
		path: `main/${groupId}/notification`,
	});
};

export const deleteNotification = function deleteNotification(
	groupId: string,
	notificationId: string
) {
	return sendRequest({
		method: 'DELETE',
		path: `main/${groupId}/notification/${notificationId}`,
	});
};

export const readNotification = function readNotification(
	groupId: string,
	notificationId: string
) {
	return sendRequest({
		method: 'POST',
		path: `main/${groupId}/notification/${notificationId}/read`,
	});
};
