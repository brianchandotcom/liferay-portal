/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jethr0.event.jenkins;

import com.liferay.jethr0.event.EventHandler;
import com.liferay.jethr0.util.StringUtil;

import java.util.Objects;

/**
 * @author Michael Hashimoto
 */
public interface JenkinsEventHandler extends EventHandler {

	public enum EventType {

		BUILD_COMPLETED, BUILD_STARTED, COMPUTER_BUSY, COMPUTER_IDLE,
		COMPUTER_OFFLINE, COMPUTER_ONLINE, COMPUTER_TEMPORARILY_OFFLINE,
		COMPUTER_TEMPORARILY_ONLINE, QUEUE_ITEM_ENTER_BLOCKED,
		QUEUE_ITEM_ENTER_BUILDABLE, QUEUE_ITEM_ENTER_WAITING,
		QUEUE_ITEM_LEAVE_BLOCKED, QUEUE_ITEM_LEAVE_BUILDABLE,
		QUEUE_ITEM_LEAVE_WAITING, QUEUE_ITEM_LEFT;

		public static EventType get(String eventTypeString) {
			if (StringUtil.isNullOrEmpty(eventTypeString)) {
				return null;
			}

			for (EventType eventType : values()) {
				if (Objects.equals(eventTypeString, eventType.toString())) {
					return eventType;
				}
			}

			return null;
		}

	}

}