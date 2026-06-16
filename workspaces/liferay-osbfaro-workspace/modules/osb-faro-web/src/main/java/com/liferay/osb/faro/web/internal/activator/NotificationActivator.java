/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.osb.faro.web.internal.activator;

import com.liferay.osb.faro.service.FaroNotificationLocalService;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Geyson Silva
 */
@Component(service = NotificationActivator.class)
public class NotificationActivator {

	@Activate
	protected void activate() {
		if (log.isInfoEnabled()) {
			log.info("Cleaning up notifications");
		}

		_faroNotificationLocalService.clearDismissedNotifications();
	}

	protected static final Log log = LogFactoryUtil.getLog(
		NotificationActivator.class);

	@Reference
	private FaroNotificationLocalService _faroNotificationLocalService;

}