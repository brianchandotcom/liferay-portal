/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {useEffect} from 'react';
import {modalTypes} from '~/shared/actions/modals';
import * as API from '~/shared/api';
import {Modal} from '~/shared/types';
import Notification, {
	NotificationSubtypes,
	NotificationTypes,
} from '~/shared/util/records/Notification';

interface IModalNotificationRenderProps {
	close: Modal.close;
	groupId: string;
	notificationId: string;
	open: Modal.open;
}

const renderTimeZoneAdminModal = (
	modalType: Modal.modalTypes,
	{close, groupId, notificationId, open}: IModalNotificationRenderProps
) =>
	open(
		modalType,
		{
			groupId,
			notificationId,
			onClose: close,
		},
		{closeOnBlur: false}
	);

const modalNotificationStrategies = new Map<string, Function>([
	[
		NotificationSubtypes.TimeZoneAdmin,
		(params: IModalNotificationRenderProps) =>
			renderTimeZoneAdminModal(
				modalTypes.TIME_ZONE_SELECTION_MODAL,
				params
			),
	],
]);

export function useModalNotifications(
	close: Modal.close,
	groupId: string,
	open: Modal.open
): void {
	const handleRender = (notificationList: Array<Notification>): void => {
		const notificationToRender = notificationList.pop();

		if (notificationToRender) {
			const renderFn = modalNotificationStrategies.get(
				notificationToRender.subtype
			);

			const onClose = (notificationList: Array<Notification>) => {
				handleRender(notificationList);
				close();
			};

			renderFn?.({
				close: () => onClose(notificationList),
				groupId,
				notificationId: notificationToRender.id,
				open,
			});
		}
	};

	useEffect(() => {
		API.notifications
			.fetchNotifications({
				groupId,
				type: NotificationTypes.Modal,
			})
			.then(handleRender);

		// eslint-disable-next-line react-hooks/exhaustive-deps
	}, []);
}
